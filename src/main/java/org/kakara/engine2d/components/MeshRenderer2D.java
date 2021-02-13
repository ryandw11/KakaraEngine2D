package org.kakara.engine2d.components;

import org.kakara.engine.GameHandler;
import org.kakara.engine.components.Component;
import org.kakara.engine.gameitems.mesh.IMesh;
import org.kakara.engine2d.Abstract2DScene;

import java.util.Objects;

/**
 * The Mesh Renderer for 2D GameItems.
 *
 * <p>If you want to use {@link org.kakara.engine2d.Mesh2D} this mesh renderer <b>must</b> be used.
 * The current scene must also be a child of {@link Abstract2DScene}.</p>
 *
 * <p>Like {@link org.kakara.engine.components.MeshRenderer}, the game item will be moved around internally
 * between the ItemHandler and {@link org.kakara.engine2d.Item2DHandler}</p>
 *
 * <code>
 *     MeshRenderer2D renderer = gameItem.addComponent(MeshRenderer2D.class);<br>
 *     renderer.setMesh(mesh2D);
 * </code>
 */
public class MeshRenderer2D extends Component {
    private IMesh[] mesh;
    private boolean visible = true;
    private Abstract2DScene currentScene;

    @Override
    public void start() {
        if (!(GameHandler.getInstance().getCurrentScene() instanceof Abstract2DScene))
            throw new IllegalStateException("Current scene must be an abstract 2D scene");
        this.currentScene = (Abstract2DScene) GameHandler.getInstance().getCurrentScene();
        if (Objects.requireNonNull(currentScene.getItemHandler()).containsItem(getGameItem()))
            currentScene.getItemHandler().removeItem(getGameItem());
        currentScene.getItem2DHandler().addItem(getGameItem());
    }

    @Override
    public void onRemove() {
        currentScene.getItem2DHandler().removeItem(getGameItem());
        Objects.requireNonNull(currentScene.getItemHandler()).addItem(getGameItem());
    }

    @Override
    public void update() {

    }

    @Override
    public void cleanup() {
        int numMeshes = this.mesh != null ? this.mesh.length : 0;
        for (int i = 0; i < numMeshes; i++) {
            this.mesh[i].cleanUp();
        }
    }

    /**
     * Set the mesh for the component.
     *
     * @param mesh The mesh to set.
     */
    public void setMesh(IMesh mesh) {
        Objects.requireNonNull(mesh);
        if (this.mesh != null)
            this.mesh[0].cleanUp();
        this.mesh = new IMesh[1];
        this.mesh[0] = mesh;
    }

    /**
     * Set the meshes for the component.
     *
     * @param mesh The array of meshes to set.
     */
    public void setMesh(IMesh[] mesh) {
        Objects.requireNonNull(mesh);
        if (this.mesh != null)
            for (IMesh m : this.mesh)
                m.cleanUp();
        this.mesh = mesh;
    }

    /**
     * Get the mesh.
     *
     * @return The mesh.
     */
    public IMesh getMesh() {
        return mesh[0];
    }

    /**
     * Get the meshes.
     *
     * @return The meshes.
     */
    public IMesh[] getMeshes() {
        return mesh;
    }

    /**
     * If the mesh is visible.
     *
     * @return If the mesh is visible.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Set if the mesh is visible.
     *
     * @param visible if the mesh is visible.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
