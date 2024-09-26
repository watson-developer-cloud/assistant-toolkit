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
 * SearchResult
 */
@JsonPropertyOrder({
  SearchResult.JSON_PROPERTY_ID,
  SearchResult.JSON_PROPERTY_RESULT_METADATA,
  SearchResult.JSON_PROPERTY_BODY,
  SearchResult.JSON_PROPERTY_TITLE,
  SearchResult.JSON_PROPERTY_URL,
  SearchResult.JSON_PROPERTY_HIGHLIGHT,
  SearchResult.JSON_PROPERTY_ANSWERS
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class SearchResult {
  public static final String JSON_PROPERTY_ID = "id";
  private String id;

  public static final String JSON_PROPERTY_RESULT_METADATA = "result_metadata";
  private SearchResultMetadata resultMetadata;

  public static final String JSON_PROPERTY_BODY = "body";
  private String body;

  public static final String JSON_PROPERTY_TITLE = "title";
  private String title;

  public static final String JSON_PROPERTY_URL = "url";
  private String url;

  public static final String JSON_PROPERTY_HIGHLIGHT = "highlight";
  private SearchResultHighlight highlight;

  public static final String JSON_PROPERTY_ANSWERS = "answers";
  private List<SearchResultAnswer> answers = new ArrayList<>();

  public SearchResult() {
  }

  public SearchResult id(String id) {
    
    this.id = id;
    return this;
  }

  /**
   * The unique identifier of the document in the Discovery service collection.  This property is included in responses from search skills, which are available only to Plus or Enterprise plan users.
   * @return id
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getId() {
    return id;
  }


  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setId(String id) {
    this.id = id;
  }

  public SearchResult resultMetadata(SearchResultMetadata resultMetadata) {
    
    this.resultMetadata = resultMetadata;
    return this;
  }

  /**
   * Get resultMetadata
   * @return resultMetadata
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_RESULT_METADATA)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public SearchResultMetadata getResultMetadata() {
    return resultMetadata;
  }


  @JsonProperty(JSON_PROPERTY_RESULT_METADATA)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setResultMetadata(SearchResultMetadata resultMetadata) {
    this.resultMetadata = resultMetadata;
  }

  public SearchResult body(String body) {
    
    this.body = body;
    return this;
  }

  /**
   * A description of the search result. This is taken from an abstract, summary, or highlight field in the Discovery service response, as specified in the search skill configuration.
   * @return body
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_BODY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getBody() {
    return body;
  }


  @JsonProperty(JSON_PROPERTY_BODY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setBody(String body) {
    this.body = body;
  }

  public SearchResult title(String title) {
    
    this.title = title;
    return this;
  }

  /**
   * The title of the search result. This is taken from a title or name field in the Discovery service response, as specified in the search skill configuration.
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

  public SearchResult url(String url) {
    
    this.url = url;
    return this;
  }

  /**
   * The URL of the original data object in its native data source.
   * @return url
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_URL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getUrl() {
    return url;
  }


  @JsonProperty(JSON_PROPERTY_URL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUrl(String url) {
    this.url = url;
  }

  public SearchResult highlight(SearchResultHighlight highlight) {
    
    this.highlight = highlight;
    return this;
  }

  /**
   * Get highlight
   * @return highlight
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_HIGHLIGHT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public SearchResultHighlight getHighlight() {
    return highlight;
  }


  @JsonProperty(JSON_PROPERTY_HIGHLIGHT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setHighlight(SearchResultHighlight highlight) {
    this.highlight = highlight;
  }

  public SearchResult answers(List<SearchResultAnswer> answers) {
    
    this.answers = answers;
    return this;
  }

  public SearchResult addAnswersItem(SearchResultAnswer answersItem) {
    if (this.answers == null) {
      this.answers = new ArrayList<>();
    }
    this.answers.add(answersItem);
    return this;
  }

  /**
   * An array specifying segments of text within the result that were identified as direct answers to the search query. Currently, only the single answer with the highest confidence (if any) is returned.  **Notes:**   - Answer finding is available only if the search skill is connected to a Discovery v2 service instance.   - Answer finding is not supported on IBM Cloud Pak for Data.
   * @return answers
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ANSWERS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<SearchResultAnswer> getAnswers() {
    return answers;
  }


  @JsonProperty(JSON_PROPERTY_ANSWERS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAnswers(List<SearchResultAnswer> answers) {
    this.answers = answers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchResult searchResult = (SearchResult) o;
    return Objects.equals(this.id, searchResult.id) &&
        Objects.equals(this.resultMetadata, searchResult.resultMetadata) &&
        Objects.equals(this.body, searchResult.body) &&
        Objects.equals(this.title, searchResult.title) &&
        Objects.equals(this.url, searchResult.url) &&
        Objects.equals(this.highlight, searchResult.highlight) &&
        Objects.equals(this.answers, searchResult.answers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, resultMetadata, body, title, url, highlight, answers);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchResult {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    resultMetadata: ").append(toIndentedString(resultMetadata)).append("\n");
    sb.append("    body: ").append(toIndentedString(body)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    highlight: ").append(toIndentedString(highlight)).append("\n");
    sb.append("    answers: ").append(toIndentedString(answers)).append("\n");
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

