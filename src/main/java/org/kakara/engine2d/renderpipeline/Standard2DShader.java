package org.kakara.engine2d.renderpipeline;

import org.kakara.engine.render.Shader;
import org.kakara.engine.render.ShaderProgram;
import org.kakara.engine.utils.Utils;

/**
 * This is the standard shader for the 2D GameItems.
 *
 * <p>This shader is used with the {@link Standard2DPipeline} and is only available
 * when using the {@link org.kakara.engine2d.Abstract2DScene} scene.</p>
 */
public class Standard2DShader implements ShaderProgram {
    private Shader shader;

    @Override
    public void initializeShader() {
        try {
            shader = new Shader();
            shader.createVertexShader(Utils.loadResource("/Standard2DVertex.vs"));
            shader.createFragmentShader(Utils.loadResource("/Standard2DFragment.fs"));
            shader.link();
            shader.createUniform("model");
            shader.createUniform("ortho");
            shader.createUniform("textureOffset");
            shader.createUniform("columnsRows");
            shader.createUniform("isSpriteSheet");
            shader.createUniform("material.texture");
            shader.createUniform("material.color");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Shader getShader() {
        return shader;
    }


}
