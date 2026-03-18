#!/usr/bin/env bash

HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/arbres"
shopt -s expand_aliases
alias curljson='curl -H "Content-Type: application/json" -d @-'

function exo_2_4() {
    curljson -X GET "${INDEX}/_search?pretty" <<JSON
{
  "query": {
    "bool": {
      "must": [
        { "match": { "nom commun": "Platane" }},
        { "match": { "adresse.arrondissement": 12 }}
      ],
      "must_not": [
        { "match": { "nom commun": "commun" }}
      ]
    }
  }
}
JSON
}
exo_2_4
