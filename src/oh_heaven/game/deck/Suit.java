package oh_heaven.game.deck;

import oh_heaven.game.utilities.ServiceRandom;

public enum Suit {
    SPADES, HEARTS, DIAMONDS, CLUBS;

    // return random Enum value
	public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
		int x = ServiceRandom.getSeedRandom().nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}
}

	
