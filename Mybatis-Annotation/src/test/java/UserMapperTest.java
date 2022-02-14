import com.He.dao.UserMapper;
import com.He.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * @author AmythistHe
 * @version 1.0
 * @description
 * @create 2022/2/14 21:25
 */
public class UserMapperTest {
    @Test
    public void test() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        // 底层主要使用反射
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.getUsers().stream().forEach(System.out::println);
    }
}
