package oh_heaven.game.playerboard.playerbuilder;

import oh_heaven.game.playerboard.player.Player;

public class Director {
    private PlayerBuilder playerBuilder;

    public Director(PlayerBuilder playerBuilder) {
        this.playerBuilder = playerBuilder;
    }

    public Player constructPlayer() {
        playerBuilder.buildBrain();
        playerBuilder.buildScores();
        playerBuilder.buildTricks();
        playerBuilder.buildBids();
        playerBuilder.buildDeck();
        return playerBuilder.buildPlayer();
    }
}
