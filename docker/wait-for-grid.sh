#!/bin/bash
# wait-for-grid.sh

set -e

if [ -z "$HOST_IP" ]; then
    echo "variable HOST_IP has to be defined"
else
    while ! curl -sSL "http://localhost:4444/wd/hub/status" 2>&1 \
             | grep "\"ready\": true," >/dev/null; do
            echo 'Waiting for the Grid'
         sleep 1
    done

    >&2 echo "Selenium Grid is up"
fi