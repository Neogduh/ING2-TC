#!/usr/bin/env bash

HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/arbres"

function exo_1_5() {
    Q='hauteur:>=40'
    curl --get "${INDEX}/_search" --data-urlencode "q=${Q}" --data-urlencode "pretty"
}
exo_1_5
