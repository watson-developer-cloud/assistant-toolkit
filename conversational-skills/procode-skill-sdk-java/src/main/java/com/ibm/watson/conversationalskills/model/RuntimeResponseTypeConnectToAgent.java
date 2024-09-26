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
 * RuntimeResponseTypeConnectToAgent
 */
@JsonPropertyOrder({
  RuntimeResponseTypeConnectToAgent.JSON_PROPERTY_RESPONSE_TYPE,
  RuntimeResponseTypeConnectToAgent.JSON_PROPERTY_MESSAGE_TO_HUMAN_AGENT,
  RuntimeResponseTypeConnectToAgent.JSON_PROPERTY_AGENT_AVAILABLE,
  RuntimeResponseTypeConnectToAgent.JSON_PROPERTY_AGENT_UNAVAILABLE,
  RuntimeResponseTypeConnectToAgent.JSON_PROPERTY_TRANSFER_INFO,
  RuntimeResponseTypeConnectToAgent.JSON_PROPERTY_TOPIC,
  RuntimeResponseTypeConnectToAgent.JSON_PROPERTY_CHANNELS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class RuntimeResponseTypeConnectToAgent {
  public static final String JSON_PROPERTY_RESPONSE_TYPE = "response_type";
  private String responseType;

  public static final String JSON_PROPERTY_MESSAGE_TO_HUMAN_AGENT = "message_to_human_agent";
  private String messageToHumanAgent;

  public static final String JSON_PROPERTY_AGENT_AVAILABLE = "agent_available";
  private RuntimeResponseTypeConnectToAgentAgentAvailable agentAvailable;

  public static final String JSON_PROPERTY_AGENT_UNAVAILABLE = "agent_unavailable";
  private RuntimeResponseTypeConnectToAgentAgentUnavailable agentUnavailable;

  public static final String JSON_PROPERTY_TRANSFER_INFO = "transfer_info";
  private DialogNodeOutputConnectToAgentTransferInfo transferInfo;

  public static final String JSON_PROPERTY_TOPIC = "topic";
  private String topic;

  public static final String JSON_PROPERTY_CHANNELS = "channels";
  private List<ResponseGenericChannel> channels = new ArrayList<>();

  public RuntimeResponseTypeConnectToAgent() {
  }

  public RuntimeResponseTypeConnectToAgent responseType(String responseType) {
    
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

  public RuntimeResponseTypeConnectToAgent messageToHumanAgent(String messageToHumanAgent) {
    
    this.messageToHumanAgent = messageToHumanAgent;
    return this;
  }

  /**
   * A message to be sent to the human agent who will be taking over the conversation.
   * @return messageToHumanAgent
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_MESSAGE_TO_HUMAN_AGENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getMessageToHumanAgent() {
    return messageToHumanAgent;
  }


  @JsonProperty(JSON_PROPERTY_MESSAGE_TO_HUMAN_AGENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setMessageToHumanAgent(String messageToHumanAgent) {
    this.messageToHumanAgent = messageToHumanAgent;
  }

  public RuntimeResponseTypeConnectToAgent agentAvailable(RuntimeResponseTypeConnectToAgentAgentAvailable agentAvailable) {
    
    this.agentAvailable = agentAvailable;
    return this;
  }

  /**
   * Get agentAvailable
   * @return agentAvailable
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_AGENT_AVAILABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public RuntimeResponseTypeConnectToAgentAgentAvailable getAgentAvailable() {
    return agentAvailable;
  }


  @JsonProperty(JSON_PROPERTY_AGENT_AVAILABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAgentAvailable(RuntimeResponseTypeConnectToAgentAgentAvailable agentAvailable) {
    this.agentAvailable = agentAvailable;
  }

  public RuntimeResponseTypeConnectToAgent agentUnavailable(RuntimeResponseTypeConnectToAgentAgentUnavailable agentUnavailable) {
    
    this.agentUnavailable = agentUnavailable;
    return this;
  }

  /**
   * Get agentUnavailable
   * @return agentUnavailable
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_AGENT_UNAVAILABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public RuntimeResponseTypeConnectToAgentAgentUnavailable getAgentUnavailable() {
    return agentUnavailable;
  }


  @JsonProperty(JSON_PROPERTY_AGENT_UNAVAILABLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAgentUnavailable(RuntimeResponseTypeConnectToAgentAgentUnavailable agentUnavailable) {
    this.agentUnavailable = agentUnavailable;
  }

  public RuntimeResponseTypeConnectToAgent transferInfo(DialogNodeOutputConnectToAgentTransferInfo transferInfo) {
    
    this.transferInfo = transferInfo;
    return this;
  }

  /**
   * Get transferInfo
   * @return transferInfo
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TRANSFER_INFO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public DialogNodeOutputConnectToAgentTransferInfo getTransferInfo() {
    return transferInfo;
  }


  @JsonProperty(JSON_PROPERTY_TRANSFER_INFO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTransferInfo(DialogNodeOutputConnectToAgentTransferInfo transferInfo) {
    this.transferInfo = transferInfo;
  }

  public RuntimeResponseTypeConnectToAgent topic(String topic) {
    
    this.topic = topic;
    return this;
  }

  /**
   * A label identifying the topic of the conversation, derived from the **title** property of the relevant node or the **topic** property of the dialog node response.
   * @return topic
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TOPIC)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getTopic() {
    return topic;
  }


  @JsonProperty(JSON_PROPERTY_TOPIC)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTopic(String topic) {
    this.topic = topic;
  }

  public RuntimeResponseTypeConnectToAgent channels(List<ResponseGenericChannel> channels) {
    
    this.channels = channels;
    return this;
  }

  public RuntimeResponseTypeConnectToAgent addChannelsItem(ResponseGenericChannel channelsItem) {
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
    RuntimeResponseTypeConnectToAgent runtimeResponseTypeConnectToAgent = (RuntimeResponseTypeConnectToAgent) o;
    return Objects.equals(this.responseType, runtimeResponseTypeConnectToAgent.responseType) &&
        Objects.equals(this.messageToHumanAgent, runtimeResponseTypeConnectToAgent.messageToHumanAgent) &&
        Objects.equals(this.agentAvailable, runtimeResponseTypeConnectToAgent.agentAvailable) &&
        Objects.equals(this.agentUnavailable, runtimeResponseTypeConnectToAgent.agentUnavailable) &&
        Objects.equals(this.transferInfo, runtimeResponseTypeConnectToAgent.transferInfo) &&
        Objects.equals(this.topic, runtimeResponseTypeConnectToAgent.topic) &&
        Objects.equals(this.channels, runtimeResponseTypeConnectToAgent.channels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(responseType, messageToHumanAgent, agentAvailable, agentUnavailable, transferInfo, topic, channels);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RuntimeResponseTypeConnectToAgent {\n");
    sb.append("    responseType: ").append(toIndentedString(responseType)).append("\n");
    sb.append("    messageToHumanAgent: ").append(toIndentedString(messageToHumanAgent)).append("\n");
    sb.append("    agentAvailable: ").append(toIndentedString(agentAvailable)).append("\n");
    sb.append("    agentUnavailable: ").append(toIndentedString(agentUnavailable)).append("\n");
    sb.append("    transferInfo: ").append(toIndentedString(transferInfo)).append("\n");
    sb.append("    topic: ").append(toIndentedString(topic)).append("\n");
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

