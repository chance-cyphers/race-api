#!/bin/bash
docker run --rm --name racedb -e POSTGRES_DB=raceDb -e POSTGRES_USER=foo -e POSTGRES_PASSWORD=foo  -d -p 5432:5432 postgres
echo "(use 'lobos.migrations) (run-migrations)" | lein repl