package accountancy.model;

import java.util.ArrayList;

/**
 * Describe a list of basic model resources (typically a set of entries in one database table)
 */
public interface EntityList {

    /**
     * If the entity doesn't exist, it will be saved and returned.
     * If the entity exists yet, it is returned
     *
     * @param entity a representation of the searched entity
     * @return Entity
     */
    Entity save(Entity entity);

    /**
     * Remove an entity from the list, searching by id
     * @param id the entity id
     */
    void remove(int id);

    /**
     * Find one saved entity by id
     *
     * @param id the entity id
     * @return Entity
     */
    Entity getOne(int id);

    /**
     * Fine one saved entity by title
     *
     * @param title the entity title
     * @return Entity
     */
    Entity getOne(String title);

    /**
     * Sorted list of all saved entities
     *
     * @return ArrayList<Entity>
     */
    ArrayList<Entity> getAll();
}
