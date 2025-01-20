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

1. コマンドラインで `npm install` を実行
2. DATABASEを作成
    ```postgresql
    CREATE DATABASE tabisketch;
    ```
3. .envファイルを作成
    `.env.example` ファイルをコピーし、適切な値に書き換えて `.env` ファイルを作成する
4. `./bin/CreateTable.sh` を実行

## 各種コマンド

### 実行
```shell
./bin/BuildAndRun.sh
```
TailwindCSSも同時にビルドされる

### Jar ビルド
```shell
./bin/BuildJar.sh
```
テストも実行される

### TailwindCSS ビルド
```shell
./bin/BuildTailwindCss.sh
```

### Table一括作成
```shell
./bin/CreateTable.sh
```

### Table一括削除
```shell
./bin/DropTable.sh
```

### docker compose 環境のビルド
```shell
npm run docker:build
```

### docker compose 環境の起動
```shell
npm run docker:up
```

### docker compose 環境の破棄
```shell
npm run docker:down
```
