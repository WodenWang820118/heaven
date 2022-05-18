package oh_heaven.game.playerboard.player.Npc;

import oh_heaven.game.playerboard.player.Brain;

public class SmartNpc extends Npc {
    public SmartNpc() {
        super();
        setBrain(new Brain());
    }
}
