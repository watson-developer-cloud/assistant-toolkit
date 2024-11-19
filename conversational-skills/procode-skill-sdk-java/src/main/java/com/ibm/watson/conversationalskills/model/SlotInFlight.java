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
import com.ibm.watson.conversationalskills.model.EntitySchema;
import com.ibm.watson.conversationalskills.model.SlotValue;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * SlotInFlight
 */
@JsonPropertyOrder({
  SlotInFlight.JSON_PROPERTY_NAME,
  SlotInFlight.JSON_PROPERTY_VALUE,
  SlotInFlight.JSON_PROPERTY_EVENT,
  SlotInFlight.JSON_PROPERTY_TYPE,
  SlotInFlight.JSON_PROPERTY_SCHEMA,
  SlotInFlight.JSON_PROPERTY_DESCRIPTION,
  SlotInFlight.JSON_PROPERTY_VALIDATION_ERROR,
  SlotInFlight.JSON_PROPERTY_PROMPT
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class SlotInFlight {
  public static final String JSON_PROPERTY_NAME = "name";
  private String name;

  public static final String JSON_PROPERTY_VALUE = "value";
  private SlotValue value;

  /**
   * Type of the event: - &#x60;fill&#x60;: the value has been newly filled. - &#x60;repair&#x60;: previous value has been modified. - &#x60;refine&#x60;: previous ambiguous value has been resolved with a selection
   */
  public enum EventEnum {
    FILL("fill"),
    
    REPAIR("repair"),
    
    REFINE("refine");

    private String value;

    EventEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static EventEnum fromValue(String value) {
      for (EventEnum b : EventEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_EVENT = "event";
  private EventEnum event;

  /**
   * The type of the slot value.
   */
  public enum TypeEnum {
    STRING("string"),
    
    NUMBER("number"),
    
    DATE("date"),
    
    TIME("time"),
    
    REGEX("regex"),
    
    ENTITY("entity"),
    
    CONFIRMATION("confirmation"),
    
    ANY("any");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_TYPE = "type";
  private TypeEnum type;

  public static final String JSON_PROPERTY_SCHEMA = "schema";
  private EntitySchema schema;

  public static final String JSON_PROPERTY_DESCRIPTION = "description";
  private String description;

  public static final String JSON_PROPERTY_VALIDATION_ERROR = "validation_error";
  private String validationError;

  public static final String JSON_PROPERTY_PROMPT = "prompt";
  private String prompt;

  public SlotInFlight() {
  }

  public SlotInFlight name(String name) {
    
    this.name = name;
    return this;
  }

  /**
   * A unique name for the slot.
   * @return name
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getName() {
    return name;
  }


  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setName(String name) {
    this.name = name;
  }

  public SlotInFlight value(SlotValue value) {
    
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_VALUE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SlotValue getValue() {
    return value;
  }


  @JsonProperty(JSON_PROPERTY_VALUE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setValue(SlotValue value) {
    this.value = value;
  }

  public SlotInFlight event(EventEnum event) {
    
    this.event = event;
    return this;
  }

  /**
   * Type of the event: - &#x60;fill&#x60;: the value has been newly filled. - &#x60;repair&#x60;: previous value has been modified. - &#x60;refine&#x60;: previous ambiguous value has been resolved with a selection
   * @return event
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_EVENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public EventEnum getEvent() {
    return event;
  }


  @JsonProperty(JSON_PROPERTY_EVENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEvent(EventEnum event) {
    this.event = event;
  }

  public SlotInFlight type(TypeEnum type) {
    
    this.type = type;
    return this;
  }

  /**
   * The type of the slot value.
   * @return type
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public TypeEnum getType() {
    return type;
  }


  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setType(TypeEnum type) {
    this.type = type;
  }

  public SlotInFlight schema(EntitySchema schema) {
    
    this.schema = schema;
    return this;
  }

  /**
   * Get schema
   * @return schema
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_SCHEMA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public EntitySchema getSchema() {
    return schema;
  }


  @JsonProperty(JSON_PROPERTY_SCHEMA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSchema(EntitySchema schema) {
    this.schema = schema;
  }

  public SlotInFlight description(String description) {
    
    this.description = description;
    return this;
  }

  /**
   * A description for the slot.
   * @return description
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_DESCRIPTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getDescription() {
    return description;
  }


  @JsonProperty(JSON_PROPERTY_DESCRIPTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDescription(String description) {
    this.description = description;
  }

  public SlotInFlight validationError(String validationError) {
    
    this.validationError = validationError;
    return this;
  }

  /**
   * An error message to display to the user in case the slot value is not valid per the business rules.
   * @return validationError
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_VALIDATION_ERROR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getValidationError() {
    return validationError;
  }


  @JsonProperty(JSON_PROPERTY_VALIDATION_ERROR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setValidationError(String validationError) {
    this.validationError = validationError;
  }

  public SlotInFlight prompt(String prompt) {
    
    this.prompt = prompt;
    return this;
  }

  /**
   * The prompt to present to the user.
   * @return prompt
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PROMPT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getPrompt() {
    return prompt;
  }


  @JsonProperty(JSON_PROPERTY_PROMPT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SlotInFlight slotInFlight = (SlotInFlight) o;
    return Objects.equals(this.name, slotInFlight.name) &&
        Objects.equals(this.value, slotInFlight.value) &&
        Objects.equals(this.event, slotInFlight.event) &&
        Objects.equals(this.type, slotInFlight.type) &&
        Objects.equals(this.schema, slotInFlight.schema) &&
        Objects.equals(this.description, slotInFlight.description) &&
        Objects.equals(this.validationError, slotInFlight.validationError) &&
        Objects.equals(this.prompt, slotInFlight.prompt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, value, event, type, schema, description, validationError, prompt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SlotInFlight {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    event: ").append(toIndentedString(event)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    schema: ").append(toIndentedString(schema)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    validationError: ").append(toIndentedString(validationError)).append("\n");
    sb.append("    prompt: ").append(toIndentedString(prompt)).append("\n");
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

