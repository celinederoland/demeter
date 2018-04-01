package accountancy.repository.csv;

import accountancy.model.base.Account;
import accountancy.model.base.Transaction;
import accountancy.repository.sql.SqlCsvImportRepositoryTest;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class CsvRepositoryTest extends SqlCsvImportRepositoryTest {

    public CsvRepositoryTest() throws Exception {

        super();
    }

    @Override public void setUp() throws Exception {

        super.setUp();
    }

    @Test
    public void doImportInvalid() throws Exception {

        assertEquals(1, repository.transactions().getAll().size());

        (new CsvRepository(
            repository, csvRepository, new File("datas/compta_export_invalid.csv"),
            (Account) repository.accounts().getOne()
        )).doImport();

        assertEquals(1, repository.transactions().getAll().size());
    }

    @Test
    public void doImportCA() {

        (new CsvRepository(
            repository, csvRepository, new File("datas/compta_export_ca.csv"),
            (Account) repository.accounts().getOne()
        )).doImport();

        assertEquals(51, repository.transactions().getAll().size());
        assertEquals(
            "Duis nec elit a massa gravida posuere.\n" +
            "Proin venenatis orci a nisi dapibus finibus.\n" +
            "Fusce porta lectus eget elementum elementum.",
            repository.transactions().getOne(5).title()
        );
        assertEquals(-246.00, ((Transaction) repository.transactions().getOne(5)).amount(), 0);
    }

    @Test
    public void doImportMyFile() {

        (new CsvRepository(
            repository, csvRepository, new File("datas/compta_export.csv"),
            (Account) repository.accounts().getOne()
        )).doImport();

        assertEquals(118, repository.transactions().getAll().size());
        assertEquals(
            "Feugiat Fermentum",
            repository.transactions().getOne(5).title()
        );
        assertEquals("Facilisis", ((Transaction) repository.transactions().getOne(5)).category().title());
        assertEquals("Feugiat", ((Transaction) repository.transactions().getOne(5)).subCategory().title());
        assertEquals(4000.00, ((Transaction) repository.transactions().getOne(5)).amount(), 0);
    }

}