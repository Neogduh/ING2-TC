#!/usr/bin/env bash

HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/arbres"
shopt -s expand_aliases
alias curljson='curl -H "Content-Type: application/json" -d @-'

function exo_2_2() {
    curljson -X GET "${INDEX}/_search?pretty" <<JSON
{
  "query": {
    "match": {
      "nom commun": "Erable"
    }
  }
}
JSON
}
exo_2_2
