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
import com.ibm.watson.conversationalskills.model.ConversationalSkillActiveDetails;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * MessageContextConversationalSkills
 */
@JsonPropertyOrder({
  MessageContextConversationalSkills.JSON_PROPERTY_STACK,
  MessageContextConversationalSkills.JSON_PROPERTY_SESSION_VARIABLES
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class MessageContextConversationalSkills {
  public static final String JSON_PROPERTY_STACK = "stack";
  private List<ConversationalSkillActiveDetails> stack = new ArrayList<>();

  public static final String JSON_PROPERTY_SESSION_VARIABLES = "session_variables";
  private Map<String, Object> sessionVariables = new HashMap<>();

  public MessageContextConversationalSkills() {
  }

  public MessageContextConversationalSkills stack(List<ConversationalSkillActiveDetails> stack) {
    
    this.stack = stack;
    return this;
  }

  public MessageContextConversationalSkills addStackItem(ConversationalSkillActiveDetails stackItem) {
    if (this.stack == null) {
      this.stack = new ArrayList<>();
    }
    this.stack.add(stackItem);
    return this;
  }

  /**
   * Stack of active conversational skills
   * @return stack
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STACK)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<ConversationalSkillActiveDetails> getStack() {
    return stack;
  }


  @JsonProperty(JSON_PROPERTY_STACK)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStack(List<ConversationalSkillActiveDetails> stack) {
    this.stack = stack;
  }

  public MessageContextConversationalSkills sessionVariables(Map<String, Object> sessionVariables) {
    
    this.sessionVariables = sessionVariables;
    return this;
  }

  public MessageContextConversationalSkills putSessionVariablesItem(String key, Object sessionVariablesItem) {
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
    MessageContextConversationalSkills messageContextConversationalSkills = (MessageContextConversationalSkills) o;
    return Objects.equals(this.stack, messageContextConversationalSkills.stack) &&
        Objects.equals(this.sessionVariables, messageContextConversationalSkills.sessionVariables);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stack, sessionVariables);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MessageContextConversationalSkills {\n");
    sb.append("    stack: ").append(toIndentedString(stack)).append("\n");
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

