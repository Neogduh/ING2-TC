#!/usr/bin/env bash

HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/movies"


# alias et fonctions diverses
shopt -s expand_aliases
alias curljson='curl -H "Content-Type: application/json" -d @-'

function exo_4_4() {
    curljson -X GET "${INDEX}/_search?pretty" <<JSON
{
	"query": {
		"bool": {
			"must": {
				"query_string": {
					"fields": [ "fields.actors" ],
					"query": "Harrison Ford"
				}
			},
			"filter": {
				"match": {
					"fields.plot": "Jones"
				}
			}
		}
	}
}
JSON
}
exo_4_4
