package oh_heaven.game.playerboard.player;

public interface PlayerBuilder {
    public PlayerBuilder buildBrain();
    public PlayerBuilder buildScores();
    public PlayerBuilder buildTricks();
    public PlayerBuilder buildBids();
    public Player buildPlayer();
}
