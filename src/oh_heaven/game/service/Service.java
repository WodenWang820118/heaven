package oh_heaven.game.service;

import ch.aplu.jgamegrid.Actor;

public class Service {
    private Actor[] scoreActors = {null, null, null, null };
    public final int thinkingTime = 2000;
    private boolean enforceRules = false;
    public Service() {    
    }

    public Actor[] getScoreActors() {
        return scoreActors;
    }

    public boolean getEnforceRules() {
        return enforceRules;
    }

    public void setEnforceRules(boolean enforceRules) {
        this.enforceRules = enforceRules;
    }
}
