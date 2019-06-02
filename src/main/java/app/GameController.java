package app;

import app.GameModel.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Rock-Paper-Scissor HTTTP API implementation written by Michaela Jangefalk according to given API:
 * POST /api/games
 * POST /api/games/{id}/join
 * POST /api/games/{id}/move
 * GET /api/games/{id}
 */

@RestController
@RequestMapping(value = "/api/games")
public class GameController {

    private GameService gameService;
    private int gameID;
    private Map<Integer, Game> games;

    public GameController() {
        gameService = new GameService();
        games = new HashMap<>();
        gameID = 0;
    }

    /**
     * @param name the name of the player who wants to create a new game
     * @return the game id of the created game
     */
    @RequestMapping(method = RequestMethod.POST, value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public int startNewGame(@RequestParam(value = "name") String name) {
        Game newGame = new Game(gameID, new Player(name));
        games.put(gameID, newGame);
        gameID++;

        return newGame.getId();
    }

    /**
     * @param id the id of the game the user are interested in
     * @return the entire game state
     * @throws GameDoesNotExistException if game does not exist
     * @throws GameNotFinishedException  if the game is not finsihed
     */
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

    /**
     * @param id   the id of the game that a player wants to join
     * @param name the name of the new player
     * @throws GameDoesNotExistException if the game does not exist
     */
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

    /**
     * @param id   the id of the game that the player wants to do a move in
     * @param name the name of the player that wants to do the move
     * @param move the move which the player wants to do
     * @throws GameDoesNotExistException if the game does not exist
     * @throws GameNotActiveException    if the game is already finsihed
     */
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
                playerTwo = game.getPlayerTwo().getMove();
                game.getPlayerOne().setMove(playerOne);
            }

            Result result = gameService.evaluateMoves(playerOne, playerTwo);
            game.setResult(result);
            game.setState(State.FINISHED);
        }
    }

}
