# たびすけっち

# 規則
## ブランチ

- main ... メインブランチ
- release ... アプリリリースブランチ

その他ブランチ名は [GitHub フロー](https://docs.github.com/ja/get-started/using-github/github-flow) に従い、変更内容に沿った名前にする。

## 用語

- MailAddressAuthenticationToken -> MAAToken  
  メールアドレス認証トークン

# 開発
## 環境構築
以下の手順をコマンドラインで実行する。
1. `npm install`
2. `.env.example` を `.env` として複製、値を書き換える
3. `cd docker`
4. `docker compose up -d`
