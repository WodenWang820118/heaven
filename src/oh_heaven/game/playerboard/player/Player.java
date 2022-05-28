package oh_heaven.game.playerboard.player;

import ch.aplu.jcardgame.Hand;

public class Player {
    private String playerType;
    private Hand deck;
    private Brain brain;
    // records
    private int scores;
    private int tricks;
    private int bids;

    public Player() {
    }

    public void setDeck(Hand deck) {
        this.deck = deck;
    }

    public Hand getDeck() {
        return this.deck;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setBrain(Brain brain) {
        this.brain = brain;
    }
    public Brain getBrain() {
        return brain;
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
