#!/usr/bin/env bash

#
# You can test other ports running: PORT=9090 ./requests.sh
#

LOCAL_HOST=${HOST:-"localhost"}
LOCAL_PORT=${PORT:-2010}

ENDPOINT="$LOCAL_HOST:$LOCAL_PORT"

echo
curl "http://$ENDPOINT/tasks"
echo
