#!/usr/bin/env bash

# ce script permet de lancer des requêtes sur Elasticsearch
## Configuration générale

# configuration
HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/arbres"


# alias et fonctions diverses
shopt -s expand_aliases
alias curljson='curl -H "Content-Type: application/json" -d @-'
function titre() {
    echo -e "\n$(tput bold)$*$(tput sgr0)"
}

titre "URL de base de cet index: ${INDEX}"


## Définitions des fonctions

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
                  "genre":            { "type": "text" },
                  "variete":          { "type": "keyword" },
                  "espece":           {"type": "keyword"},
                  "geopoint":         { "type": "geo_point" },
                  "famille" :         { "type": "text" },
                  "annee plantation" :           { "type": "integer" },
                  "hauteur" : { "type": "integer" },
                  "circonference" : {"type": "integer"},
                  "adresse":          {
                      "properties": {
                          "arrondissement": { "type": "integer" },
                          "lieu":           { "type": "keyword" }
                      }
                  },
                  "nom commun" :  { "type": "text" },
                  "objectid":         { "type": "integer" }
            }
        }
    }
JSON
    titre "Ouvrir l'URL ${INDEX}?pretty pour vérifier le schéma"
}


function donnees() {
    titre "ajout des données dans l'index"
    curl -H "Content-Type: application/x-ndjson" -XPOST "${SERVEUR}/_bulk?pretty" --data-binary @arbres.json
    titre "Ouvrir l'URL ${INDEX}/_search?pretty pour vérifier les données"
}


function deleteall() {
    titre "suppression de l'index arbres"
    curl -X DELETE "${INDEX}?pretty"
}

# requêtes dans l'URL

function recherche_url() {
    titre "recherche arbres genre Acer par l'URL"
    Q='genre:Acer'
    curl --get "${INDEX}/_search" --data-urlencode "q=${Q}" --data-urlencode "pretty"
    titre "Ouvrir l'URL ${INDEX}/_search?pretty&q=${Q}"
}

# requêtes DSL (JSON)

function recherche_dsl() {
    titre "recherche arbres genre Acer avec DSL"
    curljson -X GET "${INDEX}/_search?pretty" <<JSON
    {
      "query": {
        "match": {
          "genre": "Acer"
        }
      }
    }
JSON
}

# agrégations DSL
function agregation_dsl() {
    titre "hauteur moyenne des arbres"
    curljson -X GET "${INDEX}/_search?size=0&pretty" <<JSON
    {
        "aggs": {
            "hauteur moyenne": {
                "avg": {
                    "field": "hauteur"
                }
            }
        }
    }
JSON
}


#schema
#donnees

#pour faire des tests

#recherche_url
#recherche_dsl
#agregation_dsl

#pour supprimmer l'index
#deleteall
