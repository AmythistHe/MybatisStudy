package com.He.dao;

import com.He.pojo.Blog;

import java.util.List;
import java.util.Map;

/**
 * @author AmythistHe
 * @version 1.0
 * @description
 * @create 2022/2/18 11:12
 */
public interface BlogMapper {
    int addBlog(Blog blog);

    List<Blog> queryBlogIF(Map map);
}
