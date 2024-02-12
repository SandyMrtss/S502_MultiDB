# ITAcademy JAVA DiceGame Project

The following RestAPI was developed as part of the Java programming boot camp at ITAcademy Barcelona.

It's a simple Dice Game where two randomly obtained dice must sum 7 to win.

This API uses both MySQL and MongoDB for persistence. On [S502_MySQL](https://github.com/SandyMrtss/S0502_MySQL) you can find the same API using only MySQL for persistence.

## Table Of Contents
1. [Requirements](#req)
2. [Configuration](#conf)
    1. [Installation](#installation)
    2. [Data Persistence](#persist)
    3. [Default properties](#prop)
3. [Functionality](#func)
   1. [Security](#sec)
   2. [Make a Request](#requests)
5. [Testing](#testing)
6. [Built with](#built)
7. [Author](#author)

## 1. Requirements <a name="req"></a>
- Java Development Kit (JDK) 8 or higher
- MySQL database and JDBC Driver for persistence
- MongoDB database for persistence
- Postman or similar to manage requests
## 2. Configuration <a name="conf"></a>
### 2.i. Installation <a name="installation"></a>
Clone the repository to your local machine:
```git
git clone https://github.com/SandyMrtss/S502_MongoDB.git
```
*Before executing the program, please read  [**2.ii. Data persistence**](#persist).*
<br><br>
To execute the program, use a Java IDE (I used IntelliJ IDE) and execute <strong>S05T03MartosSandraApplication</strong> class.
<br><br>
It can also be done from the command line as you would with any Maven project. 
### 2.ii. Data Persistence <a name="persist"></a>
Before starting, you must create your database on MySQL and MongoDB and make sure application.properties matches your local settings. Mongo's collection's name should be *games*.
<br>
If you don't use MongoDB's local server, make sure to update the necessary properties. Consider also adding username and password fields if your database configuration needs it.
- You don't have to create tables for the API, they will be automatically created when the API runs for the first time.
### 2.iii. Default Properties <a name="prop"></a>
Please ensure your properties file follows this pattern:
```properties
server.port=9000

#MySQL config
spring.datasource.url= jdbc:mysql://<host>:3306/<databaseName>?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=<username>
spring.datasource.password=<password>

spring.jpa.hibernate.ddl-auto= update

#MongoDB config
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=<databaseName>
spring.data.mongodb.auto-index-creation=true

#swagger (openAPI) config
springdoc.api-docs.path=/flor/v1/api-docs
springdoc.swagger-ui.operationsSorter=method
```
## 3. Functionality <a name="func"></a>
### 3.i. Security <a name="sec"></a>
This API has JWT security. You must add your Token as a BEARER to all requests.
- SignUp Request
```
curl --location  --request POST 'http://localhost:9000/api/v1/diceGame/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "yourName",
    "lastName": "yourLastName",
    "email": "yourMail@gmail.com",
    "password": "yourPassword"
}'
```
- SignIn Request
```
curl --location  --request POST 'http://localhost:9000/api/v1/diceGame/auth/signin' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "yourMail@gmail.com",
    "password": "yourPassword"
}'
```
### 3.ii. Make a Request <a name="requests"></a>
This API supports the following requests:
- Adds new player. It doesn't allow duplicated usernames but accepts no username (will be an anonymous player)
```
curl --location --request POST 'http://localhost:9000/api/v1/diceGame/players' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TOKEN>' \
--data'{
    "username":"newUserName"
}'
```
- Updates player's username
```
curl --location --request PUT 'http://localhost:9000/api/v1/diceGame/players/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TOKEN>' \
--data '{
    "username":"updatedUsername"
}'
```
- A specific user plays a new game
```
curl --location --request POST 'http://localhost:9000/api/v1/diceGame/players/1/games' \
--header 'Authorization: Bearer <TOKEN>'
```

- Deletes all previous plays from a user
```
curl --location --request DELETES 'http://localhost:9000/api/v1/diceGame/players/1/games' \
--header 'Authorization: Bearer <TOKEN>'
```
- Returns all players and their average success rate
```
curl --location --request GET 'http://localhost:9000/api/v1/diceGame/players' \
--header 'Authorization: Bearer <TOKEN>'
```
- Returns all plays from a player
```
curl --location --request GET 'http://localhost:9000/api/v1/diceGame/players/1/games' \
--header 'Authorization: Bearer <TOKEN>'
```
- Returns the overall average success rate
```
curl --location --request GET 'http://localhost:9000/api/v1/diceGame/players/ranking' \
--header 'Authorization: Bearer <TOKEN>'
```
- Returns the user with the worst success rate
```
curl --location --request GET 'http://localhost:9000/api/v1/diceGame/players/ranking/loser' \
--header 'Authorization: Bearer <TOKEN>'
```
- Returns the user with the best success rate
```
curl --location --request GET 'http://localhost:9000/api/v1/diceGame/players/ranking/winner' \
--header 'Authorization: Bearer <TOKEN>'
```

## 4. Testing <a name="testing"></a>
There are unit tests for the service implementations that use Mockito to replicate database persistence.
<br><br>
For integration tests, you must create two new databases (MySQL and MongoDB) that will delete, create and populate tables on startup. If you change the database's name, make sure to change it on the scripts too. You can find them in test/resources 
<br><br>
Please ensure that the test properties file follows this pattern:
```properties
server.port=9000

#MySQL config
spring.datasource.url= jdbc:mysql://<host>/<dausTest>?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=<username>
spring.datasource.password=<password>

#initialize data set from scratch for tests
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
spring.jpa.hibernate.ddl-auto=none

#MongoDB config
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=DiceGameTestDB
spring.data.mongodb.auto-index-creation=true
```
## 5. Built with <a name="built"></a>
- [Maven](https://maven.apache.org/) - Dependency manager
- [Spring Security](https://spring.io/projects/spring-security/) - Security for SignUp and SignIn
- [MySQL Database](https://www.mysql.com/) - Persistence
- [MongoDB](https://www.mongodb.com/es) - Persistence
- [Junit](https://junit.org/junit5/) - Testing
- [Mockito](https://site.mockito.org/) - Mocks for unit testing 
- [Swagger](https://swagger.io/) - Documentation

## 6. Author <a name="author"></a>
This project was developed by:
[@SandyMrtss](https://github.com/SandyMrtss)
