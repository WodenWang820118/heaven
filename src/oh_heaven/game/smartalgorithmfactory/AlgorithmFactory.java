package oh_heaven.game.smartalgorithmfactory;

public class AlgorithmFactory {
    private IAlgorithm smartAlgorithm;

    public IAlgorithm createAlgorithm(String playerType){
        switch (playerType) {
            case "random":
                smartAlgorithm = new RandomAlgorithm();
                break;
            case "smart":
                smartAlgorithm = new Algorithm();
                break;
            case "legal":
                smartAlgorithm = new LegalAlgorithm();
            default:
                System.out.println("Unknown player type: " + playerType);
        }
        return smartAlgorithm;
    }
    
}
