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

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * System context data used by the skill.
 */
@JsonPropertyOrder({
  MessageContextSkillSystem.JSON_PROPERTY_STATE
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class MessageContextSkillSystem extends HashMap<String, Object> {
  public static final String JSON_PROPERTY_STATE = "state";
  private String state;

  public MessageContextSkillSystem() {

  }

  public MessageContextSkillSystem state(String state) {
    
    this.state = state;
    return this;
  }

  /**
   * An encoded string that represents the current conversation state. By saving this value and then sending it in the context of a subsequent message request, you can return to an earlier point in the conversation. If you are using stateful sessions, you can also use a stored state value to restore a paused conversation whose session is expired.
   * @return state
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STATE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getState() {
    return state;
  }


  @JsonProperty(JSON_PROPERTY_STATE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setState(String state) {
    this.state = state;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageContextSkillSystem messageContextSkillSystem = (MessageContextSkillSystem) o;
    return Objects.equals(this.state, messageContextSkillSystem.state) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(state, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MessageContextSkillSystem {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
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

