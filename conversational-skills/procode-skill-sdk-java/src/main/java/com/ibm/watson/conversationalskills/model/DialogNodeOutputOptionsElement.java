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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * DialogNodeOutputOptionsElement
 */
@JsonPropertyOrder({
  DialogNodeOutputOptionsElement.JSON_PROPERTY_LABEL,
  DialogNodeOutputOptionsElement.JSON_PROPERTY_VALUE
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class DialogNodeOutputOptionsElement {
  public static final String JSON_PROPERTY_LABEL = "label";
  private String label;

  public static final String JSON_PROPERTY_VALUE = "value";
  private DialogNodeOutputOptionsElementValue value;

  public DialogNodeOutputOptionsElement() {
  }

  public DialogNodeOutputOptionsElement label(String label) {
    
    this.label = label;
    return this;
  }

  /**
   * The user-facing label for the option.
   * @return label
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_LABEL)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getLabel() {
    return label;
  }


  @JsonProperty(JSON_PROPERTY_LABEL)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setLabel(String label) {
    this.label = label;
  }

  public DialogNodeOutputOptionsElement value(DialogNodeOutputOptionsElementValue value) {
    
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_VALUE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public DialogNodeOutputOptionsElementValue getValue() {
    return value;
  }


  @JsonProperty(JSON_PROPERTY_VALUE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setValue(DialogNodeOutputOptionsElementValue value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DialogNodeOutputOptionsElement dialogNodeOutputOptionsElement = (DialogNodeOutputOptionsElement) o;
    return Objects.equals(this.label, dialogNodeOutputOptionsElement.label) &&
        Objects.equals(this.value, dialogNodeOutputOptionsElement.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DialogNodeOutputOptionsElement {\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

