#!/usr/bin/env bash

HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/movies"


# alias et fonctions diverses
shopt -s expand_aliases
alias curljson='curl -H "Content-Type: application/json" -d @-'

function exo_4_8() {
    curljson -X GET "${INDEX}/_search?size=0&pretty" <<JSON
{
    "size": 0,
	"aggs": {
		"mean_rating_action_movies": {
			"avg": {
				"field": "fields.rating"
			}
		}
	}
}
JSON
}
exo_4_8
