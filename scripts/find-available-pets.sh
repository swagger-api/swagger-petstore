#!/usr/bin/env bash
# Calls GET /pet/findByStatus?status=available on the local Petstore server
# Start the server first: mvn package jetty:run

BASE_URL="${PETSTORE_URL:-http://localhost:8080}"

curl -s "${BASE_URL}/api/v3/pet/findByStatus?status=available" | python3 -m json.tool
