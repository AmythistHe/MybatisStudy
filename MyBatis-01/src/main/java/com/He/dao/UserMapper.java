package com.He.dao;

import com.He.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author AmythistHe
 * @version 1.0
 * @description
 * @create 2022/2/11 12:37
 */
public interface UserMapper {
    List<User> getUserList();

    List<User> getUserByLimit(Map<String, Object> map);

    List<User> getUserByRowBounds(Map<String, Object> map);
}
