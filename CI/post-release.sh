#!/bin/bash

CUR=$(pwd)
TMPDIR="$(dirname -- "${0}")"

SC_RELEASE_TAG="swagger-petstore-v2-$SC_VERSION"

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
### update all other versions in files around to the next snapshot or new release ###
#####################

sc_find="<param-value>$SC_VERSION"
sc_replace="<param-value>$SC_NEXT_VERSION\-SNAPSHOT"
sed -i -e "s/$sc_find/$sc_replace/g" $CUR/src/main/webapp/WEB-INF/web.xml


sc_find="$SC_VERSION"
sc_replace="$SC_NEXT_VERSION\-SNAPSHOT"
sed -i -e "s/$sc_find/$sc_replace/g" Dockerfile
