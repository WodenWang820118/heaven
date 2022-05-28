package oh_heaven.game.playerboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.algorithmfactory.AlgorithmFactory;
import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.playerboard.playerbuilder.ConcreteBuilder;
import oh_heaven.game.playerboard.playerbuilder.Director;
import oh_heaven.game.playerboard.playerbuilder.PlayerBuilder;
import oh_heaven.game.utilities.PropertiesLoader;

public class CompositePlayer extends Player {

    private List<Player> players;

    public CompositePlayer(Properties properties) {
        super();
        this.players = new ArrayList<>();

        List<String> playerTypes = PropertiesLoader.loadPlayers(properties);
        this.addPlayerAccordingToType(playerTypes);
    }

    public void addPlayerAccordingToType(List<String> playerTypes) {
        for (String playerType : playerTypes) {
            PlayerBuilder playerBuilder = new ConcreteBuilder(playerType);
            Director director = new Director(playerBuilder);
            addPlayer(director.constructPlayer());
        }
    }

    private void addPlayer(Player player) {
        players.add(player);
    }

    public void playerSortCards(List<Player> players) {
		for (Player p:players) {
			p.getDeck().sort(Hand.SortType.SUITPRIORITY, true);
		}
	}

    public List<Player> getPlayers() {
        return players;
    }

    public Card botPlayCard(Player player) {
        AlgorithmFactory.getInstance();
        if (player.getPlayerType().equals("random")) {
            return AlgorithmFactory.getRandomAlgorithm().nextPlay(player);
        } else if (player.getPlayerType().equals("legal")) {
            return AlgorithmFactory.getLegalAlgorithm().nextPlay(player);
        } else if (player.getPlayerType().equals("smart")) {
            return AlgorithmFactory.getSmartAlgorithm().nextPlay(player);
        } else {
            System.out.println("Unknown player type: " + player.getPlayerType());
            System.exit(1);
            return null;
        }
    }

}
