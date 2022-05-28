package oh_heaven.game.service;

import java.util.List;
import java.util.Properties;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;
import ch.aplu.jgamegrid.Actor;
import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.utilities.ServiceRandom;
import oh_heaven.game.deck.Suit;
import oh_heaven.game.deck.Rank;

public class Rule {

    private final int thinkingTime;
    private final int madeBidBouns;
    private final int nbStartCards;
    private final boolean enforceRules;
    private Actor[] scoreActors = {null, null, null, null};

    public Rule(Properties properties) {
        thinkingTime = Integer.parseInt(properties.getProperty("thinkingTime"));
        madeBidBouns = Integer.parseInt(properties.getProperty("madeBidBonus"));
        nbStartCards = Integer.parseInt(properties.getProperty("nbStartCards"));
        enforceRules = Boolean.parseBoolean(properties.getProperty("enforceRules"));
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

    public void initBids(Suit trumps, int nextPlayer, List<Player> players) {
        // TODO: smarter bidding algorithm
        int total = 0;
        int nbPlayers = players.size();

        for (int i = nextPlayer; i < nextPlayer + nbPlayers; i++) {
            int iP = i % nbPlayers;
            int bids = nbStartCards / nbPlayers + ServiceRandom.getSeedRandom().nextInt(2);
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

    public int getThinkingTime() {
        return thinkingTime;
    }
}
