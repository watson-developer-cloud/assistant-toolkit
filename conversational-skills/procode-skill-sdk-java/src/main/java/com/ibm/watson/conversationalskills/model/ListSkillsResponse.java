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

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * ListSkillsResponse
 */
@JsonPropertyOrder({
  ListSkillsResponse.JSON_PROPERTY_CONVERSATIONAL_SKILLS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class ListSkillsResponse {
  public static final String JSON_PROPERTY_CONVERSATIONAL_SKILLS = "conversational_skills";
  private List<ConversationalSkill> conversationalSkills = new ArrayList<>();

  public ListSkillsResponse() {
  }

  public ListSkillsResponse conversationalSkills(List<ConversationalSkill> conversationalSkills) {
    
    this.conversationalSkills = conversationalSkills;
    return this;
  }

  public ListSkillsResponse addConversationalSkillsItem(ConversationalSkill conversationalSkillsItem) {
    if (this.conversationalSkills == null) {
      this.conversationalSkills = new ArrayList<>();
    }
    this.conversationalSkills.add(conversationalSkillsItem);
    return this;
  }

  /**
   * An array of conversational_skill objects. Each item in the array should be a unique conversational skill.
   * @return conversationalSkills
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CONVERSATIONAL_SKILLS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<ConversationalSkill> getConversationalSkills() {
    return conversationalSkills;
  }


  @JsonProperty(JSON_PROPERTY_CONVERSATIONAL_SKILLS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setConversationalSkills(List<ConversationalSkill> conversationalSkills) {
    this.conversationalSkills = conversationalSkills;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListSkillsResponse listSkillsResponse = (ListSkillsResponse) o;
    return Objects.equals(this.conversationalSkills, listSkillsResponse.conversationalSkills);
  }

  @Override
  public int hashCode() {
    return Objects.hash(conversationalSkills);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ListSkillsResponse {\n");
    sb.append("    conversationalSkills: ").append(toIndentedString(conversationalSkills)).append("\n");
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

