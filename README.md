# RockPaperScissor
The known Rock Paper Scissor game as a REST API using Spring Boot and Gradle

Rules:
- PAPPER beats ROCK
- ROCK beats SCISSOR
- SCISSOR beats PAPPER

## How to run

Download the code and build it with gradle
```shell
gradle build
```

and then execute it with
```shell
java -jar build/libs/rock-paper-scissor-0.1.0.jar
```

The application starts a web server that listens to the 8080 port. The follwing is a example session of how to play the game using curl. 

1. We create a new game with a post request and a name in the request body. We recieve an ID of the game to send to a friend that we want to face. 

```shell
curl -i -X POST http://localhost:8080/api/games?name=Michaela
HTTP/1.1 201
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sat, 01 Jun 2019 21:40:09 GMT

1
```

2. Our friend connects through another post request and inputs his/hers name in the request body and the ID number in the path. 
```shell
curl -i -X POST http://localhost:8080/api/games/1/join?name=Johan
HTTP/1.1 200
Content-Length: 0
Date: Sat, 01 Jun 2019 21:40:22 GMT

```

3. Then the two players makes a POST request each to make their move.
```shell
curl -i -d "name=Johan&move=SCISSOR" -X POST http://localhost:8080/api/games/1/move
HTTP/1.1 200
Content-Length: 0
Date: Sat, 01 Jun 2019 21:40:35 GMT

```

```shell
curl -i -d "name=Michaela&move=ROCK" -X POST http://localhost:8080/api/games/1/move
HTTP/1.1 200
Content-Length: 0
Date: Sat, 01 Jun 2019 21:40:35 GMT

```

4. Then both players can request the result by a GET request with the ID of the game in the path. 
```shell
curl -i -X GET http://localhost:8080/api/games/1
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sat, 01 Jun 2019 21:47:20 GMT

{"id":1,"playerOne":{"name":"Michaela","move":"ROCK"},"playerTwo":{"name":"Johan","move":"SCISSOR"},"state":"FINISHED","result":"Michaela WIN"}
```





