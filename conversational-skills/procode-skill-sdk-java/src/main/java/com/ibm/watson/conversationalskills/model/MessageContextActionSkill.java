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

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * MessageContextActionSkill
 */
@JsonPropertyOrder({
  MessageContextActionSkill.JSON_PROPERTY_USER_DEFINED,
  MessageContextActionSkill.JSON_PROPERTY_SYSTEM,
  MessageContextActionSkill.JSON_PROPERTY_ACTION_VARIABLES,
  MessageContextActionSkill.JSON_PROPERTY_SKILL_VARIABLES
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class MessageContextActionSkill {
  public static final String JSON_PROPERTY_USER_DEFINED = "user_defined";
  private Map<String, Object> userDefined = new HashMap<>();

  public static final String JSON_PROPERTY_SYSTEM = "system";
  private MessageContextSkillSystem system;

  public static final String JSON_PROPERTY_ACTION_VARIABLES = "action_variables";
  private Map<String, Object> actionVariables = new HashMap<>();

  public static final String JSON_PROPERTY_SKILL_VARIABLES = "skill_variables";
  private Map<String, Object> skillVariables = new HashMap<>();

  public MessageContextActionSkill() {
  }

  public MessageContextActionSkill userDefined(Map<String, Object> userDefined) {
    
    this.userDefined = userDefined;
    return this;
  }

  public MessageContextActionSkill putUserDefinedItem(String key, Object userDefinedItem) {
    if (this.userDefined == null) {
      this.userDefined = new HashMap<>();
    }
    this.userDefined.put(key, userDefinedItem);
    return this;
  }

  /**
   * An object containing any arbitrary variables that can be read and written by a particular skill.
   * @return userDefined
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_USER_DEFINED)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, Object> getUserDefined() {
    return userDefined;
  }


  @JsonProperty(JSON_PROPERTY_USER_DEFINED)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUserDefined(Map<String, Object> userDefined) {
    this.userDefined = userDefined;
  }

  public MessageContextActionSkill system(MessageContextSkillSystem system) {
    
    this.system = system;
    return this;
  }

  /**
   * Get system
   * @return system
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SYSTEM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public MessageContextSkillSystem getSystem() {
    return system;
  }


  @JsonProperty(JSON_PROPERTY_SYSTEM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSystem(MessageContextSkillSystem system) {
    this.system = system;
  }

  public MessageContextActionSkill actionVariables(Map<String, Object> actionVariables) {
    
    this.actionVariables = actionVariables;
    return this;
  }

  public MessageContextActionSkill putActionVariablesItem(String key, Object actionVariablesItem) {
    if (this.actionVariables == null) {
      this.actionVariables = new HashMap<>();
    }
    this.actionVariables.put(key, actionVariablesItem);
    return this;
  }

  /**
   * An object containing action variables. Action variables can be accessed only by steps in the same action, and do not persist after the action ends.
   * @return actionVariables
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ACTION_VARIABLES)
  @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, Object> getActionVariables() {
    return actionVariables;
  }


  @JsonProperty(JSON_PROPERTY_ACTION_VARIABLES)
  @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
  public void setActionVariables(Map<String, Object> actionVariables) {
    this.actionVariables = actionVariables;
  }

  public MessageContextActionSkill skillVariables(Map<String, Object> skillVariables) {
    
    this.skillVariables = skillVariables;
    return this;
  }

  public MessageContextActionSkill putSkillVariablesItem(String key, Object skillVariablesItem) {
    if (this.skillVariables == null) {
      this.skillVariables = new HashMap<>();
    }
    this.skillVariables.put(key, skillVariablesItem);
    return this;
  }

  /**
   * An object containing skill variables. (In the watsonx Assistant user interface, skill variables are called _session variables_.) Skill variables can be accessed by any action and persist for the duration of the session.
   * @return skillVariables
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SKILL_VARIABLES)
  @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, Object> getSkillVariables() {
    return skillVariables;
  }


  @JsonProperty(JSON_PROPERTY_SKILL_VARIABLES)
  @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
  public void setSkillVariables(Map<String, Object> skillVariables) {
    this.skillVariables = skillVariables;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageContextActionSkill messageContextActionSkill = (MessageContextActionSkill) o;
    return Objects.equals(this.userDefined, messageContextActionSkill.userDefined) &&
        Objects.equals(this.system, messageContextActionSkill.system) &&
        Objects.equals(this.actionVariables, messageContextActionSkill.actionVariables) &&
        Objects.equals(this.skillVariables, messageContextActionSkill.skillVariables);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userDefined, system, actionVariables, skillVariables);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MessageContextActionSkill {\n");
    sb.append("    userDefined: ").append(toIndentedString(userDefined)).append("\n");
    sb.append("    system: ").append(toIndentedString(system)).append("\n");
    sb.append("    actionVariables: ").append(toIndentedString(actionVariables)).append("\n");
    sb.append("    skillVariables: ").append(toIndentedString(skillVariables)).append("\n");
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

