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
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * RuntimeResponseTypeOption
 */
@JsonPropertyOrder({
  RuntimeResponseTypeOption.JSON_PROPERTY_RESPONSE_TYPE,
  RuntimeResponseTypeOption.JSON_PROPERTY_TITLE,
  RuntimeResponseTypeOption.JSON_PROPERTY_DESCRIPTION,
  RuntimeResponseTypeOption.JSON_PROPERTY_PREFERENCE,
  RuntimeResponseTypeOption.JSON_PROPERTY_OPTIONS,
  RuntimeResponseTypeOption.JSON_PROPERTY_CHANNELS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class RuntimeResponseTypeOption {
  public static final String JSON_PROPERTY_RESPONSE_TYPE = "response_type";
  private String responseType;

  public static final String JSON_PROPERTY_TITLE = "title";
  private String title;

  public static final String JSON_PROPERTY_DESCRIPTION = "description";
  private String description;

  /**
   * The preferred type of control to display.
   */
  public enum PreferenceEnum {
    DROPDOWN("dropdown"),
    
    BUTTON("button");

    private String value;

    PreferenceEnum(String value) {
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
    public static PreferenceEnum fromValue(String value) {
      for (PreferenceEnum b : PreferenceEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_PREFERENCE = "preference";
  private PreferenceEnum preference;

  public static final String JSON_PROPERTY_OPTIONS = "options";
  private List<DialogNodeOutputOptionsElement> options = new ArrayList<>();

  public static final String JSON_PROPERTY_CHANNELS = "channels";
  private List<ResponseGenericChannel> channels = new ArrayList<>();

  public RuntimeResponseTypeOption() {
  }

  public RuntimeResponseTypeOption responseType(String responseType) {
    
    this.responseType = responseType;
    return this;
  }

  /**
   * The type of response returned by the dialog node. The specified response type must be supported by the client application or channel.
   * @return responseType
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_RESPONSE_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getResponseType() {
    return responseType;
  }


  @JsonProperty(JSON_PROPERTY_RESPONSE_TYPE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setResponseType(String responseType) {
    this.responseType = responseType;
  }

  public RuntimeResponseTypeOption title(String title) {
    
    this.title = title;
    return this;
  }

  /**
   * The title or introductory text to show before the response.
   * @return title
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TITLE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getTitle() {
    return title;
  }


  @JsonProperty(JSON_PROPERTY_TITLE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTitle(String title) {
    this.title = title;
  }

  public RuntimeResponseTypeOption description(String description) {
    
    this.description = description;
    return this;
  }

  /**
   * The description to show with the the response.
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

  public RuntimeResponseTypeOption preference(PreferenceEnum preference) {
    
    this.preference = preference;
    return this;
  }

  /**
   * The preferred type of control to display.
   * @return preference
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_PREFERENCE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public PreferenceEnum getPreference() {
    return preference;
  }


  @JsonProperty(JSON_PROPERTY_PREFERENCE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPreference(PreferenceEnum preference) {
    this.preference = preference;
  }

  public RuntimeResponseTypeOption options(List<DialogNodeOutputOptionsElement> options) {
    
    this.options = options;
    return this;
  }

  public RuntimeResponseTypeOption addOptionsItem(DialogNodeOutputOptionsElement optionsItem) {
    if (this.options == null) {
      this.options = new ArrayList<>();
    }
    this.options.add(optionsItem);
    return this;
  }

  /**
   * An array of objects describing the options from which the user can choose.
   * @return options
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_OPTIONS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<DialogNodeOutputOptionsElement> getOptions() {
    return options;
  }


  @JsonProperty(JSON_PROPERTY_OPTIONS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setOptions(List<DialogNodeOutputOptionsElement> options) {
    this.options = options;
  }

  public RuntimeResponseTypeOption channels(List<ResponseGenericChannel> channels) {
    
    this.channels = channels;
    return this;
  }

  public RuntimeResponseTypeOption addChannelsItem(ResponseGenericChannel channelsItem) {
    if (this.channels == null) {
      this.channels = new ArrayList<>();
    }
    this.channels.add(channelsItem);
    return this;
  }

  /**
   * An array of objects specifying channels for which the response is intended. If **channels** is present, the response is intended for a built-in integration and should not be handled by an API client.
   * @return channels
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CHANNELS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<ResponseGenericChannel> getChannels() {
    return channels;
  }


  @JsonProperty(JSON_PROPERTY_CHANNELS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setChannels(List<ResponseGenericChannel> channels) {
    this.channels = channels;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RuntimeResponseTypeOption runtimeResponseTypeOption = (RuntimeResponseTypeOption) o;
    return Objects.equals(this.responseType, runtimeResponseTypeOption.responseType) &&
        Objects.equals(this.title, runtimeResponseTypeOption.title) &&
        Objects.equals(this.description, runtimeResponseTypeOption.description) &&
        Objects.equals(this.preference, runtimeResponseTypeOption.preference) &&
        Objects.equals(this.options, runtimeResponseTypeOption.options) &&
        Objects.equals(this.channels, runtimeResponseTypeOption.channels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(responseType, title, description, preference, options, channels);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RuntimeResponseTypeOption {\n");
    sb.append("    responseType: ").append(toIndentedString(responseType)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    preference: ").append(toIndentedString(preference)).append("\n");
    sb.append("    options: ").append(toIndentedString(options)).append("\n");
    sb.append("    channels: ").append(toIndentedString(channels)).append("\n");
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

