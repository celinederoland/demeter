package accountancy.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public abstract class ObservableEntityList extends ObservableModel {

    private HashMap<Integer, Entity> entities;

    public ObservableEntityList() {

        this.entities = new HashMap<>();
    }

    public Entity getOne(Entity entity) {

        if (!this.entities.containsKey(entity.id())) {
            this.entities.put(entity.id(), entity);
            this.publish();
        }
        return this.entities.get(entity.id());
    }

    public void remove(int id) {

        if (!this.entities.containsKey(id))
            return;

        this.entities.remove(id);
        this.publish();
    }

    public Entity getOne(int id) {

        return this.entities.get(id);
    }

    public Entity getOne(String title) {

        for (Entity account : this.getAll()) {
            if (account.title().equals(title)) {
                return account;
            }
        }
        return null;
    }

    public ArrayList<Entity> getAll() {

        ArrayList<Entity> entities = new ArrayList<>(this.entities.values());
        entities.sort(Comparator.comparingInt(Entity::id));
        return entities;
    }

    @Override public String toString() {

        StringBuilder text = new StringBuilder();
        for (Entity entity : this.getAll()) {
            text.append("id: ").append(entity.id()).append(" - title: ").append(entity.title()).append("\n");
        }
        return text.toString();
    }
}
