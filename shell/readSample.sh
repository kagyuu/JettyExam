#!/bin/bash

tmpfile=$(mktemp)

function rm_tmpfile {
  if [[ -f "$tmpfile" ]]
  then
    # echo "delete $tmpfile"
    rm -f "$tmpfile"
  fi
}

trap rm_tmpfile EXIT

curl http://localhost:8080/api/app/findAll -o $tmpfile 2> /dev/null

VERSION=$(jq -r '.[0].version' $tmpfile)
BRANCH=$(jq -r '.[0].branchNo' $tmpfile)
NAME=$(jq -r '.[0].name' $tmpfile)

echo "${NAME}-${VERSION}-${BRANCH}"

