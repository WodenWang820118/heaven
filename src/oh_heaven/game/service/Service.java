package oh_heaven.game.service;

import java.util.Random;

import ch.aplu.jgamegrid.Actor;
import oh_heaven.game.Oh_Heaven.Suit;
import oh_heaven.game.gameboard.GameBoard;

public class Service {
    private Actor[] scoreActors = {null, null, null, null };
    public final int thinkingTime = 2000;
    static public final int seed = 30006;
    static final Random random = new Random(seed);
    private boolean enforceRules = false;
    private GameBoard gb;
    private int[] scores;
    private int[] tricks;
    private int[] bids;

    public Service(GameBoard gb) {
        this.gb = gb;
        this.scores = new int[gb.nbPlayers];
        this.tricks = new int[gb.nbPlayers];
        this.bids = new int[gb.nbPlayers];
    }

    public void initScores() {
        for (int i = 0; i < gb.nbPlayers; i++) {
            getScores()[i] = 0;
        }
   }

    public void updateScores() {
        for (int i = 0; i < gb.nbPlayers; i++) {
            getScores()[i] += getTricks()[i];
            if (getTricks()[i] == getBids()[i]) getScores()[i] += gb.madeBidBonus;
        }
    }

    public void initTricks() {
        for (int i = 0; i < gb.nbPlayers; i++) {
            getTricks()[i] = 0;
        }
    }

    public void initBids(Suit trumps, int nextPlayer) {
        int total = 0;
        for (int i = nextPlayer; i < nextPlayer + gb.nbPlayers; i++) {
            int iP = i % gb.nbPlayers;
            getBids()[iP] = gb.nbStartCards / 4 + random.nextInt(2);
            total += getBids()[iP];
        }
        if (total == gb.nbStartCards) {  // Force last bid so not every bid possible
            int iP = (nextPlayer + gb.nbPlayers) % gb.nbPlayers;
            if (getBids()[iP] == 0) {
                getBids()[iP] = 1;
            } else {
                getBids()[iP] += random.nextBoolean() ? -1 : 1;
            }
        }
        // for (int i = 0; i < nbPlayers; i++) {
        // 	 bids[i] = nbStartCards / 4 + 1;
        //  }
    }

    public Actor[] getScoreActors() {
        return scoreActors;
    }

    public boolean getEnforceRules() {
        return enforceRules;
    }

    public void setEnforceRules(boolean enforceRules) {
        this.enforceRules = enforceRules;
    }

    public int[] getScores() {
        return scores;
    }

    public int[] getTricks() {
        return tricks;
    }

    public int[] getBids() {
        return bids;
    }
}
