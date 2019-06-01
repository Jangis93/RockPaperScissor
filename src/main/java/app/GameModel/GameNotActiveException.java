package app.GameModel;

public class GameNotActiveException extends Exception{

    public GameNotActiveException(int id) {
        super(String.format("The game with id %d is finsihed!", id));
    }
}
