package com.He.dao;

import com.He.pojo.User;
import org.apache.ibatis.annotations.Select;

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
}
