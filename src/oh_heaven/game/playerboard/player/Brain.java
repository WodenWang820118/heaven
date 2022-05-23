package oh_heaven.game.playerboard.player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.service.Rule.Suit;

import java.util.HashSet;

public class Brain {
    private Suit trump;
    private Suit lead;
    private Hand hand;

    private HashSet<Card> cardRound = new HashSet<>();

    public Brain(Hand hand) {
        this.hand = hand;
    }

    public Suit getTrump() {
        return trump;
    }
    public void setTrump(Suit trump) {
        this.trump = trump;
    }

    public Suit getLead() {
        return lead;
    }
    public void setLead(Suit lead) {
        this.lead = lead;
    }

    public HashSet<Card> getCardRound() {
        return cardRound;
    }
    public void setCardRound(Card card) {
        cardRound.add(card);
    }

    public Hand getHand() {
        return hand;
    }
}