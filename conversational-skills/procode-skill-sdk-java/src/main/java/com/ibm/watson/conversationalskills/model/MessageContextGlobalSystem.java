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
 * Built-in system properties that apply to all skills used by the assistant.
 */
@JsonPropertyOrder({
  MessageContextGlobalSystem.JSON_PROPERTY_TIMEZONE,
  MessageContextGlobalSystem.JSON_PROPERTY_USER_ID,
  MessageContextGlobalSystem.JSON_PROPERTY_TURN_COUNT,
  MessageContextGlobalSystem.JSON_PROPERTY_LOCALE,
  MessageContextGlobalSystem.JSON_PROPERTY_REFERENCE_TIME,
  MessageContextGlobalSystem.JSON_PROPERTY_SESSION_START_TIME,
  MessageContextGlobalSystem.JSON_PROPERTY_STATE,
  MessageContextGlobalSystem.JSON_PROPERTY_SKIP_USER_INPUT
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class MessageContextGlobalSystem {
  public static final String JSON_PROPERTY_TIMEZONE = "timezone";
  private String timezone;

  public static final String JSON_PROPERTY_USER_ID = "user_id";
  private String userId;

  public static final String JSON_PROPERTY_TURN_COUNT = "turn_count";
  private Integer turnCount;

  /**
   * The language code for localization in the user input. The specified locale overrides the default for the assistant, and is used for interpreting entity values in user input such as date values. For example, &#x60;04/03/2018&#x60; might be interpreted either as April 3 or March 4, depending on the locale.   This property is included only if the new system entities are enabled for the skill.
   */
  public enum LocaleEnum {
    EN_US("en-us"),
    
    EN_CA("en-ca"),
    
    EN_GB("en-gb"),
    
    AR_AR("ar-ar"),
    
    CS_CZ("cs-cz"),
    
    DE_DE("de-de"),
    
    ES_ES("es-es"),
    
    FR_FR("fr-fr"),
    
    IT_IT("it-it"),
    
    JA_JP("ja-jp"),
    
    KO_KR("ko-kr"),
    
    NL_NL("nl-nl"),
    
    PT_BR("pt-br"),
    
    ZH_CN("zh-cn"),
    
    ZH_TW("zh-tw");

    private String value;

    LocaleEnum(String value) {
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
    public static LocaleEnum fromValue(String value) {
      for (LocaleEnum b : LocaleEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_LOCALE = "locale";
  private LocaleEnum locale;

  public static final String JSON_PROPERTY_REFERENCE_TIME = "reference_time";
  private String referenceTime;

  public static final String JSON_PROPERTY_SESSION_START_TIME = "session_start_time";
  private String sessionStartTime;

  public static final String JSON_PROPERTY_STATE = "state";
  private String state;

  public static final String JSON_PROPERTY_SKIP_USER_INPUT = "skip_user_input";
  private Boolean skipUserInput;

  public MessageContextGlobalSystem() {
  }

  public MessageContextGlobalSystem timezone(String timezone) {
    
    this.timezone = timezone;
    return this;
  }

  /**
   * The user time zone. The assistant uses the time zone to correctly resolve relative time references.
   * @return timezone
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TIMEZONE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getTimezone() {
    return timezone;
  }


  @JsonProperty(JSON_PROPERTY_TIMEZONE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public MessageContextGlobalSystem userId(String userId) {
    
    this.userId = userId;
    return this;
  }

  /**
   * A string value that identifies the user who is interacting with the assistant. The client must provide a unique identifier for each individual end user who accesses the application. For user-based plans, this user ID is used to identify unique users for billing purposes. This string cannot contain carriage return, newline, or tab characters. If no value is specified in the input, **user_id** is automatically set to the value of **context.global.session_id**.  **Note:** This property is the same as the **user_id** property at the root of the message body. If **user_id** is specified in both locations in a message request, the value specified at the root is used.
   * @return userId
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_USER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getUserId() {
    return userId;
  }


  @JsonProperty(JSON_PROPERTY_USER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUserId(String userId) {
    this.userId = userId;
  }

  public MessageContextGlobalSystem turnCount(Integer turnCount) {
    
    this.turnCount = turnCount;
    return this;
  }

  /**
   * A counter that is automatically incremented with each turn of the conversation. A value of 1 indicates that this is the the first turn of a new conversation, which can affect the behavior of some skills (for example, triggering the start node of a dialog).
   * @return turnCount
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TURN_COUNT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getTurnCount() {
    return turnCount;
  }


  @JsonProperty(JSON_PROPERTY_TURN_COUNT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTurnCount(Integer turnCount) {
    this.turnCount = turnCount;
  }

  public MessageContextGlobalSystem locale(LocaleEnum locale) {
    
    this.locale = locale;
    return this;
  }

  /**
   * The language code for localization in the user input. The specified locale overrides the default for the assistant, and is used for interpreting entity values in user input such as date values. For example, &#x60;04/03/2018&#x60; might be interpreted either as April 3 or March 4, depending on the locale.   This property is included only if the new system entities are enabled for the skill.
   * @return locale
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_LOCALE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public LocaleEnum getLocale() {
    return locale;
  }


  @JsonProperty(JSON_PROPERTY_LOCALE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLocale(LocaleEnum locale) {
    this.locale = locale;
  }

  public MessageContextGlobalSystem referenceTime(String referenceTime) {
    
    this.referenceTime = referenceTime;
    return this;
  }

  /**
   * The base time for interpreting any relative time mentions in the user input. The specified time overrides the current server time, and is used to calculate times mentioned in relative terms such as &#x60;now&#x60; or &#x60;tomorrow&#x60;. This can be useful for simulating past or future times for testing purposes, or when analyzing documents such as news articles.  This value must be a UTC time value formatted according to ISO 8601 (for example, &#x60;2021-06-26T12:00:00Z&#x60; for noon UTC on 26 June 2021).  This property is included only if the new system entities are enabled for the skill.
   * @return referenceTime
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_REFERENCE_TIME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getReferenceTime() {
    return referenceTime;
  }


  @JsonProperty(JSON_PROPERTY_REFERENCE_TIME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setReferenceTime(String referenceTime) {
    this.referenceTime = referenceTime;
  }

  public MessageContextGlobalSystem sessionStartTime(String sessionStartTime) {
    
    this.sessionStartTime = sessionStartTime;
    return this;
  }

  /**
   * The time at which the session started. With the stateful &#x60;message&#x60; method, the start time is always present, and is set by the service based on the time the session was created. With the stateless &#x60;message&#x60; method, the start time is set by the service in the response to the first message, and should be returned as part of the context with each subsequent message in the session.  This value is a UTC time value formatted according to ISO 8601 (for example, &#x60;2021-06-26T12:00:00Z&#x60; for noon UTC on 26 June 2021).
   * @return sessionStartTime
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SESSION_START_TIME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getSessionStartTime() {
    return sessionStartTime;
  }


  @JsonProperty(JSON_PROPERTY_SESSION_START_TIME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSessionStartTime(String sessionStartTime) {
    this.sessionStartTime = sessionStartTime;
  }

  public MessageContextGlobalSystem state(String state) {
    
    this.state = state;
    return this;
  }

  /**
   * An encoded string that represents the configuration state of the assistant at the beginning of the conversation. If you are using the stateless &#x60;message&#x60; method, save this value and then send it in the context of the subsequent message request to avoid disruptions if there are configuration changes during the conversation (such as a change to a skill the assistant uses).
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

  public MessageContextGlobalSystem skipUserInput(Boolean skipUserInput) {
    
    this.skipUserInput = skipUserInput;
    return this;
  }

  /**
   * For internal use only.
   * @return skipUserInput
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SKIP_USER_INPUT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getSkipUserInput() {
    return skipUserInput;
  }


  @JsonProperty(JSON_PROPERTY_SKIP_USER_INPUT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSkipUserInput(Boolean skipUserInput) {
    this.skipUserInput = skipUserInput;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageContextGlobalSystem messageContextGlobalSystem = (MessageContextGlobalSystem) o;
    return Objects.equals(this.timezone, messageContextGlobalSystem.timezone) &&
        Objects.equals(this.userId, messageContextGlobalSystem.userId) &&
        Objects.equals(this.turnCount, messageContextGlobalSystem.turnCount) &&
        Objects.equals(this.locale, messageContextGlobalSystem.locale) &&
        Objects.equals(this.referenceTime, messageContextGlobalSystem.referenceTime) &&
        Objects.equals(this.sessionStartTime, messageContextGlobalSystem.sessionStartTime) &&
        Objects.equals(this.state, messageContextGlobalSystem.state) &&
        Objects.equals(this.skipUserInput, messageContextGlobalSystem.skipUserInput);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timezone, userId, turnCount, locale, referenceTime, sessionStartTime, state, skipUserInput);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MessageContextGlobalSystem {\n");
    sb.append("    timezone: ").append(toIndentedString(timezone)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    turnCount: ").append(toIndentedString(turnCount)).append("\n");
    sb.append("    locale: ").append(toIndentedString(locale)).append("\n");
    sb.append("    referenceTime: ").append(toIndentedString(referenceTime)).append("\n");
    sb.append("    sessionStartTime: ").append(toIndentedString(sessionStartTime)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    skipUserInput: ").append(toIndentedString(skipUserInput)).append("\n");
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

