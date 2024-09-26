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
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * RuntimeResponseTypeConnectToAgentAgentAvailable
 */
@JsonPropertyOrder({
  RuntimeResponseTypeConnectToAgentAgentAvailable.JSON_PROPERTY_MESSAGE
})
@JsonTypeName("RuntimeResponseTypeConnectToAgent_agent_available")
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class RuntimeResponseTypeConnectToAgentAgentAvailable {
  public static final String JSON_PROPERTY_MESSAGE = "message";
  private String message;

  public RuntimeResponseTypeConnectToAgentAgentAvailable() {
  }

  public RuntimeResponseTypeConnectToAgentAgentAvailable message(String message) {
    
    this.message = message;
    return this;
  }

  /**
   * The text of the message.
   * @return message
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MESSAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getMessage() {
    return message;
  }


  @JsonProperty(JSON_PROPERTY_MESSAGE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RuntimeResponseTypeConnectToAgentAgentAvailable runtimeResponseTypeConnectToAgentAgentAvailable = (RuntimeResponseTypeConnectToAgentAgentAvailable) o;
    return Objects.equals(this.message, runtimeResponseTypeConnectToAgentAgentAvailable.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RuntimeResponseTypeConnectToAgentAgentAvailable {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

