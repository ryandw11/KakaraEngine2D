package org.kakara.engine2d.components;

import org.kakara.engine.components.Component;
import org.kakara.engine.resources.Resource;
import org.kakara.engine.scene.Scene;
import org.kakara.engine2d.animator.SpriteAnimation;

import java.util.HashMap;
import java.util.Map;

/**
 * This component handles the animations of 2D GameItems which use a sprite sheet.
 *
 * <p>Note: A sprite sheet is nothing special, just a Texture with columns and rows. See
 * {@link org.kakara.engine.gameitems.Texture#Texture(Resource, int, int, Scene)} for creating a texture
 * with a sprite sheet.</p>
 *
 * <p>A SpriteAnimator on its own does not do anything. The sprite needs actual animations ({@link SpriteAnimation}).
 * You can add those SpriteAnimations to the SpriteAnimator by using the {@link #addSpriteAnimation(SpriteAnimation)}
 * method. The SpriteAnimator then allows you to easily change between animations.</p>
 *
 * <h3>Example</h3>
 * <code>
 * SpriteAnimator spriteAnimator = gameItem.addComponent(SpriteAnimator.class);<br>
 * SpriteAnimation animation = new SpriteAnimation("run_left", Arrays.asList(0, 1, 2, 3));<br>
 * <br>
 * spriteAnimator.addSpriteAnimation(animation);<br>
 * <br>
 * spriteAnimator.setCurrentAnimation("run_left");<br>
 * </code>
 */
public class SpriteAnimator extends Component {
    private final Map<String, SpriteAnimation> spriteAnimations = new HashMap<>();

    private SpriteAnimation currentAnimation;

    @Override
    public void start() {
        for (Map.Entry<String, SpriteAnimation> animation : spriteAnimations.entrySet()) {
            animation.getValue().init(getGameItem());
        }
    }

    @Override
    public void update() {
        if (currentAnimation == null) return;
        currentAnimation.update();
    }

    /**
     * Add a sprite animation to the animator.
     *
     * @param spriteAnimation The sprite animation to add.
     */
    public void addSpriteAnimation(SpriteAnimation spriteAnimation) {
        if (getGameItem() != null)
            spriteAnimation.init(getGameItem());
        this.spriteAnimations.put(spriteAnimation.getName(), spriteAnimation);
    }

    /**
     * Set the current animation for the sprite.
     *
     * @param name The name of the current animation.
     */
    public void setCurrentAnimation(String name) {
        if (!spriteAnimations.containsKey(name))
            throw new IllegalArgumentException("The specified animation does not exist. Name: " + name);

        currentAnimation = spriteAnimations.get(name);
        currentAnimation.reset();
    }

    /**
     * Set the current animation to none.
     */
    public void resetCurrentAnimation() {
        this.currentAnimation = null;
    }

    /**
     * Get the current animation in progress.
     *
     * @return The current animation.
     */
    public SpriteAnimation getCurrentAnimation() {
        return this.currentAnimation;
    }

    /**
     * Get an animation by name.
     *
     * @param name The name of the animation to get.
     * @return The animation.
     */
    public SpriteAnimation getAnimationByName(String name) {
        if (!spriteAnimations.containsKey(name))
            throw new IllegalArgumentException("The specified animation does not exist. Name: " + name);

        return spriteAnimations.get(name);
    }
}
