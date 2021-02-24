import org.kakara.engine.GameHandler;
import org.kakara.engine.gameitems.GameItem;
import org.kakara.engine.gameitems.Texture;
import org.kakara.engine.input.Input;
import org.kakara.engine.input.key.KeyCode;
import org.kakara.engine2d.Abstract2DScene;
import org.kakara.engine2d.Mesh2D;
import org.kakara.engine2d.components.MeshRenderer2D;
import org.kakara.engine2d.primitives.SquareData;

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
        mesh2D.getMaterial2D().setTexture(new Texture(gameHandler.getResourceManager().getResource("/ExampleBlock.png"), 4, 3, this));
        gameItem.setTextPos(0);
        gameItem.transform.setPosition(600, 500, 0);
        gameItem.transform.setScale(200, 200, 1);
        gameItem.addComponent(FollowMouse.class);
        gameItem.addComponent(SpriteAnimator.class);

        add(gameItem);


    }

    @Override
    public void update(float v) {
        if (Input.isKeyDown(KeyCode.RIGHT_ARROW)) {
            getCamera2D().movePosition(5, 0, 0);
        }
        if (Input.isKeyDown(KeyCode.LEFT_ARROW)) {
            getCamera2D().movePosition(-5, 0, 0);
        }
    }
}