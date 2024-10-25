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
import com.ibm.watson.conversationalskills.model.ChannelTransferInfo;
import com.ibm.watson.conversationalskills.model.ResponseGenericChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * RuntimeResponseTypeChannelTransfer
 */
@JsonPropertyOrder({
  RuntimeResponseTypeChannelTransfer.JSON_PROPERTY_RESPONSE_TYPE,
  RuntimeResponseTypeChannelTransfer.JSON_PROPERTY_MESSAGE_TO_USER,
  RuntimeResponseTypeChannelTransfer.JSON_PROPERTY_TRANSFER_INFO,
  RuntimeResponseTypeChannelTransfer.JSON_PROPERTY_CHANNELS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.9.0")
public class RuntimeResponseTypeChannelTransfer {
  public static final String JSON_PROPERTY_RESPONSE_TYPE = "response_type";
  private String responseType;

  public static final String JSON_PROPERTY_MESSAGE_TO_USER = "message_to_user";
  private String messageToUser;

  public static final String JSON_PROPERTY_TRANSFER_INFO = "transfer_info";
  private ChannelTransferInfo transferInfo;

  public static final String JSON_PROPERTY_CHANNELS = "channels";
  private List<ResponseGenericChannel> channels = new ArrayList<>();

  public RuntimeResponseTypeChannelTransfer() {
  }

  public RuntimeResponseTypeChannelTransfer responseType(String responseType) {
    
    this.responseType = responseType;
    return this;
  }

  /**
   * The type of response returned by the dialog node. The specified response type must be supported by the client application or channel.    **Note:** The &#x60;channel_transfer&#x60; response type is not supported on IBM Cloud Pak for Data.
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

  public RuntimeResponseTypeChannelTransfer messageToUser(String messageToUser) {
    
    this.messageToUser = messageToUser;
    return this;
  }

  /**
   * The message to display to the user when initiating a channel transfer.
   * @return messageToUser
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_MESSAGE_TO_USER)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getMessageToUser() {
    return messageToUser;
  }


  @JsonProperty(JSON_PROPERTY_MESSAGE_TO_USER)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setMessageToUser(String messageToUser) {
    this.messageToUser = messageToUser;
  }

  public RuntimeResponseTypeChannelTransfer transferInfo(ChannelTransferInfo transferInfo) {
    
    this.transferInfo = transferInfo;
    return this;
  }

  /**
   * Get transferInfo
   * @return transferInfo
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TRANSFER_INFO)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public ChannelTransferInfo getTransferInfo() {
    return transferInfo;
  }


  @JsonProperty(JSON_PROPERTY_TRANSFER_INFO)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTransferInfo(ChannelTransferInfo transferInfo) {
    this.transferInfo = transferInfo;
  }

  public RuntimeResponseTypeChannelTransfer channels(List<ResponseGenericChannel> channels) {
    
    this.channels = channels;
    return this;
  }

  public RuntimeResponseTypeChannelTransfer addChannelsItem(ResponseGenericChannel channelsItem) {
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
    RuntimeResponseTypeChannelTransfer runtimeResponseTypeChannelTransfer = (RuntimeResponseTypeChannelTransfer) o;
    return Objects.equals(this.responseType, runtimeResponseTypeChannelTransfer.responseType) &&
        Objects.equals(this.messageToUser, runtimeResponseTypeChannelTransfer.messageToUser) &&
        Objects.equals(this.transferInfo, runtimeResponseTypeChannelTransfer.transferInfo) &&
        Objects.equals(this.channels, runtimeResponseTypeChannelTransfer.channels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(responseType, messageToUser, transferInfo, channels);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RuntimeResponseTypeChannelTransfer {\n");
    sb.append("    responseType: ").append(toIndentedString(responseType)).append("\n");
    sb.append("    messageToUser: ").append(toIndentedString(messageToUser)).append("\n");
    sb.append("    transferInfo: ").append(toIndentedString(transferInfo)).append("\n");
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

