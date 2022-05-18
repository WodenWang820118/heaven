package oh_heaven.game.service;

import java.util.Random;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;
import ch.aplu.jgamegrid.Actor;
import oh_heaven.game.gameboard.GameBoard;

public class Rule {

    static Random random;
    GameBoard gb;
    public final int thinkingTime = 2000;
    private boolean enforceRules = false;
    private Actor[] scoreActors = {null, null, null, null };
    private int[] scores;
    private int[] tricks;
    private int[] bids;

    public Rule(Random random, GameBoard gb) {
        Rule.random = random;
        this.gb = gb;
        this.scores = new int[gb.nbPlayers];
        this.tricks = new int[gb.nbPlayers];
        this.bids = new int[gb.nbPlayers];
    }

	public static enum Suit {
		SPADES, HEARTS, DIAMONDS, CLUBS
	}
	
	public static enum Rank {
		// Reverse order of rank importance (see rankGreater() below)
		// Order of cards is tied to card images
		ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
	}
  
	// return random Enum value
	public <T extends Enum<?>> T randomEnum(Class<T> clazz){
		int x = random.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}
	
	public boolean rankGreater(Card card1, Card card2) {
		return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
	}

    public Deck getDeck() {
        return new Deck(Suit.values(), Rank.values(), "cover");
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
