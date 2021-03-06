package accountancy.repository.sql;

import accountancy.repository.AnyRepositoryTest;
import accountancy.repository.BaseRepository;
import accountancy.repository.RepositoryTest;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

public class SqlBaseRepositoryTest extends RepositoryTest {

    protected BaseRepository repository;

    public SqlBaseRepositoryTest() throws Exception {

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

        repository = new SqlBaseRepository(connectionProvider);
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

    @Test
    public void findAll() throws Exception {

        (new AnyRepositoryTest(repository)).findAll(new SqlBaseRepository(connectionProvider));
    }

    @Test
    public void find() throws Exception {

        (new AnyRepositoryTest(repository)).find(new SqlBaseRepository(connectionProvider));
    }

    @Test
    public void clean() throws Exception {

        (new AnyRepositoryTest(repository)).clean();
    }

}