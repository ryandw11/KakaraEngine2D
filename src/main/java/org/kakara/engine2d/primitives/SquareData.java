package org.kakara.engine2d.primitives;

/**
 * Contains the data required to make a square mesh.
 */
public class SquareData {
    /**
     * Vertex data for a square.
     */
    public final static float[] vertices = {
            -0.5f, 0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f
    };

    /**
     * Texture data for a square.
     */
    public final static float[] textures = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    /**
     * Indices data for a square.
     */
    public final static int[] indices = {
            0, 3, 2, 2, 1, 0
    };
}
