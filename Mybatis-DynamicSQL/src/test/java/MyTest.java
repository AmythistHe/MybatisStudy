import com.He.dao.BlogMapper;
import com.He.pojo.Blog;
import com.He.utils.IDUtils;
import com.He.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author AmythistHe
 * @version 1.0
 * @description
 * @create 2022/2/18 11:37
 */
public class MyTest {
    @Test
    public void addInitBlog() {
        SqlSession session = MybatisUtils.getSqlSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        try {
            mapper.addBlog(new Blog(IDUtils.getId(), "Java", "He",
                    new Date(), 1000));
            mapper.addBlog(new Blog(IDUtils.getId(), "C++", "He",
                    new Date(), 1000));
            mapper.addBlog(new Blog(IDUtils.getId(), "Python", "He",
                    new Date(), 1000));
            mapper.addBlog(new Blog(IDUtils.getId(), "C#", "He",
                    new Date(), 1000));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Test
    public void queryBlogIF() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("title", null);
        mapper.queryBlogIF(map).stream().forEach(System.out::println);
    }
}
