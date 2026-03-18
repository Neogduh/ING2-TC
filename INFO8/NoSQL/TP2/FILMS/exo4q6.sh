#!/usr/bin/env bash

HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/movies"


# alias et fonctions diverses
shopt -s expand_aliases
alias curljson='curl -H "Content-Type: application/json" -d @-'

function exo_4_6() {
    curljson -X GET "${INDEX}/_search?pretty" <<JSON
{
	"query": {
		"bool": {
			"must": [
				{
					"query_string": {
						"fields": [ "fields.directors" ],
						"query": "James Cameron"
					}
				},
				{
					"range": {
						"fields.rating": {
							"gt": 7
						}
					}
				}
			],
			"must_not": {
				"query_string": {
					"fields": [ "fields.genres" ],
					"query": "Fantasy Thriller"
				}
			}
		}
	}
}
JSON
}
exo_4_6
