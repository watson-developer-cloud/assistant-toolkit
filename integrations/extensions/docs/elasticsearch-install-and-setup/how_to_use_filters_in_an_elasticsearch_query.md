# How to use filters in an Elasticsearch query

This guide documents how to use filters in an Elasticsearch query for different use cases. It provides a list of examples to be used with the custom filters supported by watsonx Assistant's Search integration.

## Use Elasticsearch boolearn query
You can filter the Elasticsearch search results using the boolean query. It supports four occurence types: `must`, `should`, `filter`, and `must_not`. For more information, see the [Elasticsearch boolearn query documentation](https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-bool-query.html).

The following are some examples using the `filter` occurence type with nested clauses to support different use cases.


### must
The `must` type can be viewed as `AND`.
```json
{
  "query": {
    "bool": {
      "should": [
        {
          "text_expansion": {
            "ml.tokens": {
              "model_id": ".elser_model_2_linux-x86_64",
              "model_text": "$QUERY"
            }
          }
        }
      ],
      "filter": [
        {
          "bool": {
            "must": [
              {
                "term": {
                  "title": "a specific title"
                  }
              }
            ]
          }
        }
      ]
    }
  }
}
```
This query with the inner `must` clause will only return results with the `title` field containing "a specifc title".

### should
The `should` type can be viewed as `OR`.
```json
{
  "query": {
    "bool": {
      "should": [
        {
          "text_expansion": {
            "ml.tokens": {
              "model_id": ".elser_model_2_linux-x86_64",
              "model_text": "$QUERY"
            }
          }
        }
      ],
      "filter": [
        {
          "bool": {
            "should": [
              {
                "term": {
                  "title": "a specific title"
                }
              }
            ]
          }
        }
      ]
    }
  }
}
```
Unlike the `must` clause, this query with the inner `should` clause will return all results matched by the `text_expansion` query, but the ones with the `title` field containing "a specific title" will have higher scores, so they will be ranked higher in the search results.

### filter
The `filter` type can be viewed as `AND`.
```json
{
  "query": {
    "bool": {
      "should": [
        {
          "text_expansion": {
            "ml.tokens": {
              "model_id": ".elser_model_2_linux-x86_64",
              "model_text": "$QUERY"
            }
          }
        }
      ],
      "filter": [
        {
            "bool": {
                "filter": [
                    {
                        "term": {
                            "title": "a specific title"
                        }
                    }
                ]
            }
        }
      ]
    }
  }
}
```
Similary to the inner `must` clause, this query will only return results with the `title` field containing "a specific title". The difference is that the `filter` clause doesn't contribute to the scores of the search results.

### must_not
The `must_not` type can be viewed as `NOT`.
```json
{
  "query": {
    "bool": {
      "should": [
        {
          "text_expansion": {
            "ml.tokens": {
              "model_id": ".elser_model_2_linux-x86_64",
              "model_text": "$QUERY"
            }
          }
        }
      ],
      "filter": [
        {
          "bool": {
            "must_not": [
              {
                "term": {
                  "title": "a specific title"
                }
              }
            ]
          }
        }
      ]
    }
  }
}
```
Opposite to inner `filter` clause, this query will not return any results with the `title` field containing "a specific title". The `must_not` clause doesn't contribute to the the scores of the search results.

Notes:
* If the `title` field is not a `text` type, the above queries will perform exact match on the `title` field. For more information, see the [Elasticsearch field types documentation](https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-types.html).
* Instead of using the `term` clause, you can use the `match` clause to perform keyword search on the given phrase.
* The four coourence types can be used in combinations to achieve more complex filtering in a query.


## Configure customer filters for the Search integration
You can use customer filters for your Elasticsearch integration to filter search results.

### Configure global filters in the Search integration settings
You can configure custom filters for your Elasticsearch integration under `Advanced Elasticsearch Settings`. These custom filters will be used as `filter` clauses and can be accessed using the `$FILTER` variable. If a custom query body is provided, the `$FILTER` variable needs to be included in the query body to use the custom filters. For example,

<img src="assets/query_body_with_custom_filters.png" width="547" height="638" />


### Configure local filters in an action
You can also configure customer filters within an action step when calling the Search integration via `Search for the answer` option. The customer filters defined in an action step will overwrite what's provided in the Search integration settings.

**Session variables or step variables can be used in the customer filters to achieve more dynamic filtering use cases.**

For example,

<img src="assets/use_session_variable_in_custom_filters_for_search.png" />

The above sceenshot shows the settings for `Search for the answer` in an action step where,
* A custom search query is provided using a step variable, and
* An array of custom filters are provided using the `Set new filter` option, and
* A step variable from the second step is used to build a dynamic filter clause.

Note: double quotes are needed for using variables in building the custom filters.
