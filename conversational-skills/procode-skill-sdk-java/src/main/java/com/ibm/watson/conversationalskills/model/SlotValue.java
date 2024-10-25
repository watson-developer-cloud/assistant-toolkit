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
import com.ibm.watson.conversationalskills.model.SlotValueNormalized;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * SlotValue
 */
@JsonPropertyOrder({
  SlotValue.JSON_PROPERTY_NORMALIZED,
  SlotValue.JSON_PROPERTY_LITERAL
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class SlotValue {
  public static final String JSON_PROPERTY_NORMALIZED = "normalized";
  private SlotValueNormalized normalized;

  public static final String JSON_PROPERTY_LITERAL = "literal";
  private String literal;

  public SlotValue() {
  }

  public SlotValue normalized(SlotValueNormalized normalized) {
    
    this.normalized = normalized;
    return this;
  }

  /**
   * Get normalized
   * @return normalized
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NORMALIZED)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public SlotValueNormalized getNormalized() {
    return normalized;
  }


  @JsonProperty(JSON_PROPERTY_NORMALIZED)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setNormalized(SlotValueNormalized normalized) {
    this.normalized = normalized;
  }

  public SlotValue literal(String literal) {
    
    this.literal = literal;
    return this;
  }

  /**
   * The literal value as provided by the user.
   * @return literal
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_LITERAL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getLiteral() {
    return literal;
  }


  @JsonProperty(JSON_PROPERTY_LITERAL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLiteral(String literal) {
    this.literal = literal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SlotValue slotValue = (SlotValue) o;
    return Objects.equals(this.normalized, slotValue.normalized) &&
        Objects.equals(this.literal, slotValue.literal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(normalized, literal);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SlotValue {\n");
    sb.append("    normalized: ").append(toIndentedString(normalized)).append("\n");
    sb.append("    literal: ").append(toIndentedString(literal)).append("\n");
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

