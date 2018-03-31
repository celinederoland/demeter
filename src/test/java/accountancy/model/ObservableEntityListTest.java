package accountancy.model;

import accountancy.framework.Observer;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ObservableEntityListTest {

    private final class MockObservableEntityList extends ObservableEntityList {

    }

    private final class MockEntity implements Entity {

        private int    id;
        private String title;

        public MockEntity(int id, String title) {

            this.id = id;
            this.title = title;
        }

        public int id() {

            return this.id;
        }

        public Entity id(int id) {

            if (this.id != id) {
                this.id = id;
            }
            return this;
        }

        public String title() {

            return this.title;
        }

        public Entity title(String title) {

            if (!this.title.equals(title)) {
                this.title = title;
            }
            return this;
        }

        @Override
        public boolean equals(Object object) {

            boolean sameSame = false;

            if (object != null && object.getClass().equals(this.getClass())) {
                sameSame = this.id == ((Entity) object).id();
            }

            return sameSame;
        }


        @Override
        public String toString() {

            return this.title;
        }
    }

    @Test
    public void save() throws Exception {

        ObservableEntityList list = new MockObservableEntityList();
        MockEntity           entity1 = new MockEntity(5, "mock title");
        MockEntity entity2 = new MockEntity(5, "other title");
        MockEntity entity3 = new MockEntity(8, "mock title");

        list.add(entity1);
        list.add(entity2);
        list.add(entity3);

        ArrayList<Entity> actual = list.getAll();
        assertEquals(2, actual.size());
        assertTrue(actual.contains(entity1));
        assertTrue(actual.contains(entity3));
    }

    @Test
    public void remove() throws Exception {

        ObservableEntityList list = new MockObservableEntityList();
        MockEntity           entity1 = new MockEntity(5, "mock title");
        MockEntity entity3 = new MockEntity(8, "mock title");

        list.add(entity1);
        list.add(entity3);

        list.remove(5);

        ArrayList<Entity> actual = list.getAll();
        assertEquals(1, actual.size());
        assertFalse(actual.contains(entity1));
        assertTrue(actual.contains(entity3));
    }

    @Test
    public void getOne() throws Exception {

        ObservableEntityList list = new MockObservableEntityList();
        MockEntity           entity1 = new MockEntity(5, "mock title");
        MockEntity entity3 = new MockEntity(8, "other title");

        list.add(entity1);
        list.add(entity3);

        assertEquals(entity1, list.getOne(5));
        assertEquals(entity3, list.getOne("other title"));
        assertNull(list.getOne(12));
    }

    @Test
    public void toStringT() throws Exception {

        ObservableEntityList list = new MockObservableEntityList();
        MockEntity           entity1 = new MockEntity(5, "mock title");
        MockEntity entity3 = new MockEntity(8, "other title");

        list.add(entity3);
        list.add(entity1);

        String expected = "id: 5 - title: mock title\nid: 8 - title: other title\n";
        assertEquals(expected, list.toString());
    }

    @Test
    public void addObserver() throws Exception {

        ObservableEntityList list = new MockObservableEntityList();
        MockObserver         observer = new MockObserver();

        list.addObserver(observer);
        assertFalse(observer.hasBeenUpdated());

        list.add(new MockEntity(5, "mock title"));
        assertTrue(observer.hasBeenUpdated());

        observer.resetMock();
        list.remove(5);
        assertTrue(observer.hasBeenUpdated());
    }

    @Test
    public void removeObserver() throws Exception {

        ObservableEntityList list = new MockObservableEntityList();
        MockObserver         observer = new MockObserver();

        list.addObserver(observer);
        assertFalse(observer.hasBeenUpdated());

        list.removeObserver(observer);
        list.add(new MockEntity(5, "mock title"));
        assertFalse(observer.hasBeenUpdated());
    }

    @Test
    public void transaction() throws Exception {

        ObservableEntityList list = new MockObservableEntityList();
        MockObserver         observer = new MockObserver();

        list.addObserver(observer);
        assertFalse(observer.hasBeenUpdated());
        list.startTransaction();

        list.add(new MockEntity(5, "mock title"));
        assertFalse(observer.hasBeenUpdated());
        list.add(new MockEntity(8, "mock title 2"));
        assertFalse(observer.hasBeenUpdated());

        list.commit();
        assertTrue(observer.hasBeenUpdated());
    }

    private final class MockObserver implements Observer {

        private boolean update = false;

        @Override public void update() {
            update = true;
        }

        public boolean hasBeenUpdated() {
            return update;
        }

        public void resetMock() {
            this.update = false;
        }
    }

}