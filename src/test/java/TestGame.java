import org.kakara.engine.Game;
import org.kakara.engine.GameHandler;
import org.kakara.engine.scene.Scene;

public class TestGame implements Game {
    @Override
    public void start(GameHandler gameHandler) throws Exception {

    }

    @Override
    public Scene firstScene(GameHandler gameHandler) throws Exception {
        return new MainScene(gameHandler);
    }

    @Override
    public void update() {

    }

    @Override
    public void exit() {

    }
}
