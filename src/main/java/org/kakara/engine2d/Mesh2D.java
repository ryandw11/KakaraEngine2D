package org.kakara.engine2d;

import org.jetbrains.annotations.NotNull;
import org.kakara.engine.GameEngine;
import org.kakara.engine.exceptions.InvalidThreadException;
import org.kakara.engine.gameitems.GameItem;
import org.kakara.engine.gameitems.Material;
import org.kakara.engine.gameitems.Texture;
import org.kakara.engine.gameitems.mesh.IMesh;
import org.kakara.engine.render.culling.FrustumCullingFilter;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * The Mesh for 2D GameItems.
 *
 * <p>This Mesh cannot be used with the normal MeshRenderer. Instead the {@link org.kakara.engine2d.components.MeshRenderer2D}
 * must be used.</p>
 *
 * <p>This mesh is designed for 2D usage, thus the vertices are a 2D vector instead of 3D.</p>
 *
 * <p>Example for a Square:</p>
 * <code>
 * float[] vertices = { <br>
 * -0.5f, 0.5f,<br>
 * -0.5f, -0.5f,<br>
 * 0.5f, -0.5f,<br>
 * 0.5f, 0.5f<br>
 * };<br>
 * float[] textures = {<br>
 * 0.0f, 0.0f,<br>
 * 0.0f, 1.0f,<br>
 * 1.0f, 1.0f,<br>
 * 1.0f, 0.0f<br>
 * };<br>
 * int[] indices = {<br>
 * 0, 3, 2, 2, 1, 0<br>
 * };<br>
 * Mesh2D mesh = new Mesh2D(vertices, textures, indices);<br>
 * </code>
 */
public class Mesh2D implements IMesh {

    private Material2D material;

    private final int vaoID;
    private final int vertexCount;
    private final List<Integer> vboIds;

    /**
     * Construct the Mesh2D.
     *
     * <p>Note: This class can only be constructed on the Main Thread.</p>
     *
     * @param position The position values (A 2D vector instead of 3D).
     * @param texture  The texture values.
     * @param indices  The indices values.
     */
    public Mesh2D(float[] position, float[] texture, int[] indices) {
        if (Thread.currentThread() != GameEngine.currentThread)
            throw new InvalidThreadException("This class can only be constructed on the main thread.");

        this.material = new Material2D();

        vertexCount = indices.length;
        vboIds = new ArrayList<>();

        FloatBuffer positionBuffer;
        FloatBuffer textureBuffer;
        IntBuffer indexBuffer;

        try (MemoryStack stack = MemoryStack.stackPush()) {

            vaoID = glGenVertexArrays();
            glBindVertexArray(vaoID);

            // Position VBO
            int vboId = glGenBuffers();
            vboIds.add(vboId);
            positionBuffer = stack.mallocFloat(position.length);
            positionBuffer.put(position).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

            // Texture coordinates VBO
            vboId = glGenBuffers();
            vboIds.add(vboId);
            textureBuffer = stack.mallocFloat(texture.length);
            textureBuffer.put(texture).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, textureBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

            // Index VBO
            vboId = glGenBuffers();
            vboIds.add(vboId);
            indexBuffer = stack.mallocInt(indices.length);
            indexBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);


            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }
    }

    @Override
    public void render() {
        if (material.getTexture().isPresent()) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, material.getTexture().get().getId());
        }
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public void cleanUp() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for (int vboId : vboIds) {
            glDeleteBuffers(vboId);
        }

        material.getTexture().ifPresent(Texture::cleanup);

        glBindVertexArray(0);
        glDeleteVertexArrays(vaoID);
    }

    /**
     * Not used in this implementation.
     * <p>Use {@link #getMaterial2D()} instead.</p>
     *
     * @return Not used.
     * @deprecated Not used in this implementation.
     */
    @Deprecated
    @Override
    public Optional<Material> getMaterial() {
        return Optional.empty();
    }

    /**
     * <p>This method is not used by the Mesh2D.</p>
     *
     * @param list                 Unused
     * @param frustumCullingFilter Unused
     * @param consumer             Unused
     * @deprecated Unused.
     */
    @Deprecated
    @Override
    public void renderList(List<GameItem> list, FrustumCullingFilter frustumCullingFilter, Consumer<GameItem> consumer) {
        throw new UnsupportedOperationException("Cannot render by list on the 2DMesh.");
    }

    @Override
    public boolean isWireframe() {
        return false;
    }

    @Override
    public void setWireframe(boolean b) {

    }

    /**
     * Get the 2D material of the mesh.
     * <p>All meshes are guaranteed to have a 2D material.</p>
     *
     * @return The 2D material of the mesh.
     */
    public Material2D getMaterial2D() {
        return material;
    }

    /**
     * Set the material of the Mesh.
     *
     * @param material2D Set the material of the mesh. (Cannot be null).
     */
    public void setMaterial(@NotNull Material2D material2D) {
        this.material = material2D;
    }
}
