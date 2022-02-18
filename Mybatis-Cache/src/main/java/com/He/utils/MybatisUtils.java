package com.He.utils;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author AmythistHe
 * @version 1.0
 * @description
 * @create 2022/2/11 12:25
 */
public class MybatisUtils {


    private static SqlSessionFactory sqlSessionFactory;
    // ��ȡSqlSessionFactory����
    static {
        String resource = "mybatis-config.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ��ȡSQLSession
    public static SqlSession getSqlSession() {
        // trueΪ�Զ��ύ����
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        return sqlSession;
    }
}
