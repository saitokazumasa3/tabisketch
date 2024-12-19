package com.tabisketch.mapper;

import com.tabisketch.bean.entity.MAAToken;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface IMAATokensMapper {
    @Insert("INSERT INTO maa_tokens (user_id, new_mail_address) VALUES (#{userId}, #{newMailAddress})")
    @Options(useGeneratedKeys = true, keyProperty = "id,token")
    int insert(final MAAToken maaToken);

    @Select("SELECT * FROM maa_tokens WHERE token = #{token}")
    MAAToken selectByToken(final UUID token);

    @Delete("DELETE FROM maa_tokens WHERE id = #{id}")
    int delete(final int id);
}
