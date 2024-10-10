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

package com.ibm.watson.conversationalskills.sdk.utils;

import com.ibm.watson.conversationalskills.model.SlotInFlight;
import com.ibm.watson.conversationalskills.sdk.SlotHandler;
import com.ibm.watson.conversationalskills.sdk.State;

public class AmountSlotHandler extends SlotHandler {
    private static String SLOT_NAME = "amount";

    public AmountSlotHandler(String validationError) {
        super(new SlotInFlight().name(SLOT_NAME).type(SlotInFlight.TypeEnum.NUMBER).validationError(validationError));
    }

    @Override
    public boolean isShownByDefault() {
        return true;
    }

    @Override
    public void onFill(State state) {
        state.getLocalVariables().put("amount", getNormalizedValue());
    }

    @Override
    public void onRepair(State state) {
        onFill(state);
    }
}
