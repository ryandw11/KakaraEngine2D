package org.kakara.engine2d;

import org.kakara.engine.GameHandler;
import org.kakara.engine.gameitems.GameItem;
import org.kakara.engine.physics.FixedPhysicsUpdater;
import org.kakara.engine.scene.AbstractScene;
import org.kakara.engine2d.components.MeshRenderer2D;
import org.kakara.engine2d.renderpipeline.Standard2DPipeline;
import org.kakara.engine2d.renderpipeline.Standard2DShader;

import java.util.Timer;

/**
 * This is the primary scene to be used with the 2D Game Item system. This abstract class
 * must be used if you want to use 2D GameItems.
 * <h2>Creation of 2D GameItems</h2>
 * <p>
 * 2D GameItems are only special from their 3D counter part by the components they use. You create
 * a 2D GameItem the exact same way (using the same class). (Note: You must use the default constructor and not
 * the one with a mesh parameter).
 * </p>
 *
 * <p>Like normal GameItems, 2D GameItems also have a Transform component. In order to change the position, rotation,
 * and scale, you must use the transform component. (The Z axis is ignored.) It is also important to note
 * that the position of the camera is automatically added to the position of the GameItem. (The rotation of the
 * camera is ignored).</p>
 *
 * <p>In order for a GameItem to become a 2D game item you need to add a special 2D component. This special component
 * is the {@link MeshRenderer2D} component. You want to add that component instead of the normal MeshRenderer component.
 * (Note: GameItem#getMeshRenderer() will not work with the 2D version. GameItem#getComponent() needs to be used
 * instead.) You then must use the Mesh2D with the MeshRenderer2D.</p>
 *
 * <code>
 * GameItem gameItem = new GameItem();<br>
 * Mesh2D mesh = new Mesh2D(vertex, texture, indices);<br>
 * MeshRenderer2D renderer = gameItem.addComponent(MeshRenderer2D.class);<br>
 * renderer.setMesh(mesh);<br>
 * add(gameItem);<br>
 * </code>
 */
public abstract class Abstract2DScene extends AbstractScene {
    private final Item2DHandler item2DHandler;
    private final Camera2D camera2D;
    private final Timer physicsUpdater;

    protected Abstract2DScene(GameHandler gameHandler) {
        super(gameHandler);
        this.item2DHandler = new Item2DHandler();
        this.camera2D = new Camera2D();

        if (gameHandler.getGameEngine().getShaderManager().findShader("Standard2D") == null) {
            System.out.println("Test");
            gameHandler.getGameEngine().getShaderManager().addShader("Standard2D", new Standard2DShader());
            gameHandler.getGameEngine().getPipelineManager().addPipeline(new Standard2DPipeline());
        }

        this.physicsUpdater = new Timer("Fixed Physics Update Timer");
        this.physicsUpdater.schedule(new FixedPhysicsUpdater(this), 10, 10);
    }

    @Override
    public void render() {
        gameHandler.getGameEngine().getRenderer().render(gameHandler.getWindow(), getCamera(), this);
        if (getSkyBox() != null)
            gameHandler.getGameEngine().getRenderer().renderSkyBox(gameHandler.getWindow(), getCamera(), this);
        userInterface.render(gameHandler.getWindow());
        this.item2DHandler.update();
    }

    /**
     * Add a normal GameItem or 2D GameItem to the scene.
     *
     * <p>The system will automatically decided where to put it based upon if
     * it has the MeshRenderer2D component or not.</p>
     *
     * @param gameItem The game item.
     */
    @Override
    public void add(GameItem gameItem) {
        if (gameItem.hasComponent(MeshRenderer2D.class)){
            if (!item2DHandler.getItems().contains(gameItem))
                item2DHandler.addItem(gameItem);
        }
        else
            super.add(gameItem);
    }

    /**
     * Removes a normal GameItem or 2D GameItem from the scene.
     * <p>Just like {@link #add(GameItem)} this method uses the MeshRenderer2D to decided
     * if the GameItem is a normal or 2D GameItem.</p>
     *
     * @param item The item to remove.
     */
    @Override
    public void remove(GameItem item) {
        if (item.hasComponent(MeshRenderer2D.class))
            item2DHandler.removeItem(item);
        else
            super.remove(item);
    }

    /**
     * <p>Get the Camera responsible for rendering the 2D Objects.</p>
     * @return The 2D camera.
     */
    public Camera2D getCamera2D(){
        return camera2D;
    }

    /**
     * Get the 2D ItemHandler.
     *
     * @return The 2D ItemHandler.
     */
    public Item2DHandler getItem2DHandler() {
        return item2DHandler;
    }

    @Override
    public void unload() {
        this.physicsUpdater.cancel();
    }
}
