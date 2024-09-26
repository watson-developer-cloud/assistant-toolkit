/*
 Copyright 2024 IBM Corporation

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.example.bluepoints.slot_handlers;

import com.example.bluepoints.data.User;
import com.example.bluepoints.qualifiers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.conversationalskills.model.EntitySchema;
import com.ibm.watson.conversationalskills.model.EntityValue;
import com.ibm.watson.conversationalskills.model.SlotInFlight;
import com.ibm.watson.conversationalskills.model.SlotState;
import com.ibm.watson.conversationalskills.sdk.SlotHandler;
import com.ibm.watson.conversationalskills.sdk.State;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@RequestScoped
@Recipient
public class RecipientSlotHandler extends SlotHandler {
	private static final Logger LOG = Logger.getLogger(RecipientSlotHandler.class);
	private static String SLOT_NAME = "recipient";

	public RecipientSlotHandler() {
		super(new SlotInFlight().name(SLOT_NAME).type(SlotInFlight.TypeEnum.STRING));
	}

	@Override
	public boolean isShownByDefault() {
		return true;
	}

	@Override
	public void onFill(State state) {
		requestPersonSearch(state);
	}

	@Override
	public void onRepair(State state) {
		if (recipientSelectorSlotHandler.getEvent() != SlotState.EventEnum.FILL) {
			var user = state.getLocalVariables().containsKey(getSlotInFlight().getName())
					? new ObjectMapper().convertValue(state.getLocalVariables().get("recipient"), User.class)
					: null;

			if ((user == null) || (!getNormalizedValue().equals(user.name)
					&& !getNormalizedValue().equals(user.name + " (" + user.email + ")"))) {

				LOG.info("Value does not correspond to stored recipient → searching persons");
				state.getLocalVariables().remove("recipient");
				requestPersonSearch(state);
			} else {
				// watsonx Assistant may set the "recipient" slot with one of the following
				// values while filling other slots → ignore:
				//
				// - {first name} {last name}
				// - {first name} {last name} ({e-mail})
				LOG.info("Ignoring repair event as value corresponds to stored recipient");
			}
		} else {
			LOG.info("Ignoring repair event as corresponding selector was filled");
		}
	}

	private void initializeRecipientSelector(User[] users, State state) {
		var recipientSelectorSlot = recipientSelectorSlotHandler.getSlotInFlight();

		var entitySchema = new EntitySchema();
		entitySchema.setEntity(recipientSelectorSlot.getName());

		for (var prediction : users) {
			var entityValue = new EntityValue();
			entityValue.setValue(prediction.name + " (" + prediction.email + ")");

			entitySchema.addValuesItem(entityValue);
		}

		recipientSelectorSlot.setSchema(entitySchema);
		// outdated value may have been received by watsonx Assistant due to a repair
		recipientSelectorSlot.setValue(null);
		state.getLocalVariables().put("recipient_lookup_result", users);
		state.getLocalVariables().put(this.recipientSelectorSlotHandler.getSlotInFlight().getName() + "_schema",
				entitySchema);
	}

	private void requestPersonSearch(State state) {
		User[] availableUsers = new User[] {
				new User("USER1", "John Miller", "john.miller@example.com"),
				new User("USER2", "Mary Miller", "mary.miller@example.com"),
				new User("USER3", "Peter Miller", "peter.miller@example.com")
		};

		User[] users = null;

		if (getNormalizedValue().equals("Miller")) {
			users = availableUsers;
		} else {
			var userList = Arrays.stream(availableUsers).filter(user -> {
				return user.name.equals(getNormalizedValue());
			}).collect(Collectors.toList());

			users = userList.toArray(new User[userList.size()]);
		}

		if (users.length == 0) {
			getSlotInFlight().setValidationError(ResourceBundle
					.getBundle(com.example.bluepoints.i18n.BluePointsSkillResource.class.getName(), state.getLocale())
					.getString("recipient_validation_error_template"));

			getSlotInFlight().setValue(null);
		} else {
			initializeRecipientSelector(users, state);
			this.recipientSelectorSlotHandler.show(state);
		}
	}

	@Inject
	@RecipientSelector
	SlotHandler recipientSelectorSlotHandler;
}
