#!/bin/bash

set -e

# シェルスクリプトのディレクトリを取得
SCRIPT_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

# Create Tables
psql -U postgres -d tabisketch -f "$SCRIPT_DIR/.run/sql/CreateUsersTable.sql"
psql -U postgres -d tabisketch -f "$SCRIPT_DIR/.run/sql/CreatePasswordResetTokensTable.sql"
psql -U postgres -d tabisketch -f "$SCRIPT_DIR/.run/sql/CreateMailAddressAuthTokensTable.sql"
psql -U postgres -d tabisketch -f "$SCRIPT_DIR/.run/sql/CreatePlansTable.sql"
psql -U postgres -d tabisketch -f "$SCRIPT_DIR/.run/sql/CreateDaysTable.sql"
psql -U postgres -d tabisketch -f "$SCRIPT_DIR/.run/sql/CreateGooglePlacesTable.sql"
psql -U postgres -d tabisketch -f "$SCRIPT_DIR/.run/sql/CreateGooglePlaceTypesTable.sql"
psql -U postgres -d tabisketch -f "$SCRIPT_DIR/.run/sql/CreateGoogleTypeAssociationsTable.sql"
psql -U postgres -d tabisketch -f "$SCRIPT_DIR/.run/sql/CreateTransportationsType.sql"
psql -U postgres -d tabisketch -f "$SCRIPT_DIR/.run/sql/CreatePlacesTable.sql"
psql -U postgres -d tabisketch -f "$SCRIPT_DIR/.run/sql/CreatePackingsTable.sql"
