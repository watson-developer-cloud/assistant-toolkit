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
import com.ibm.watson.conversationalskills.model.DialogSuggestionValue;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * DialogSuggestion
 */
@JsonPropertyOrder({
  DialogSuggestion.JSON_PROPERTY_LABEL,
  DialogSuggestion.JSON_PROPERTY_VALUE,
  DialogSuggestion.JSON_PROPERTY_OUTPUT
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class DialogSuggestion {
  public static final String JSON_PROPERTY_LABEL = "label";
  private String label;

  public static final String JSON_PROPERTY_VALUE = "value";
  private DialogSuggestionValue value;

  public static final String JSON_PROPERTY_OUTPUT = "output";
  private Map<String, Object> output = new HashMap<>();

  public DialogSuggestion() {
  }

  public DialogSuggestion label(String label) {
    
    this.label = label;
    return this;
  }

  /**
   * The user-facing label for the suggestion. This label is taken from the **title** or **user_label** property of the corresponding dialog node, depending on the disambiguation options.
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

  public DialogSuggestion value(DialogSuggestionValue value) {
    
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

  public DialogSuggestionValue getValue() {
    return value;
  }


  @JsonProperty(JSON_PROPERTY_VALUE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setValue(DialogSuggestionValue value) {
    this.value = value;
  }

  public DialogSuggestion output(Map<String, Object> output) {
    
    this.output = output;
    return this;
  }

  public DialogSuggestion putOutputItem(String key, Object outputItem) {
    if (this.output == null) {
      this.output = new HashMap<>();
    }
    this.output.put(key, outputItem);
    return this;
  }

  /**
   * The dialog output that will be returned from the watsonx Assistant service if the user selects the corresponding option.
   * @return output
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_OUTPUT)
  @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, Object> getOutput() {
    return output;
  }


  @JsonProperty(JSON_PROPERTY_OUTPUT)
  @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.USE_DEFAULTS)
  public void setOutput(Map<String, Object> output) {
    this.output = output;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DialogSuggestion dialogSuggestion = (DialogSuggestion) o;
    return Objects.equals(this.label, dialogSuggestion.label) &&
        Objects.equals(this.value, dialogSuggestion.value) &&
        Objects.equals(this.output, dialogSuggestion.output);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, value, output);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DialogSuggestion {\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    output: ").append(toIndentedString(output)).append("\n");
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

