package org.kakara.engine2d;

/**
 * This class is responsible for some of the global settings of the 2D portion of the
 * engine.
 *
 * <p>These settings are similar to the ones provided by the UICanvas. The engine can automatically handle
 * scale for you.</p>
 */
public final class GameEngine2D {
    private static boolean isStandard = false;
    private static float standardWidth = 1080;
    private static float standardHeight = 720;

    /**
     * Set if the scale of the viewport is standard.
     * <p>So if you resize the window, the items will also resize.</p>
     *
     * @param standard If you want the scale of the viewport to be standard.
     */
    public static void setStandard(boolean standard) {
        isStandard = standard;
    }

    /**
     * Get if the scale of the viewport is standard.
     *
     * @return If the viewport is standard.
     */
    public static boolean isStandard() {
        return isStandard;
    }

    /**
     * Set the standard size of the of the viewport.
     *
     * @param width  The width.
     * @param height The height.
     */
    public static void setStandardSize(float width, float height) {
        standardWidth = width;
        standardHeight = height;
    }

    /**
     * Get the standard width of the viewport.
     * <p>The default value is 1080.</p>
     *
     * @return The standard width of the viewport.
     */
    public static float getStandardWidth() {
        return standardWidth;
    }

    /**
     * Get the standard height of the viewport.
     * <p>The default value is 720.</p>
     *
     * @return The standard height of the viewport.
     */
    public static float getStandardHeight() {
        return standardHeight;
    }
}
