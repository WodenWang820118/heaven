package oh_heaven.game.service;

import java.util.List;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;
import ch.aplu.jgamegrid.Actor;
import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.utilities.ServiceRandom;

public class Rule {


    public final int thinkingTime = 500;
    private final int madeBidBouns = 10;
    private boolean enforceRules = false;
    private Actor[] scoreActors = {null, null, null, null};

    public Rule() {
    }

    public void violateRule(int nextPlayer, Card selected) {
		// Rule violation
		String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
		System.out.println(violation);
		if (getEnforceRules()) {
			try {
				throw(new BrokeRuleException(violation));
			} catch (BrokeRuleException e) {
				e.printStackTrace();
				System.out.println("A cheating player spoiled the game!");
				System.exit(0);
			}
		}
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
		int x = ServiceRandom.getSeedRandom().nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}
	
	public boolean rankGreater(Card card1, Card card2) {
		return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
	}

    public Deck getDeck() {
        return new Deck(Suit.values(), Rank.values(), "cover");
    }

    public void initPlayerScores(List<Player> players) {
        for (Player player : players) {
            player.setScores(0);
        }
    }

    public void updatePlayerScores(List<Player> players) {
        for (Player player : players) {
            player.setScores(player.getScores() + player.getTricks());
            if (player.getTricks() == player.getBids()) {
                int newScore = player.getScores() + madeBidBouns;
                player.setScores(newScore);
            }
        }
    }

    public void initTricks(List<Player> players) {
        for (Player player : players) {
            player.setTricks(0);
        }
    }
    // TODO: how to get nbStartCards from GameBoard
    // for now, it is set manually
    public void initBids(Suit trumps, int nextPlayer, List<Player> players) {
        int total = 0;
        int nbStartCards = 13;
        int nbPlayers = players.size();

        for (int i = nextPlayer; i < nextPlayer + nbPlayers; i++) {
            int iP = i % 4;
            int bids = nbStartCards / 4 + ServiceRandom.getSeedRandom().nextInt(2);
            players.get(iP).setBids(bids);
            total += bids;
        }
        if (total == nbStartCards) {  // Force last bid so not every bid possible
            int iP = (nextPlayer + nbPlayers) % nbPlayers;
            if (players.get(iP).getBids() == 0) {
                players.get(iP).setBids(1);
            } else {
                int bids = players.get(iP).getBids() + (ServiceRandom.getSeedRandom().nextBoolean() ? -1 : 1);
                players.get(iP).setBids(bids);
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
}
