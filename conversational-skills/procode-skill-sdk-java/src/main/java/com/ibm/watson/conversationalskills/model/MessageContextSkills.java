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


package com.ibm.watson.conversationalskills.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Context data specific to particular skills used by the assistant.
 */
@JsonPropertyOrder({
  MessageContextSkills.JSON_PROPERTY_MAIN_SKILL,
  MessageContextSkills.JSON_PROPERTY_ACTIONS_SKILL,
  MessageContextSkills.JSON_PROPERTY_CONVERSATIONAL_SKILLS,
  MessageContextSkills.JSON_PROPERTY_ACTIVE_SKILL
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class MessageContextSkills {
  public static final String JSON_PROPERTY_MAIN_SKILL = "main skill";
  private MessageContextDialogSkill mainSkill;

  public static final String JSON_PROPERTY_ACTIONS_SKILL = "actions skill";
  private MessageContextActionSkill actionsSkill;

  public static final String JSON_PROPERTY_CONVERSATIONAL_SKILLS = "conversational skills";
  private MessageContextConversationalSkills conversationalSkills;

  /**
   * When a conversation is underway, and it hasn&#39;t yet finished, the Assistant uses this attribute to track which skill should start processing the next user message in the conversation. At appropriate milestones in a conversation, e.g., initial routing, digression, return from digression, the Assistant updates this attribute to indicate which skill should process the next user message.
   */
  public enum ActiveSkillEnum {
    MAIN_SKILL("main skill"),
    
    ACTIONS_SKILL("actions skill"),
    
    CONVERSATIONAL_SKILLS("conversational_skills");

    private String value;

    ActiveSkillEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ActiveSkillEnum fromValue(String value) {
      for (ActiveSkillEnum b : ActiveSkillEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }
  }

  public static final String JSON_PROPERTY_ACTIVE_SKILL = "active_skill";
  private ActiveSkillEnum activeSkill;

  public MessageContextSkills() {
  }

  public MessageContextSkills mainSkill(MessageContextDialogSkill mainSkill) {
    
    this.mainSkill = mainSkill;
    return this;
  }

  /**
   * Get mainSkill
   * @return mainSkill
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MAIN_SKILL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public MessageContextDialogSkill getMainSkill() {
    return mainSkill;
  }


  @JsonProperty(JSON_PROPERTY_MAIN_SKILL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMainSkill(MessageContextDialogSkill mainSkill) {
    this.mainSkill = mainSkill;
  }

  public MessageContextSkills actionsSkill(MessageContextActionSkill actionsSkill) {
    
    this.actionsSkill = actionsSkill;
    return this;
  }

  /**
   * Get actionsSkill
   * @return actionsSkill
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ACTIONS_SKILL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public MessageContextActionSkill getActionsSkill() {
    return actionsSkill;
  }


  @JsonProperty(JSON_PROPERTY_ACTIONS_SKILL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setActionsSkill(MessageContextActionSkill actionsSkill) {
    this.actionsSkill = actionsSkill;
  }

  public MessageContextSkills conversationalSkills(MessageContextConversationalSkills conversationalSkills) {
    
    this.conversationalSkills = conversationalSkills;
    return this;
  }

  /**
   * Get conversationalSkills
   * @return conversationalSkills
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CONVERSATIONAL_SKILLS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public MessageContextConversationalSkills getConversationalSkills() {
    return conversationalSkills;
  }


  @JsonProperty(JSON_PROPERTY_CONVERSATIONAL_SKILLS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setConversationalSkills(MessageContextConversationalSkills conversationalSkills) {
    this.conversationalSkills = conversationalSkills;
  }

  public MessageContextSkills activeSkill(ActiveSkillEnum activeSkill) {
    
    this.activeSkill = activeSkill;
    return this;
  }

  /**
   * When a conversation is underway, and it hasn&#39;t yet finished, the Assistant uses this attribute to track which skill should start processing the next user message in the conversation. At appropriate milestones in a conversation, e.g., initial routing, digression, return from digression, the Assistant updates this attribute to indicate which skill should process the next user message.
   * @return activeSkill
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ACTIVE_SKILL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ActiveSkillEnum getActiveSkill() {
    return activeSkill;
  }


  @JsonProperty(JSON_PROPERTY_ACTIVE_SKILL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setActiveSkill(ActiveSkillEnum activeSkill) {
    this.activeSkill = activeSkill;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageContextSkills messageContextSkills = (MessageContextSkills) o;
    return Objects.equals(this.mainSkill, messageContextSkills.mainSkill) &&
        Objects.equals(this.actionsSkill, messageContextSkills.actionsSkill) &&
        Objects.equals(this.conversationalSkills, messageContextSkills.conversationalSkills) &&
        Objects.equals(this.activeSkill, messageContextSkills.activeSkill);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mainSkill, actionsSkill, conversationalSkills, activeSkill);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MessageContextSkills {\n");
    sb.append("    mainSkill: ").append(toIndentedString(mainSkill)).append("\n");
    sb.append("    actionsSkill: ").append(toIndentedString(actionsSkill)).append("\n");
    sb.append("    conversationalSkills: ").append(toIndentedString(conversationalSkills)).append("\n");
    sb.append("    activeSkill: ").append(toIndentedString(activeSkill)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

