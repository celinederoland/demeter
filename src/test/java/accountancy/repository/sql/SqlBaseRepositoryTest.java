package accountancy.repository.sql;

import accountancy.model.base.*;
import accountancy.repository.AnyRepositoryTest;
import accountancy.repository.BaseRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;

import static org.junit.Assert.*;

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

        Bank bnk = repository.create(new Bank(0, "BNK"));
        Bank bn2 = repository.create(new Bank(0, "BN2"));

        assertEquals(1, bnk.id());
        assertEquals(2, bn2.id());

        assertEquals(bnk, repository.banks().getOne("BNK"));
        assertEquals(bn2, repository.banks().getOne(2));

        assertEquals(2, repository.banks().getAll().size());
        assertTrue(repository.banks().getAll().contains(bnk));
        assertTrue(repository.banks().getAll().contains(bn2));

        bn2.title("BN-2");
        repository.save(bn2);
        assertEquals("BN-2", repository.banks().getOne(2).title());
    }

    @Test
    public void currencies() throws Exception {

        Currency eur = repository.create(new Currency(0, "EUR"));
        Currency chf = repository.create(new Currency(0, "CHF"));

        assertEquals(1, eur.id());
        assertEquals(2, chf.id());

        assertEquals(eur, repository.currencies().getOne(1));
        assertEquals(chf, repository.currencies().getOne(2));

        assertEquals(2, repository.currencies().getAll().size());
        assertTrue(repository.currencies().getAll().contains(eur));
        assertTrue(repository.currencies().getAll().contains(chf));

        eur.title("EU");
        repository.save(eur);
        assertEquals("EU", repository.currencies().getOne(1).title());
        assertNull(repository.currencies().getOne("EUR"));
    }

    @Test
    public void accounts() throws Exception {

        Type current = repository.create(new Type(0, "current"));
        Type saving  = repository.create(new Type(0, "saving"));
        Type credit  = repository.create(new Type(0, "credit"));

        Bank bnk = repository.create(new Bank(0, "BNK"));
        Bank bn2 = repository.create(new Bank(0, "BN2"));

        Currency eur = repository.create(new Currency(0, "EUR"));
        Currency chf = repository.create(new Currency(0, "CHF"));

        Account bnkCurrent = repository.create(new Account(
            0, "current", eur, bnk, current
        ));
        Account bnkSaving = repository.create(new Account(
            0, "pel", eur, bnk, saving
        ));
        Account bn2Current = repository.create(new Account(
            0, "current", chf, bn2, current
        ));
        Account bnkCredit = repository.create(new Account(
            0, "immo", chf, bnk, credit
        ));

        assertEquals(1, bnkCurrent.id());
        assertEquals(2, bnkSaving.id());
        assertEquals(3, bn2Current.id());
        assertEquals(4, bnkCredit.id());

        assertEquals(bnkCurrent, repository.accounts().getOne(1));
        assertEquals(bnkSaving, repository.accounts().getOne(2));
        assertEquals(bn2Current, repository.accounts().getOne(3));
        assertEquals(bnkCredit, repository.accounts().getOne(4));

        assertEquals(4, repository.accounts().getAll().size());
        assertTrue(repository.accounts().getAll().contains(bnkCurrent));
        assertTrue(repository.accounts().getAll().contains(bnkSaving));
        assertTrue(repository.accounts().getAll().contains(bn2Current));
        assertTrue(repository.accounts().getAll().contains(bnkCredit));

        bnkCurrent.currency(chf).bank(bn2).type(credit).title("modified");
        repository.save(bnkCurrent);
        assertEquals("modified", repository.accounts().getOne(1).title());
        assertEquals(chf, ((Account) repository.accounts().getOne(1)).currency());
        assertEquals(bn2, ((Account) repository.accounts().getOne(1)).bank());
        assertEquals(credit, ((Account) repository.accounts().getOne(1)).type());
    }

    @Test
    public void categories() throws Exception {

        Category    invoices = repository.create(new Category(0, "invoices"));
        SubCategory taxes    = repository.create(new SubCategory(0, "taxes"), invoices);
        SubCategory energy   = repository.create(new SubCategory(0, "energy"), invoices);

        Category    dayToDay = repository.create(new Category(0, "day to day"));
        SubCategory food     = repository.create(new SubCategory(0, "food"), dayToDay);
        SubCategory medics   = repository.create(new SubCategory(0, "medics"), dayToDay);

        assertEquals(invoices, repository.categories().getOne(1));
        assertEquals(
            taxes,
            ((Category) repository.categories().getOne("invoices"))
                .subCategories().getOne(1)
        );
        assertEquals(
            energy,
            ((Category) repository.categories().getOne("invoices"))
                .subCategories().getOne(2)
        );
        assertEquals(dayToDay, repository.categories().getOne(2));
        assertEquals(
            food,
            ((Category) repository.categories().getOne(2))
                .subCategories().getOne(3)
        );
        assertEquals(
            medics,
            ((Category) repository.categories().getOne(2))
                .subCategories().getOne("medics")
        );

        assertEquals(2, repository.categories().getAll().size());
        assertTrue(repository.categories().getAll().contains(invoices));
        assertTrue(repository.categories().getAll().contains(dayToDay));

        invoices.title("bills");
        repository.save(invoices);
        assertEquals("bills", repository.categories().getOne(1).title());

        taxes.title("state tax");
        repository.save(taxes);
        assertEquals(
            "state tax",
            ((Category) repository.categories().getOne(1)).subCategories().getOne(1).title()
        );
    }

    @Test
    public void findAll() throws Exception {

        Type current = repository.create(new Type(0, "current"));
        Type saving  = repository.create(new Type(0, "saving"));
        Type credit  = repository.create(new Type(0, "credit"));

        Bank bnk = repository.create(new Bank(0, "BNK"));
        Bank bn2 = repository.create(new Bank(0, "BN2"));

        Currency eur = repository.create(new Currency(0, "EUR"));
        Currency chf = repository.create(new Currency(0, "CHF"));

        Category    invoices = repository.create(new Category(0, "invoices"));
        SubCategory taxes    = repository.create(new SubCategory(0, "taxes"), invoices);
        SubCategory energy   = repository.create(new SubCategory(0, "energy"), invoices);

        Category    dayToDay = repository.create(new Category(0, "day to day"));
        SubCategory food     = repository.create(new SubCategory(0, "food"), dayToDay);
        SubCategory medics   = repository.create(new SubCategory(0, "medics"), dayToDay);

        Account bnkCurrent = repository.create(new Account(
            0, "current", eur, bnk, current
        ));
        Account bnkSaving = repository.create(new Account(
            0, "pel", eur, bnk, saving
        ));
        Account bn2Current = repository.create(new Account(
            0, "current", chf, bn2, current
        ));
        Account bnkCredit = repository.create(new Account(
            0, "immo", chf, bnk, credit
        ));

        Transaction t1 = repository.create(new Transaction(
            0, "random 01", -150.43, new Date(),
            bnkCurrent, invoices, energy
        ));
        Transaction t2 = repository.create(new Transaction(
            0, "random 02", -50.89, new Date(),
            bnkCurrent, dayToDay, food
        ));
        Transaction t3 = repository.create(new Transaction(
            0, "random 03", -300, new Date(),
            bn2Current, invoices, taxes
        ));

        current.title("current -b");
        repository.save(current);
        bn2.title("BN-2");
        repository.save(bn2);
        eur.title("EU");
        repository.save(eur);
        bnkCurrent.currency(chf).bank(bn2).type(credit).title("modified");
        repository.save(bnkCurrent);
        invoices.title("bills");
        repository.save(invoices);
        taxes.title("state tax");
        repository.save(taxes);

        BaseRepository newRepository = new SqlBaseRepository(connectionProvider);
        newRepository.findAll();
        assertEquals(3, newRepository.types().getAll().size());
        assertTrue(newRepository.types().getAll().contains(current));
        assertTrue(newRepository.types().getAll().contains(saving));
        assertTrue(newRepository.types().getAll().contains(credit));
        assertEquals(2, newRepository.banks().getAll().size());
        assertTrue(newRepository.banks().getAll().contains(bnk));
        assertTrue(newRepository.banks().getAll().contains(bn2));
        assertEquals(2, newRepository.currencies().getAll().size());
        assertTrue(newRepository.currencies().getAll().contains(eur));
        assertTrue(newRepository.currencies().getAll().contains(chf));
        assertEquals(4, newRepository.accounts().getAll().size());
        assertTrue(newRepository.accounts().getAll().contains(bnkCurrent));
        assertTrue(newRepository.accounts().getAll().contains(bnkSaving));
        assertTrue(newRepository.accounts().getAll().contains(bn2Current));
        assertTrue(newRepository.accounts().getAll().contains(bnkCredit));
        assertEquals(2, newRepository.categories().getAll().size());
        assertTrue(newRepository.categories().getAll().contains(invoices));
        assertTrue(newRepository.categories().getAll().contains(dayToDay));
        assertEquals("current -b", newRepository.types().getOne(1).title());
        assertEquals("BN-2", newRepository.banks().getOne(2).title());
        assertEquals("EU", newRepository.currencies().getOne(1).title());
        assertEquals("modified", newRepository.accounts().getOne(1).title());
        assertEquals(chf, ((Account) newRepository.accounts().getOne(1)).currency());
        assertEquals(bn2, ((Account) newRepository.accounts().getOne(1)).bank());
        assertEquals(credit, ((Account) newRepository.accounts().getOne(1)).type());
        assertEquals("bills", newRepository.categories().getOne(1).title());
        assertEquals(
            "state tax",
            ((Category) newRepository.categories().getOne(1)).subCategories().getOne(1).title()
        );
    }


    @Test
    public void transactions() throws Exception {

        Type current = repository.create(new Type(0, "current"));
        Type saving  = repository.create(new Type(0, "saving"));
        Type credit  = repository.create(new Type(0, "credit"));

        Bank bnk = repository.create(new Bank(0, "BNK"));
        Bank bn2 = repository.create(new Bank(0, "BN2"));

        Currency eur = repository.create(new Currency(0, "EUR"));
        Currency chf = repository.create(new Currency(0, "CHF"));

        Category    invoices = repository.create(new Category(0, "invoices"));
        SubCategory taxes    = repository.create(new SubCategory(0, "taxes"), invoices);
        SubCategory energy   = repository.create(new SubCategory(0, "energy"), invoices);

        Category    dayToDay = repository.create(new Category(0, "day to day"));
        SubCategory food     = repository.create(new SubCategory(0, "food"), dayToDay);
        SubCategory medics   = repository.create(new SubCategory(0, "medics"), dayToDay);

        Account bnkCurrent = repository.create(new Account(
            0, "current", eur, bnk, current
        ));
        Account bnkSaving = repository.create(new Account(
            0, "pel", eur, bnk, saving
        ));
        Account bn2Current = repository.create(new Account(
            0, "current", chf, bn2, current
        ));
        Account bnkCredit = repository.create(new Account(
            0, "immo", chf, bnk, credit
        ));

        Transaction t1 = repository.create(new Transaction(
            0, "random 01", -150.43, new Date(),
            bnkCurrent, invoices, energy
        ));
        Transaction t2 = repository.create(new Transaction(
            0, "random 02", -50.89, new Date(),
            bnkCurrent, dayToDay, food
        ));
        Transaction t3 = repository.create(new Transaction(
            0, "random 03", -300, new Date(),
            bn2Current, invoices, taxes
        ));

        assertEquals(t1, repository.transactions().getOne(1));
        assertEquals(t2, repository.transactions().getOne(2));
        assertEquals(t3, repository.transactions().getOne(3));

        assertEquals(3, repository.transactions().getAll().size());
        assertTrue(repository.transactions().getAll().contains(t1));
        assertTrue(repository.transactions().getAll().contains(t2));
        assertTrue(repository.transactions().getAll().contains(t3));

        t3.subCategory(energy);
        repository.save(t3);
        assertEquals(energy, ((Transaction) repository.transactions().getOne(3)).subCategory());
    }

}