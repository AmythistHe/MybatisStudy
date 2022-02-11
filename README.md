## Mybatis 学习记录

### Mybatis-01
- Mybatis使用流程：首先在config.xml文件中设置数据源，并定义mybatis映射文件位置。
然后定义bean和mysql操作接口，并在Mapper.xml文件中编写mysql操作接口对应的SQL语句。
然后通过SqlSessionFactoryBuilder获取数据源，并构建SqlSessionFactory对象。
最后通过SqlSessionFactory对象获取SqlSession，并通过SqlSession执行SQL语句。
- 如果项目中xml等资源文件不在resource文件夹中，则需要在pom文件中通过build配置resources目录，
防止资源导出失败。

