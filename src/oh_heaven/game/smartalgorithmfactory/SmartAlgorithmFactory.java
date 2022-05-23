package oh_heaven.game.smartalgorithmfactory;

public class SmartAlgorithmFactory {
    private ISmartAlgorithm smartAlgorithm;

    public ISmartAlgorithm createAlgorithm(String playerType){
        switch (playerType) {
            case "random":
                smartAlgorithm = new RandomAlgorithm();
                break;
            case "smart":
                smartAlgorithm = new SmartAlgorithm();
                break;
            case "legal":
                smartAlgorithm = new LegalAlgorithm();
            default:
                System.out.println("Unknown player type: " + playerType);
        }
        return smartAlgorithm;
    }
    
}
