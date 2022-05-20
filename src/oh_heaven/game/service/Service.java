package oh_heaven.game.service;

public class Service {
    private Rule rule;
    private Dealer dealer;

    public Service(Rule rule, Dealer dealer) {
        this.rule = rule;
        this.dealer = dealer;
    }

    public Rule getRule() {
        return rule;
    }

    public Dealer getDealer() {
        return dealer;
    }
}
