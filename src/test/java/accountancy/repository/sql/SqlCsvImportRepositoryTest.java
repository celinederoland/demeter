package accountancy.repository.sql;

import accountancy.model.base.*;
import accountancy.repository.BaseRepository;
import accountancy.repository.CsvImportRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SqlCsvImportRepositoryTest extends RepositoryTest {

    protected BaseRepository      repository;
    protected CsvImportRepository csvRepository;

    public SqlCsvImportRepositoryTest() throws Exception {

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

        repository = new SqlBaseRepository(connectionProvider);
        csvRepository = new SqlCsvImportRepository(connectionProvider);


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

    @Test
    public void csvLineImportSaveAndCheck() throws Exception {


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
}