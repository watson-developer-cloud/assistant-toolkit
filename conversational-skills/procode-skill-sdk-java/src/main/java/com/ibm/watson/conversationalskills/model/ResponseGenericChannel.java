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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * ResponseGenericChannel
 */
@JsonPropertyOrder({
  ResponseGenericChannel.JSON_PROPERTY_CHANNEL
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class ResponseGenericChannel {
  public static final String JSON_PROPERTY_CHANNEL = "channel";
  private String channel;

  public ResponseGenericChannel() {
  }

  public ResponseGenericChannel channel(String channel) {
    
    this.channel = channel;
    return this;
  }

  /**
   * A channel for which the response is intended.
   * @return channel
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CHANNEL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getChannel() {
    return channel;
  }


  @JsonProperty(JSON_PROPERTY_CHANNEL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setChannel(String channel) {
    this.channel = channel;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResponseGenericChannel responseGenericChannel = (ResponseGenericChannel) o;
    return Objects.equals(this.channel, responseGenericChannel.channel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(channel);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResponseGenericChannel {\n");
    sb.append("    channel: ").append(toIndentedString(channel)).append("\n");
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

