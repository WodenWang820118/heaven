package oh_heaven.game.deck;

import ch.aplu.jcardgame.Card;

public enum Rank {
    // Reverse order of rank importance (see rankGreater() below)
    // Order of cards is tied to card images
    ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO;

    public static boolean rankGreater(Card card1, Card card2) {
		return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
	}
}