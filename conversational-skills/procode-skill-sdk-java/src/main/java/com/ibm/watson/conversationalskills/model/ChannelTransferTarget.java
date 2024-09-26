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
 * An object specifying target channels available for the transfer. Each property of this object represents an available transfer target. Currently, the only supported property is **chat**, representing the web chat integration.
 */
@JsonPropertyOrder({
  ChannelTransferTarget.JSON_PROPERTY_CHAT
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class ChannelTransferTarget {
  public static final String JSON_PROPERTY_CHAT = "chat";
  private ChannelTransferTargetChat chat;

  public ChannelTransferTarget() {
  }

  public ChannelTransferTarget chat(ChannelTransferTargetChat chat) {
    
    this.chat = chat;
    return this;
  }

  /**
   * Get chat
   * @return chat
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_CHAT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ChannelTransferTargetChat getChat() {
    return chat;
  }


  @JsonProperty(JSON_PROPERTY_CHAT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setChat(ChannelTransferTargetChat chat) {
    this.chat = chat;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChannelTransferTarget channelTransferTarget = (ChannelTransferTarget) o;
    return Objects.equals(this.chat, channelTransferTarget.chat);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chat);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChannelTransferTarget {\n");
    sb.append("    chat: ").append(toIndentedString(chat)).append("\n");
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

