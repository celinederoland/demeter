package accountancy.model.base;

import accountancy.model.Entity;
import accountancy.model.ObservableEntityList;

import java.util.ArrayList;

public class Transactions extends ObservableEntityList {

    @Override public ArrayList<Entity> getAll() {

        ArrayList<Entity> entities = new ArrayList<>(this.entities.values());
        entities.sort((o1, o2) -> {

            Transaction t1 = (Transaction) o1;
            Transaction t2 = (Transaction) o2;

            if (t1.date().getTime() != t2.date().getTime()) {
                return (t1.date().getTime() - t2.date().getTime() > 0 ? 1 : -1);
            }
            return t1.id() - t2.id();
        });
        return entities;
    }
}
