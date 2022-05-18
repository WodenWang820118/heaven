package oh_heaven.game.gameboard;

import ch.aplu.jgamegrid.Location;

public class GameBoard {
    public final String trumpImage[] = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};
    public final String version = "1.0";
    public final int nbPlayers = 4;
    public final int nbStartCards = 13;
    public final int nbRounds = 3;
    public final int madeBidBonus = 10;
    public final int handWidth = 400;
    public final int trickWidth = 40;
    public final Location[] handLocations = {
                new Location(350, 625),
                new Location(75, 350),
                new Location(350, 75),
                new Location(625, 350)
        };
    public final Location[] scoreLocations = {
                new Location(575, 675),
                new Location(25, 575),
                new Location(575, 25),
                // new Location(650, 575)
                new Location(575, 575)
        };
    public final Location trickLocation = new Location(350, 350);
    public final Location textLocation = new Location(350, 450);
    // orginally not final, so using getters
    private Location hideLocation = new Location(-500, - 500);
    private Location trumpsActorLocation = new Location(50, 50);

    public GameBoard() {
    }

    public Location getHideLocation() {
        return hideLocation;
    }

    public Location getTrumpsActorLocation() {
        return trumpsActorLocation;
    }
}
