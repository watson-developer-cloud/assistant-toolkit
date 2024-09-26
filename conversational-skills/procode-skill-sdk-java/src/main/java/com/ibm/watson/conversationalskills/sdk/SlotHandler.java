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
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.conversationalskills.model.SlotInFlight;
import com.ibm.watson.conversationalskills.model.SlotState;

public abstract class SlotHandler {
	public SlotHandler(SlotInFlight slot) {
		this.hidden = true;
		this.slot = slot;
	}

	public SlotState.EventEnum getEvent() {
		return this.event;
	}

	public SlotInFlight getSlotInFlight() {
		return this.slot;
	}

	public void hide(State state) {
		this.hidden = true;

		if (state.getLocalVariables().containsKey("visible_slots")) {
			var visible_slots = new ArrayList<>(Arrays.asList(
					new ObjectMapper().convertValue(state.getLocalVariables().get("visible_slots"), String[].class)));

			if (visible_slots.remove(getSlotInFlight().getName())) {
				state.getLocalVariables().put("visible_slots", visible_slots);
			}
		}
	}

	public void initializeLanguage(ResourceBundle resourceBundle) {
		var slot = getSlotInFlight();
		var description = slot.getName() + "_description";
		var prompt = slot.getName() + "_prompt";

		if (description != null) {
			slot.setDescription(resourceBundle.getString(description));
		}

		if (prompt != null) {
			slot.setPrompt(resourceBundle.getString(prompt));
		}
	}

	public boolean isHidden() {
		return this.hidden;
	}

	public boolean isShownByDefault() {
		return false;
	}

	public abstract void onFill(State state);

	public void onRefine(State state) {
	}

	public abstract void onRepair(State state);

	public void setEvent(SlotState.EventEnum event) {
		this.event = event;
	}

	public void show(State state) {
		this.hidden = false;

		var visible_slots = state.getLocalVariables().containsKey("visible_slots")
				? new ArrayList<>(Arrays.asList(new ObjectMapper()
						.convertValue(state.getLocalVariables().get("visible_slots"), String[].class)))
				: new ArrayList<String>();

		if (!visible_slots.contains(getSlotInFlight().getName())) {
			visible_slots.add(getSlotInFlight().getName());
			Collections.sort(visible_slots);
			state.getLocalVariables().put("visible_slots", visible_slots);
		}
	}

	public void showWithoutRegistration() {
		this.hidden = false;
	}

	protected String getNormalizedValue() {
		var value = getSlotInFlight().getValue();

		return (value != null) ? value.getNormalized() : null;
	}

	private SlotState.EventEnum event;
	private boolean hidden;
	private SlotInFlight slot;
}
