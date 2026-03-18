#!/usr/bin/env bash

HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/arbres"
shopt -s expand_aliases
alias curljson='curl -H "Content-Type: application/json" -d @-'

function exo_2_3() {
    curljson -X GET "${INDEX}/_search?pretty" <<JSON
{
  "query": {
    "bool": {
      "must": [
        { "match": { "adresse.rue": "avenue" }},
        { "match": { "adresse.rue": "Matignon" }}
      ]
    }
  }
}
JSON
}
exo_2_3
