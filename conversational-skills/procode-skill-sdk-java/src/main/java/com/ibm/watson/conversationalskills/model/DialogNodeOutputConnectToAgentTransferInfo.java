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
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Routing or other contextual information to be used by target service desk systems.
 */
@JsonPropertyOrder({
  DialogNodeOutputConnectToAgentTransferInfo.JSON_PROPERTY_TARGET
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class DialogNodeOutputConnectToAgentTransferInfo {
  public static final String JSON_PROPERTY_TARGET = "target";
  private Map<String, Map<String, Object>> target = new HashMap<>();

  public DialogNodeOutputConnectToAgentTransferInfo() {
  }

  public DialogNodeOutputConnectToAgentTransferInfo target(Map<String, Map<String, Object>> target) {
    
    this.target = target;
    return this;
  }

  public DialogNodeOutputConnectToAgentTransferInfo putTargetItem(String key, Map<String, Object> targetItem) {
    if (this.target == null) {
      this.target = new HashMap<>();
    }
    this.target.put(key, targetItem);
    return this;
  }

  /**
   * Get target
   * @return target
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TARGET)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, Map<String, Object>> getTarget() {
    return target;
  }


  @JsonProperty(JSON_PROPERTY_TARGET)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTarget(Map<String, Map<String, Object>> target) {
    this.target = target;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DialogNodeOutputConnectToAgentTransferInfo dialogNodeOutputConnectToAgentTransferInfo = (DialogNodeOutputConnectToAgentTransferInfo) o;
    return Objects.equals(this.target, dialogNodeOutputConnectToAgentTransferInfo.target);
  }

  @Override
  public int hashCode() {
    return Objects.hash(target);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DialogNodeOutputConnectToAgentTransferInfo {\n");
    sb.append("    target: ").append(toIndentedString(target)).append("\n");
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
