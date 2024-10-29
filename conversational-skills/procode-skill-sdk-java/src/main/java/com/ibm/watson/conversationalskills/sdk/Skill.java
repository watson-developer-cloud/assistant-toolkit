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

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.ibm.watson.conversationalskills.model.*;

public abstract class Skill {

	/**
	 * Returns a confirmation message that watsonx Assistant presents to the user
	 * once all slots are filled
	 *
	 * Once the user confirmed, the onConfirmed() method is called. If this method
	 * returns null, watsonx Assistant does not ask for a confirmation once all
	 * slots are filled. Instead, the onConfirmed() method is directly called.
	 *
	 * @param resourceBundle resource bundle for i18n
	 * @param state          state exchanged with watsonx Assistant
	 * @return confirmation message
	 */
	public String getConfirmationMessage(ResourceBundle resourceBundle, State state) {
		return null;
	}

	/**
	 * Returns the creation timestamp of the skill
	 *
	 * @return creation timestamp of the skill
	 */
	public abstract ZonedDateTime getCreationTimestamp();

	/**
	 * Returns the description of the skill.
	 *
	 * The description is shown in the watsonx Assistant UI.
	 *
	 * @return description of the skill
	 */
	public abstract String getDescription();

	/**
	 * Return the ID of the conversational skill.
	 *
	 * The ID is part of the conversational skill URL:
	 *
	 * /providers/{provider_id}/conversational_skills/ ↩
	 * {conversational_skill_id}/orchestrate
	 *
	 * @return ID of the conversational skill
	 */
	public abstract String getID();

	/**
	 * Returns skill metadata.
	 *
	 * @return skill metadata
	 */
	public Object getMetadata() {
		return null;
	}

	/**
	 * Returns the modification timestamp of the skill
	 *
	 * @return modification timestamp of the skill
	 */
	public abstract ZonedDateTime getModificationTimestamp();

	/**
	 * Returns the skill name
	 *
	 * @return skill name
	 */
	public abstract String getName();

	/**
	 * Returns the resource bundle for the given locale
	 *
	 * @param locale locale for which a resource bundle shall be returned
	 * @return resource bundle
	 */
	public abstract ResourceBundle getResourceBundle(Locale locale);

	/**
	 * Returns the slot handlers of the skills.
	 *
	 * The position of the slot handlers within the array is important. It
	 * determines the order in which watsonx Assistant asks the user for the values
	 * of the corresponding slots.
	 *
	 * @return
	 */
	public abstract SlotHandler[] getSlotHandlers();

	/**
	 * Callback invoked when all slots were filled by watsonx Assistant
	 *
	 * If the skill provides a confirmation message, this method is called after the
	 * user's confirmation.
	 *
	 * @param resourceBundle resource bundle for i18n
	 * @param state          state exchanged with watsonx Assistant
	 * @return
	 */
	public abstract ConversationalResponseGeneric onConfirmed(ResourceBundle resourceBundle, State state);

	public ConversationalSkill formatForListSkills() {
		var conversationalSkill = new ConversationalSkill();
		conversationalSkill.setCreated(this.getCreationTimestamp() != null ? ZonedDateTime
				.ofInstant(this.getCreationTimestamp().toInstant(), ZoneId.of("UTC"))
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnn'Z'")) : null);

		conversationalSkill.setDescription(this.getDescription());
		conversationalSkill.setId(this.getID());
		conversationalSkill.setMetadata(this.getMetadata());
		conversationalSkill.setModified(this.getModificationTimestamp() != null ? ZonedDateTime
				.ofInstant(this.getModificationTimestamp().toInstant(), ZoneId.of("UTC"))
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnn'Z'")) : null);

		conversationalSkill.setName(this.getName());

		return conversationalSkill;
	};

	public GetSkillResponse formatForGetSkill() {
		var getSkillResponse = new GetSkillResponse();
		getSkillResponse.setCreated(this.getCreationTimestamp() != null ? ZonedDateTime
				.ofInstant(this.getCreationTimestamp().toInstant(), ZoneId.of("UTC"))
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnn'Z'")) : null);

		getSkillResponse.setDescription(this.getDescription());
		getSkillResponse.setId(this.getID());
		getSkillResponse.setMetadata(this.getMetadata());
		getSkillResponse.setModified(this.getModificationTimestamp() != null ? ZonedDateTime
				.ofInstant(this.getModificationTimestamp().toInstant(), ZoneId.of("UTC"))
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnn'Z'")) : null);

		getSkillResponse.setName(this.getName());

		var slotHandlers = this.getSlotHandlers();
		var inputSlots = new ArrayList<ConversationalSkillInputSlot>();
		Arrays.stream(slotHandlers).forEach(slotHandler -> {
			var slot = slotHandler.getSlotInFlight();
			var inputSlot = new ConversationalSkillInputSlot()
					.name(slot.getName())
					.description(slot.getDescription())
					.type(ConversationalSkillInputSlot.TypeEnum.fromValue(slot.getType().getValue()));

			inputSlots.add(inputSlot);
		});
		getSkillResponse.setInput(new GetSkillResponseAllOfInput().slots(inputSlots));

		return getSkillResponse;
	}
}
