package oh_heaven.game.algorithmfactory;

import java.util.ArrayList;
import java.util.List;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.playerboard.player.Brain;
import oh_heaven.game.deck.Suit;
import oh_heaven.game.utilities.ServiceRandom;

public class LegalAlgorithm implements IAlgorithm {
    @Override
    public Card nextPlay(Player player) {
        Brain brain = player.getBrain();
        Hand handCards = player.getDeck(); // player deck remained
        Suit lead = brain.getLead(); // lead suit

        if (isHoldingSuit(handCards, lead)) {
            // give random card and follow the rule
            return getSameSuitRandomCard(lead, handCards);
        } else {
            // give random card and follow the rule
            return randomCard(handCards);
        }
    }

    private boolean isHoldingSuit(Hand handCards, Suit lead) {
        boolean isHoldingSuit = false;
        for (int i = 0; i < handCards.getNumberOfCards(); i++) {
            Card card = handCards.get(i);
            if (card.getSuit() == lead) {
                isHoldingSuit = true;
                break;
            }
        }
        return isHoldingSuit;
    }

    public Card randomCard(Hand hand){
        int x = ServiceRandom.getSeedRandom().nextInt(hand.getNumberOfCards());
        return hand.get(x);
    }

    private Card getSameSuitRandomCard(Suit suit, Hand hand) {
        List<Card> sameSuit = new ArrayList<Card>();
        for (int i = 0; i < hand.getNumberOfCards(); i++) {
            Card card = hand.get(i);
            if (card.getSuit() == suit) {
                sameSuit.add(card);
            }
        }
        // given a suit, get a random rank card
        int x = ServiceRandom.getSeedRandom().nextInt(sameSuit.size());
        return sameSuit.get(x);
    }
}
