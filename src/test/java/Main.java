import org.kakara.engine.GameEngine;
import org.lwjgl.system.Configuration;

public class Main {
    public static void main(String[] args) {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
        System.out.println("GameEngine.getEngineVersion() = " + GameEngine.getEngineVersion());
        TestGame kt = new TestGame();

        Configuration.DEBUG_MEMORY_ALLOCATOR.set(true);

        GameEngine.LOGGER.debug("Test");

        System.out.println(GameEngine.LOGGER.isErrorEnabled());

        GameEngine gameEng = new GameEngine("Kakara Engine 2D :: Test", 1080, 720, true, kt);
        gameEng.run();
    }
}
