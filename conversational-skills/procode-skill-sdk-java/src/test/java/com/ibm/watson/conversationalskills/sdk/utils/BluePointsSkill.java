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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


import com.ibm.watson.conversationalskills.model.ConversationalResponseGeneric;
import com.ibm.watson.conversationalskills.sdk.Skill;
import com.ibm.watson.conversationalskills.sdk.SlotHandler;
import com.ibm.watson.conversationalskills.sdk.State;


public class BluePointsSkill extends Skill {
    private String confirmationMessage = "not_yet";
    private AmountSlotHandler amountSlotHandler = new AmountSlotHandler(null);

    public void setAmountSlotHandlerValidationError(String validationError) {
        this.amountSlotHandler = new AmountSlotHandler(validationError);
    }

    public void setConfirmationMessage(String msg) {
        this.confirmationMessage = msg;
    }
    @Override
    public String getConfirmationMessage(ResourceBundle resourceBundle, State state) {
        return this.confirmationMessage;
    }

    public ZonedDateTime getCreationTimestamp() {
        return ZonedDateTime.of(2024, 7, 16, 0, 0, 0, 0, ZoneId.of("UTC"));
    }

    @Override
    public String getDescription() {
        return "This feature allows an initiator to send BluePoints";
    }

    @Override
    public String getID() {
        return "bluepoints";
    }

    @Override
    public ZonedDateTime getModificationTimestamp() {
        return getCreationTimestamp();
    }

    @Override
    public String getName() {
        return "Send BluePoints";
    }

    @Override
    public ResourceBundle getResourceBundle(Locale locale) {
        return ResourceBundle.getBundle(BluePointsSkillResource_en_US.class.getName(), locale);
    }

    @Override
    public SlotHandler[] getSlotHandlers() {
        return new SlotHandler[] { this.amountSlotHandler };
    }

    @Override
    public ConversationalResponseGeneric onConfirmed(ResourceBundle resourceBundle, State state) {
        var conversationalResponseGeneric = new ConversationalResponseGeneric();
        conversationalResponseGeneric.setResponseType("text");
        conversationalResponseGeneric
                .setText("text");

        return conversationalResponseGeneric;
    }
}


