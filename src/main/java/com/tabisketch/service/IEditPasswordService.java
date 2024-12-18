package com.tabisketch.service;

public interface IEditPasswordService {
    boolean editPassword(final String mailAddress, final String newPassword);  // パスワード編集メソッド
}
