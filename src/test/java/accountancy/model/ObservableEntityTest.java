package accountancy.model;

import accountancy.framework.Observer;
import org.junit.Test;

import static org.junit.Assert.*;

public class ObservableEntityTest {

    @Test
    public void id() throws Exception {

        MockObservableEntity entity = new MockObservableEntity(5, "mock title");
        assertEquals(5, entity.id());

        entity.id(12);
        assertEquals(12, entity.id());
    }

    @Test
    public void title() throws Exception {

        MockObservableEntity entity = new MockObservableEntity(5, "mock title");
        assertEquals("mock title", entity.title());

        entity.title("new title");
        assertEquals("new title", entity.title());
    }

    @Test
    public void equals() throws Exception {

        ObservableEntity entity                     = new MockObservableEntity(5, "mock title");
        ObservableEntity entityWithSameClassSameId  = new MockObservableEntity(5, "mock title");
        ObservableEntity entityWithSameClassOtherId = new MockObservableEntity(8, "mock title");
        ObservableEntity entityWithOtherClassSameId = new MockObservableEntity2(5, "mock title");

        assertTrue(entity.equals(entityWithSameClassSameId));
        assertFalse(entity.equals(entityWithSameClassOtherId));
        assertFalse(entity.equals(entityWithOtherClassSameId));
    }

    @Test
    public void toStringT() throws Exception {

        MockObservableEntity entity = new MockObservableEntity(5, "mock title");
        assertEquals("mock title", entity.toString());
    }

    @Test
    public void addObserver() throws Exception {

        MockObservableEntity entity   = new MockObservableEntity(5, "mock title");
        MockObserver         observer = new MockObserver();
        entity.addObserver(observer);
        entity.id(12);
        assertTrue(observer.hasBeenUpdated());
    }

    @Test
    public void removeObserver() throws Exception {

        MockObservableEntity entity   = new MockObservableEntity(5, "mock title");
        MockObserver         observer = new MockObserver();
        entity.addObserver(observer);
        entity.removeObserver(observer);
        entity.id(12);
        assertFalse(observer.hasBeenUpdated());
    }

    @Test
    public void startTransaction() throws Exception {

        MockObservableEntity entity   = new MockObservableEntity(5, "mock title");
        MockObserver         observer = new MockObserver();
        entity.startTransaction();
        entity.addObserver(observer);
        entity.id(12);
        assertFalse(observer.hasBeenUpdated());
    }

    @Test
    public void commit() throws Exception {

        MockObservableEntity entity   = new MockObservableEntity(5, "mock title");
        MockObserver         observer = new MockObserver();
        entity.startTransaction();
        entity.addObserver(observer);
        entity.id(12);
        entity.commit();
        assertTrue(observer.hasBeenUpdated());
    }

    private final class MockObservableEntity extends ObservableEntity {

        public MockObservableEntity(int id, String title) {

            super(id, title);
        }
    }

    private final class MockObservableEntity2 extends ObservableEntity {

        public MockObservableEntity2(int id, String title) {

            super(id, title);
        }
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