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
 * ErrorResponse
 */
@JsonPropertyOrder({
  ErrorResponse.JSON_PROPERTY_ERROR,
  ErrorResponse.JSON_PROPERTY_ERRORS,
  ErrorResponse.JSON_PROPERTY_CODE
})
@jakarta.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", comments = "Generator version: 7.8.0")
public class ErrorResponse {
  public static final String JSON_PROPERTY_ERROR = "error";
  private String error;

  public static final String JSON_PROPERTY_ERRORS = "errors";
  private List<ErrorDetail> errors = new ArrayList<>();

  public static final String JSON_PROPERTY_CODE = "code";
  private Integer code;

  public ErrorResponse() {
  }

  public ErrorResponse error(String error) {
    
    this.error = error;
    return this;
  }

  /**
   * General description of an error.
   * @return error
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ERROR)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getError() {
    return error;
  }


  @JsonProperty(JSON_PROPERTY_ERROR)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setError(String error) {
    this.error = error;
  }

  public ErrorResponse errors(List<ErrorDetail> errors) {
    
    this.errors = errors;
    return this;
  }

  public ErrorResponse addErrorsItem(ErrorDetail errorsItem) {
    if (this.errors == null) {
      this.errors = new ArrayList<>();
    }
    this.errors.add(errorsItem);
    return this;
  }

  /**
   * Collection of specific constraint violations associated with the error.
   * @return errors
   */
  @jakarta.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_ERRORS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<ErrorDetail> getErrors() {
    return errors;
  }


  @JsonProperty(JSON_PROPERTY_ERRORS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setErrors(List<ErrorDetail> errors) {
    this.errors = errors;
  }

  public ErrorResponse code(Integer code) {
    
    this.code = code;
    return this;
  }

  /**
   * HTTP status code for the error response.
   * @return code
   */
  @jakarta.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CODE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getCode() {
    return code;
  }


  @JsonProperty(JSON_PROPERTY_CODE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCode(Integer code) {
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse errorResponse = (ErrorResponse) o;
    return Objects.equals(this.error, errorResponse.error) &&
        Objects.equals(this.errors, errorResponse.errors) &&
        Objects.equals(this.code, errorResponse.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(error, errors, code);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
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

