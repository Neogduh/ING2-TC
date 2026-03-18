#!/usr/bin/env bash

HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/movies"


# alias et fonctions diverses
shopt -s expand_aliases
alias curljson='curl -H "Content-Type: application/json" -d @-'

function exo_4_7() {
    curljson -X GET "${INDEX}/_search?size=0&pretty" <<JSON
{
	"size": 0,
	"aggs": {
		"nb_movies_each_year": {
			"terms": {
				"field": "fields.year"
			}
		}
	}
}
JSON
}
exo_4_7
