#!/usr/bin/env bash

# ce script permet de lancer des requêtes sur Elasticsearch
## Configuration générale

# configuration
HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/tests"


# alias et fonctions diverses
shopt -s expand_aliases
alias curljson='curl -H "Content-Type: application/json" -d @-'

function titre() {
    echo -e "\n$(tput bold)$*$(tput sgr0)"
}

titre "URL de base de cet index: ${INDEX}"


## Définitions des fonctions

function schema() {
    titre "création de l'index tests"
    curljson -X PUT "${INDEX}?pretty" <<JSON
    {
        "settings": {
            "number_of_shards": 1,
            "number_of_replicas": 0
        },
        "mappings": {

                "properties": {
                  "nom":    { "type": "keyword" },
                  "valeur": { "type": "integer"}

            }
        }
    }
JSON
    titre "Ouvrir l'URL ${INDEX}?pretty pour vérifier le schéma"
}


function donnees() {
    titre "ajout de quelques données dans l'index"
    curljson -X PUT "${INDEX}/_create/1?pretty" <<JSON
    {
      "nom": "a",
      "valeur": 2
    }
JSON
    curljson -X PUT "${INDEX}/_create/2?pretty" <<JSON
    {
      "nom": "b",
      "valeur": -7
    }
JSON
    curljson -X PUT "${INDEX}/_create/3?pretty" <<JSON
    {
      "nom": "c",
      "valeur": 4
    }
JSON
    titre "Ouvrir l'URL ${INDEX}/_search?pretty pour vérifier les données"
}


function recherche_url() {
    titre "recherche document dont valeur vaut -7"
    Q='valeur:\\-7'
    curl --get "${INDEX}/_search" --data-urlencode "q=${Q}" --data-urlencode "pretty"
    titre "Ouvrir l'URL ${INDEX}/_search?pretty&q=${Q}"
}


function recherche_dsl() {
    titre "recherche document dont valeur vaut -7"
    curljson -X GET "${INDEX}/_search?pretty" <<JSON
    {
      "query": {
        "match": {
          "valeur": -7
        }
      }
    }
JSON
}


function deleteall() {
    titre "suppression de l'index tests"
    curl -X DELETE "${INDEX}?pretty"
}


## Appels des fonctions voulues

# commenter les fonctions qu'il ne faut pas appeler, selon ce que vous faites

# créer le schéma et les données
schema
donnees

# requêtes
#get1
#recherche_url
#recherche_dsl

# tout supprimer
#deleteall
