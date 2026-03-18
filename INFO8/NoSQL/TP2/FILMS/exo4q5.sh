#!/usr/bin/env bash

HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/movies"


# alias et fonctions diverses
shopt -s expand_aliases
alias curljson='curl -H "Content-Type: application/json" -d @-'

function exo_4_5() {
    curljson -X GET "${INDEX}/_search?pretty" <<JSON
{
	"query": {
		"bool": {
			"must": {
				"query_string": {
					"fields": [ "fields.directors" ],
					"query": "James Cameron"
				}
			},
			"filter": {
                "range": {
                    "fields.rank": {
                        "gt": 1000
                    }
                }
			}
		}
	}
}
JSON
}
exo_4_5
