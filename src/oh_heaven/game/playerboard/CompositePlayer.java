package oh_heaven.game.playerboard;

import java.util.ArrayList;
import java.util.Random;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;
import ch.aplu.jcardgame.Hand;

public class CompositePlayer {
    private Hand[] hands;
    private final Random random;

    public CompositePlayer(Random random) {
        this.random = random;
    }

    public void dealingOut(Deck deck, Hand[] hands, int nbPlayers, int nbCardsPerPlayer) {
        Hand pack = deck.toHand(false);
        // pack.setView(Oh_Heaven.this, new RowLayout(hideLocation, 0));
        for (int i = 0; i < nbCardsPerPlayer; i++) {
            for (int j=0; j < nbPlayers; j++) {
                if (pack.isEmpty()) return;
                Card dealt = randomCard(pack);
                // System.out.println("Cards = " + dealt);
                dealt.removeFromHand(false);
                hands[j].insert(dealt, false);
                // dealt.transfer(hands[j], true);
            }
        }
    }

    public Card randomCard(Hand hand){
        int x = random.nextInt(hand.getNumberOfCards());
        return hand.get(x);
    }

    public Card randomCard(ArrayList<Card> list){
        int x = random.nextInt(list.size());
        return list.get(x);
    }

    public Hand[] getHands() {
        return hands;
    }

    public void setHands(int nbPlayers) {
        this.hands = new Hand[nbPlayers];
    }
}
