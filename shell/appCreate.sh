#!/bin/bash

. createTmp.sh

createTmp

# ----- STEP 1 -----
echo "Create myapp"

curl -X POST \
-H "Content-Type: application/json" \
-d "{\"name\":\"myapp\"}" \
-o $tmpFile \
http://localhost:8080/api/app 2> /dev/null

cat $tmpFile
jq . $tmpFile

ID=$(jq -r '.id' $tmpFile)

# ----- STEP 2 -----
echo -e "\nUpdate myapp"

curl -X PUT \
-H "Content-Type: application/json" \
-d "{\"enabled\":true}" \
-o $tmpFile \
http://localhost:8080/api/app/${ID} 2> /dev/null

jq . $tmpFile

# ----- STEP 3 -----
echo -e "\nRead myapp"

curl -X GET \
-o $tmpFile \
http://localhost:8080/api/app/findByName/myapp 2> /dev/null

jq . $tmpFile

VERSION=$(jq -r '.[0].version' $tmpFile)
BRANCH=$(jq -r '.[0].branchNo' $tmpFile)
NAME=$(jq -r '.[0].name' $tmpFile)

echo "LATEST : ${NAME}-${VERSION}-${BRANCH}"