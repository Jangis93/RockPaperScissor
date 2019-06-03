package app.GameModel;

/**
 * Helper class to evaluate the result of two moves
 */
public class GameService {

    /**
     * @param moveOne the move of the first player
     * @param moveTwo the move of the second player
     * @return the result of the two moves
     */
    public Result evaluateMoves(Move moveOne, Move moveTwo) {
        Result result;

        if (moveOne == moveTwo) {
            result = Result.DRAW;
        } else if (moveOne.evaluate(moveTwo)) {
            result = Result.WIN;
        } else {
            result = Result.LOSS;
        }
        return result;
    }
}
