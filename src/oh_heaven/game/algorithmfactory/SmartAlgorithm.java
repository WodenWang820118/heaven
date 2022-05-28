package oh_heaven.game.algorithmfactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.playerboard.player.Brain;
import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.deck.Suit;

public class SmartAlgorithm implements IAlgorithm {
    private List<Card> choices = new ArrayList<Card>();
    
    @Override
    public Card nextPlay(Player player) {
        Brain brain = player.getBrain();
        Hand handCards = player.getDeck(); // player deck remained
        Suit lead = brain.getLead(); // lead suit
        Suit trump = brain.getTrump(); // trump suit

        // bids and tricks to decide to control the algorithm
        int bids = player.getBids();
        int tricks = player.getTricks();

        // the block trying to maximize the points -> give the highest rank card
        // first, check if holding the same suit as lead
        if (isHoldingSuit(handCards, lead)) {
            // always play the lowest rank card, following the lead suit
            Card smallestCard = getLowestRank(lead, handCards);
            return smallestCard;
        } else {
            // if not holding the same suit, play the non-trump, lowest rank card
            // get and add the lowest rank card except the trump suit to the choices
            Stream.of(Suit.values())
                .filter(suit -> suit != trump)
                .forEach(suit -> addChoice(getLowestRank(suit, handCards)));

            // get the lowest rank card of the lowest suit
            Card smallestCard = getSmallestCardFromChoices(choices);
            return smallestCard;
        }

        // TODO: need to consider the smarter bids to maximize the chance of winning bonus 
        // want to achieve bids equals tricks
        // tricks < bids -> get more tricks
        // tricks > bids -> get more tricks since already surpass the bids
        // tricks == bids -> try to remain the tricks
    }

    /**
     * Get the lowest rank card according to the suit
     * @return Rank
     */
    private Card getLowestRank(Suit suit, Hand hand) {
        Card lowestRank = null;
        for (int i = 0; i < hand.getNumberOfCards(); i++) {
            Card card = hand.get(i);
            if (card.getSuit() == suit) {
                if (lowestRank == null) {
                    lowestRank = card;
                } else if (card.getRankId() < lowestRank.getRankId()) {
                    lowestRank = card;
                }
            }
        }
        return lowestRank;
    }

    /**
     * Get the lowest rank from a Card list according to the suit
     * @return Rank
     */
    private Card getLowestRank(Suit suit, List<Card> choices) {
        Card lowestRank = null;
        for (int i = 0; i < choices.size(); i++) {
            Card card = choices.get(i);
            if (card.getSuit() == suit) {
                if (lowestRank == null) {
                    lowestRank = card;
                } else if (card.getRankId() < lowestRank.getRankId()) {
                    lowestRank = card;
                }
            }
        }
        return lowestRank;
    }

    private Card getSmallestCardFromChoices(List<Card> choices) {
        Suit lowestSuit = null;
        Card smallestCard = null;

        // get the lowest suit according to the card choices
        for (Card card : choices) {
            if (lowestSuit == null) {
                lowestSuit = (Suit) card.getSuit();
            } else if (card.getSuitId() < lowestSuit.ordinal()) {
                // TODO: ordinal() is not a good practice
                lowestSuit = (Suit) card.getSuit();
            }
        }

        // get the lowest rank card according to the suit
        smallestCard = getLowestRank(lowestSuit, choices);

        return smallestCard;
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

    private void addChoice(Card card) {
        choices.add(card);
    }
}
