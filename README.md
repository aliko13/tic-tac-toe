# tic-tac-toe application

## Requirements

For building and running the application you need:

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)


## Running the application

# Locally
We need to start the database docker container in order to establish a successful to read/write game and player tables.
Execute the command below in /docker folder
```shell
$ docker-compose up -d db
```
You could start the application with two different ways
* execute the `main` method in the `com.example.tictactoe.TicTacToeApplication` class from your IDE.
* use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html)
back to application root folder and execute the command below.
```shell
$ mvn spring-boot:run
```

# Docker
Application is dockerized, so you could start the Spring application running in docker container.
Execute the command below, it will boot up the database container and application.
```shell
$ docker-compose up -d
```


## Technologies used
Application is Spring Boot application and other Spring features and libraries has been used
* Spring REST
* Spring Data JPA (with Hibernate as ORM provider)

* PostgreSQL

* Flyway - to control the DB migrations
* Lombok - to automatically generate getter/setter/builder and constructors
* Mockito - for service layer mock tests


## How to play the game
* Create game: POST http://localhost:8080/games
It will create a new game and initialize players. Game will automatically set the symbols to each player and automatically
give turn to first player.

* Make a move: POST http://localhost:8080/games/{gameId}/moves
Based on move request, board will be updated and player un turn changed to another player.
System will check if there is a winner or game is draw


## Suggestion for improvements
At the moment, we are fetching players and game each time when requested, and this would lead to performance issues.
To improve this process we could cache the Game and related Players in application state using Spring Simple Cache or
external providers such as Redis, Ehcache.



