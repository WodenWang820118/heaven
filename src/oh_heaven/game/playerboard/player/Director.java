package oh_heaven.game.playerboard.player;

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
        return playerBuilder.buildPlayer();
    }
}
