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
 * MessageContextDialogSkill
 */
@JsonPropertyOrder({
  MessageContextDialogSkill.JSON_PROPERTY_USER_DEFINED,
  MessageContextDialogSkill.JSON_PROPERTY_SYSTEM
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class MessageContextDialogSkill {
  public static final String JSON_PROPERTY_USER_DEFINED = "user_defined";
  private Map<String, Object> userDefined = new HashMap<>();

  public static final String JSON_PROPERTY_SYSTEM = "system";
  private MessageContextSkillSystem system;

  public MessageContextDialogSkill() {
  }

  public MessageContextDialogSkill userDefined(Map<String, Object> userDefined) {
    
    this.userDefined = userDefined;
    return this;
  }

  public MessageContextDialogSkill putUserDefinedItem(String key, Object userDefinedItem) {
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

  public MessageContextDialogSkill system(MessageContextSkillSystem system) {
    
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageContextDialogSkill messageContextDialogSkill = (MessageContextDialogSkill) o;
    return Objects.equals(this.userDefined, messageContextDialogSkill.userDefined) &&
        Objects.equals(this.system, messageContextDialogSkill.system);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userDefined, system);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MessageContextDialogSkill {\n");
    sb.append("    userDefined: ").append(toIndentedString(userDefined)).append("\n");
    sb.append("    system: ").append(toIndentedString(system)).append("\n");
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

