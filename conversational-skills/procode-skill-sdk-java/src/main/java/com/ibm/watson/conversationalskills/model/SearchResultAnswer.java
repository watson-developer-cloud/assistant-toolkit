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
 * An object specifing a segment of text that was identified as a direct answer to the search query.
 */
@JsonPropertyOrder({
  SearchResultAnswer.JSON_PROPERTY_TEXT,
  SearchResultAnswer.JSON_PROPERTY_CONFIDENCE
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class SearchResultAnswer {
  public static final String JSON_PROPERTY_TEXT = "text";
  private String text;

  public static final String JSON_PROPERTY_CONFIDENCE = "confidence";
  private Double confidence;

  public SearchResultAnswer() {
  }

  public SearchResultAnswer text(String text) {
    
    this.text = text;
    return this;
  }

  /**
   * The text of the answer.
   * @return text
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TEXT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getText() {
    return text;
  }


  @JsonProperty(JSON_PROPERTY_TEXT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setText(String text) {
    this.text = text;
  }

  public SearchResultAnswer confidence(Double confidence) {
    
    this.confidence = confidence;
    return this;
  }

  /**
   * The confidence score for the answer, as returned by the Discovery service.
   * minimum: 0
   * maximum: 1
   * @return confidence
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CONFIDENCE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Double getConfidence() {
    return confidence;
  }


  @JsonProperty(JSON_PROPERTY_CONFIDENCE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setConfidence(Double confidence) {
    this.confidence = confidence;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchResultAnswer searchResultAnswer = (SearchResultAnswer) o;
    return Objects.equals(this.text, searchResultAnswer.text) &&
        Objects.equals(this.confidence, searchResultAnswer.confidence);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text, confidence);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchResultAnswer {\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    confidence: ").append(toIndentedString(confidence)).append("\n");
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

