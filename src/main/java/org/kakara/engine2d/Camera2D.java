package org.kakara.engine2d;

import org.kakara.engine.Camera;
import org.kakara.engine.math.Vector2;

/**
 * The 2D version of the Camera.
 *
 * <p>This Camera provides additional utility methods for 2D movement. This is separate from the normal
 * Camera so that 2D graphics can be moved separately from 3D graphics.</p>
 */
public class Camera2D extends Camera {
    /**
     * Set the position of the Camera.
     *
     * @param x The x value.
     * @param y The y value.
     */
    public void setPosition(float x, float y) {
        this.setPosition(x, y, getPosition().z);
    }

    /**
     * Set the position of the Camera.
     *
     * @param position The position to set.
     */
    public void setPosition(Vector2 position) {
        this.setPosition(position.x, position.y);
    }

    /**
     * Move the position of the Camera based upon the direction it is looking.
     *
     * @param offsetX The x offset.
     * @param offsetY The y offset.
     */
    public void movePosition(float offsetX, float offsetY) {
        super.movePosition(offsetX, offsetY, 0);
    }

    /**
     * Move the position of the Camera based upon the direction it is look.
     *
     * @param offset The offset.
     */
    public void movePosition(Vector2 offset) {
        this.movePosition(offset.x, offset.y);
    }
}
