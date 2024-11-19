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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * RuntimeResponseTypeUserDefined
 */
@JsonPropertyOrder({
  RuntimeResponseTypeUserDefined.JSON_PROPERTY_RESPONSE_TYPE,
  RuntimeResponseTypeUserDefined.JSON_PROPERTY_USER_DEFINED,
  RuntimeResponseTypeUserDefined.JSON_PROPERTY_CHANNELS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class RuntimeResponseTypeUserDefined {
  public static final String JSON_PROPERTY_RESPONSE_TYPE = "response_type";
  private String responseType;

  public static final String JSON_PROPERTY_USER_DEFINED = "user_defined";
  private Map<String, Object> userDefined = new HashMap<>();

  public static final String JSON_PROPERTY_CHANNELS = "channels";
  private List<ResponseGenericChannel> channels;

  public RuntimeResponseTypeUserDefined() {
  }

  public RuntimeResponseTypeUserDefined responseType(String responseType) {
    
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

  public RuntimeResponseTypeUserDefined userDefined(Map<String, Object> userDefined) {
    
    this.userDefined = userDefined;
    return this;
  }

  public RuntimeResponseTypeUserDefined putUserDefinedItem(String key, Object userDefinedItem) {
    this.userDefined.put(key, userDefinedItem);
    return this;
  }

  /**
   * An object containing any properties for the user-defined response type.
   * @return userDefined
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_USER_DEFINED)
  @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.ALWAYS)

  public Map<String, Object> getUserDefined() {
    return userDefined;
  }


  @JsonProperty(JSON_PROPERTY_USER_DEFINED)
  @JsonInclude(content = JsonInclude.Include.ALWAYS, value = JsonInclude.Include.ALWAYS)
  public void setUserDefined(Map<String, Object> userDefined) {
    this.userDefined = userDefined;
  }

  public RuntimeResponseTypeUserDefined channels(List<ResponseGenericChannel> channels) {
    
    this.channels = channels;
    return this;
  }

  public RuntimeResponseTypeUserDefined addChannelsItem(ResponseGenericChannel channelsItem) {
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
    RuntimeResponseTypeUserDefined runtimeResponseTypeUserDefined = (RuntimeResponseTypeUserDefined) o;
    return Objects.equals(this.responseType, runtimeResponseTypeUserDefined.responseType) &&
        Objects.equals(this.userDefined, runtimeResponseTypeUserDefined.userDefined) &&
        Objects.equals(this.channels, runtimeResponseTypeUserDefined.channels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(responseType, userDefined, channels);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RuntimeResponseTypeUserDefined {\n");
    sb.append("    responseType: ").append(toIndentedString(responseType)).append("\n");
    sb.append("    userDefined: ").append(toIndentedString(userDefined)).append("\n");
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

