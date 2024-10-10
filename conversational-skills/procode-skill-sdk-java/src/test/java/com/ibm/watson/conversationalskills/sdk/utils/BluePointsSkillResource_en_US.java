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

import java.util.ListResourceBundle;

public class BluePointsSkillResource_en_US extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {
                        "amount_description", "Amount"
                }, {
                "amount_prompt", "How many BluePoints?"
        },

                {
                        "confirmation", "We sent ${amount} BluePoints to ${recipientName}."
                }, {
                "confirmation_question", "Do you really want to send ${amount} BluePoints to ${recipientName}?"
        },

                {
                        "receiver_comment_description", "Comment to be sent to the receiver"
                }, {
                "receiver_comment_prompt", "Which comment do you want to send to the receiver?"
        },

                {
                        "recipient_description", "Name of the employee to whom to send BluePoints"
                }, {
                "recipient_prompt", "To whom do you want to send BluePoints?"
        }, {
                "recipient_validation_error_template",
                "We couldn't find the employee you mentioned. Please try again."
        },

                {
                        "recipient_selector_description", "The exact employee to whom to send BluePoints"
                }, {
                "recipient_selector_prompt", "Select the employee to whom to send BluePoints:"
        }, {
                "recipient_selector_validation_error_template",
                "You need to select a recipient from the list. Please try again."
        }
        };
    };
}
