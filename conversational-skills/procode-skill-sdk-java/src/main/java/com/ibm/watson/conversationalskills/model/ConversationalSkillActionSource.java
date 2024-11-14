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
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Source properties of the conversational skill action.
 */
@JsonPropertyOrder({
  ConversationalSkillActionSource.JSON_PROPERTY_TYPE,
  ConversationalSkillActionSource.JSON_PROPERTY_ACTION,
  ConversationalSkillActionSource.JSON_PROPERTY_ACTION_TITLE,
  ConversationalSkillActionSource.JSON_PROPERTY_IS_SKILL_BASED,
  ConversationalSkillActionSource.JSON_PROPERTY_STEP
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class ConversationalSkillActionSource {
  public static final String JSON_PROPERTY_TYPE = "type";
  private String type;

  public static final String JSON_PROPERTY_ACTION = "action";
  private String action;

  public static final String JSON_PROPERTY_ACTION_TITLE = "action_title";
  private String actionTitle;

  public static final String JSON_PROPERTY_IS_SKILL_BASED = "is_skill_based";
  private Boolean isSkillBased;

  public static final String JSON_PROPERTY_STEP = "step";
  private String step;

  public ConversationalSkillActionSource() {
  }

  public ConversationalSkillActionSource type(String type) {
    
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getType() {
    return type;
  }


  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setType(String type) {
    this.type = type;
  }

  public ConversationalSkillActionSource action(String action) {
    
    this.action = action;
    return this;
  }

  /**
   * Get action
   * @return action
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ACTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getAction() {
    return action;
  }


  @JsonProperty(JSON_PROPERTY_ACTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAction(String action) {
    this.action = action;
  }

  public ConversationalSkillActionSource actionTitle(String actionTitle) {
    
    this.actionTitle = actionTitle;
    return this;
  }

  /**
   * Get actionTitle
   * @return actionTitle
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ACTION_TITLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getActionTitle() {
    return actionTitle;
  }


  @JsonProperty(JSON_PROPERTY_ACTION_TITLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setActionTitle(String actionTitle) {
    this.actionTitle = actionTitle;
  }

  public ConversationalSkillActionSource isSkillBased(Boolean isSkillBased) {
    
    this.isSkillBased = isSkillBased;
    return this;
  }

  /**
   * Get isSkillBased
   * @return isSkillBased
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_IS_SKILL_BASED)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getIsSkillBased() {
    return isSkillBased;
  }


  @JsonProperty(JSON_PROPERTY_IS_SKILL_BASED)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIsSkillBased(Boolean isSkillBased) {
    this.isSkillBased = isSkillBased;
  }

  public ConversationalSkillActionSource step(String step) {
    
    this.step = step;
    return this;
  }

  /**
   * Get step
   * @return step
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STEP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getStep() {
    return step;
  }


  @JsonProperty(JSON_PROPERTY_STEP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStep(String step) {
    this.step = step;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConversationalSkillActionSource conversationalSkillActionSource = (ConversationalSkillActionSource) o;
    return Objects.equals(this.type, conversationalSkillActionSource.type) &&
        Objects.equals(this.action, conversationalSkillActionSource.action) &&
        Objects.equals(this.actionTitle, conversationalSkillActionSource.actionTitle) &&
        Objects.equals(this.isSkillBased, conversationalSkillActionSource.isSkillBased) &&
        Objects.equals(this.step, conversationalSkillActionSource.step);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, action, actionTitle, isSkillBased, step);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConversationalSkillActionSource {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    actionTitle: ").append(toIndentedString(actionTitle)).append("\n");
    sb.append("    isSkillBased: ").append(toIndentedString(isSkillBased)).append("\n");
    sb.append("    step: ").append(toIndentedString(step)).append("\n");
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

