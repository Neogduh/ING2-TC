#!/usr/bin/env bash

HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/movies"


# alias et fonctions diverses
shopt -s expand_aliases
alias curljson='curl -H "Content-Type: application/json" -d @-'

function exo_4_1() {
    curljson -X GET "${INDEX}/_search?pretty" <<JSON
{
    "query": {
        "match_phrase": {
            "fields.title": "Star Wars"
        }
    }
}
JSON
}
exo_4_1
