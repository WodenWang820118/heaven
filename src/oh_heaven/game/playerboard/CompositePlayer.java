package oh_heaven.game.playerboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.aplu.jcardgame.Deck;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.playerboard.player.HumanPlayer;
import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.playerboard.player.Npc.LegalNpc;

public class CompositePlayer extends Player {

    private final Random random;
    // TODO: should be a list of players
    // TODO: when in SmartNpc's turn, trigger the algorithm and bring the information from the brain
    // to calculate the next move
    private List<Player> players;

    public CompositePlayer(Random random) {
        super();
        this.random = random;
        this.players = new ArrayList<>();
        // TODO: add SmartNpc by propertyLoader and property file
        // add the players as the default manually
        addPlayer(new HumanPlayer());
        addPlayer(new LegalNpc());
        addPlayer(new LegalNpc());
        addPlayer(new LegalNpc());
    }

    private void addPlayer(Player player) {
        players.add(player);
    }

    public void initPlayerDeck(List<Player> players, Deck deck) {
		for (Player p:players) {
			p.deck = new Hand(deck);
		}
	}

    public void playerSortCards(List<Player> players) {
		for (Player p:players) {
			p.deck.sort(Hand.SortType.SUITPRIORITY, true);
		}
	}

    public List<Player> getPlayers() {
        return players;
    }
}
