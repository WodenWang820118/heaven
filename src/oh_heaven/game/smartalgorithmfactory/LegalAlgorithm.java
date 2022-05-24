package oh_heaven.game.smartalgorithmfactory;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.service.Rule;
import oh_heaven.game.utilities.ServiceRandom;

public class LegalAlgorithm implements IAlgorithm {
    Rule rule;
    @Override
    public Card nextPlay(Player player) {
        rule.setEnforceRules(true);
        Hand hand = player.getDeck();
        return randomCard(hand);
    }
    public Card randomCard(Hand hand){
        int x = ServiceRandom.getSeedRandom().nextInt(hand.getNumberOfCards());
        return hand.get(x);
    }
}
