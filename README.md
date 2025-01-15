# たびすけっち

## ブランチ

- main ... メインブランチ
- release ... アプリリリースブランチ

その他ブランチ名は [GitHub フロー](https://docs.github.com/ja/get-started/using-github/github-flow) に従い、変更内容に沿った名前にする。

## 用語

- MailAddressAuthenticationToken -> MAAToken  
  メールアドレス認証トークン

## 環境構築

1. コマンドラインで `npm install` を実行する
2. DATABASEを作成する
    ```postgresql
    CREATE DATABASE tabisketch;
    ```
3. 環境変数を設定する

## 環境変数

```
DATABASE_URL        データベースのURL
DATABASE_USERNAME   データベースのユーザー名
DATABASE_PASSWORD   データベースのパスワード
MAIL_USERNAME       Gmailアカウントのユーザー名
MAIL_PASSWORD       Gmailアカウントのアプリパスワード
GOOGLE_MAPS_API_KEY GoogleMapのAPIキー
```

## 各種コマンド

#### TailwindCSSビルド

`.run/bin/BuildTailwindCss.sh`

#### テーブル一括作成

`.run/bin/CreateTables.sh [PostgreSQLのユーザー名] [PostgreSQLのパスワード] tabisketch`

#### テーブル一括削除

`.run/bin/DropTables.sh [PostgreSQLのユーザー名] [PostgreSQLのパスワード] tabisketch`

#### PlaywrightによるE2Eテストの実行

```bash
npm run e2e
```
