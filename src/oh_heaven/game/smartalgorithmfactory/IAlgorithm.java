package oh_heaven.game.smartalgorithmfactory;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.playerboard.player.Npc.Npc;
import oh_heaven.game.playerboard.player.Player;

public interface IAlgorithm {
    public Card nextPlay(Player player);
}
