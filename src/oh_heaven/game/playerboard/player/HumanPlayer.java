package oh_heaven.game.playerboard.player;

public class HumanPlayer extends Player {
    public HumanPlayer() {
        super();
        setBrain(new Brain());
    }
    
}
