#!/usr/bin/env bash

HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/arbres"
shopt -s expand_aliases
alias curljson='curl -H "Content-Type: application/json" -d @-'

function exo_2_1() {
    curljson -X GET "${INDEX}/_search?pretty" <<JSON
{
  "query": {
    "range": {
      "hauteur": {
        "gte": 40
      }
    }
  }
}

JSON
}

exo_2_1
