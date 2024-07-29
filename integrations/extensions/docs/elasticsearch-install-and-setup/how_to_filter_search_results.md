# How to use filters in an Elasticsearch query

This guide documents how to use filters in an Elasticsearch query for different use cases. It provides a list of examples to be used with the custom filters supported by watsonx Assistant's Search integration.

## Use Elasticsearch boolearn query
You can filter the Elasticsearch search results using the boolean query. It supports four occurence types: `must`, `should`, `filter`, and `must_not`. For more information, see the [Elasticsearch boolearn query documentation](https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-bool-query.html).

The following are some examples using the `filter` occurence type with nested clauses to support different use cases.

**When you configure custom filters in the `Advanced Elasticsearch Settings` in the Search integration, they will be used as `filter` clauses and can be accessed via `$FILTER` variable in the custom query body.**

<img src="assets/query_body_with_custom_filters.png" width="547" height="638" />

### must
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
