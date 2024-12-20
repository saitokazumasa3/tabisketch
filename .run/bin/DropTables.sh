#!/bin/bash

DB_USER=$1
DB_PASSWORD=$2
DB_NAME=$3

export PGPASSWORD="$DB_PASSWORD"
psql -U "$DB_USER" -d "$DB_NAME" -f .run/sql/DropTables.sql