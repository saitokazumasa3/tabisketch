package com.tabisketch.mapper;

import com.tabisketch.bean.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IUsersMapper {
    @Insert("INSERT INTO users (mail_address, password) VALUES (#{mailAddress}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(final User user);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User selectById(final int id);

    @Select("SELECT * FROM users WHERE mail_address = #{mailAddress}")
    User selectByMailAddress(final String mailAddress);

    @Update("UPDATE users " +
            "   SET mail_address = #{mailAddress}, " +
            "       password = #{password}, " +
            "       mail_address_authenticated = #{mailAddressAuthenticated} " +
            "   WHERE id = #{id}")
    int update(final User user);
}
