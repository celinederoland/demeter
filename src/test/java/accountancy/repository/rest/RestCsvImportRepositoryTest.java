package accountancy.repository.rest;

import accountancy.repository.AnyCsvRepositoryTest;
import accountancy.repository.BaseRepository;
import accountancy.repository.CsvImportRepository;
import accountancy.repository.RepositoryTest;
import accountancy.repository.sql.ScriptRunner;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

public class RestCsvImportRepositoryTest extends RepositoryTest {

    protected BaseRepository      repository;
    protected CsvImportRepository csvRepository;

    public RestCsvImportRepositoryTest() throws Exception {

        super();
        ScriptRunner scriptRunner = new ScriptRunner(connectionProvider.getConnection(), true, true);
        String       file         = "datas/schema-base.sql";
        scriptRunner.runScript(new BufferedReader(new FileReader(file)));

        String file2 = "datas/schema-csv.sql";
        scriptRunner.runScript(new BufferedReader(new FileReader(file2)));
    }

    @Before
    public void setUp() throws Exception {

        ScriptRunner scriptRunner = new ScriptRunner(connectionProvider.getConnection(), true, true);
        String       file         = "datas/fixture-base.sql";
        scriptRunner.runScript(new BufferedReader(new FileReader(file)));

        String file2 = "datas/fixture-csv.sql";
        scriptRunner.runScript(new BufferedReader(new FileReader(file2)));

        repository = new RestBaseRepository(System.getenv("ACCOUNTANCY_URL"));
        csvRepository = new RestCsvImportRepository(System.getenv("ACCOUNTANCY_URL"), repository);

        (new AnyCsvRepositoryTest()).setUp(repository);
    }

    @Test
    public void csvLineImportSaveAndCheck() throws Exception {

        (new AnyCsvRepositoryTest()).csvLineImportSaveAndCheck(repository, csvRepository);
    }

    @Test
    public void doImportInvalid() throws Exception {

        (new AnyCsvRepositoryTest()).doImportInvalid(repository, csvRepository);
    }

    @Test
    public void doImportCA() {

        (new AnyCsvRepositoryTest()).doImportCA(repository, csvRepository);
    }

    @Test
    public void doImportMyFile() {

        (new AnyCsvRepositoryTest()).doImportMyFile(repository, csvRepository);
    }
}