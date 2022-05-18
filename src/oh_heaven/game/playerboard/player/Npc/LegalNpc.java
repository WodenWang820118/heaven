package oh_heaven.game.playerboard.player.Npc;

import oh_heaven.game.playerboard.player.Brain;

public class LegalNpc extends Npc {
    public LegalNpc() {
        super();
        setBrain(new Brain());
    }
}
