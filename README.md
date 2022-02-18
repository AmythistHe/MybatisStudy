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
- settings：设置mybatis的运行时行为，如缓存、懒加载、自动生成主键、日志等。
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
### 日志
- 日志工厂：settings中的logImpl用于指定mybatis日志的具体实现，未指定时将自动查找。
主要包括SLF4J、LOG4J、STDOUT_LOGGING等实现方式。
- STDOUT_LOGGING(标准日志输出)
```xml
<settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
```

```text
PooledDataSource forcefully closed/removed all connections.
Opening JDBC Connection
Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
Created connection 1866850137.
Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@6f45df59]
==>  Preparing: select * from school.user limit 3
==> Parameters: 
查询结果
Resetting autocommit to true on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@6f45df59]
Closing JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@6f45df59]
Returned connection 1866850137 to pool.
```
- log4j(日志控制包)：可以通过Loggers(日志类别和级别)、Appenders(日志输出位置和格式)、Layout(日志输出的形式)配置日志。
log4j可以通过properties和xml两种方式配置。log4j日志级别为DEBUG < INFO < WARN < ERROR < FATAL。
### 分页
- Limit语句：基于SQL语句实现。
- RowBounds：在java代码层面实现。
- PageHelper：Mybatis分页插件。
### Mybatis-Annotation
- 使用注解替代mapper.xml。首先在接口上设置注解，然后在核心配置文件(mybatis-config)中绑定接口。
```java
@Select("select * from user")
    List<User> getUsers();
```
- 注解本质通过反射实现，底层为动态代理。
- @Param注解：指定属性名。方法存在一个或多个基本类型参数，所有参数前建议添加@Param，引用类型不需要添加。
- @Data注解：无参构造、get、set、toString、hashcode、equals
- @AllArgsConstructor注解：有参构造
- @NoArgsConstructor：无参构造
### 一对一(或一对多)查询
- 一对一：student表包含teacher属性，在查询student表时需要使用resultMap标签中的association(bean映射)建立两表之间的映射。
- 一对多：student表包含list格式的teacher属性，在查询student表时需要使用resultMap标签中的collection(集合映射)建立两表之间的映射。
- resultMap标签中的JavaType用来指定实体类中属性的类型，ofType用来指定映射到list或集合中的bean类型、泛型中的约束类型。
### 动态SQL
- 动态SQL：基于不同的条件去生成不同的SQL语句，减少SQL拼接。
- if：在where语句中提供多条件查询，if标签满足后才会执行该条件。
```xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’
  <if test="title != null">
    AND title like #{title}
  </if>
  <if test="author != null and author.name != null">
    AND author_name like #{author.name}
  </if>
</select>
```
- choose、when、otherwise：在choose标签中提供多条件查询，if标签需要使用全部的条件，而when标签只用从多个条件中选择一个使用。
如果所有的条件都不满足，则执行otherwise标签中的条件。
```xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG WHERE state = ‘ACTIVE’
  <choose>
    <when test="title != null">
      AND title like #{title}
    </when>
    <when test="author != null and author.name != null">
      AND author_name like #{author.name}
    </when>
    <otherwise>
      AND featured = 1
    </otherwise>
  </choose>
</select>
```
- trim、where、set：where标签和set标签分别用于替代where语句和set语句，可以避免一些语法错误。
trim标签可以通过自定义操作去替代where标签和set标签。
```xml
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG
  <where>
    <if test="state != null">
         state = #{state}
    </if>
    <if test="title != null">
        AND title like #{title}
    </if>
    <if test="author != null and author.name != null">
        AND author_name like #{author.name}
    </if>
  </where>
</select>
```
```xml
<update id="updateAuthorIfNecessary">
  update Author
    <set>
      <if test="username != null">username=#{username},</if>
      <if test="password != null">password=#{password},</if>
      <if test="email != null">email=#{email},</if>
      <if test="bio != null">bio=#{bio}</if>
    </set>
  where id=#{id}
</update>
```
```xml
<trim prefix="WHERE" prefixOverrides="AND |OR ">
  ...
</trim>

<trim prefix="SET" suffixOverrides=",">
  ...
</trim>
```
- sql、include：使用sql标签抽取公用的部分，在需要使用的地方使用include标签应用，从而实现代码复用。
sql标签中不建议放where标签。
```xml
<sql id="if-state-title">
    <if test="state != null">
         state = #{state}
    </if>
    <if test="title != null">
        AND title like #{title}
    </if>
</sql>
<select id="findActiveBlogLike"
     resultType="Blog">
  SELECT * FROM BLOG
  <where>
    <include refid="if-state-title"></include>
  </where>
</select>
```
- foreach：对集合进行遍历，并基于集合中每一项完成各种操作。
```xml
<select id="selectPostIn" resultType="domain.blog.Post">
  SELECT *
  FROM POST P
  <where>
    <foreach item="item" index="index" collection="list"
        open="ID in (" separator="," close=")" nullable="true">
          #{item}
    </foreach>
  </where>
</select>
```
### Mybatis缓存
- Mybatis缓存：Mybatis中包含一级(SqlSession，本地缓存)和二级(namespace)缓存。
所有的数据都会先放到一级缓存中，只有当会话提交或关闭时，数据才会放到二级缓存中。
- 一级缓存(SqlSession级)：多次查询相同数据时，缓存生效。缓存失效情况：增删改操作可能会改变原来的数据，必定会刷新缓存；查询不同东西；查询不同mapper.xml；手动通过SqlSession的clearCache方法清理缓存。
Mybatis默认开启一级缓存，结构为map，只在一个SqlSession中缓存数据。
- 二级缓存(namespace级)：二级缓存需要在setting标签中开启，并通过cache标签手动配置。二级缓存可设置LRU、FIFO、SOFT、WEAK等策略，默认为LRU。
二级缓存作用于cache标签所在的映射文件中的语句，多个sqlSession可以共享一个Mapper的二级缓存区域，作用范围比一级缓存大。
```xml
setting name="cacheEnabled" value="true"/>
```
```xml
<!--二级缓存-->
    <!--淘汰策略为FIFO，60s刷新一次缓存，缓存对象数目为512，缓存只读-->
    <cache eviction="FIFO"
           flushInterval="60000"
           size="512"
           readOnly="true"/>
```
- 查询顺序：二级缓存->一级缓存->数据库
- 自定义缓存框架(ehcache)：分布式缓存。
```xml
<cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
```