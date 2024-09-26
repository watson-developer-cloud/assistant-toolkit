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

package com.example.bluepoints.i18n;

import java.util.ListResourceBundle;

public class BluePointsSkillResource_de_DE extends ListResourceBundle {
	@Override
	protected Object[][] getContents() {
		return new Object[][] {
				{
						"amount_description", "Betrag"
				}, {
						"amount_prompt", "Wie viele BluePoints?"
				},

				{
						"confirmation", "Wir haben ${amount} BluePoints an ${recipientName} gesendet."
				}, {
						"confirmation_question", "Möchten Sie wirklich ${amount} BluePoints an ${recipientName} senden?"
				},

				{
						"receiver_comment_description", "Kommentar, der dem Empfänger gesendet werden soll"
				}, {
						"receiver_comment_prompt", "Welchen Kommentar möchten Sie dem Empfänger senden?"
				},

				{
						"recipient_description", "Name des Mitarbeiters, dem BluePoints gesendet werden soll"
				}, {
						"recipient_prompt", "Wem möchten Sie BluePoints senden?"
				}, {
						"recipient_validation_error_template",
						"Wir konnten den von Ihnen genannten Mitarbeiter nicht finden. Bitte versuchen Sie es erneut."
				},

				{
						"recipient_selector_description",
						"Der genaue Mitarbeiter, dem BluePoints gesendet werden sollen"
				}, {
						"recipient_selector_prompt",
						"Wählen Sie den Mitarbeiter, dem BluePoints gesendet werden sollen:"
				}, {
						"recipient_selector_validation_error_template",
						"Sie müssen einen Empfänger aus der Liste auswählen. Bitte versuchen Sie es erneut."
				}
		};
	};
}
