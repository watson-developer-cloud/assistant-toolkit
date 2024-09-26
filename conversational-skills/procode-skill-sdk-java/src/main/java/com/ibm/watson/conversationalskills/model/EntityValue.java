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
 * EntityValue
 */
@JsonPropertyOrder({
  EntityValue.JSON_PROPERTY_VALUE,
  EntityValue.JSON_PROPERTY_SYNONYMS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class EntityValue {
  public static final String JSON_PROPERTY_VALUE = "value";
  private String value;

  public static final String JSON_PROPERTY_SYNONYMS = "synonyms";
  private List<String> synonyms = new ArrayList<>();

  public EntityValue() {
  }

  public EntityValue value(String value) {
    
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

  public String getValue() {
    return value;
  }


  @JsonProperty(JSON_PROPERTY_VALUE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setValue(String value) {
    this.value = value;
  }

  public EntityValue synonyms(List<String> synonyms) {
    
    this.synonyms = synonyms;
    return this;
  }

  public EntityValue addSynonymsItem(String synonymsItem) {
    if (this.synonyms == null) {
      this.synonyms = new ArrayList<>();
    }
    this.synonyms.add(synonymsItem);
    return this;
  }

  /**
   * Get synonyms
   * @return synonyms
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SYNONYMS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getSynonyms() {
    return synonyms;
  }


  @JsonProperty(JSON_PROPERTY_SYNONYMS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSynonyms(List<String> synonyms) {
    this.synonyms = synonyms;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntityValue entityValue = (EntityValue) o;
    return Objects.equals(this.value, entityValue.value) &&
        Objects.equals(this.synonyms, entityValue.synonyms);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, synonyms);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EntityValue {\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    synonyms: ").append(toIndentedString(synonyms)).append("\n");
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

