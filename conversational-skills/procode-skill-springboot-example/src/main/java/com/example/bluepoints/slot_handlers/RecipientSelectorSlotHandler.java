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
import com.ibm.watson.conversationalskills.model.SlotInFlight;
import com.ibm.watson.conversationalskills.sdk.SlotHandler;
import com.ibm.watson.conversationalskills.sdk.State;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
@RecipientSelector
public class RecipientSelectorSlotHandler extends SlotHandler {
	private static String SLOT_NAME = "recipient_selector";

	public RecipientSelectorSlotHandler() {
		super(new SlotInFlight().name(SLOT_NAME).type(SlotInFlight.TypeEnum.ENTITY));
	}

	@Override
	public void onFill(State state) {
		var users = state.getLocalVariable("recipient_lookup_result", User[].class);
		var selectedPrediction = searchUser(users, getNormalizedValue());

		if (selectedPrediction == null) {
			throw new AssertionError("selectedPrediction == null");
		}

		hide(state);
		state.getLocalVariables().put("recipient", selectedPrediction);
		state.getLocalVariables().remove("recipient_lookup_result");
		state.getLocalVariables().remove("recipient_selector_schema");
	}

	@Override
	public void onRepair(State state) {
	}

	public User searchUser(User[] users, String value) {
		User selectedUser = null;

		for (var user : users) {
			if (value.equals(user.name + " (" + user.email + ")")) {
				selectedUser = user;

				break;
			}
		}

		return selectedUser;
	}

	@Inject
	@Recipient
	SlotHandler recipientSlotHandler;
}
