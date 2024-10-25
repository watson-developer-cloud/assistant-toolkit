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
import com.ibm.watson.conversationalskills.model.MessageContextGlobal;
import com.ibm.watson.conversationalskills.model.MessageContextSkills;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * MessageContext
 */
@JsonPropertyOrder({
  MessageContext.JSON_PROPERTY_GLOBAL,
  MessageContext.JSON_PROPERTY_SKILLS,
  MessageContext.JSON_PROPERTY_INTEGRATIONS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class MessageContext {
  public static final String JSON_PROPERTY_GLOBAL = "global";
  private MessageContextGlobal global;

  public static final String JSON_PROPERTY_SKILLS = "skills";
  private MessageContextSkills skills;

  public static final String JSON_PROPERTY_INTEGRATIONS = "integrations";
  private Object integrations;

  public MessageContext() {
  }

  public MessageContext global(MessageContextGlobal global) {
    
    this.global = global;
    return this;
  }

  /**
   * Get global
   * @return global
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_GLOBAL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public MessageContextGlobal getGlobal() {
    return global;
  }


  @JsonProperty(JSON_PROPERTY_GLOBAL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setGlobal(MessageContextGlobal global) {
    this.global = global;
  }

  public MessageContext skills(MessageContextSkills skills) {
    
    this.skills = skills;
    return this;
  }

  /**
   * Get skills
   * @return skills
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SKILLS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public MessageContextSkills getSkills() {
    return skills;
  }


  @JsonProperty(JSON_PROPERTY_SKILLS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSkills(MessageContextSkills skills) {
    this.skills = skills;
  }

  public MessageContext integrations(Object integrations) {
    
    this.integrations = integrations;
    return this;
  }

  /**
   * An object containing context data that is specific to particular integrations. For more information, see the [documentation](https://cloud.ibm.com/docs/assistant?topic&#x3D;assistant-dialog-integrations). This will include &#x60;chat.private.jwt&#x60; containing Cade&#39;s SSO security token.
   * @return integrations
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_INTEGRATIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Object getIntegrations() {
    return integrations;
  }


  @JsonProperty(JSON_PROPERTY_INTEGRATIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIntegrations(Object integrations) {
    this.integrations = integrations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageContext messageContext = (MessageContext) o;
    return Objects.equals(this.global, messageContext.global) &&
        Objects.equals(this.skills, messageContext.skills) &&
        Objects.equals(this.integrations, messageContext.integrations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(global, skills, integrations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MessageContext {\n");
    sb.append("    global: ").append(toIndentedString(global)).append("\n");
    sb.append("    skills: ").append(toIndentedString(skills)).append("\n");
    sb.append("    integrations: ").append(toIndentedString(integrations)).append("\n");
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

