## Mybatis 学习记录

### Mybatis-01
- Mybatis使用流程：首先在config.xml文件中设置数据源，并定义mybatis映射文件位置。
然后定义bean和mysql操作接口，并在Mapper.xml文件中编写mysql操作接口对应的SQL语句。
然后通过SqlSessionFactoryBuilder获取数据源，并构建SqlSessionFactory对象。
最后通过SqlSessionFactory对象获取SqlSession，并通过SqlSession执行SQL语句。
- 如果项目中xml等资源文件不在resource文件夹中，则需要在pom文件中通过build配置resources目录，
防止资源导出失败。
### Mybatis-01
- Environment：Mybatis可以配置多种环境（数据源），但是每个SqlSessionFactory实例只能选择一个环境。
Mybatis默认的事务管理器为JDBC，连接池为POOLED。
- Properties：引用外部配置文件，也可以在该标签中直接添加属性配置。如果有配置字段重复，
优先使用外部配置文件的内容。
- typeAliases：为实体类起别名，可以直接使用注解。
- settings：设置mybatis的运行时行为，如缓存、懒加载、自动生成主键等。
- mappers：基于mapper路径去注册绑定xml对应的mapper文件。
### 生命周期和作用域
- SqlSessionFactoryBuilder：基于数据源创建SqlSessionFactory，一旦创建了SqlSessionFactory，
就不再需要SqlSessionFactoryBuilder。
- SqlSessionFactory：相对于数据库连接池，在程序的运行过程中一直存在，一般为单例模式。
- SqlSession：相当于连接到SqlSessionFactory的一个请求，可以有多个，使用完成后需要关闭。
- mapper：用于通过任意SqlSession实现具体的业务，每个SqlSession可以对应多个mapper，
每个mapper对应一个具体的业务。
### bean属性名和表字段名不一致的问题
- 方法1：SQL查询时基于bean属性名为表字段名添加别名。
- 方法2：使用resultMap标签建立映射，并替代resultType。
```xml
<resultMap id="UserMap" type="user">
        <result column="password" property="pwd"/>
</resultMap>
    
<select id="getUserById" resultMap="UserMap"></select>
```
