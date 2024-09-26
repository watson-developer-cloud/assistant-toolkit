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
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * An object containing segments of text from search results with query-matching text highlighted using HTML &#x60;&lt;em&gt;&#x60; tags.
 */
@JsonPropertyOrder({
  SearchResultHighlight.JSON_PROPERTY_BODY,
  SearchResultHighlight.JSON_PROPERTY_TITLE,
  SearchResultHighlight.JSON_PROPERTY_URL
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class SearchResultHighlight extends HashMap<String, List> {
  public static final String JSON_PROPERTY_BODY = "body";
  private List<String> body = new ArrayList<>();

  public static final String JSON_PROPERTY_TITLE = "title";
  private List<String> title = new ArrayList<>();

  public static final String JSON_PROPERTY_URL = "url";
  private List<String> url = new ArrayList<>();

  public SearchResultHighlight() {

  }

  public SearchResultHighlight body(List<String> body) {
    
    this.body = body;
    return this;
  }

  public SearchResultHighlight addBodyItem(String bodyItem) {
    if (this.body == null) {
      this.body = new ArrayList<>();
    }
    this.body.add(bodyItem);
    return this;
  }

  /**
   * An array of strings containing segments taken from body text in the search results, with query-matching substrings highlighted.
   * @return body
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_BODY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getBody() {
    return body;
  }


  @JsonProperty(JSON_PROPERTY_BODY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setBody(List<String> body) {
    this.body = body;
  }

  public SearchResultHighlight title(List<String> title) {
    
    this.title = title;
    return this;
  }

  public SearchResultHighlight addTitleItem(String titleItem) {
    if (this.title == null) {
      this.title = new ArrayList<>();
    }
    this.title.add(titleItem);
    return this;
  }

  /**
   * An array of strings containing segments taken from title text in the search results, with query-matching substrings highlighted.
   * @return title
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_TITLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getTitle() {
    return title;
  }


  @JsonProperty(JSON_PROPERTY_TITLE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTitle(List<String> title) {
    this.title = title;
  }

  public SearchResultHighlight url(List<String> url) {
    
    this.url = url;
    return this;
  }

  public SearchResultHighlight addUrlItem(String urlItem) {
    if (this.url == null) {
      this.url = new ArrayList<>();
    }
    this.url.add(urlItem);
    return this;
  }

  /**
   * An array of strings containing segments taken from URLs in the search results, with query-matching substrings highlighted.
   * @return url
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_URL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<String> getUrl() {
    return url;
  }


  @JsonProperty(JSON_PROPERTY_URL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUrl(List<String> url) {
    this.url = url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchResultHighlight searchResultHighlight = (SearchResultHighlight) o;
    return Objects.equals(this.body, searchResultHighlight.body) &&
        Objects.equals(this.title, searchResultHighlight.title) &&
        Objects.equals(this.url, searchResultHighlight.url) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(body, title, url, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchResultHighlight {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    body: ").append(toIndentedString(body)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
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

