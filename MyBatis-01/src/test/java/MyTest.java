import com.He.dao.UserMapper;
import com.He.pojo.User;
import com.He.utils.MybatisUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author AmythistHe
 * @version 1.0
 * @description
 * @create 2022/2/11 14:15
 */
public class MyTest {
    @Test
    public void test1() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.getUserList().stream().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    // log
    static Logger logger = Logger.getLogger(MyTest.class);
    @Test
    public void test2() {
        // DEBUG < INFO < WARN < ERROR < FATAL
        logger.info("into test2 method");
        logger.debug("debug into test2 method");
        logger.error("error into test2 method");
    }

    // limit
    @Test
    public void test3() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("startIndex", 0);
        map.put("pageSize", 2);
        mapper.getUserByLimit(map).stream().forEach(System.out::println);
    }

    // RowBounds
    @Test
    public void test4() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        RowBounds rowBounds = new RowBounds(1,2);
        List<User> userList = sqlSession.selectList("com.com.He.dao.UserMapper.getUserByRowBounds", null, rowBounds);
        userList.stream().forEach(System.out::println);
    }
}
