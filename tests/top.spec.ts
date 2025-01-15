import { test, expect } from '@playwright/test';
import { url } from './settings'

test('リダイレクト', async ({ page }) => {
  // TOPページにアクセスする
  await page.goto(url);

  // http://localhost:8080/top にリダイレクトされる
  await expect(page).toHaveURL(`${url}/top`);
});

test('タイトル', async ({ page }) => {
  // TOPページにアクセスする
  await page.goto(`${url}/top`);

  // タイトルタグが 'トップ' であることを確認する
  await expect(page).toHaveTitle('トップ');
});

test('ログインのnavリンク', async ({ page }) => {
  // TOPページにアクセスする
  await page.goto(`${url}/top`);

  // ログインリンクが header > nav > ul > li:nth-child(1) に表示されていることを確認する
  const loginLink = page.locator('header > nav > ul > li:nth-child(1) > a'); // 'a'タグの通常セレクタを使用
  await expect(loginLink).toHaveText('ログイン');
});

test('新規登録のnavリンク', async ({ page }) => {
  // TOPページにアクセスする
  await page.goto(`${url}/top`);

  // 新規登録リンクが header > nav > ul > li:nth-child(2) 表示されていることを確認する
  const registerLink = page.locator('header > nav > ul > li:nth-child(2) > a'); // 'a'タグの通常セレクタを使用
  await expect(registerLink).toHaveText('新規登録');
});
