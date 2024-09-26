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
 * RuntimeResponseTypePause
 */
@JsonPropertyOrder({
  RuntimeResponseTypePause.JSON_PROPERTY_RESPONSE_TYPE,
  RuntimeResponseTypePause.JSON_PROPERTY_TIME,
  RuntimeResponseTypePause.JSON_PROPERTY_TYPING,
  RuntimeResponseTypePause.JSON_PROPERTY_CHANNELS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class RuntimeResponseTypePause {
  public static final String JSON_PROPERTY_RESPONSE_TYPE = "response_type";
  private String responseType;

  public static final String JSON_PROPERTY_TIME = "time";
  private Integer time;

  public static final String JSON_PROPERTY_TYPING = "typing";
  private Boolean typing;

  public static final String JSON_PROPERTY_CHANNELS = "channels";
  private List<ResponseGenericChannel> channels = new ArrayList<>();

  public RuntimeResponseTypePause() {
  }

  public RuntimeResponseTypePause responseType(String responseType) {
    
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

  public RuntimeResponseTypePause time(Integer time) {
    
    this.time = time;
    return this;
  }

  /**
   * How long to pause, in milliseconds.
   * @return time
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TIME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getTime() {
    return time;
  }


  @JsonProperty(JSON_PROPERTY_TIME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTime(Integer time) {
    this.time = time;
  }

  public RuntimeResponseTypePause typing(Boolean typing) {
    
    this.typing = typing;
    return this;
  }

  /**
   * Whether to send a \&quot;user is typing\&quot; event during the pause.
   * @return typing
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TYPING)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getTyping() {
    return typing;
  }


  @JsonProperty(JSON_PROPERTY_TYPING)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTyping(Boolean typing) {
    this.typing = typing;
  }

  public RuntimeResponseTypePause channels(List<ResponseGenericChannel> channels) {
    
    this.channels = channels;
    return this;
  }

  public RuntimeResponseTypePause addChannelsItem(ResponseGenericChannel channelsItem) {
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
    RuntimeResponseTypePause runtimeResponseTypePause = (RuntimeResponseTypePause) o;
    return Objects.equals(this.responseType, runtimeResponseTypePause.responseType) &&
        Objects.equals(this.time, runtimeResponseTypePause.time) &&
        Objects.equals(this.typing, runtimeResponseTypePause.typing) &&
        Objects.equals(this.channels, runtimeResponseTypePause.channels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(responseType, time, typing, channels);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RuntimeResponseTypePause {\n");
    sb.append("    responseType: ").append(toIndentedString(responseType)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
    sb.append("    typing: ").append(toIndentedString(typing)).append("\n");
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

