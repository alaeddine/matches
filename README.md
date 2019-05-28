# matches
project to manage a simple tennis match score with a unique set.

# download code
you can find release for each use case, so you can return to execute
the use cases one by one, for example :
- sprint1 uc1 :  release 0.0.1
- sprint1 uc2 :  release 0.0.2


# technical info
- web tier :  jboss RESTEasy library is used as implimentation for the JAX-RS 2.2 spec
- database : liquibase is used to manage database schema changes.
           H2 is used as memory database support
- unit test: mockito and Spring unit test are used
- Spring boot:  is used to bootstrap the project

# Controller
- MatchController : resource controller

# env 
- port: 8080
- context : tennis
- app ping uri : GET http://localhost:8080/tennis/matches 
- h2 management console uri : http;//localhost:8080/h2-console/ (matchdb,sa, )


# test case
integration tests:
- use postman for integration tests, you have just to import the file match.postman_collection.json
in postman.
- unit tests:
see MatchControllerTest and  MatchController

## example :

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
GET http://localhost:8080/tennis/matches/1/score

4. get the winner when the match is finished
GET http://localhost:8080/tennis/matches/1/winner

5. check if the match finished
GET http://localhost:8080/tennis/matches/1/status

