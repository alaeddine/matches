# matches
project to manage a simple tennis match score with a unique set.

# technical info
web tier : JAX-RS 2.2 spec is used with the implementation of jboss RESTEasy
database : liquibase is used to manage database schema changes.
           H2 is used as memory database support
unit test: mockito and Spring unit test are used
Spring boot:  is used to bootstrap the project

# Controller
MatchController : resource controller

# env 
port: 8080
context : tennis


# test case
integration tests:
- use postman for integration tests, you have just to import the file match.postman_collection.json
in postman.
- unit tests:
see MatchControllerTest and  MatchController

-example :

1. create and start a new tennis match
POST http://localhost:8080/tennis/matches
body {
         "player1Name": "palyer1",
         "player2Name": "palyer2"
     }
2. Player 1 win point
POST http://localhost:8080/tennis/matches/1/players/PLAYER1/goal
body {}

3. get the match score
GET http://localhost:8080/tennis/matches/6/score

4. get the winner when the match is finished
GET http://localhost:8080/tennis/matches/1/winner

5. check if the match finished
GET http://localhost:8080/tennis/matches/1/status

