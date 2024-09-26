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
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * An orchestration request.
 */
@JsonPropertyOrder({
  OrchestrationRequest.JSON_PROPERTY_INPUT,
  OrchestrationRequest.JSON_PROPERTY_CONTEXT,
  OrchestrationRequest.JSON_PROPERTY_SLOTS,
  OrchestrationRequest.JSON_PROPERTY_STATE,
  OrchestrationRequest.JSON_PROPERTY_CONFIRMATION_EVENT
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class OrchestrationRequest {
  public static final String JSON_PROPERTY_INPUT = "input";
  private MessageInput input;

  public static final String JSON_PROPERTY_CONTEXT = "context";
  private MessageContext context;

  public static final String JSON_PROPERTY_SLOTS = "slots";
  private List<SlotState> slots = new ArrayList<>();

  public static final String JSON_PROPERTY_STATE = "state";
  private ConversationalSkillStateInput state;

  /**
   * Gets or Sets confirmationEvent
   */
  public enum ConfirmationEventEnum {
    CONFIRMED("user_confirmed"),
    
    CANCELLED("user_cancelled");

    private String value;

    ConfirmationEventEnum(String value) {
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
    public static ConfirmationEventEnum fromValue(String value) {
      for (ConfirmationEventEnum b : ConfirmationEventEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_CONFIRMATION_EVENT = "confirmation_event";
  private ConfirmationEventEnum confirmationEvent;

  public OrchestrationRequest() {
  }

  public OrchestrationRequest input(MessageInput input) {
    
    this.input = input;
    return this;
  }

  /**
   * Get input
   * @return input
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_INPUT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public MessageInput getInput() {
    return input;
  }


  @JsonProperty(JSON_PROPERTY_INPUT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setInput(MessageInput input) {
    this.input = input;
  }

  public OrchestrationRequest context(MessageContext context) {
    
    this.context = context;
    return this;
  }

  /**
   * Get context
   * @return context
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CONTEXT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public MessageContext getContext() {
    return context;
  }


  @JsonProperty(JSON_PROPERTY_CONTEXT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setContext(MessageContext context) {
    this.context = context;
  }

  public OrchestrationRequest slots(List<SlotState> slots) {
    
    this.slots = slots;
    return this;
  }

  public OrchestrationRequest addSlotsItem(SlotState slotsItem) {
    if (this.slots == null) {
      this.slots = new ArrayList<>();
    }
    this.slots.add(slotsItem);
    return this;
  }

  /**
   * Slots runtime state (sans definition, since conversational skill is itself their source).
   * @return slots
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SLOTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<SlotState> getSlots() {
    return slots;
  }


  @JsonProperty(JSON_PROPERTY_SLOTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSlots(List<SlotState> slots) {
    this.slots = slots;
  }

  public OrchestrationRequest state(ConversationalSkillStateInput state) {
    
    this.state = state;
    return this;
  }

  /**
   * Get state
   * @return state
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STATE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ConversationalSkillStateInput getState() {
    return state;
  }


  @JsonProperty(JSON_PROPERTY_STATE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setState(ConversationalSkillStateInput state) {
    this.state = state;
  }

  public OrchestrationRequest confirmationEvent(ConfirmationEventEnum confirmationEvent) {
    
    this.confirmationEvent = confirmationEvent;
    return this;
  }

  /**
   * Get confirmationEvent
   * @return confirmationEvent
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CONFIRMATION_EVENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ConfirmationEventEnum getConfirmationEvent() {
    return confirmationEvent;
  }


  @JsonProperty(JSON_PROPERTY_CONFIRMATION_EVENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setConfirmationEvent(ConfirmationEventEnum confirmationEvent) {
    this.confirmationEvent = confirmationEvent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrchestrationRequest orchestrationRequest = (OrchestrationRequest) o;
    return Objects.equals(this.input, orchestrationRequest.input) &&
        Objects.equals(this.context, orchestrationRequest.context) &&
        Objects.equals(this.slots, orchestrationRequest.slots) &&
        Objects.equals(this.state, orchestrationRequest.state) &&
        Objects.equals(this.confirmationEvent, orchestrationRequest.confirmationEvent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(input, context, slots, state, confirmationEvent);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrchestrationRequest {\n");
    sb.append("    input: ").append(toIndentedString(input)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    slots: ").append(toIndentedString(slots)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    confirmationEvent: ").append(toIndentedString(confirmationEvent)).append("\n");
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

