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
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * ConversationalSkillStateOutput
 */
@JsonPropertyOrder({
  ConversationalSkillStateOutput.JSON_PROPERTY_LOCAL_VARIABLES,
  ConversationalSkillStateOutput.JSON_PROPERTY_SESSION_VARIABLES
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class ConversationalSkillStateOutput {
  public static final String JSON_PROPERTY_LOCAL_VARIABLES = "local_variables";
  private Map<String, Object> localVariables = new HashMap<>();

  public static final String JSON_PROPERTY_SESSION_VARIABLES = "session_variables";
  private Map<String, Object> sessionVariables = new HashMap<>();

  public ConversationalSkillStateOutput() {
  }

  public ConversationalSkillStateOutput localVariables(Map<String, Object> localVariables) {
    
    this.localVariables = localVariables;
    return this;
  }

  public ConversationalSkillStateOutput putLocalVariablesItem(String key, Object localVariablesItem) {
    if (this.localVariables == null) {
      this.localVariables = new HashMap<>();
    }
    this.localVariables.put(key, localVariablesItem);
    return this;
  }

  /**
   * Local scope variables that the conversational skill needs across turns to orchestrate the conversation.
   * @return localVariables
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_LOCAL_VARIABLES)
  @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, Object> getLocalVariables() {
    return localVariables;
  }


  @JsonProperty(JSON_PROPERTY_LOCAL_VARIABLES)
  @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
  public void setLocalVariables(Map<String, Object> localVariables) {
    this.localVariables = localVariables;
  }

  public ConversationalSkillStateOutput sessionVariables(Map<String, Object> sessionVariables) {
    
    this.sessionVariables = sessionVariables;
    return this;
  }

  public ConversationalSkillStateOutput putSessionVariablesItem(String key, Object sessionVariablesItem) {
    if (this.sessionVariables == null) {
      this.sessionVariables = new HashMap<>();
    }
    this.sessionVariables.put(key, sessionVariablesItem);
    return this;
  }

  /**
   * Conversation session scoped variables that the skill wants to share with other skills. These variables may live beyond the conclusion of the conversation being orchestrated by the skill.
   * @return sessionVariables
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SESSION_VARIABLES)
  @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, Object> getSessionVariables() {
    return sessionVariables;
  }


  @JsonProperty(JSON_PROPERTY_SESSION_VARIABLES)
  @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
  public void setSessionVariables(Map<String, Object> sessionVariables) {
    this.sessionVariables = sessionVariables;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConversationalSkillStateOutput conversationalSkillStateOutput = (ConversationalSkillStateOutput) o;
    return Objects.equals(this.localVariables, conversationalSkillStateOutput.localVariables) &&
        Objects.equals(this.sessionVariables, conversationalSkillStateOutput.sessionVariables);
  }

  @Override
  public int hashCode() {
    return Objects.hash(localVariables, sessionVariables);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConversationalSkillStateOutput {\n");
    sb.append("    localVariables: ").append(toIndentedString(localVariables)).append("\n");
    sb.append("    sessionVariables: ").append(toIndentedString(sessionVariables)).append("\n");
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

