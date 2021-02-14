import org.kakara.engine.GameHandler;
import org.kakara.engine.gameitems.GameItem;
import org.kakara.engine.gameitems.Texture;
import org.kakara.engine.input.KeyInput;
import org.kakara.engine2d.Abstract2DScene;
import org.kakara.engine2d.Mesh2D;
import org.kakara.engine2d.components.MeshRenderer2D;
import org.kakara.engine2d.primitives.SquareData;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;

public class MainScene extends Abstract2DScene {
    protected MainScene(GameHandler gameHandler) {
        super(gameHandler);
    }

    @Override
    public void work() {
    }

    @Override
    public void loadGraphics(GameHandler gameHandler) throws Exception {

        Mesh2D mesh2D = new Mesh2D(SquareData.vertices, SquareData.textures, SquareData.indices);
        GameItem gameItem = new GameItem();
        MeshRenderer2D meshRenderer2D = gameItem.addComponent(MeshRenderer2D.class);
        meshRenderer2D.setMesh(mesh2D);
        mesh2D.getMaterial2D().setTexture(new Texture(gameHandler.getResourceManager().getResource("/ExampleBlock.png"), this));
        gameItem.transform.setPosition(600, 500, 0);
        gameItem.transform.setScale(200, 500, 1);
        gameItem.addComponent(FollowMouse.class);

        add(gameItem);


    }

    @Override
    public void update(float v) {
        KeyInput input = GameHandler.getInstance().getKeyInput();
        if (input.isKeyPressed(GLFW_KEY_RIGHT)) {
            getCamera().movePosition(5, 0, 0);
        }
        if (input.isKeyPressed(GLFW_KEY_LEFT)) {
            getCamera().movePosition(-5, 0, 0);
        }
    }
}