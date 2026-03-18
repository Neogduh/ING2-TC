#!/usr/bin/env bash

HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/movies"


# alias et fonctions diverses
shopt -s expand_aliases
alias curljson='curl -H "Content-Type: application/json" -d @-'

function exo_4_3() {
    curljson -X GET "${INDEX}/_search?pretty" <<JSON
{
	"query": {
		"query_string": {
			"fields": [ "fields.actors" ],
			"query": "Harrison Ford"
		}
	}
}
JSON
}
exo_4_3
