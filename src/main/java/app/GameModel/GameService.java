package app.GameModel;


public class GameService {

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
