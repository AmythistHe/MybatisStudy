package com.He.dao;

import com.He.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author AmythistHe
 * @version 1.0
 * @description
 * @create 2022/2/11 12:37
 */
public interface UserMapper {
    @Select("select * from user")
    List<User> getUsers();

    // 方法存在多个参数，所有参数前添加@Param。
    @Select("select * from user where id = #{id}")
    User getUserByID(@Param("id") int id);

    @Insert("insert into user(id, name, pwd) values (#{id}, #{name}, #{pwd})")
    int addUser(User user);

    @Update("update user set name = #{name}, pwd = #{pwd} where id = #{id}")
    int updateUser(User user);

    @Delete("delete from user where id = #{id}")
    int deleteUser(@Param("id") int id);
}
