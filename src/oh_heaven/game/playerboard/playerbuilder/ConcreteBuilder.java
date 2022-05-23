package oh_heaven.game.playerboard.playerbuilder;

import oh_heaven.game.playerboard.player.Brain;
import oh_heaven.game.playerboard.player.HumanPlayer;
import oh_heaven.game.playerboard.player.Npc.RandomNpc;
import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.playerboard.player.Npc.LegalNpc;
import oh_heaven.game.playerboard.player.Npc.SmartNpc;

public class ConcreteBuilder implements PlayerBuilder {
    private Player player;

    public ConcreteBuilder(String playerType) {
        switch (playerType) {
            case "human":
                player = new HumanPlayer();
                break;
            case "smart":
                player = new SmartNpc("smart");
                break;
            case "random":
                player = new RandomNpc("random");
            case "legal":
                player = new LegalNpc("legal");

                break;
            default:
                System.out.println("Unknown player type: " + playerType);
        }
    }

    @Override
    public PlayerBuilder buildBrain() {
        Brain brain = new Brain(player.getDeck());
        player.setBrain(brain);
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
    public Player buildPlayer() {
        return player;
    }
}