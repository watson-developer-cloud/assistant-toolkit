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

package com.ibm.watson.conversationalskills.sdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.conversationalskills.model.ConversationalResponseGeneric;
import com.ibm.watson.conversationalskills.model.ConversationalSkillOutput;
import com.ibm.watson.conversationalskills.model.ConversationalSkillStateOutput;
import com.ibm.watson.conversationalskills.model.EntitySchema;
import com.ibm.watson.conversationalskills.model.OrchestrationRequest;
import com.ibm.watson.conversationalskills.model.OrchestrationRequest.ConfirmationEventEnum;
import com.ibm.watson.conversationalskills.model.OrchestrationResponse;
import com.ibm.watson.conversationalskills.model.OrchestrationResponseResolver;
import com.ibm.watson.conversationalskills.model.ResponseTypeSlotsConfirmation;
import com.ibm.watson.conversationalskills.model.SlotState;

public class SkillOrchestrator {

	private static final Logger LOG = Logger.getLogger(Skill.class);

	public OrchestrationResponse orchestrate(Skill skill, OrchestrationRequest orchestrationRequest) throws Exception {
		if (orchestrationRequest == null) {
			return new OrchestrationResponse();
		}

		var orchestrationResponse = createOrchestrationResponse();
		var state = new State(extractLocale(orchestrationRequest).orElse(Locale.forLanguageTag("en-US")),
				orchestrationRequest.getState().getLocalVariables(),
				orchestrationRequest.getState().getSessionVariables());

		var resourceBundle = skill.getResourceBundle(state.getLocale());

		if (orchestrationRequest.getConfirmationEvent() == ConfirmationEventEnum.CANCELLED) {
			createSkillCancelResponse(orchestrationResponse);
		} else if (orchestrationRequest.getConfirmationEvent() == ConfirmationEventEnum.CONFIRMED) {
			createSkillCompleteResponse(skill, resourceBundle, orchestrationResponse, state);
		} else {
			initializeSlotHandlers(skill, orchestrationRequest, state);

			for (var slot : orchestrationRequest.getSlots()) {
				if (slot.getEvent() != null) {
					LOG.debug("Processing slot: " + slot.getName() + " (event: " + slot.getEvent() + ")");

					var slotHandler = getSlotHandlerForSlotState(skill, slot);

					switch (slot.getEvent()) {
					case FILL -> slotHandler.onFill(state);
					case REFINE -> slotHandler.onRefine(state);
					case REPAIR -> slotHandler.onRepair(state);
					}
				} else {
					LOG.debug("Processing slot: " + slot.getName() + " (skipped)");
				}
			}

			var conversationalResponseGeneric = new ConversationalResponseGeneric();
			conversationalResponseGeneric.setResponseType("slots");

			if (Arrays.stream(skill.getSlotHandlers()).filter(slotHandler -> {
				return !slotHandler.isHidden();
			}).allMatch(slotHandler -> {
				return (slotHandler.getNormalizedValue() != null)
						&& (slotHandler.getSlotInFlight().getValidationError() == null);
			})) {
				var confirmationMessage = skill.getConfirmationMessage(resourceBundle, state);

				if (confirmationMessage != null) {
					var responseTypeSlotsConfirmation = new ResponseTypeSlotsConfirmation();
					responseTypeSlotsConfirmation.setPrompt(confirmationMessage);

					conversationalResponseGeneric.setConfirmation(responseTypeSlotsConfirmation);
					createUserInteractionResponse(orchestrationResponse, conversationalResponseGeneric, state);
				} else {
					createSkillCompleteResponse(skill, resourceBundle, orchestrationResponse, state);
				}
			} else {
				conversationalResponseGeneric.setSlots(Arrays.stream(skill.getSlotHandlers()).filter(slotHandler -> {
					return !slotHandler.isHidden();
				}).map(slotHandler -> {
					return slotHandler.getSlotInFlight();
				}).toList());

				createUserInteractionResponse(orchestrationResponse, conversationalResponseGeneric, state);
			}
		}

		return orchestrationResponse;
	}

	private OrchestrationResponse createOrchestrationResponse() {
		var orchestrationResponse = new OrchestrationResponse();
		orchestrationResponse.setOutput(new ConversationalSkillOutput());
		orchestrationResponse.setResolver(new OrchestrationResponseResolver());
		orchestrationResponse.setState(new ConversationalSkillStateOutput());

		return orchestrationResponse;
	}

	private void createSkillCancelResponse(OrchestrationResponse orchestrationResponse) {
		var conversationalResponseGeneric = new ConversationalResponseGeneric();
		conversationalResponseGeneric.setResponseType("text");
		conversationalResponseGeneric.setSlots(null);
		conversationalResponseGeneric.setText("The skill was cancelled.");

		orchestrationResponse.getOutput().addGenericItem(conversationalResponseGeneric);
		orchestrationResponse.getResolver().put("type", "skill_cancel");
	}

	private void createSkillCompleteResponse(Skill skill, ResourceBundle resourceBundle,
			OrchestrationResponse orchestrationResponse, State state) {

		orchestrationResponse.getOutput().addGenericItem(skill.onConfirmed(resourceBundle, state));
		orchestrationResponse.getResolver().put("type", "skill_complete");
	}

	private void createUserInteractionResponse(OrchestrationResponse orchestrationResponse,
			ConversationalResponseGeneric conversationalResponseGeneric, State state) {

		orchestrationResponse.getOutput().addGenericItem(conversationalResponseGeneric);
		orchestrationResponse.getResolver().put("type", "user_interaction");
		orchestrationResponse.getState().setLocalVariables(new TreeMap<>(state.getLocalVariables()));
		orchestrationResponse.getState().setSessionVariables(new TreeMap<>(state.getSessionVariables()));
	}

	private Optional<Locale> extractLocale(OrchestrationRequest orchestrationRequest) {
		Optional<Locale> result = Optional.empty();

		if (orchestrationRequest.getContext().getIntegrations() instanceof Map) {
			@SuppressWarnings("rawtypes")
			var integrationsMap = (Map) orchestrationRequest.getContext().getIntegrations();

			if (integrationsMap.containsKey("chat") && (integrationsMap.get("chat") instanceof Map)) {
				var chat = (Map<?, ?>) integrationsMap.get("chat");

				if (chat.containsKey("browser_info") && (chat.get("browser_info") instanceof Map)) {
					var browserInfo = (Map<?, ?>) chat.get("browser_info");

					if (browserInfo.containsKey("language") && browserInfo.get("language") instanceof String) {
						result = Optional.of(Locale.forLanguageTag((String) browserInfo.get("language")));
					}
				}
			}
		}

		return result;
	}

	private SlotHandler getSlotHandlerForSlotState(Skill skill, SlotState slot) throws Exception {
		var slotHandler = Arrays.stream(skill.getSlotHandlers()).filter(e -> {
			return e.getSlotInFlight().getName().equals(slot.getName());
		}).findFirst();

		if (!slotHandler.isPresent()) {
			throw new Exception("Unknown slot: " + slot.getName());
		}

		return slotHandler.get();
	}

	private void initializeSlotHandlers(Skill skill, OrchestrationRequest orchestrationRequest, State state)
			throws Exception {

		var visible_slots = initializeVisibleSlots(skill, state);

		for (var slotHandler : skill.getSlotHandlers()) {
			slotHandler.initializeLanguage(skill.getResourceBundle(state.getLocale()));

			if (visible_slots.contains(slotHandler.getSlotInFlight().getName())) {
				var schema = orchestrationRequest.getState().getLocalVariables()
						.get(slotHandler.getSlotInFlight().getName() + "_schema");

				if (schema != null) {
					slotHandler.getSlotInFlight()
							.setSchema(new ObjectMapper().convertValue(schema, EntitySchema.class));
				}

				slotHandler.showWithoutRegistration();
			}
		}

		for (var slot : orchestrationRequest.getSlots()) {
			var slotHandler = getSlotHandlerForSlotState(skill, slot);

			slotHandler.getSlotInFlight().setValue(slot.getValue());
			slotHandler.setEvent(slot.getEvent());
		}
	}

	private ArrayList<String> initializeVisibleSlots(Skill skill, State state) {
		ArrayList<String> visible_slots = null;

		if (state.getLocalVariables().containsKey("visible_slots")) {
			visible_slots = new ArrayList<>(Arrays.asList(
					new ObjectMapper().convertValue(state.getLocalVariables().get("visible_slots"), String[].class)));
		} else {
			visible_slots = new ArrayList<String>();

			for (var slotHandler : Arrays.stream(skill.getSlotHandlers()).filter(slotHandler -> {
				return slotHandler.isShownByDefault();
			}).collect(Collectors.toList())) {
				if (!visible_slots.contains(slotHandler.getSlotInFlight().getName())) {
					visible_slots.add(slotHandler.getSlotInFlight().getName());
				}
			}

			Collections.sort(visible_slots);
			state.getLocalVariables().put("visible_slots", visible_slots);
		}

		return visible_slots;
	}
}
