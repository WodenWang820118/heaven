package oh_heaven.game.smartalgorithmfactory;

public class AlgorithmFactory {
    private static final AlgorithmFactory INSTANCE = new AlgorithmFactory();

    public static AlgorithmFactory getInstance() {
        return INSTANCE;
    }

    public static IAlgorithm getSmartAlgorithm() {
        return new SmartAlgorithm();
    }

    public static IAlgorithm getRandomAlgorithm() {
        return new RandomAlgorithm();
    }

    public static IAlgorithm getLegalAlgorithm() {
        return new LegalAlgorithm();
    }
}
