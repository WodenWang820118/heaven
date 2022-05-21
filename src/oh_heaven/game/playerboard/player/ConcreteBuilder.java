package oh_heaven.game.playerboard.player;

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
                player = new SmartNpc();
                break;
            case "random":
                player = new LegalNpc();
                break;
            default:
                System.out.println("Unknown player type: " + playerType);
        }
    }

    @Override
    public PlayerBuilder buildBrain() {
        Brain brain = new Brain();
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