package com.tabisketch.bean.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MAAToken {
    private int id;
    private UUID token;
    private String newMailAddress;
    private int userId;
    private LocalDateTime createdAt;

    /**
     * 新規登録時に使う
     */
    public static MAAToken generate(final int userId) {
        return new MAAToken(
                -1,
                UUID.randomUUID(),
                "",
                userId,
                LocalDateTime.now()
        );
    }

    /**
     * メール編集時に使う
     */
    public static MAAToken generate(final int userId, final String newMailAddress) {
        return new MAAToken(
                -1,
                UUID.randomUUID(),
                newMailAddress,
                userId,
                LocalDateTime.now()
        );
    }
}
