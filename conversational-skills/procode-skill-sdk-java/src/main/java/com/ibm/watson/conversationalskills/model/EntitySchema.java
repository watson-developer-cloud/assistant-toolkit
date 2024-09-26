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
 * Schema definition for the slot, required if type is entity.
 */
@JsonPropertyOrder({
  EntitySchema.JSON_PROPERTY_ENTITY,
  EntitySchema.JSON_PROPERTY_VALUES
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class EntitySchema {
  public static final String JSON_PROPERTY_ENTITY = "entity";
  private String entity;

  public static final String JSON_PROPERTY_VALUES = "values";
  private List<EntityValue> values = new ArrayList<>();

  public EntitySchema() {
  }

  public EntitySchema entity(String entity) {
    
    this.entity = entity;
    return this;
  }

  /**
   * Watson Assistant&#39;s entity schema name.
   * @return entity
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ENTITY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getEntity() {
    return entity;
  }


  @JsonProperty(JSON_PROPERTY_ENTITY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setEntity(String entity) {
    this.entity = entity;
  }

  public EntitySchema values(List<EntityValue> values) {
    
    this.values = values;
    return this;
  }

  public EntitySchema addValuesItem(EntityValue valuesItem) {
    if (this.values == null) {
      this.values = new ArrayList<>();
    }
    this.values.add(valuesItem);
    return this;
  }

  /**
   * Get values
   * @return values
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_VALUES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<EntityValue> getValues() {
    return values;
  }


  @JsonProperty(JSON_PROPERTY_VALUES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setValues(List<EntityValue> values) {
    this.values = values;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntitySchema entitySchema = (EntitySchema) o;
    return Objects.equals(this.entity, entitySchema.entity) &&
        Objects.equals(this.values, entitySchema.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entity, values);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EntitySchema {\n");
    sb.append("    entity: ").append(toIndentedString(entity)).append("\n");
    sb.append("    values: ").append(toIndentedString(values)).append("\n");
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

