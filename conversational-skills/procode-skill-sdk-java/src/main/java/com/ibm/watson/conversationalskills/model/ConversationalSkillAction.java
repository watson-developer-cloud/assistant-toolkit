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
import com.ibm.watson.conversationalskills.model.ConversationalSkillActionRegistration;
import com.ibm.watson.conversationalskills.model.ConversationalSkillActionSource;
import com.ibm.watson.conversationalskills.model.SlotInFlight;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * ConversationalSkillAction
 */
@JsonPropertyOrder({
  ConversationalSkillAction.JSON_PROPERTY_ACTION,
  ConversationalSkillAction.JSON_PROPERTY_REGISTRATION,
  ConversationalSkillAction.JSON_PROPERTY_ASYNC,
  ConversationalSkillAction.JSON_PROPERTY_SLOTS,
  ConversationalSkillAction.JSON_PROPERTY_SOURCE
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class ConversationalSkillAction {
  public static final String JSON_PROPERTY_ACTION = "action";
  private String action;

  public static final String JSON_PROPERTY_REGISTRATION = "registration";
  private ConversationalSkillActionRegistration registration;

  public static final String JSON_PROPERTY_ASYNC = "async";
  private Boolean async;

  public static final String JSON_PROPERTY_SLOTS = "slots";
  private List<SlotInFlight> slots = new ArrayList<>();

  public static final String JSON_PROPERTY_SOURCE = "source";
  private ConversationalSkillActionSource source;

  public ConversationalSkillAction() {
  }

  public ConversationalSkillAction action(String action) {
    
    this.action = action;
    return this;
  }

  /**
   * The ID of the action.
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

  public ConversationalSkillAction registration(ConversationalSkillActionRegistration registration) {
    
    this.registration = registration;
    return this;
  }

  /**
   * Get registration
   * @return registration
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_REGISTRATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ConversationalSkillActionRegistration getRegistration() {
    return registration;
  }


  @JsonProperty(JSON_PROPERTY_REGISTRATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRegistration(ConversationalSkillActionRegistration registration) {
    this.registration = registration;
  }

  public ConversationalSkillAction async(Boolean async) {
    
    this.async = async;
    return this;
  }

  /**
   * True if this is an async skill
   * @return async
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ASYNC)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getAsync() {
    return async;
  }


  @JsonProperty(JSON_PROPERTY_ASYNC)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAsync(Boolean async) {
    this.async = async;
  }

  public ConversationalSkillAction slots(List<SlotInFlight> slots) {
    
    this.slots = slots;
    return this;
  }

  public ConversationalSkillAction addSlotsItem(SlotInFlight slotsItem) {
    if (this.slots == null) {
      this.slots = new ArrayList<>();
    }
    this.slots.add(slotsItem);
    return this;
  }

  /**
   * Information for slots requested by this conversational skill
   * @return slots
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SLOTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<SlotInFlight> getSlots() {
    return slots;
  }


  @JsonProperty(JSON_PROPERTY_SLOTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSlots(List<SlotInFlight> slots) {
    this.slots = slots;
  }

  public ConversationalSkillAction source(ConversationalSkillActionSource source) {
    
    this.source = source;
    return this;
  }

  /**
   * Get source
   * @return source
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SOURCE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ConversationalSkillActionSource getSource() {
    return source;
  }


  @JsonProperty(JSON_PROPERTY_SOURCE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSource(ConversationalSkillActionSource source) {
    this.source = source;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConversationalSkillAction conversationalSkillAction = (ConversationalSkillAction) o;
    return Objects.equals(this.action, conversationalSkillAction.action) &&
        Objects.equals(this.registration, conversationalSkillAction.registration) &&
        Objects.equals(this.async, conversationalSkillAction.async) &&
        Objects.equals(this.slots, conversationalSkillAction.slots) &&
        Objects.equals(this.source, conversationalSkillAction.source);
  }

  @Override
  public int hashCode() {
    return Objects.hash(action, registration, async, slots, source);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConversationalSkillAction {\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    registration: ").append(toIndentedString(registration)).append("\n");
    sb.append("    async: ").append(toIndentedString(async)).append("\n");
    sb.append("    slots: ").append(toIndentedString(slots)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
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

