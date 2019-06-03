package app.GameModel;

/**
 * The Game class gathers all information needed for a game
 */
public class Game {
    private int id;

    private Player playerOne;
    private Player playerTwo;

    private State state;
    private String result;

    /**
     * @param id        the id of the game
     * @param playerOne the player who creates the game
     */
    public Game(int id, Player playerOne) {
        this.id = id;
        this.playerOne = playerOne;
        state = State.INIT;
    }

    public int getId() {
        return id;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public String getResult() {
        return result;
    }

    /**
     * method setResult sets the private variable result to either:
     * -   the winning player + WIN
     * -   DRAW
     *
     * @param result the result evaluated from player ones perspective
     */
    public void setResult(Result result) {
        if (result == Result.WIN) {
            this.result = playerOne.getName() + " " + result;
        } else if (result == Result.DRAW) {
            this.result = "" + result;
        } else {
            this.result = playerTwo.getName() + " " + Result.WIN;
        }
    }

}
