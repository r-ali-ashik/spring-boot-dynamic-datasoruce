# Dynamic-Datasource-Routing!

## Use case
Let's begin by discussing a specific use case. There may arise a situation where we need to dynamically change the DATASOURCE. For instance, let's say you are developing a highly impressive RESTful API, and your clients are local banks. These banks have a preference for using their own integrated databases instead of the default database used by your service. Consequently, you end up with a RESTful API that needs to handle multiple databases.

To differentiate requests coming from different banks, you can utilize information from the request header or path. In `Spring 2.0.1`, an `AbstractRoutingDataSource` was introduced, which proves to be quite useful in this scenario. The basic concept is that a routing DataSource acts as an intermediary, allowing the determination of the 'real' DataSource dynamically at runtime.

## Technology Used
- Spring Boot 2.2.0
- MySQL
- Spring Data JPA

## Objectives
 - We will create two database ( **database1** & **database2**). Each of the database will have one table **EMPLOYEE**
 - We will expose a **GET** API with a path variable  **/{bank}/employees**. Path variable **{bank}** will be replaced in run time. Example: 
  -- `http://localhost:8080/bank1/employees` 
  -- `http://localhost:8080/bank2/employees`
 - If the path variable is **bank1** application will serve data from the **database1**, if it is  **bank2** then from **database2** and so on.

## How we are going to do it?
- We will write an interceptor, which will intercept every request and read the path variable, and based on the path variable we will decide which datasoruce to use (at startup one of the datasoruce will be selected as the default datasource)
- We will not let spring boot auto-configuration come our way to configure the data source. So we will disable the data-source and transaction-manager auto-configuration.
- We will configure two data source **datasurce1**, **datasoruce2** and one **transactionManager**,  one **entityManagerFactory** manually
- We will provide the corresponding property in the **application.properties** file to configure both data sources. Properties will look like the following:
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
				 
		server.port=8080
</pre>

## SQL Script to create table and insert data 
<pre>
// database1
create table Employee
(
    id       int auto_increment primary key,
    bankName varchar(255) null,
    name     varchar(255) null
);
INSERT INTO database1.Employee (id, bankName, name) VALUES (1, 'Bank 1', 'ABC Bank');


// database2
create table Employee
(
    id       int auto_increment primary key,
    bankName varchar(255) null,
    name     varchar(255) null
);
INSERT INTO database2.Employee (id, bankName, name) VALUES (1, 'Bank 2', 'XYZ Bank');
</pre>

## Try it out 
**With bank1 in the path**

`GET http://localhost:8080/bank1/employees`
<pre>
curl --location --request GET 'http://localhost:8080/bank1/employees'
</pre>

**With bank2 in the path** 

`GET http://localhost:8080/bank2/employees`
<pre>
curl --location --request GET 'http://localhost:8080/bank2/employees'
</pre>
