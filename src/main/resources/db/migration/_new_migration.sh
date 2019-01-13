#!/bin/bash

echo -n "Enter name of new migration: "
read migrationName

java -jar sqlTimestampGenerator.jar $migrationName

exit


