package oh_heaven.game.smartalgorithmfactory;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.service.Dealer;

public class RandomAlgorithm implements IAlgorithm {
    @Override
    public Card nextPlay(Player player) {
        Hand hand = player.deck;
        return Dealer.randomCard(hand);
    }
}
