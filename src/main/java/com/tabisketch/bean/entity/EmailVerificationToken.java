package com.tabisketch.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/** メールアドレス認証トークン */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationToken {
    /** 識別子文字列 */
    private UUID uuid;
    /** 作成日時 */
    private LocalDateTime createdAt;
    /** 関連する「ユーザー」の識別子 */
    private int userId;
}
