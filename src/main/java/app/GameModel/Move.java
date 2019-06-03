package app.GameModel;

/**
 * RULES:
 * PAPPER BEATS ROCK
 * ROCK BEATS SCISSOR
 * SCISSOR BEATS PAPPER
 */
public enum Move {

    PAPER {
        @Override
        public boolean evaluate(Move move) {
            return (ROCK == move);
        }
    },

    SCISSOR {
        @Override
        public boolean evaluate(Move move) {
            return (PAPER == move);
        }
    },

    ROCK {
        @Override
        public boolean evaluate(Move move) {
            return (SCISSOR == move);
        }
    };

    /**
     * @param move the move of the second player
     * @return the result from player ones perspective
     */
    public abstract boolean evaluate(Move move);
}
