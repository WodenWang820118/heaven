package oh_heaven.game.algorithmfactory;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.utilities.ServiceRandom;

public class RandomAlgorithm implements IAlgorithm {

    public Card randomCard(Hand hand){
        int x = ServiceRandom.getSeedRandom().nextInt(hand.getNumberOfCards());
        return hand.get(x);
    }

    @Override
    public Card nextPlay(Player player) {
        Hand hand = player.getDeck();
        return randomCard(hand);
    }
}
