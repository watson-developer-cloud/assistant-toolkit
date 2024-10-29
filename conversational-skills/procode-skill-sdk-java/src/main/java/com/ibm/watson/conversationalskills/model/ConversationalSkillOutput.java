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
import com.ibm.watson.conversationalskills.model.ConversationalResponseGeneric;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Conversational skill output uses the same schema as that of WxA message output, with some enhancements. In addition, it includes an internally processed response_type for conveying slots in flight.
 */
@JsonPropertyOrder({
  ConversationalSkillOutput.JSON_PROPERTY_GENERIC
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class ConversationalSkillOutput {
  public static final String JSON_PROPERTY_GENERIC = "generic";
  private List<ConversationalResponseGeneric> generic = new ArrayList<>();

  public ConversationalSkillOutput() {
  }

  public ConversationalSkillOutput generic(List<ConversationalResponseGeneric> generic) {
    
    this.generic = generic;
    return this;
  }

  public ConversationalSkillOutput addGenericItem(ConversationalResponseGeneric genericItem) {
    if (this.generic == null) {
      this.generic = new ArrayList<>();
    }
    this.generic.add(genericItem);
    return this;
  }

  /**
   * Ordered list of responses.
   * @return generic
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_GENERIC)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<ConversationalResponseGeneric> getGeneric() {
    return generic;
  }


  @JsonProperty(JSON_PROPERTY_GENERIC)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setGeneric(List<ConversationalResponseGeneric> generic) {
    this.generic = generic;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConversationalSkillOutput conversationalSkillOutput = (ConversationalSkillOutput) o;
    return Objects.equals(this.generic, conversationalSkillOutput.generic);
  }

  @Override
  public int hashCode() {
    return Objects.hash(generic);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConversationalSkillOutput {\n");
    sb.append("    generic: ").append(toIndentedString(generic)).append("\n");
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

