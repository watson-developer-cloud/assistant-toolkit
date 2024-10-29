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
 * AssistantMessage
 */
@JsonPropertyOrder({
  AssistantMessage.JSON_PROPERTY_A
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class AssistantMessage {
  public static final String JSON_PROPERTY_A = "a";
  private String a;

  public AssistantMessage() {
  }

  public AssistantMessage a(String a) {
    
    this.a = a;
    return this;
  }

  /**
   * Assistant message
   * @return a
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_A)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getA() {
    return a;
  }


  @JsonProperty(JSON_PROPERTY_A)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setA(String a) {
    this.a = a;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AssistantMessage assistantMessage = (AssistantMessage) o;
    return Objects.equals(this.a, assistantMessage.a);
  }

  @Override
  public int hashCode() {
    return Objects.hash(a);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AssistantMessage {\n");
    sb.append("    a: ").append(toIndentedString(a)).append("\n");
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

