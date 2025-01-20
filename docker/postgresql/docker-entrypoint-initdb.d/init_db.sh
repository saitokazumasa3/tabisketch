#!/bin/bash

set -e  # エラーが発生した場合はスクリプトを終了

# 必要な環境変数の確認
if [[ -z "$POSTGRES_DB" ]]; then
  echo "Error: POSTGRES_DB is not set."
  exit 1
fi

# SQLファイルのパスを変数化
SQL_FILE="/sql/CreateTables.sql"

# SQLファイルが存在するか確認
if [[ ! -f "$SQL_FILE" ]]; then
  echo "Error: SQL file '$SQL_FILE' not found."
  exit 1
fi

# テーブル作成処理
psql -v ON_ERROR_STOP=1 --dbname="$POSTGRES_DB" <<-EOSQL
  \i $SQL_FILE
EOSQL

# 処理成功時のメッセージを here document で表示
cat <<EOS
==============================
Database tables created successfully.
==============================
EOS
