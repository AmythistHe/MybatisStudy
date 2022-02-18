import com.He.dao.UserMapper;
import com.He.pojo.User;
import com.He.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * @author AmythistHe
 * @version 1.0
 * @description
 * @create 2022/2/18 16:52
 */
public class MyTest {
    @Test
    public void test1() {
        SqlSession session = MybatisUtils.getSqlSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        try {
            User user1 = mapper.queryUserById(1001);
            System.out.println(user1);
            System.out.println("-------------------------------");
            User user2 = mapper.queryUserById(1001);
            System.out.println(user2);
            // true，证明user2为一级缓存中的数据
            System.out.println(user1 == user2);
            session.clearCache();
            User user3 = mapper.queryUserById(1001);
            System.out.println(user1 == user3);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
