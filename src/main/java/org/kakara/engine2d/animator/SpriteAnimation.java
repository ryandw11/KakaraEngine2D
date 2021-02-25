package org.kakara.engine2d.animator;

import org.kakara.engine.gameitems.GameItem;
import org.kakara.engine.utils.Time;

import java.util.ArrayList;
import java.util.List;

/**
 * This handles a specific animation for a 2D GameItem.
 *
 * <p>You can use the SpriteAnimation class to define tiles from a sprite sheet that you wish the animation to display.
 * The order of which the tile ids are added is the order they will be played in.</p>
 *
 * <p>The SpriteAnimation can then be added to a {@link org.kakara.engine2d.components.SpriteAnimator} on the
 * GameItem.</p>
 *
 * <h3>Example</h3>
 * <code>
 * int[] tileIds = {0, 1, 2, 3, 5};<br>
 * SpriteAnimation = new SpriteAnimation("run_left", tileIds);<br>
 * </code>
 * <p>The code above creates a sprite animation which has the frames of (0, 1, 2, 3, 5). The ID of a tile
 * is based upon its index. So if a sprite sheet is 3 x 3, than the tile ids are 0-8 (Counting starts
 * at 0). 0 is the top-left while 8 is the bottom right. Here would be the Tile ID layout of a 3x3 sprite sheet:</p>
 * <table>
 *     <tr>
 *         <td>0</td>
 *         <td>1</td>
 *         <td>2</td>
 *     </tr>
 *     <tr>
 *        <td>3</td>
 *        <td>4</td>
 *        <td>5</td>
 *    </tr>
 *    <tr>
 *        <td>6</td>
 *        <td>7</td>
 *        <td>8</td>
 *    </tr>
 * </table>
 */
public class SpriteAnimation {

    private final List<Integer> sprites;
    private final String name;
    private int currentID;
    private float currentTime;
    private float timeBetweenFrames = 0.5f;

    private GameItem gameItem;

    /**
     * Create a sprite animation with no frames.
     *
     * @param name The name of the sprite animation.
     */
    public SpriteAnimation(String name) {
        this.name = name;
        sprites = new ArrayList<>();
        currentID = 0;
        currentTime = timeBetweenFrames;
    }

    /**
     * Create a sprite animation.
     *
     * @param name      The name of the sprite animation.
     * @param spriteIds The list of Tile Ids that will make up the frames. (Order matters.)
     */
    public SpriteAnimation(String name, List<Integer> spriteIds) {
        this(name);
        sprites.addAll(spriteIds);
    }

    /**
     * Create a sprite animation.
     *
     * @param name      The name of the animation.
     * @param spriteIds The array of Tile Ids that will make up the frames. (Order matters.)
     */
    public SpriteAnimation(String name, int[] spriteIds) {
        this(name);

        for (int i : spriteIds) {
            sprites.add(i);
        }
    }

    /**
     * Initialize the Animation with important information.
     * <p>This is for internal use only.</p>
     *
     * @param gameItem The game item.
     */
    public final void init(GameItem gameItem) {
        this.gameItem = gameItem;
    }

    /**
     * Reset the state of the Animation.
     */
    public void reset() {
        this.currentID = 0;
        this.currentTime = 0;
    }

    /**
     * Change to the next frame.
     */
    private void nextSprite() {
        if (currentID + 1 >= sprites.size())
            currentID = 0;
        else
            currentID++;

        if (gameItem == null)
            return;

        gameItem.setTextPos(sprites.get(currentID));
    }

    /**
     * Update the animation.
     * <p>Internal use only.</p>
     */
    public void update() {
        currentTime += Time.getDeltaTime();

        if (currentTime >= timeBetweenFrames) {
            currentTime = 0;
            nextSprite();
        }
    }

    /**
     * Set the time between frames.
     *
     * @param time The time between frames.
     */
    public void setTimeBetweenFrames(float time) {
        this.timeBetweenFrames = time;
    }

    /**
     * Get the time between frames.
     *
     * @return The time between frames.
     */
    public float getTimeBetweenFrames() {
        return this.timeBetweenFrames;
    }

    /**
     * Get the cname of the animation.
     *
     * @return The name of the animation.
     */
    public String getName() {
        return name;
    }

    /**
     * Add a frame to the animation.
     *
     * @param tileId The tile id.
     */
    public void addFrame(int tileId) {
        this.sprites.add(tileId);
    }

}
