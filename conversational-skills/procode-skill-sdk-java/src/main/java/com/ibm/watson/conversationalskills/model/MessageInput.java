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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * An input object that includes the input text.
 */
@JsonPropertyOrder({
  MessageInput.JSON_PROPERTY_MESSAGE_TYPE,
  MessageInput.JSON_PROPERTY_TEXT,
  MessageInput.JSON_PROPERTY_ATTACHMENTS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class MessageInput extends HashMap<String, Object> {
  /**
   * The type of the message:  - &#x60;text&#x60;: The user input is processed normally by the assistant. - &#x60;search&#x60;: Only search results are returned. (Any dialog or action skill is bypassed.) - &#x60;event&#x60;: user interaction event communicated - &#x60;form&#x60;: user interaction results on a form display communicated  **Note:** A &#x60;search&#x60; message results in an error if no search skill is configured for the assistant.
   */
  public enum MessageTypeEnum {
    TEXT("text"),
    
    SEARCH("search"),
    
    FORM("form"),
    
    EVENT("event");

    private String value;

    MessageTypeEnum(String value) {
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
    public static MessageTypeEnum fromValue(String value) {
      for (MessageTypeEnum b : MessageTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_MESSAGE_TYPE = "message_type";
  private MessageTypeEnum messageType = MessageTypeEnum.TEXT;

  public static final String JSON_PROPERTY_TEXT = "text";
  private String text;

  public static final String JSON_PROPERTY_ATTACHMENTS = "attachments";
  private List<MessageInputAttachment> attachments = new ArrayList<>();

  public MessageInput() {

  }

  public MessageInput messageType(MessageTypeEnum messageType) {
    
    this.messageType = messageType;
    return this;
  }

  /**
   * The type of the message:  - &#x60;text&#x60;: The user input is processed normally by the assistant. - &#x60;search&#x60;: Only search results are returned. (Any dialog or action skill is bypassed.) - &#x60;event&#x60;: user interaction event communicated - &#x60;form&#x60;: user interaction results on a form display communicated  **Note:** A &#x60;search&#x60; message results in an error if no search skill is configured for the assistant.
   * @return messageType
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MESSAGE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public MessageTypeEnum getMessageType() {
    return messageType;
  }


  @JsonProperty(JSON_PROPERTY_MESSAGE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMessageType(MessageTypeEnum messageType) {
    this.messageType = messageType;
  }

  public MessageInput text(String text) {
    
    this.text = text;
    return this;
  }

  /**
   * The text of the user input. This string cannot contain carriage return, newline, or tab characters.
   * @return text
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TEXT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getText() {
    return text;
  }


  @JsonProperty(JSON_PROPERTY_TEXT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setText(String text) {
    this.text = text;
  }

  public MessageInput attachments(List<MessageInputAttachment> attachments) {
    
    this.attachments = attachments;
    return this;
  }

  public MessageInput addAttachmentsItem(MessageInputAttachment attachmentsItem) {
    if (this.attachments == null) {
      this.attachments = new ArrayList<>();
    }
    this.attachments.add(attachmentsItem);
    return this;
  }

  /**
   * An array of multimedia attachments to be sent with the message. Attachments are not processed by the assistant itself, but can be sent to external services by webhooks.    **Note:** Attachments are not supported on IBM Cloud Pak for Data.
   * @return attachments
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ATTACHMENTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<MessageInputAttachment> getAttachments() {
    return attachments;
  }


  @JsonProperty(JSON_PROPERTY_ATTACHMENTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAttachments(List<MessageInputAttachment> attachments) {
    this.attachments = attachments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageInput messageInput = (MessageInput) o;
    return Objects.equals(this.messageType, messageInput.messageType) &&
        Objects.equals(this.text, messageInput.text) &&
        Objects.equals(this.attachments, messageInput.attachments) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(messageType, text, attachments, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MessageInput {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    messageType: ").append(toIndentedString(messageType)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    attachments: ").append(toIndentedString(attachments)).append("\n");
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

