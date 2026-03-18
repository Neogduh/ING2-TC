#!/usr/bin/env bash

HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/movies"


# alias et fonctions diverses
shopt -s expand_aliases
alias curljson='curl -H "Content-Type: application/json" -d @-'
function titre() {
    echo -e "\n$(tput bold)$*$(tput sgr0)"
}

titre "URL de base de cet index: ${INDEX}"

function schema() {
    titre "création de l'index ${INDEX}"
    curljson -X PUT "${INDEX}?pretty" <<JSON
 {
        "settings": {
            "number_of_shards": 1,
            "number_of_replicas": 0
        },
        "mappings": {
                  "properties": {
                    "fields":{
                        "properties": {
                          "directors":            { "type": "text" },
                          "release_date":          { "type": "date" },
                          "rating":           {"type": "float"},
                          "genres":         { "type": "text" },
                          "image_url" :         { "type": "text" },
                          "plot" :           { "type": "text" },
                          "title" : { "type": "text" },
                          "rank" : {"type": "integer"},
                          "running_time_secs":   {"type": "integer"},
                          "actors" :  { "type": "text" },
                          "year":         { "type": "integer" },
                          "id":{"type": "text"}
                          }
                    }
          }
        }
    }
JSON
    titre "Ouvrir l'URL ${INDEX}?pretty pour vérifier le schéma"
}

schema
