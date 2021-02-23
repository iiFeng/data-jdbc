# data-jdbc
practice java jdbc

### how to use
- install and open MySql in your PC
- find init.sql in `./data-jdbc/src/main/resources/init.sql` then init in mysql
- change jdbcUrl in `./data-jdbc/src/main/java/service/StudentService.java`,example
```
jdbcUrl = "jdbc:mysql://192.168.232.233:3306/learnjdbc?useSSL=false&characterEncoding=utf8";
```
- run Main.java

### introduce how to write
1. create Student javaBean       
2. gradle mysql connector
3. use setting properties to connect java jdbc
4. use PreparedStatement by sql to find mysql data
5. use ResultSet to change into javaBean
6. add Exception