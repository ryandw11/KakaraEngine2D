package org.kakara.engine2d;

import org.kakara.engine.gameitems.Texture;
import org.kakara.engine.utils.RGBA;

import java.util.Optional;

/**
 * The 2D version of the normal Material class.
 *
 * <p>This class is used with the {@link Mesh2D}. Note: This class is not related
 * to the normal Material class in any way.</p>
 *
 * <p>The RGBA and Texture classes are not 2D specific and are form the standard engine library.</p>
 *
 * <code>
 * Material2D mat = mesh.getMaterial2D();<br>
 * mat.setColor(new RGBA(255, 255, 255, 1));<br>
 * mat.setTexture(text);<br>
 * </code>
 */
public class Material2D {
    private RGBA color;
    private Texture texture;

    /**
     * Construct the Material2D class.
     *
     * @param color   The color of the mesh.
     * @param texture The texture for the mesh.
     */
    public Material2D(RGBA color, Texture texture) {
        this.color = color;
        this.texture = texture;
    }

    /**
     * Construct the Material2D class.
     *
     * @param color The color of the mesh.
     */
    public Material2D(RGBA color) {
        this(color, null);
    }

    /**
     * Construct the Material2D class.
     *
     * @param texture The texture of the mesh.
     */
    public Material2D(Texture texture) {
        this(new RGBA(), texture);
    }

    /**
     * Construct the Material2D class.
     */
    public Material2D() {
        this(new RGBA(), null);
    }

    /**
     * Get the color for of the Material.
     *
     * @return The color of the Material.
     */
    public RGBA getColor() {
        return color;
    }

    /**
     * Set the color of the Material.
     *
     * @param color The color of the material.
     */
    public void setColor(RGBA color) {
        this.color = color;
    }

    /**
     * Get the texture for the material
     * <p>This returns an optional since a texture is not required.</p>
     *
     * @return An optional of the texture.
     */
    public Optional<Texture> getTexture() {
        return Optional.ofNullable(texture);
    }

    /**
     * Set the texture of the Material.
     *
     * @param texture The texture of the Material. (Set to null for none).
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
