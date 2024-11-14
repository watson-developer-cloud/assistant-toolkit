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
 * ConversationalSkillActionRegistration
 */
@JsonPropertyOrder({
  ConversationalSkillActionRegistration.JSON_PROPERTY_PROVIDER_ID,
  ConversationalSkillActionRegistration.JSON_PROPERTY_CONVERSATIONAL_SKILL_ID,
  ConversationalSkillActionRegistration.JSON_PROPERTY_CONVERSATIONAL_SKILL_REVISION_ID
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class ConversationalSkillActionRegistration {
  public static final String JSON_PROPERTY_PROVIDER_ID = "provider_id";
  private String providerId;

  public static final String JSON_PROPERTY_CONVERSATIONAL_SKILL_ID = "conversational_skill_id";
  private String conversationalSkillId;

  public static final String JSON_PROPERTY_CONVERSATIONAL_SKILL_REVISION_ID = "conversational_skill_revision_id";
  private String conversationalSkillRevisionId;

  public ConversationalSkillActionRegistration() {
  }

  public ConversationalSkillActionRegistration providerId(String providerId) {
    
    this.providerId = providerId;
    return this;
  }

  /**
   * The ID of the provider of this conversational skill.
   * @return providerId
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PROVIDER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getProviderId() {
    return providerId;
  }


  @JsonProperty(JSON_PROPERTY_PROVIDER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProviderId(String providerId) {
    this.providerId = providerId;
  }

  public ConversationalSkillActionRegistration conversationalSkillId(String conversationalSkillId) {
    
    this.conversationalSkillId = conversationalSkillId;
    return this;
  }

  /**
   * The ID of this conversational skill.
   * @return conversationalSkillId
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CONVERSATIONAL_SKILL_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getConversationalSkillId() {
    return conversationalSkillId;
  }


  @JsonProperty(JSON_PROPERTY_CONVERSATIONAL_SKILL_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setConversationalSkillId(String conversationalSkillId) {
    this.conversationalSkillId = conversationalSkillId;
  }

  public ConversationalSkillActionRegistration conversationalSkillRevisionId(String conversationalSkillRevisionId) {
    
    this.conversationalSkillRevisionId = conversationalSkillRevisionId;
    return this;
  }

  /**
   * The revision ID of this conversational skill.
   * @return conversationalSkillRevisionId
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CONVERSATIONAL_SKILL_REVISION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getConversationalSkillRevisionId() {
    return conversationalSkillRevisionId;
  }


  @JsonProperty(JSON_PROPERTY_CONVERSATIONAL_SKILL_REVISION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setConversationalSkillRevisionId(String conversationalSkillRevisionId) {
    this.conversationalSkillRevisionId = conversationalSkillRevisionId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConversationalSkillActionRegistration conversationalSkillActionRegistration = (ConversationalSkillActionRegistration) o;
    return Objects.equals(this.providerId, conversationalSkillActionRegistration.providerId) &&
        Objects.equals(this.conversationalSkillId, conversationalSkillActionRegistration.conversationalSkillId) &&
        Objects.equals(this.conversationalSkillRevisionId, conversationalSkillActionRegistration.conversationalSkillRevisionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(providerId, conversationalSkillId, conversationalSkillRevisionId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConversationalSkillActionRegistration {\n");
    sb.append("    providerId: ").append(toIndentedString(providerId)).append("\n");
    sb.append("    conversationalSkillId: ").append(toIndentedString(conversationalSkillId)).append("\n");
    sb.append("    conversationalSkillRevisionId: ").append(toIndentedString(conversationalSkillRevisionId)).append("\n");
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

