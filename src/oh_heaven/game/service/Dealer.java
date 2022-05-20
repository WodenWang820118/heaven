package oh_heaven.game.service;

import java.util.ArrayList;
import java.util.List;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.utilities.ServiceRandom;

public class Dealer {

    // private Random random;

    public Dealer() {
        // this.random = random;
    }

    // trying to replace the original dealOut method
    public void dealingOut(Deck deck, List<Player> players, int nbStartCards) {
		Hand pack = deck.toHand(false);
		for (int i = 0; i < nbStartCards; i++) {
			for (Player p:players) {
				if (pack.isEmpty()) return;
				Card dealt = randomCard(pack);
				dealt.removeFromHand(false);
				p.deck.insert(dealt, false);
			}
		}
	}

    public Card randomCard(Hand hand){
        int x = ServiceRandom.getSeedRandom().nextInt(hand.getNumberOfCards());
        return hand.get(x);
    }

    public Card randomCard(ArrayList<Card> list){
        int x = ServiceRandom.getSeedRandom().nextInt(list.size());
        return list.get(x);
    }
}