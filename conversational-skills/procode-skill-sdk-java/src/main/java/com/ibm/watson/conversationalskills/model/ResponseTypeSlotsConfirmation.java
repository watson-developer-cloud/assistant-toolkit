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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * ResponseTypeSlotsConfirmation
 */
@JsonPropertyOrder({
  ResponseTypeSlotsConfirmation.JSON_PROPERTY_PROMPT
})
@JsonTypeName("ResponseTypeSlots_confirmation")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class ResponseTypeSlotsConfirmation {
  public static final String JSON_PROPERTY_PROMPT = "prompt";
  private String prompt;

  public ResponseTypeSlotsConfirmation() {
  }

  public ResponseTypeSlotsConfirmation prompt(String prompt) {
    
    this.prompt = prompt;
    return this;
  }

  /**
   * Get prompt
   * @return prompt
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PROMPT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getPrompt() {
    return prompt;
  }


  @JsonProperty(JSON_PROPERTY_PROMPT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResponseTypeSlotsConfirmation responseTypeSlotsConfirmation = (ResponseTypeSlotsConfirmation) o;
    return Objects.equals(this.prompt, responseTypeSlotsConfirmation.prompt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(prompt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResponseTypeSlotsConfirmation {\n");
    sb.append("    prompt: ").append(toIndentedString(prompt)).append("\n");
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

