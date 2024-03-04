#!/bin/bash

CUR=$(pwd)
TMPDIR="$(dirname -- "${0}")"

SC_RELEASE_TAG="v$SC_VERSION"

#####################
### publish pre-prepared release (tag is created)
#####################
python $CUR/CI/publishRelease.py "$SC_RELEASE_TAG"

