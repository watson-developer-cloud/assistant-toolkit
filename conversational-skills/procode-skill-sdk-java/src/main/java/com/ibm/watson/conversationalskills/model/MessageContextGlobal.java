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
import com.ibm.watson.conversationalskills.model.MessageContextGlobalSystem;
import com.ibm.watson.conversationalskills.model.SessionHistoryMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * MessageContextGlobal
 */
@JsonPropertyOrder({
  MessageContextGlobal.JSON_PROPERTY_SYSTEM,
  MessageContextGlobal.JSON_PROPERTY_SESSION_ID,
  MessageContextGlobal.JSON_PROPERTY_ASSISTANT_ID,
  MessageContextGlobal.JSON_PROPERTY_ENVIRONMENT_ID,
  MessageContextGlobal.JSON_PROPERTY_SESSION_HISTORY,
  MessageContextGlobal.JSON_PROPERTY_LANGUAGE
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class MessageContextGlobal {
  public static final String JSON_PROPERTY_SYSTEM = "system";
  private MessageContextGlobalSystem system;

  public static final String JSON_PROPERTY_SESSION_ID = "session_id";
  private String sessionId;

  public static final String JSON_PROPERTY_ASSISTANT_ID = "assistant_id";
  private String assistantId;

  public static final String JSON_PROPERTY_ENVIRONMENT_ID = "environment_id";
  private String environmentId;

  public static final String JSON_PROPERTY_SESSION_HISTORY = "session_history";
  private List<SessionHistoryMessage> sessionHistory = new ArrayList<>();

  public static final String JSON_PROPERTY_LANGUAGE = "language";
  private String language;

  public MessageContextGlobal() {
  }

  public MessageContextGlobal system(MessageContextGlobalSystem system) {
    
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

  public MessageContextGlobalSystem getSystem() {
    return system;
  }


  @JsonProperty(JSON_PROPERTY_SYSTEM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSystem(MessageContextGlobalSystem system) {
    this.system = system;
  }

  public MessageContextGlobal sessionId(String sessionId) {
    
    this.sessionId = sessionId;
    return this;
  }

  /**
   * WxA session ID.
   * @return sessionId
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SESSION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getSessionId() {
    return sessionId;
  }


  @JsonProperty(JSON_PROPERTY_SESSION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public MessageContextGlobal assistantId(String assistantId) {
    
    this.assistantId = assistantId;
    return this;
  }

  /**
   * WxA assistant ID.
   * @return assistantId
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ASSISTANT_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getAssistantId() {
    return assistantId;
  }


  @JsonProperty(JSON_PROPERTY_ASSISTANT_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAssistantId(String assistantId) {
    this.assistantId = assistantId;
  }

  public MessageContextGlobal environmentId(String environmentId) {
    
    this.environmentId = environmentId;
    return this;
  }

  /**
   * WxA environment ID.
   * @return environmentId
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ENVIRONMENT_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getEnvironmentId() {
    return environmentId;
  }


  @JsonProperty(JSON_PROPERTY_ENVIRONMENT_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEnvironmentId(String environmentId) {
    this.environmentId = environmentId;
  }

  public MessageContextGlobal sessionHistory(List<SessionHistoryMessage> sessionHistory) {
    
    this.sessionHistory = sessionHistory;
    return this;
  }

  public MessageContextGlobal addSessionHistoryItem(SessionHistoryMessage sessionHistoryItem) {
    if (this.sessionHistory == null) {
      this.sessionHistory = new ArrayList<>();
    }
    this.sessionHistory.add(sessionHistoryItem);
    return this;
  }

  /**
   * An array of message objects representing the session history.
   * @return sessionHistory
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SESSION_HISTORY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<SessionHistoryMessage> getSessionHistory() {
    return sessionHistory;
  }


  @JsonProperty(JSON_PROPERTY_SESSION_HISTORY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSessionHistory(List<SessionHistoryMessage> sessionHistory) {
    this.sessionHistory = sessionHistory;
  }

  public MessageContextGlobal language(String language) {
    
    this.language = language;
    return this;
  }

  /**
   * WxA assistant language.
   * @return language
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_LANGUAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getLanguage() {
    return language;
  }


  @JsonProperty(JSON_PROPERTY_LANGUAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLanguage(String language) {
    this.language = language;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageContextGlobal messageContextGlobal = (MessageContextGlobal) o;
    return Objects.equals(this.system, messageContextGlobal.system) &&
        Objects.equals(this.sessionId, messageContextGlobal.sessionId) &&
        Objects.equals(this.assistantId, messageContextGlobal.assistantId) &&
        Objects.equals(this.environmentId, messageContextGlobal.environmentId) &&
        Objects.equals(this.sessionHistory, messageContextGlobal.sessionHistory) &&
        Objects.equals(this.language, messageContextGlobal.language);
  }

  @Override
  public int hashCode() {
    return Objects.hash(system, sessionId, assistantId, environmentId, sessionHistory, language);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MessageContextGlobal {\n");
    sb.append("    system: ").append(toIndentedString(system)).append("\n");
    sb.append("    sessionId: ").append(toIndentedString(sessionId)).append("\n");
    sb.append("    assistantId: ").append(toIndentedString(assistantId)).append("\n");
    sb.append("    environmentId: ").append(toIndentedString(environmentId)).append("\n");
    sb.append("    sessionHistory: ").append(toIndentedString(sessionHistory)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
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

