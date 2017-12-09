#!/bin/bash

echo "Hello Requested $1"

./kafkawatcher-1.0/bin/kafkawatcher $1 | $MAPD_PATH/SampleCode/StreamInsert --host localhost --port 9091 --delim '|' -u mapd -p HyperInteractive --database mapd --table csticket_stream --batch 1
