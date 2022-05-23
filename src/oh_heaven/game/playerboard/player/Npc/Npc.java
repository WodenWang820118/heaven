package oh_heaven.game.playerboard.player.Npc;

import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.smartalgorithmfactory.IAlgorithm;
import oh_heaven.game.smartalgorithmfactory.AlgorithmFactory;

abstract public class Npc extends Player {

    private IAlgorithm smartAlgorithm;
    private AlgorithmFactory saFactory = new AlgorithmFactory();
    public Npc(String playerType) {
        super();
        this.smartAlgorithm = saFactory.createAlgorithm(playerType);

    }
}
