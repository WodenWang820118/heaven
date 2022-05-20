package oh_heaven.game.playerboard.player;

public class ConcreteBuilder implements PlayerBuilder{
    private Player player;

    public ConcreteBuilder (){
        player = new Player();
    }

    @Override
    public PlayerBuilder buildBrain() {
        Brain brain = null;
        player.setBrain(brain);
        return null;
    }

    @Override
    public PlayerBuilder buildScores() {
        int scores = 0;
        player.setScores(scores);
        return null;
    }

    @Override
    public PlayerBuilder buildTricks() {
        int tricks = 0;
        player.setTricks(tricks);
        return null;
    }

    @Override
    public PlayerBuilder buildBids() {
        int bids = 0;
        player.setBids(bids);
        return null;
    }
}