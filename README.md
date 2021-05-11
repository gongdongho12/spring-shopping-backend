# spring-shopping-backend

The modules used are as follows.
* JPA
* R2DBC
* MYSQL
* SWAGGER
* JACKSON
* WEB-FLUX
* JWT

시작하기 전에
-------------
1. application.yml 추적 방지
<pre>
<code>
   git update-index --assume-unchanged src/main/resources/application.yml
</code>
</pre>
- 이후 application.yml내 db커넥션 수정
2. sql 등록
- shopping_query.sql > mariadb or mysql.
3. spring profile "local" 지정
- application.yml내 db커넥션 파일을 local로 세팅해 두었기에 설정 필요. (Intelij run.xml로 등록되있음)