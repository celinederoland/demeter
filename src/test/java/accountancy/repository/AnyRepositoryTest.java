package accountancy.repository;

import accountancy.model.base.Type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnyRepositoryTest {

    private BaseRepository repository;

    public AnyRepositoryTest(BaseRepository repository) {

        this.repository = repository;
    }

    public void types() {

        Type current = repository.create(new Type(0, "current"));
        Type saving  = repository.create(new Type(0, "saving"));
        Type credit  = repository.create(new Type(0, "credit"));

        assertEquals(1, current.id());
        assertEquals(2, saving.id());
        assertEquals(3, credit.id());

        assertEquals(current, repository.types().getOne("current"));
        assertEquals(saving, repository.types().getOne("saving"));
        assertEquals(credit, repository.types().getOne("credit"));

        assertEquals(3, repository.types().getAll().size());
        assertTrue(repository.types().getAll().contains(current));
        assertTrue(repository.types().getAll().contains(saving));
        assertTrue(repository.types().getAll().contains(credit));

        current.title("current -b");
        repository.save(current);
        assertEquals("current -b", repository.types().getOne(1).title());
    }
}
