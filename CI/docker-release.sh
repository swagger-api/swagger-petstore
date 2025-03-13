#!/bin/bash

CUR=$(pwd)

SC_RELEASE_TAG="$SC_VERSION"

echo "docker tag:"
echo "$SC_RELEASE_TAG"

export DOCKER_PETSTORE_IMAGE_NAME=swaggerapi/petstore3
docker build --rm=false -t $DOCKER_PETSTORE_IMAGE_NAME:$SC_RELEASE_TAG .
docker tag $DOCKER_PETSTORE_IMAGE_NAME:$SC_RELEASE_TAG $DOCKER_PETSTORE_IMAGE_NAME:latest
docker push $DOCKER_PETSTORE_IMAGE_NAME:$SC_RELEASE_TAG
docker push $DOCKER_PETSTORE_IMAGE_NAME:latest
docker build --rm=false -t $DOCKER_PETSTORE_IMAGE_NAME:$SC_RELEASE_TAG-telemetry -f Dockerfile-telemetry .
docker tag $DOCKER_PETSTORE_IMAGE_NAME:$SC_RELEASE_TAG-telemetry $DOCKER_PETSTORE_IMAGE_NAME:latest-telemetry
docker push $DOCKER_PETSTORE_IMAGE_NAME:$SC_RELEASE_TAG-telemetry
docker push $DOCKER_PETSTORE_IMAGE_NAME:latest-telemetry
echo "docker images:"
docker images | grep -i petstore3
