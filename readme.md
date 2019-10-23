# dynamic-datasource!
Lest  first start with discussing  one use-case. We might encounter the requirement of changing DATASOURCE dynamically. For example you are writing one kickass RESTful api and  the your clients are your local banks banks. Banks doesn't want to use the database use provide each of the banks want to have their own database integrated with your service. So you have one RESTful api but multiple database. 

You can distinguish requests from banks from data from the request header or from request path information. 
Spring 2.0.1 introduced an `AbstractRoutingDataSource` which comes in handy in this regard. The general idea is that a routing DataSource acts as an intermediary - while the ‘real’ DataSource can be determined dynamically at runtime

## Technology Used
- Spring Boot 2.2.0
- MySQL
- Spring Data JPA

## Objectives
- We will create two database ( **database1** & **database2**). Each of the database will have one table **TBL_EMPLOYEE**
-  We will expose one **GET** api with path having **/{bank}/employees**.  Path variable **{bank}** will be replaced in run time. Example: 
 -- http://localhost:8080/bank1/employees
 --  http://localhost:8080/bank2/employees
 - If context path starts with **bank1** application will serve data from the **database1**, if it starts with **bank2** then from **database2** and so on.

## Target Overview
- We will introduce one interceptor, Which will read the request context decides which database to use
- We will not let spring boot auto-configuration to come in our way regarding data source configuration. We will disable data-source and transac-manager auto-configuration. 
- We will create the two data source **datasurce1**, **datasoruce2** and one **transactionManager**,  one **entityManagerFactory** manually
-  application properties will have database related properties for both **datasource**. For example: 
<pre>
				spring.datasource.driver-class-name.1=com.mysql.cj.jdbc.Driver  
				spring.datasource.url.1=jdbc:mysql://localhost:3306/database1  
				spring.datasource.username.1=root  
				spring.datasource.password.1=admin  
				  
				  
				spring.datasource.driver-class-name.2=com.mysql.cj.jdbc.Driver  
				spring.datasource.url.2=jdbc:mysql://localhost:3306/database2  
				spring.datasource.username.2=root  
				spring.datasource.password.2=admin  
				 
				database.hibernate.schema_update = update  
				database.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect  
				database.hibernate.show_sql = true  
				database.hibernate.format_sql = true 
				 
				server.port=8081
</pre>

## File Structure