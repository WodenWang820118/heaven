package oh_heaven.game.playerboard.player;

import ch.aplu.jcardgame.Hand;

public class Player {
    Brain brain;
    public Hand deck;
    private int scores;
    private int tricks;
    private int bids;

    // since there could be more parameters, we use setter injection
    // with builder pattern
    // TODO: builder pattern
    public Player() {
    }

    public void setBrain(Brain brain) {
        this.brain = brain;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public int getScores() {
        return scores;
    }

    public void setTricks(int tricks) {
        this.tricks = tricks;
    }

    public int getTricks() {
        return tricks;
    }

    public void setBids(int bids) {
        this.bids = bids;
    }

    public int getBids() {
        return bids;
    }
}
