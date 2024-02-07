# ITAcademy JAVA DiceGame Project

The following RestAPI was developed as part of the Java programming bootcamp at ITAcademy Barcelona.

It's a simple Dice Game where two randomly obtained dice must sum 7 to win.

This API uses both MySQL and MongoDB for persistence. On [S502_MySQL](https://github.com/SandyMrtss/S0502_MySQL) you can find the same API using only MySQL for persistence.

## Table Of Contents
1. [Requirements](#req)
2. [Configuration](#conf)
    1. [Installation](#installation)
    2. [Data Persistence](#persist)
    3. [Default properties](#prop)
3. [Functionality](#func)
4. [Testing](#testing)
5. [Built with](#built)
6. [Author](#author)

## 1. Requirements <a name="req"></a>
- Java Development Kit (JDK) 8 or higher
- MySQL database and JDBC Driver for persistence
- MongoDB database for persistence
- Postman or similar to manage requests
## 2. Configuration <a name="conf"></a>
### 2.1. Installation <a name="installation"></a>
Clone the repository to your local machine:
```git
git clone https://github.com/SandyMrtss/S502_MongoDB.git
```
*Before executing the program, please read  [**2.2. Data persistence**](#persist).*
<br><br>
To execute the program, use a Java IDE (I used IntelliJ IDE) and execute <strong>S05T03MartosSandraApplication</strong> class.
<br><br>
Can also be done from the command line as you would with any maven project. 
### 2.2. Data Persistence <a name="persist"></a>
Before starting, you must create your database and make sure application.properties matches your local settings. 
- You don't have to create tables for the API, they will be automatically created when the API runs for the first time.
### 2.3. Default Properties <a name="prop"></a>
Please ensure your properties file follows this pattern:
```properties
server.port=9000

#MySQL config
spring.datasource.url= jdbc:mysql://192.168.56.102:3306/daus?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=sandy
spring.datasource.password=1234

spring.jpa.hibernate.ddl-auto= update

#MongoDB config
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=DiceGameDB
spring.data.mongodb.auto-index-creation= true

#swagger (openAPI) config
springdoc.api-docs.path=/flor/v1/api-docs
springdoc.swagger-ui.operationsSorter=method
```
## 3. Functionality <a name="func"></a>
This API supports the following requests:
- Adds new player. Doesn't allow duplicated usernames but accepts no username (will be an anonymous player)
```
curl --location --request POST 'http://localhost:9000/diceGame/v1/players' \
--header 'Content-Type: application/json' \
--data '{
    "username":"newUserName"
}'
```
- Updates players username
```
curl --location --request PUT 'http://localhost:9000/diceGame/v1/players/1' \
--header 'Content-Type: application/json' \
--data '{
    "username":"updatedUsername"
}'
```
- A specific user plays new game
```
curl --location --request POST 'http://localhost:9000/diceGame/v1/players/1/games'
```

- Deletes all previous plays from a user
```
curl --location --request DELETES 'http://localhost:9000/diceGame/v1/players/1/games'
```
- Returns all players and their average success rate
```
curl --location --request GET 'http://localhost:9000/diceGame/v1/players'
```
- Returns all plays from a player
```
curl --location --request GET 'http://localhost:9000/diceGame/v1/players/1/games'
```
- Returns the overall average success rate
```
curl --location --request GET 'http://localhost:9000/diceGame/v1/players/ranking'
```
- Returns the user with the worse success rate
```
curl --location --request GET 'http://localhost:9000/diceGame/v1/players/ranking/loser'
```
- Returns the user with the best success rate
```
curl --location --request GET 'http://localhost:9000/diceGame/v1/players/ranking/winner'
```

## 4. Testing <a name="testing"></a>
There are unit tests for the service implementations that use Mockito to replicate database persistence.
<br><br>
For integration tests, you must create a new database that will delete, create and populate tables on startup.
<br><br>
Please ensure that the test properties file follows this pattern:
```properties
server.port=9000

#MySQL config
spring.datasource.url= jdbc:mysql://192.168.56.102:3306/dausTest?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=sandy
spring.datasource.password= 1234

#inicialize data set from scratch for tests
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true
spring.jpa.hibernate.ddl-auto=none

#MongoDB config
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=DiceGameTestDB

spring.data.mongodb.auto-index-creation= true
```
## 5. Built with <a name="built"></a>
- [Maven](https://maven.apache.org/) - Dependency manager
- [Spring Security](https://spring.io/projects/spring-security/) - Para automatizar el control de acceso (autenticación y autorización) y sesión de una aplicación
- [MySQL Database](https://www.mysql.com/) - Persistence
- [Junit](https://junit.org/junit5/) - Testing
- [Mockito](https://site.mockito.org/) - Mocks for unit testing 
- [Swagger](https://swagger.io/) - Documentation

## 6. Author <a name="author"></a>
This project was developed by:
[@SandyMrtss](https://github.com/SandyMrtss)
