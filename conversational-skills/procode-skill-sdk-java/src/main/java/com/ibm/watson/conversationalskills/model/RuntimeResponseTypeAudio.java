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
import com.ibm.watson.conversationalskills.model.ResponseGenericChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * RuntimeResponseTypeAudio
 */
@JsonPropertyOrder({
  RuntimeResponseTypeAudio.JSON_PROPERTY_RESPONSE_TYPE,
  RuntimeResponseTypeAudio.JSON_PROPERTY_SOURCE,
  RuntimeResponseTypeAudio.JSON_PROPERTY_TITLE,
  RuntimeResponseTypeAudio.JSON_PROPERTY_DESCRIPTION,
  RuntimeResponseTypeAudio.JSON_PROPERTY_CHANNELS,
  RuntimeResponseTypeAudio.JSON_PROPERTY_CHANNEL_OPTIONS,
  RuntimeResponseTypeAudio.JSON_PROPERTY_ALT_TEXT
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class RuntimeResponseTypeAudio {
  public static final String JSON_PROPERTY_RESPONSE_TYPE = "response_type";
  private String responseType;

  public static final String JSON_PROPERTY_SOURCE = "source";
  private String source;

  public static final String JSON_PROPERTY_TITLE = "title";
  private String title;

  public static final String JSON_PROPERTY_DESCRIPTION = "description";
  private String description;

  public static final String JSON_PROPERTY_CHANNELS = "channels";
  private List<ResponseGenericChannel> channels = new ArrayList<>();

  public static final String JSON_PROPERTY_CHANNEL_OPTIONS = "channel_options";
  private Object channelOptions;

  public static final String JSON_PROPERTY_ALT_TEXT = "alt_text";
  private String altText;

  public RuntimeResponseTypeAudio() {
  }

  public RuntimeResponseTypeAudio responseType(String responseType) {
    
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

  public RuntimeResponseTypeAudio source(String source) {
    
    this.source = source;
    return this;
  }

  /**
   * The &#x60;https:&#x60; URL of the audio clip.
   * @return source
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SOURCE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getSource() {
    return source;
  }


  @JsonProperty(JSON_PROPERTY_SOURCE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSource(String source) {
    this.source = source;
  }

  public RuntimeResponseTypeAudio title(String title) {
    
    this.title = title;
    return this;
  }

  /**
   * The title or introductory text to show before the response.
   * @return title
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TITLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getTitle() {
    return title;
  }


  @JsonProperty(JSON_PROPERTY_TITLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTitle(String title) {
    this.title = title;
  }

  public RuntimeResponseTypeAudio description(String description) {
    
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

  public RuntimeResponseTypeAudio channels(List<ResponseGenericChannel> channels) {
    
    this.channels = channels;
    return this;
  }

  public RuntimeResponseTypeAudio addChannelsItem(ResponseGenericChannel channelsItem) {
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

  public RuntimeResponseTypeAudio channelOptions(Object channelOptions) {
    
    this.channelOptions = channelOptions;
    return this;
  }

  /**
   * For internal use only.
   * @return channelOptions
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CHANNEL_OPTIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Object getChannelOptions() {
    return channelOptions;
  }


  @JsonProperty(JSON_PROPERTY_CHANNEL_OPTIONS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setChannelOptions(Object channelOptions) {
    this.channelOptions = channelOptions;
  }

  public RuntimeResponseTypeAudio altText(String altText) {
    
    this.altText = altText;
    return this;
  }

  /**
   * Descriptive text that can be used for screen readers or other situations where the audio player cannot be seen.
   * @return altText
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ALT_TEXT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getAltText() {
    return altText;
  }


  @JsonProperty(JSON_PROPERTY_ALT_TEXT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAltText(String altText) {
    this.altText = altText;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RuntimeResponseTypeAudio runtimeResponseTypeAudio = (RuntimeResponseTypeAudio) o;
    return Objects.equals(this.responseType, runtimeResponseTypeAudio.responseType) &&
        Objects.equals(this.source, runtimeResponseTypeAudio.source) &&
        Objects.equals(this.title, runtimeResponseTypeAudio.title) &&
        Objects.equals(this.description, runtimeResponseTypeAudio.description) &&
        Objects.equals(this.channels, runtimeResponseTypeAudio.channels) &&
        Objects.equals(this.channelOptions, runtimeResponseTypeAudio.channelOptions) &&
        Objects.equals(this.altText, runtimeResponseTypeAudio.altText);
  }

  @Override
  public int hashCode() {
    return Objects.hash(responseType, source, title, description, channels, channelOptions, altText);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RuntimeResponseTypeAudio {\n");
    sb.append("    responseType: ").append(toIndentedString(responseType)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    channels: ").append(toIndentedString(channels)).append("\n");
    sb.append("    channelOptions: ").append(toIndentedString(channelOptions)).append("\n");
    sb.append("    altText: ").append(toIndentedString(altText)).append("\n");
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

