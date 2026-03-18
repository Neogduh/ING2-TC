#!/usr/bin/env bash

HOST=localhost
SERVEUR=http://$HOST:9200
INDEX="${SERVEUR}/arbres"

function exo_1_2() {
    Q='nom\ commun:Erable'
    curl --get "${INDEX}/_search" --data-urlencode "q=${Q}" --data-urlencode "pretty"
}
exo_1_2
