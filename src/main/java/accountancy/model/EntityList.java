package accountancy.model;

import java.util.ArrayList;

public interface EntityList {

    Entity getOne(Entity entity);

    void remove(int id);

    Entity getOne(int id);

    Entity getOne(String title);

    ArrayList<Entity> getAll();
}
