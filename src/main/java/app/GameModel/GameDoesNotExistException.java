package app.GameModel;

public class GameDoesNotExistException extends Exception {


    public GameDoesNotExistException(int id) {
        super(String.format("The game with id %d does not exist!", id));
    }
}
