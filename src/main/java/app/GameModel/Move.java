package app.GameModel;

/*
RULES:
PAPPER BEATS ROCK
ROCK BEATS SCISSOR
SCISSOR BEATS PAPPER
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

    public abstract boolean evaluate(Move move);
}
