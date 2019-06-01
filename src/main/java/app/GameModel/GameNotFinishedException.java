package app.GameModel;

public class GameNotFinishedException extends Exception {

    public GameNotFinishedException(int id) {
        super(String.format("The game with id %d is not finsihed!", id));
    }
}
