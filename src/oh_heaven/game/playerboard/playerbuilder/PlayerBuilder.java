package oh_heaven.game.playerboard.playerbuilder;

import oh_heaven.game.playerboard.player.Player;

public interface PlayerBuilder {
    public PlayerBuilder buildBrain();
    public PlayerBuilder buildScores();
    public PlayerBuilder buildTricks();
    public PlayerBuilder buildBids();
    public PlayerBuilder buildDeck();
    public Player buildPlayer();
}
