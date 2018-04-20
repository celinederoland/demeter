package accountancy.repository.rest;

import accountancy.repository.AnyRepositoryTest;
import accountancy.repository.BaseRepository;
import accountancy.repository.sql.RepositoryTest;
import accountancy.repository.sql.ScriptRunner;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

public class RestBaseRepositoryTest extends RepositoryTest {

    protected BaseRepository repository;

    public RestBaseRepositoryTest() throws Exception {

        super();
        ScriptRunner scriptRunner = new ScriptRunner(connectionProvider.getConnection(), true, true);
        String       file         = "datas/schema-base.sql";
        scriptRunner.runScript(new BufferedReader(new FileReader(file)));
    }

    @Before
    public void setUp() throws Exception {

        ScriptRunner scriptRunner = new ScriptRunner(connectionProvider.getConnection(), true, true);
        String       file         = "datas/fixture-base.sql";
        scriptRunner.runScript(new BufferedReader(new FileReader(file)));

        repository = new RestBaseRepository("http://localhost:8002");
    }

    @Test
    public void types() {

        (new AnyRepositoryTest(repository)).types();
    }

    @Test
    public void banks() throws Exception {

        (new AnyRepositoryTest(repository)).banks();
    }

    @Test
    public void currencies() throws Exception {

        (new AnyRepositoryTest(repository)).currencies();
    }

    @Test
    public void accounts() throws Exception {

        (new AnyRepositoryTest(repository)).accounts();
    }

    @Test
    public void categories() throws Exception {

        (new AnyRepositoryTest(repository)).categories();
    }

    @Test
    public void transactions() throws Exception {

        (new AnyRepositoryTest(repository)).transactions();
    }
}