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
import com.ibm.watson.conversationalskills.model.ConversationalSkillOutput;
import com.ibm.watson.conversationalskills.model.ConversationalSkillStateOutput;
import com.ibm.watson.conversationalskills.model.OrchestrationResponseResolver;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Response expected from Conversational skill.
 */
@JsonPropertyOrder({
  OrchestrationResponse.JSON_PROPERTY_OUTPUT,
  OrchestrationResponse.JSON_PROPERTY_STATE,
  OrchestrationResponse.JSON_PROPERTY_RESOLVER
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class OrchestrationResponse {
  public static final String JSON_PROPERTY_OUTPUT = "output";
  private ConversationalSkillOutput output;

  public static final String JSON_PROPERTY_STATE = "state";
  private ConversationalSkillStateOutput state;

  public static final String JSON_PROPERTY_RESOLVER = "resolver";
  private OrchestrationResponseResolver resolver;

  public OrchestrationResponse() {
  }

  public OrchestrationResponse output(ConversationalSkillOutput output) {
    
    this.output = output;
    return this;
  }

  /**
   * Get output
   * @return output
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_OUTPUT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ConversationalSkillOutput getOutput() {
    return output;
  }


  @JsonProperty(JSON_PROPERTY_OUTPUT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setOutput(ConversationalSkillOutput output) {
    this.output = output;
  }

  public OrchestrationResponse state(ConversationalSkillStateOutput state) {
    
    this.state = state;
    return this;
  }

  /**
   * Get state
   * @return state
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_STATE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ConversationalSkillStateOutput getState() {
    return state;
  }


  @JsonProperty(JSON_PROPERTY_STATE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setState(ConversationalSkillStateOutput state) {
    this.state = state;
  }

  public OrchestrationResponse resolver(OrchestrationResponseResolver resolver) {
    
    this.resolver = resolver;
    return this;
  }

  /**
   * Get resolver
   * @return resolver
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_RESOLVER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public OrchestrationResponseResolver getResolver() {
    return resolver;
  }


  @JsonProperty(JSON_PROPERTY_RESOLVER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setResolver(OrchestrationResponseResolver resolver) {
    this.resolver = resolver;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrchestrationResponse orchestrationResponse = (OrchestrationResponse) o;
    return Objects.equals(this.output, orchestrationResponse.output) &&
        Objects.equals(this.state, orchestrationResponse.state) &&
        Objects.equals(this.resolver, orchestrationResponse.resolver);
  }

  @Override
  public int hashCode() {
    return Objects.hash(output, state, resolver);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrchestrationResponse {\n");
    sb.append("    output: ").append(toIndentedString(output)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    resolver: ").append(toIndentedString(resolver)).append("\n");
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

