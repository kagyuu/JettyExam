#!/bin/bash

. createTmp.sh

createTmp

# ----- STEP 1 -----
echo "Create myapp"

curl -X POST \
-H "Content-Type: application/json" \
-d "{\"name\":\"myapp\",\"enabled\":true}" \
-o $tmpFile \
http://localhost:8080/api/app 2> /dev/null

cat $tmpFile

# ----- STEP 2 -----
echo -e "\nCreate myrsc"

curl -X POST \
-H "Content-Type: application/json" \
-d "{\"name\":\"myrsc\",\"directory\":\"/var/opt/resource/myrsc\"}" \
-o $tmpFile \
http://localhost:8080/api/rsc 2> /dev/null

cat $tmpFile

RSCID=$(jq -r '.id' $tmpFile)

# ----- STEP 3 -----
echo -e "\nRead myapp"

curl -X GET \
-o $tmpFile \
http://localhost:8080/api/app/findByName/myapp 2> /dev/null

cat $tmpFile

APPID=$(jq -r '.[0].id' $tmpFile)

echo -e "APPID:${APPID}\nRSCID:${RSCID}\n"

# ----- STEP 4 -----
echo -e "\nAppend myapp to myrsc"

curl -X POST \
-H "Content-Type: application/json" \
-d "{\"resource\":${RSCID}, \"app\":${APPID}}" \
-o $tmpFile \
http://localhost:8080/api/con 2> /dev/null

jq . $tmpFile

# ----- STEP 5 -----
echo -e "\nFind contains apps"

curl -X GET \
-o $tmpFile \
http://localhost:8080/api/rsc/findContainsAppBinary/${RSCID} 2> /dev/null

jq . $tmpFile
