#!/bin/bash

CUR=$(pwd)
TMPDIR="$(dirname -- "${0}")"

SC_RELEASE_TAG="swagger-petstore-v3-$SC_VERSION"

#####################
### publish pre-prepared release (tag is created)
#####################
python $CUR/CI/publishRelease.py "$SC_RELEASE_TAG"

#####################
### update the version to next snapshot in maven project with set version
#####################
mvn versions:set -DnewVersion="${SC_NEXT_VERSION}-SNAPSHOT"
mvn versions:commit

#####################
### update all other versions in files around to the next snapshot or new release, including readme and gradle ###
#####################

sc_find="version\: $SC_VERSION"
sc_replace="version: $SC_NEXT_VERSION-SNAPSHOT"
sed -i -e "s/$sc_find/$sc_replace/g" $CUR/src/main/resources/openapi.yaml
