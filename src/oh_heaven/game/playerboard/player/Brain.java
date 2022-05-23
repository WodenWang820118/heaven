package oh_heaven.game.playerboard.player;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.Oh_Heaven;
import oh_heaven.game.service.Rule.Suit;

import java.util.HashMap;
import java.util.HashSet;

public class Brain {
    private Suit trump;
    private Suit lead;
    private Card WinningCard;
    private int winner;

    private HashMap<Integer, HashSet<Card>> cardRound;

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

    public HashMap<Integer, HashSet<Card>> getCardRound() {
        return cardRound;
    }
    public void setCardRound(HashMap<Integer, HashSet<Card>> cardRound) {
        this.cardRound = cardRound;
    }
}