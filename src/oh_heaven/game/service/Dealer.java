package oh_heaven.game.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.utilities.ServiceRandom;

public class Dealer {

    Properties properties;

    public Dealer(Properties properties) {
        this.properties = properties;
    }

    // trying to replace the original dealOut method
    public void dealingOut(Deck deck, List<Player> players, int nbStartCards) {
		Hand pack = deck.toHand(false);
		for (int i = 0; i < nbStartCards; i++) {
			for (Player p:players) {
				if (pack.isEmpty()) return;
				Card dealt = randomCard(pack);
				dealt.removeFromHand(false);
				p.getDeck().insert(dealt, false);
			}
		}
	}
    // TODO: another randomCard method being used in the RandomAlgorithm class
    // need to be refactored due to duplicated code
    public Card randomCard(Hand hand){
        int x = ServiceRandom.getSeedRandom().nextInt(hand.getNumberOfCards());
        return hand.get(x);
    }

    public static Card randomCard(ArrayList<Card> list){
        int x = ServiceRandom.getSeedRandom().nextInt(list.size());
        return list.get(x);
    }

}