package oh_heaven.game.playerboard.playerbuilder;

import ch.aplu.jcardgame.Deck;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.playerboard.player.Brain;
import oh_heaven.game.deck.Rank;
import oh_heaven.game.deck.Suit;
import oh_heaven.game.playerboard.player.Player;
public class ConcreteBuilder implements PlayerBuilder {
    private Player player;
    private String[] playerTypes = {"human", "smart", "legal", "random"};


    public ConcreteBuilder(String playerType) {
        for (int i = 0; i < playerTypes.length; i++) {
            if (playerTypes[i].equals(playerType)) {
                player = new Player();
                player.setPlayerType(playerType);
            }
        }
    }

    @Override
    public PlayerBuilder buildBrain() {
        player.setBrain(new Brain());
        return this;
    }

    @Override
    public PlayerBuilder buildScores() {
        int scores = 0;
        player.setScores(scores);
        return this;
    }

    @Override
    public PlayerBuilder buildTricks() {
        int tricks = 0;
        player.setTricks(tricks);
        return this;
    }

    @Override
    public PlayerBuilder buildBids() {
        int bids = 0;
        player.setBids(bids);
        return this;
    }

    @Override
    public PlayerBuilder buildDeck() {
        Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
        player.setDeck(new Hand(deck));
        return this;
    }

    @Override
    public Player buildPlayer() {
        return player;
    }
}