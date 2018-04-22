package accountancy.repository.selection;

import accountancy.repository.BaseRepository;
import accountancy.repository.RepositoryTest;
import accountancy.repository.SelectionProvider;
import accountancy.repository.rest.RestBaseRepository;
import accountancy.repository.rest.RestSelectionProvider;
import accountancy.repository.sql.ScriptRunner;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

public class MySelectionFactoryRestTest extends RepositoryTest {

    protected BaseRepository    repository;
    protected SelectionProvider provider;

    public MySelectionFactoryRestTest() throws Exception {

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

        String file2 = "datas/fixture-myselection.sql";
        scriptRunner.runScript(new BufferedReader(new FileReader(file2)));

        String url = System.getenv("ACCOUNTANCY_URL");
        repository = new RestBaseRepository(url);
        repository.findAll();

        provider = new RestSelectionProvider(url, repository);
    }

    @Test
    public void selections() throws Exception {

        (new AnySelectionFactoryTest(repository, provider)).selections();
    }

}