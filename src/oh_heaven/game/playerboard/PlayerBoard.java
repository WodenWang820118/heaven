package oh_heaven.game.playerboard;

public class PlayerBoard {
    private CompositePlayer cp;
    public PlayerBoard(CompositePlayer cp) {
        this.cp = cp;
    }

    public CompositePlayer getCp() {
        return cp;
    }
}
