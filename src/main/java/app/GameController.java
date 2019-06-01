package app;

import app.GameModel.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*
    Rock-Paper-Scissor HTTTP API written by Michaela Jangefalk according to specification given.
 */

@RestController
@RequestMapping(value = "/api/games")
public class GameController {

    private GameService gameService;
    private int counter;
    private Map<Integer, Game> games;

    public GameController() {
        gameService = new GameService();
        games = new HashMap<>();
        counter = 0;
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public int startNewGame(@RequestParam(value = "name") String name) {
        int gameID = counter;
        Game newGame = new Game(gameID, new Player(name));
        games.put(gameID, newGame);
        counter++;

        return gameID;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Game checkGame(@PathVariable int id) throws GameDoesNotExistException, GameNotFinishedException {
        if (!games.containsKey(id)) {
            throw new GameDoesNotExistException(id);
        }

        Game game = games.get(id);
        if (game.getState() == State.FINISHED) {
            return game;
        } else {
            throw new GameNotFinishedException(id);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/join")
    @ResponseStatus(HttpStatus.OK)
    public void joinGame(@PathVariable int id,
                         @RequestParam(value = "name") String name) throws GameDoesNotExistException {

        if (!games.containsKey(id)) {
            throw new GameDoesNotExistException(id);
        }
        Player player2 = new Player(name);
        Game game = games.get(id);
        game.setPlayerTwo(player2);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/{id}/move")
    @ResponseStatus(HttpStatus.OK)
    public void move(@PathVariable int id,
                     @RequestParam(value = "name") String name,
                     @RequestParam(value = "move") String move)
            throws GameDoesNotExistException, GameNotActiveException {

        if (!games.containsKey(id)) {
            throw new GameDoesNotExistException(id);
        }

        Game game = games.get(id);
        if (game.getState() == State.FINISHED) {
            throw new GameNotActiveException(id);

        } else if (game.getState() == State.INIT) {
            if (game.getPlayerOne().getName().equals(name)) {
                game.getPlayerOne().setMove(Move.valueOf(move));
                game.setState(State.FIRST);
            } else if (game.getPlayerTwo().getName().equals(name)) {
                game.getPlayerTwo().setMove(Move.valueOf(move));
                game.setState(State.SECOND);
            }
        } else {
            Move playerOne, playerTwo;
            if (game.getState() == State.FIRST) {
                playerOne = game.getPlayerOne().getMove();
                playerTwo = Move.valueOf(move);
                game.getPlayerTwo().setMove(playerTwo);
            } else {
                playerOne = Move.valueOf(move);
                game.getPlayerOne().setMove(playerOne);
                playerTwo = game.getPlayerTwo().getMove();
            }

            Result result = gameService.evaluateMoves(playerOne, playerTwo);
            System.out.println(result);
            game.setResult(result);
            game.setState(State.FINISHED);
        }
    }

}
