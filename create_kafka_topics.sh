#!/bin/bash

echo Waiting a second to be sure kafka is up and running....
sleep 10

topics=(
  "rules-events"
)

# shellcheck disable=SC2068
for topic in ${topics[@]}
do
  echo Creating topic: $topic
  kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 1 --topic $topic
done

echo All topics created successfully...
