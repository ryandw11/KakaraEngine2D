package org.kakara.engine2d.renderpipeline;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.kakara.engine.Camera;
import org.kakara.engine.GameHandler;
import org.kakara.engine.gameitems.GameItem;
import org.kakara.engine.gameitems.Texture;
import org.kakara.engine.gameitems.mesh.IMesh;
import org.kakara.engine.lighting.ShadowMap;
import org.kakara.engine.math.Vector3;
import org.kakara.engine.render.RenderPipeline;
import org.kakara.engine.render.Shader;
import org.kakara.engine.render.ShaderManager;
import org.kakara.engine.render.Transformation;
import org.kakara.engine.render.culling.FrustumCullingFilter;
import org.kakara.engine.scene.Scene;
import org.kakara.engine.window.Window;
import org.kakara.engine2d.Abstract2DScene;
import org.kakara.engine2d.GameEngine2D;
import org.kakara.engine2d.Mesh2D;
import org.kakara.engine2d.components.MeshRenderer2D;

/**
 * The standard pipeline for the 2D GameItems.
 *
 * <p>Items are rendered from the {@link org.kakara.engine2d.Item2DHandler} instead of the normal
 * ItemHandler.</p>
 *
 * <p>This pipeline is only available when using {@link Abstract2DScene}. </p>
 */
public class Standard2DPipeline implements RenderPipeline {

    private Shader shaderProgram;

    @Override
    public void init(ShaderManager shaderManager, Transformation transformation, FrustumCullingFilter frustumCullingFilter, ShadowMap shadowMap) {
        this.shaderProgram = shaderManager.findShader("Standard2D").getShader();
    }

    @Override
    public void render(Scene scene) {
        if (!(scene instanceof Abstract2DScene))
            return;
        Abstract2DScene abstract2DScene = (Abstract2DScene) scene;
        shaderProgram.bind();
        Window window = GameHandler.getInstance().getWindow();
        float width = GameEngine2D.isStandard() ? GameEngine2D.getStandardWidth() : window.getWidth();
        float height = GameEngine2D.isStandard() ? GameEngine2D.getStandardHeight() : window.getHeight();
        Matrix4f projection = new Matrix4f().ortho2D(0, width, height, 0);
        shaderProgram.setUniform("ortho", projection);
        for (GameItem item : abstract2DScene.getItem2DHandler().getItems()) {
            for (IMesh mesh : item.getComponent(MeshRenderer2D.class).getMeshes()) {
                Mesh2D mesh2D = (Mesh2D) mesh;
                shaderProgram.setUniform("model", buildModel(item, abstract2DScene.getCamera2D()));
                shaderProgram.setUniform("material.texture", 0);
                shaderProgram.setUniform("material.color", mesh2D.getMaterial2D().getColor().getVectorColor());
                if(mesh2D.getMaterial2D().getTexture().isPresent())
                    calculateSpriteSheet(item, mesh2D.getMaterial2D().getTexture().get());
                mesh2D.render();
            }
        }

        shaderProgram.unbind();
    }

    /**
     * Calculate and set the values for the Shader uniforms which involve the sprite sheet.
     *
     * @param gameItem The game item to set the uniforms for.
     * @param text     The texture for the game item.
     */
    private void calculateSpriteSheet(GameItem gameItem, Texture text) {
        int col = gameItem.getTextPos() % text.getNumCols();
        int row = gameItem.getTextPos() / text.getNumCols();
        float textXOffset = (float) col / text.getNumCols();
        float textYOffset = (float) row / text.getNumRows();
        shaderProgram.setUniform("textureOffset", new Vector2f(textXOffset, textYOffset));
        shaderProgram.setUniform("columnsRows", new Vector2f(text.getNumCols(), text.getNumRows()));

        if (text.getNumCols() > 1 && text.getNumRows() > 1)
            shaderProgram.setUniform("isSpriteSheet", 1);
    }

    @Override
    public void renderDepthMap(Scene scene, Shader shader, Matrix4f matrix4f) {
    }

    /**
     * Build the model for 2D gameItems.
     *
     * @param gameItem The game item.
     * @param camera   The camera.
     * @return The Model Matrix with the position of the camera added on.
     */
    private Matrix4f buildModel(GameItem gameItem, Camera camera) {
        Quaternionf rotation = gameItem.transform.getRotation();
        Vector3 position = gameItem.transform.getPosition().add(camera.getPosition());
        return new Matrix4f()
                .translationRotateScale(position.x, position.y, camera.getPosition().z,
                        rotation.x, rotation.y, rotation.z, rotation.w,
                        gameItem.transform.getScale().x, gameItem.transform.getScale().y, 1);
    }
}
