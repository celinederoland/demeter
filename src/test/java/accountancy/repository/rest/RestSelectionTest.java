package accountancy.repository.rest;

import accountancy.repository.AnySelectionTest;
import accountancy.repository.BaseRepository;
import accountancy.repository.RepositoryTest;
import accountancy.repository.SelectionProvider;
import accountancy.repository.sql.ScriptRunner;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;

public class RestSelectionTest extends RepositoryTest {

    protected BaseRepository    repository;
    protected SelectionProvider provider;

    public RestSelectionTest() throws Exception {

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

        String file2 = "datas/fixture-selection.sql";
        scriptRunner.runScript(new BufferedReader(new FileReader(file2)));

        String url = System.getenv("ACCOUNTANCY_URL");
        repository = new RestBaseRepository(url);
        provider = new RestSelectionProvider(url, repository);
        repository.findAll();
    }

    @Test
    public void test1() throws Exception {

        (new AnySelectionTest(repository, provider)).test1();
    }

    @Test
    public void test2() throws ParseException {

        (new AnySelectionTest(repository, provider)).test2();
    }

    @Test
    public void test3() throws ParseException {

        (new AnySelectionTest(repository, provider)).test3();
    }

    @Test
    public void test4() throws Exception {

        (new AnySelectionTest(repository, provider)).test4();
    }

    @Test
    public void test5() throws Exception {

        (new AnySelectionTest(repository, provider)).test5();
    }

    @Test
    public void test6() throws Exception {

        (new AnySelectionTest(repository, provider)).test6();
    }

    @Test
    public void test7() throws Exception {

        (new AnySelectionTest(repository, provider)).test7();
    }

    @Test
    public void testAmountByDate() throws Exception {

        (new AnySelectionTest(repository, provider)).testAmountByDate();
    }
}