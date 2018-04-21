package accountancy.repository;

import accountancy.model.base.*;
import accountancy.repository.csv.CsvRepository;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.*;

public class AnyCsvRepositoryTest {

    public void csvLineImportSaveAndCheck(BaseRepository repository, CsvImportRepository csvRepository)
        throws Exception {

        String dateField         = "2017-05-12";
        String dateField2        = "2017-05-11";
        String descriptionField  = "foo bar";
        String descriptionField2 = "foo / bar";
        double amount            = 12.45;
        double amount2           = 12.40;

        csvRepository.saveCsvImport(
            dateField, descriptionField, amount,
            (Transaction) repository.transactions().getOne(1)
        );

        assertTrue(csvRepository.csvLineHasBeenImported(dateField, descriptionField, amount));
        assertFalse(csvRepository.csvLineHasBeenImported(dateField2, descriptionField, amount));
        assertFalse(csvRepository.csvLineHasBeenImported(dateField, descriptionField2, amount));
        assertFalse(csvRepository.csvLineHasBeenImported(dateField, descriptionField, amount2));
        assertFalse(csvRepository.csvLineHasBeenImported(dateField2, descriptionField2, amount2));
    }

    public void setUp(BaseRepository repository) {

        Type        current  = repository.create(new Type("current"));
        Bank        bnk      = repository.create(new Bank("BNK"));
        Currency    eur      = repository.create(new Currency("EUR"));
        Category    invoices = repository.create(new Category("invoices"));
        SubCategory energy   = repository.create(new SubCategory("energy"), invoices);
        Account bnkCurrent = repository.create(new Account(
            "current", eur, bnk, current
        ));
        Transaction t1 = repository.create(new Transaction(
            "random 01", -150.43, new Date(),
            bnkCurrent, invoices, energy
        ));
        repository.findAll();
    }

    public void doImportInvalid(BaseRepository repository, CsvImportRepository csvRepository) throws Exception {

        assertEquals(1, repository.transactions().getAll().size());

        (new CsvRepository(
            repository, csvRepository, new File("datas/compta_export_invalid.csv"),
            (Account) repository.accounts().getOne()
        )).doImport();

        assertEquals(1, repository.transactions().getAll().size());
    }

    public void doImportCA(BaseRepository repository, CsvImportRepository csvRepository) {

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

    public void doImportMyFile(BaseRepository repository, CsvImportRepository csvRepository) {

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
