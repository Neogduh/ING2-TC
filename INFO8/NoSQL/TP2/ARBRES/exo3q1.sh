#!/usr/bin/env bash

HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/arbres"
shopt -s expand_aliases
alias curljson='curl -H "Content-Type: application/json" -d @-'
function exo_3_1() {
    curljson -X GET "${INDEX}/_search?size=0&pretty" <<JSON
{
	"size": 0,
    "aggs": {
        "oldest_tree": {
			"min" : {
				"field": "annee plantation"
			}
		}
    }
}
JSON
}
exo_3_1
