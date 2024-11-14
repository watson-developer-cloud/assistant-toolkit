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
import com.ibm.watson.conversationalskills.model.ConversationalSkillAction;
import com.ibm.watson.conversationalskills.model.SlotInFlight;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * ConversationalSkillActiveDetails
 */
@JsonPropertyOrder({
  ConversationalSkillActiveDetails.JSON_PROPERTY_ACTION,
  ConversationalSkillActiveDetails.JSON_PROPERTY_SLOTS,
  ConversationalSkillActiveDetails.JSON_PROPERTY_LOCAL_VARIABLES,
  ConversationalSkillActiveDetails.JSON_PROPERTY_CONSECUTIVE_ERROR_TURN_COUNT
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class ConversationalSkillActiveDetails {
  public static final String JSON_PROPERTY_ACTION = "action";
  private ConversationalSkillAction action;

  public static final String JSON_PROPERTY_SLOTS = "slots";
  private List<SlotInFlight> slots = new ArrayList<>();

  public static final String JSON_PROPERTY_LOCAL_VARIABLES = "local_variables";
  private Map<String, Object> localVariables = new HashMap<>();

  public static final String JSON_PROPERTY_CONSECUTIVE_ERROR_TURN_COUNT = "consecutive_error_turn_count";
  private BigDecimal consecutiveErrorTurnCount;

  public ConversationalSkillActiveDetails() {
  }

  public ConversationalSkillActiveDetails action(ConversationalSkillAction action) {
    
    this.action = action;
    return this;
  }

  /**
   * Get action
   * @return action
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ACTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ConversationalSkillAction getAction() {
    return action;
  }


  @JsonProperty(JSON_PROPERTY_ACTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAction(ConversationalSkillAction action) {
    this.action = action;
  }

  public ConversationalSkillActiveDetails slots(List<SlotInFlight> slots) {
    
    this.slots = slots;
    return this;
  }

  public ConversationalSkillActiveDetails addSlotsItem(SlotInFlight slotsItem) {
    if (this.slots == null) {
      this.slots = new ArrayList<>();
    }
    this.slots.add(slotsItem);
    return this;
  }

  /**
   * The ordered list of slots that WxA has filled, repaired, or prompted for.
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

  public ConversationalSkillActiveDetails localVariables(Map<String, Object> localVariables) {
    
    this.localVariables = localVariables;
    return this;
  }

  public ConversationalSkillActiveDetails putLocalVariablesItem(String key, Object localVariablesItem) {
    if (this.localVariables == null) {
      this.localVariables = new HashMap<>();
    }
    this.localVariables.put(key, localVariablesItem);
    return this;
  }

  /**
   * An object containing variables local to this conversational skill.
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

  public ConversationalSkillActiveDetails consecutiveErrorTurnCount(BigDecimal consecutiveErrorTurnCount) {
    
    this.consecutiveErrorTurnCount = consecutiveErrorTurnCount;
    return this;
  }

  /**
   * The number of consecutive error attempting to fill the current slot.
   * @return consecutiveErrorTurnCount
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CONSECUTIVE_ERROR_TURN_COUNT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public BigDecimal getConsecutiveErrorTurnCount() {
    return consecutiveErrorTurnCount;
  }


  @JsonProperty(JSON_PROPERTY_CONSECUTIVE_ERROR_TURN_COUNT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setConsecutiveErrorTurnCount(BigDecimal consecutiveErrorTurnCount) {
    this.consecutiveErrorTurnCount = consecutiveErrorTurnCount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConversationalSkillActiveDetails conversationalSkillActiveDetails = (ConversationalSkillActiveDetails) o;
    return Objects.equals(this.action, conversationalSkillActiveDetails.action) &&
        Objects.equals(this.slots, conversationalSkillActiveDetails.slots) &&
        Objects.equals(this.localVariables, conversationalSkillActiveDetails.localVariables) &&
        Objects.equals(this.consecutiveErrorTurnCount, conversationalSkillActiveDetails.consecutiveErrorTurnCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(action, slots, localVariables, consecutiveErrorTurnCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConversationalSkillActiveDetails {\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    slots: ").append(toIndentedString(slots)).append("\n");
    sb.append("    localVariables: ").append(toIndentedString(localVariables)).append("\n");
    sb.append("    consecutiveErrorTurnCount: ").append(toIndentedString(consecutiveErrorTurnCount)).append("\n");
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

