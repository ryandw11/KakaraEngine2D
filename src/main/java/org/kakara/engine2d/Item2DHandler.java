package org.kakara.engine2d;

import org.kakara.engine.components.Component;
import org.kakara.engine.gameitems.GameItem;
import org.kakara.engine2d.components.MeshRenderer2D;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class is the 2D equivalent of the normal ItemHandler.
 *
 * <p>The {@link Abstract2DScene#add(GameItem)} and {@link Abstract2DScene#remove(GameItem)} will
 * automatically add or remove items from the class.</p>
 *
 * <p>Use {@link Abstract2DScene#getItem2DHandler()} to get the instance of this class.</p>
 */
public class Item2DHandler {
    private final List<GameItem> items;

    public Item2DHandler() {
        this.items = new CopyOnWriteArrayList<>();
    }

    /**
     * Add an item to the 2d item handler.
     *
     * <p>This throws IllegalStateException if the provided GameItem is not a 2D GameItem.</p>
     *
     * @param item The item to have.
     */
    public void addItem(GameItem item) {
        if (!item.hasComponent(MeshRenderer2D.class))
            throw new IllegalStateException("GameItem does not have a 2D mesh renderer.");
        items.add(item);
    }

    /**
     * Update the components in the GameItems.
     */
    public void update() {
        for (GameItem item : items) {
            for (Component component : item.getComponents()) {
                component.update();
            }
        }
    }

    /**
     * Remove items from the 2D Item Handler.
     *
     * @param item The item to remove.
     */
    public void removeItem(GameItem item) {
        items.remove(item);
    }

    /**
     * Get the list of game items.
     *
     * @return The list of 2D game items. (Returns an unmodifiable list).
     */
    public List<GameItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
