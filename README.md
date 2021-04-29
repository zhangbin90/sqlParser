基于mybatis的动态sql解析工具类
=

背景
-
我们一般在用mybatis的时候，解析的是写死在sqlmapper文件中的sql语句，而且是在系统启动的时候就解析完成了。
而我在做某个项目的时候需要解析用户输入的sql，系统已经在运行了，用户输入之前也不知道sql的具体内容，所以就把mybatis中相关的代码提取
出来，写了这么一个工具类

使用方法
-
```
SqlParser sqlParser = new SqlParser();
String sql = "select * from users WHERE id in (<foreach item=\"id\" index=\"index\" collection=\"idList\" separator=\",\" >#{id}</foreach>)";
List<Long> idList = Arrays.asList(123L,234L,345L);
Map<String, Object> params = new HashMap<String, Object>();
params.put("idList", idList);
SqlData sqlData = sqlParser.parse(sql, params);
System.out.println(sqlData.getSql());//select * from users WHERE id in (  ? , ? , ? )
System.out.println(sqlData.getParamValues());//[123, 234, 345]
```
