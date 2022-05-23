package oh_heaven.game.playerboard.player.Npc;

import oh_heaven.game.playerboard.player.Player;
import oh_heaven.game.smartalgorithmfactory.ISmartAlgorithm;
import oh_heaven.game.smartalgorithmfactory.SmartAlgorithmFactory;

abstract public class Npc extends Player {

    private ISmartAlgorithm smartAlgorithm;
    private SmartAlgorithmFactory saFactory = new SmartAlgorithmFactory();
    public Npc(String playerType) {
        super();
        this.smartAlgorithm = saFactory.createAlgorithm(playerType);

    }
}
