package oh_heaven.game.playerboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import ch.aplu.jcardgame.Deck;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.playerboard.player.HumanPlayer;
import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.playerboard.player.Npc.LegalNpc;
import oh_heaven.game.playerboard.player.Npc.SmartNpc;
import oh_heaven.game.utilities.PropertiesLoader;

public class CompositePlayer extends Player {

    // TODO: should be a list of players
    // TODO: when in SmartNpc's turn, trigger the algorithm and bring the information from the brain
    // to calculate the next move
    private List<Player> players;

    public CompositePlayer(Properties properties) {
        super();
        this.players = new ArrayList<>();

        List<String> playerTypes = PropertiesLoader.loadPlayers(properties);
        this.addPlayerAccordingToType(playerTypes);

    }

    public void addPlayerAccordingToType(List<String> playerTypes) {
        for (String playerType : playerTypes) {
            switch(playerType) {
                case "human":
                    addPlayer(new HumanPlayer());
                    break;
                case "smart":
                    addPlayer(new SmartNpc());
                    break;
                case "random":
                    addPlayer(new LegalNpc());
                    break;
                default:
                    System.out.println("Unknown player type: " + playerType);
            }
        }
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
