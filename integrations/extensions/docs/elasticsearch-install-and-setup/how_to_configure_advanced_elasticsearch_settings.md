# How to configure the advanced Elasticsearch settings
This guide shows how to configure the advanced Elasticsearch settings in watsonx Assistant's Search integration. Specifically, there are two types of settings under `Advanced Elasticsearch Settings`: custom filters and query body.

<img src="assets/advanced_elasticsearch_settings.png" width=518 height=459 />

## How to configure the query body
By default, keyword search is used for your Search integration, but you can configure the query body in the `Advanced Elasticsearch Settings` to enable advanced search such as semantic search with ELSER, KNN dense vector search, and using a nested query to search over nested documents. Here are some query body examples:

### Semantic search with ELSER
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
      "filter": "$FILTER"
    }
  }
}
```
* `ml.tokens` refers to the field that stores the ELSER tokens. You may need to update it if you use a different field in your index.
* `.elser_model_2_linux-x86-64` is the model ID for the optimized version of ELSER v2. It is recommended to use if it is available in your Elasticsearch deployment. Otherwise, use `.elser_model_2` for the regular ELSER v2 model, or `.elser_model_1` for ELSER v1.
* `$QUERY` is the variable for accesing the user query. It will make sure that the user query will be passed to the query body.
* `$FILTER` is the variable for accessing the customer filters configured either in the `Advanced Elasticsearch Settings` or when calling the search in an action step. It will make sure that the custom filters will be used in the query body.
* Learn more about ELSER from [here](https://www.elastic.co/guide/en/elasticsearch/reference/current/semantic-search-elser.html)
* Learn more about Elasticsearch boolean query and filters from [here](https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-bool-query.html)

### KNN dense vector search
```json
{
  "knn": {
    "field": "text_embedding.predicted_value",
    "query_vector_builder": {
      "text_embedding": {
        "model_id": "intfloat__multilingual-e5-small",
        "model_text": "$QUERY"
      }
    },
    "k": 10,
    "num_candidates": 100,
    "filter" : "$FILTER"
  }
}
```
Notes:
* `text_embedding.predicted_value` refers to the field that stores the dense vectors. You may need to update it if you use a different field in your index.
* `text_embedding` under `query_vector_builder` is the natural language processing task to perform. It has to be `text_embedding` for KNN search.
* `intfloat__multilingual-e5-small` is the embedding model ID. You may need to update it if you want to use a different embedding model.
* `$QUERY` is the variable for accesing the user query. It will make sure that the user query will be passed to the query body.
* `$FILTER` is the variable for accessing the customer filters configured either in the `Advanced Elasticsearch Settings` or when calling the search in an action step. It will make sure that the custom filters will be used in the query body.
* Learn more about knn search from [here](https://www.elastic.co/guide/en/elasticsearch/reference/current/knn-search.html).
* Learn more about how to set up a text embedding model in Elasticsearch from [here](text_embedding_deploy_and_use.md).

### Using a nested query to search over nested documents with ELSER
```json
{
  "query": {
    "nested": {
      "path": "passages",
      "query": {
        "bool": {
          "should": [
            {
              "text_expansion": {
                "passages.sparse.tokens": {
                  "model_id": ".elser_model_2_linux-x86_64",
                  "model_text": "$QUERY"
                }
              }
            }
          ],
          "filter": "$FILTER"
        }
      },
      "inner_hits": {"_source": {"excludes": ["passages.sparse"]}}
    }
  },
  "_source": false
}
```
Notes:
* `passages` is the nested field that stores inner documents within a parent document. You may need to update it if you use a different nested field in your index.
* `passages.sparse.tokens` refers to the field that stores the ELSER tokens for the inner documents.
* `"inner_hits": {"_source": {"excludes": ["passages.sparse"]}}` is to exclude the ELSER tokens from the inner documents in the search results.
* `"_source": false` is to exclude all the top-level fields in the search results because only the inner documents in the search results will be used.
* `$QUERY` is the variable for accesing the user query. It will make sure that the user query will be passed to the query body.
* `$FILTER` is the variable for accessing the customer filters configured either in the `Advanced Elasticsearch Settings` or when calling the search in an action step. It will make sure that the custom filters will be used in the query body.
* Learn more about nested queries and fields from [here](https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-nested-query.html)

### Hybird search with combined keyword search and dense vector search
```json
{
    "query": {
        "bool": {
            "query": {
                "query_string": {
                    "query": "$QUERY",
                    "fields": ["$BODY_FIELD_NAME", "$TITLE_FIELD_NAME"],
                    }
                },
            "filter" : "$FILTER"
        }
    },
    "knn": {
        "field": "text_embedding.predicted_value",
        "query_vector_builder": {
        "text_embedding": {
            "model_id": "intfloat__multilingual-e5-small",
            "model_text": "$QUERY"
        }
        },
        "k": 10,
        "num_candidates": 100,
        "filter" : "$FILTER"
    },
    "rank": {
        "rrf": {
        "window_size": 200
        }
    },
    "size": 10,
    "_source": {"excludes": ["text_embedding.predicted_value"]}
}
```
Notes:
* `text_embedding.predicted_value` refers to the field that stores the dense vectors. You may need to update it if you use a different field in your index.
* `text_embedding` under `query_vector_builder` is the natural language processing task to perform. It has to be `text_embedding` for KNN search.
* `intfloat__multilingual-e5-small` is the embedding model ID. You may need to update it if you want to use a different embedding model.
* `$QUERY` is the variable for accesing the user query. It makes sure that the user query will be passed to the query body.
* `$BODY_FIELD_NAME` and `$TITLE_FIELD_NAME` are the variables for accessing the Body field and Title field configured in the Search integration, respectively.
* `$FILTER` is the variable for accessing the customer filters configured either in the `Advanced Elasticsearch Settings` or when calling the search in an action step. It makes sure that the custom filters will be used in the query body.
* `rank.rrf` set the query to use Reciprocal rank fusion (rrf) method to combine the search results from keyword search and dense vector search.
* `"_source": {"excludes": ["text_embedding.predicted_value"]}` is to exclude the unnecessary dense vector field in the search results.

## How to configure the custom filters
There are two ways to configure the custom filters: 

### Global filters within the Search integration
You can configure custom filters for your Elasticsearch integration under `Advanced Elasticsearch Settings`. These custom filters will be used as global filters and apply to all user queries. If a custom query body is provided, the `$FILTER` variable needs to be included in the query body to use the custom filters. For example,

<img src="assets/query_body_with_custom_filters.png" width="547" height="638" />


### Local filters within an action step
You can also configure customer filters within an action step when calling the Search integration via `Search for the answer` option. These customer filters will overwrite the global filters defined in the Search integration.

**Session variables or step variables can be used in the customer filters to achieve more dynamic filtering use cases.**

For example,

<img src="assets/use_session_variable_in_custom_filters_for_search.png" />

The above sceenshot shows the settings for `Search for the answer` in an action step where,
* A custom search query is provided using a step variable, and
* An array of custom filters are provided using the `Set new filter` option, and
* A step variable from the second step is used to build a dynamic filter clause.

Note: double quotes are needed for using variables in building the custom filters.

### Filter object examples
Using the custom filters, you can achieve advanced filtering use cases for search and conversational search. Here are some examples:

**Note: The below examples assume `title`, `text`, and `id` fields are available in your Elasticsearch index. Among them, `title` and `text` are text type fields, while `id` is a keyword type field.**

#### AND
```json
[
    {
        "match": {
            "title": "A_keyword_in_title"
        }
    },
    {
        "match": {
            "text": "A_keyword_in_text"
        }
    },
    {
        "term": {
            "id": "A_specific_ID"
        }
    }
]
```
This filter object will filter the search results using the following conditions:
* `title` contains "A_keyword_in_title", AND
* `text` contains "A_keyword_in_text", AND
* `id` is equal to "A_specific_ID".


#### OR
```json
{
    "bool": {
        "should": [
            {
                "match": {
                    "title": "A_keyword_in_title"
                }
            },
            {
                "match": {
                    "text": "A_keyword_in_text"
                }
            },
            {
                "match": {
                     "id": "A_specific_ID"
                }
            }
        ]
    }
}
```
This filter object will filter the search results using the following conditions:
* `title` contains "A_keyword_in_title", OR
* `text` contains "A_keyword_in_text", OR
* `id` is equal to "A_specific_ID".

#### NOT
```json
[
    "bool": {
        "must_not": [
            {
                "match": {
                    "title": "A_keyword_in_title"
                }
            },
            {
                "match": {
                    "text": "A_keyword_in_text"
                }
            },
            {
                "match": {
                     "id": "A_specific_ID"
                }
            }
        ]
    }
]
```
This filter object will filter the search results using the following conditions:
* `title` does not contain "A_keyword_in_title", AND
* `text` does not contain "A_keyword_in_text", AND
* `id` is not equal to "A_specific_ID".

#### Nested filters: (A and B) or C
```json
[
    "bool": {
        "should": [
            {
                "match": {
                    "id": "A_specific_ID"
                }
            }
            {
                "bool": {
                    "filter": [
                        {
                            "match": {
                                "title": "A_keyword_in_title"
                            }
                        },
                        {
                            "match": {
                                "text": "A_keyword_in_text"
                            }
                        }
                    ]
                }
            }
        ]
    }
]
```
This filter object will filter the search results using the following conditions:
* `title` contains "A_keyword_in_title" AND `text` contains "A_keyword_in_text", OR
* `id` is not equal to "A_specific_ID"


Learn more about Elasticsearch filters from the [Elasticsearch boolean query documentation](https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-bool-query.html) 