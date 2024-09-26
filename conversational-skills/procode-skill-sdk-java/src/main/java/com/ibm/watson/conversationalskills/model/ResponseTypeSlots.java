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

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * ResponseTypeSlots
 */
@JsonPropertyOrder({
  ResponseTypeSlots.JSON_PROPERTY_RESPONSE_TYPE,
  ResponseTypeSlots.JSON_PROPERTY_SLOTS,
  ResponseTypeSlots.JSON_PROPERTY_CONFIRMATION
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class ResponseTypeSlots {
  public static final String JSON_PROPERTY_RESPONSE_TYPE = "response_type";
  private String responseType;

  public static final String JSON_PROPERTY_SLOTS = "slots";
  private List<SlotInFlight> slots = new ArrayList<>();

  public static final String JSON_PROPERTY_CONFIRMATION = "confirmation";
  private ResponseTypeSlotsConfirmation confirmation;

  public ResponseTypeSlots() {
  }

  public ResponseTypeSlots responseType(String responseType) {
    
    this.responseType = responseType;
    return this;
  }

  /**
   * The type of response returned by the dialog node. The specified response type must be supported by the client application or channel.
   * @return responseType
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_RESPONSE_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getResponseType() {
    return responseType;
  }


  @JsonProperty(JSON_PROPERTY_RESPONSE_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setResponseType(String responseType) {
    this.responseType = responseType;
  }

  public ResponseTypeSlots slots(List<SlotInFlight> slots) {
    
    this.slots = slots;
    return this;
  }

  public ResponseTypeSlots addSlotsItem(SlotInFlight slotsItem) {
    if (this.slots == null) {
      this.slots = new ArrayList<>();
    }
    this.slots.add(slotsItem);
    return this;
  }

  /**
   * The ordered list of slots in flight that WxA should strive to prompt/fill/repair.
   * @return slots
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SLOTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<SlotInFlight> getSlots() {
    return slots;
  }


  @JsonProperty(JSON_PROPERTY_SLOTS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSlots(List<SlotInFlight> slots) {
    this.slots = slots;
  }

  public ResponseTypeSlots confirmation(ResponseTypeSlotsConfirmation confirmation) {
    
    this.confirmation = confirmation;
    return this;
  }

  /**
   * Get confirmation
   * @return confirmation
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CONFIRMATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ResponseTypeSlotsConfirmation getConfirmation() {
    return confirmation;
  }


  @JsonProperty(JSON_PROPERTY_CONFIRMATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setConfirmation(ResponseTypeSlotsConfirmation confirmation) {
    this.confirmation = confirmation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResponseTypeSlots responseTypeSlots = (ResponseTypeSlots) o;
    return Objects.equals(this.responseType, responseTypeSlots.responseType) &&
        Objects.equals(this.slots, responseTypeSlots.slots) &&
        Objects.equals(this.confirmation, responseTypeSlots.confirmation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(responseType, slots, confirmation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResponseTypeSlots {\n");
    sb.append("    responseType: ").append(toIndentedString(responseType)).append("\n");
    sb.append("    slots: ").append(toIndentedString(slots)).append("\n");
    sb.append("    confirmation: ").append(toIndentedString(confirmation)).append("\n");
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

