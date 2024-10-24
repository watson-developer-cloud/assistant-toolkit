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

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ibm.watson.conversationalskills.model.SlotInFlight;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

class SlotHandlerTest {
    private final String SLOT_NAME = "tester";

    class TestSlotHandler extends SlotHandler {
        public TestSlotHandler() {
            super(new SlotInFlight().name(SLOT_NAME).type(SlotInFlight.TypeEnum.NUMBER));
        }
        @Override
        public void onFill(State state) {
            state.getLocalVariables().put("test", getNormalizedValue());
        }

        @Override
        public void onRepair(State state) {
            onFill(state);
        }
    }

    private final SlotHandler slotHandler = new TestSlotHandler();

    @Test
    void testHideSlotName() {
        Locale locale = Locale.US;
        ArrayList<String> visibleSlots = new ArrayList<>();
        visibleSlots.add("test1");
        visibleSlots.add(SLOT_NAME);
        visibleSlots.add("test3");
        Map<String, Object> localVariables = new HashMap<>();
        localVariables.put("visible_slots", visibleSlots);
        Map<String, Object> sessionVariables = Map.of("session_test", new Object());

        State state = new State(locale, localVariables, sessionVariables);

        slotHandler.hide(state);

        ArrayList<String> slots = (ArrayList<String>) state.getLocalVariables().get("visible_slots");

        assertEquals(slots.get(1), "test3");
    }

    @Test
    void testNoHideSlotName() {
        Locale locale = Locale.US;
        ArrayList<String> visibleSlots = new ArrayList<>();
        visibleSlots.add("test1");
        visibleSlots.add("test2");
        visibleSlots.add("test3");
        Map<String, Object> localVariables = new HashMap<>();
        localVariables.put("visible_slots", visibleSlots);
        Map<String, Object> sessionVariables = Map.of("session_test", new Object());

        State state = new State(locale, localVariables, sessionVariables);

        slotHandler.hide(state);

        ArrayList<String> slots = (ArrayList<String>) state.getLocalVariables().get("visible_slots");

        assertEquals(slots.get(1), "test2");
    }

    @Test
    void testShowSlotNameExists() {
        Locale locale = Locale.US;
        ArrayList<String> visibleSlots = new ArrayList<>();
        visibleSlots.add("test1");
        visibleSlots.add(SLOT_NAME);
        visibleSlots.add("test3");
        Map<String, Object> localVariables = new HashMap<>();
        localVariables.put("visible_slots", visibleSlots);
        Map<String, Object> sessionVariables = Map.of("session_test", new Object());

        State state = new State(locale, localVariables, sessionVariables);

        slotHandler.show(state);

        ArrayList<String> slots = (ArrayList<String>) state.getLocalVariables().get("visible_slots");

        assertEquals(slots.get(1), SLOT_NAME);
    }

    @Test
    void testShowSlotNameDoesNotExist() {
        Locale locale = Locale.US;
        ArrayList<String> visibleSlots = new ArrayList<>();
        visibleSlots.add("test1");
        visibleSlots.add("test2");
        visibleSlots.add("test3");
        Map<String, Object> localVariables = new HashMap<>();
        localVariables.put("visible_slots", visibleSlots);
        Map<String, Object> sessionVariables = Map.of("session_test", new Object());

        State state = new State(locale, localVariables, sessionVariables);

        slotHandler.show(state);

        ArrayList<String> slots = (ArrayList<String>) state.getLocalVariables().get("visible_slots");

        assertEquals(slots.get(1), "test2");
        assertEquals(slots.get(3), SLOT_NAME);
    }
}
