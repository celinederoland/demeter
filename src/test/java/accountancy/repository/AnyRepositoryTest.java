package accountancy.repository;

import accountancy.model.base.*;

import java.util.Date;

import static org.junit.Assert.*;

public class AnyRepositoryTest {

    private BaseRepository repository;

    public AnyRepositoryTest(BaseRepository repository) {

        this.repository = repository;
    }

    public void types() {

        Type current = repository.create(new Type("current"));
        Type saving  = repository.create(new Type("saving"));
        Type credit  = repository.create(new Type("credit"));

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

    public void banks() throws Exception {

        Bank bnk = repository.create(new Bank("BNK"));
        Bank bn2 = repository.create(new Bank("BN2"));

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

    public void currencies() throws Exception {

        Currency eur = repository.create(new Currency("EUR"));
        Currency chf = repository.create(new Currency("CHF"));

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

    public void accounts() throws Exception {

        Type current = repository.create(new Type("current"));
        Type saving  = repository.create(new Type("saving"));
        Type credit  = repository.create(new Type("credit"));

        Bank bnk = repository.create(new Bank("BNK"));
        Bank bn2 = repository.create(new Bank("BN2"));

        Currency eur = repository.create(new Currency("EUR"));
        Currency chf = repository.create(new Currency("CHF"));

        Account bnkCurrent = repository.create(new Account(
            "current", eur, bnk, current
        ));
        Account bnkSaving = repository.create(new Account(
            "pel", eur, bnk, saving
        ));
        Account bn2Current = repository.create(new Account(
            "current", chf, bn2, current
        ));
        Account bnkCredit = repository.create(new Account(
            "immo", chf, bnk, credit
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

    public void categories() throws Exception {

        Category    invoices = repository.create(new Category("invoices"));
        SubCategory taxes    = repository.create(new SubCategory("taxes"), invoices);
        SubCategory energy   = repository.create(new SubCategory("energy"), invoices);

        Category    dayToDay = repository.create(new Category("day to day"));
        SubCategory food     = repository.create(new SubCategory("food"), dayToDay);
        SubCategory medics   = repository.create(new SubCategory("medics"), dayToDay);

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

    public void transactions() throws Exception {

        Type current = repository.create(new Type("current"));
        Type saving  = repository.create(new Type("saving"));
        Type credit  = repository.create(new Type("credit"));

        Bank bnk = repository.create(new Bank("BNK"));
        Bank bn2 = repository.create(new Bank("BN2"));

        Currency eur = repository.create(new Currency("EUR"));
        Currency chf = repository.create(new Currency("CHF"));

        Category    invoices = repository.create(new Category("invoices"));
        SubCategory taxes    = repository.create(new SubCategory("taxes"), invoices);
        SubCategory energy   = repository.create(new SubCategory("energy"), invoices);

        Category    dayToDay = repository.create(new Category("day to day"));
        SubCategory food     = repository.create(new SubCategory("food"), dayToDay);
        SubCategory medics   = repository.create(new SubCategory("medics"), dayToDay);

        Account bnkCurrent = repository.create(new Account(
            "current", eur, bnk, current
        ));
        Account bnkSaving = repository.create(new Account(
            "pel", eur, bnk, saving
        ));
        Account bn2Current = repository.create(new Account(
            "current", chf, bn2, current
        ));
        Account bnkCredit = repository.create(new Account(
            "immo", chf, bnk, credit
        ));

        Transaction t1 = repository.create(new Transaction(
            "random 01", -150.43, new Date(),
            bnkCurrent, invoices, energy
        ));
        Transaction t2 = repository.create(new Transaction(
            "random 02", -50.89, new Date(),
            bnkCurrent, dayToDay, food
        ));
        Transaction t3 = repository.create(new Transaction(
            "random 03", -300, new Date(),
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

    public void findAll(BaseRepository newRepository) throws Exception {

        Type current = repository.create(new Type("current"));
        Type saving  = repository.create(new Type("saving"));
        Type credit  = repository.create(new Type("credit"));

        Bank bnk = repository.create(new Bank("BNK"));
        Bank bn2 = repository.create(new Bank("BN2"));

        Currency eur = repository.create(new Currency("EUR"));
        Currency chf = repository.create(new Currency("CHF"));

        Category    invoices = repository.create(new Category("invoices"));
        SubCategory taxes    = repository.create(new SubCategory("taxes"), invoices);
        SubCategory energy   = repository.create(new SubCategory("energy"), invoices);

        Category    dayToDay = repository.create(new Category("day to day"));
        SubCategory food     = repository.create(new SubCategory("food"), dayToDay);
        SubCategory medics   = repository.create(new SubCategory("medics"), dayToDay);

        Account bnkCurrent = repository.create(new Account(
            "current", eur, bnk, current
        ));
        Account bnkSaving = repository.create(new Account(
            "pel", eur, bnk, saving
        ));
        Account bn2Current = repository.create(new Account(
            "current", chf, bn2, current
        ));
        Account bnkCredit = repository.create(new Account(
            "immo", chf, bnk, credit
        ));

        Transaction t1 = repository.create(new Transaction(
            "random 01", -150.43, new Date(),
            bnkCurrent, invoices, energy
        ));
        Transaction t2 = repository.create(new Transaction(
            "random 02", -50.89, new Date(),
            bnkCurrent, dayToDay, food
        ));
        Transaction t3 = repository.create(new Transaction(
            "random 03", -300, new Date(),
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

    public void find(BaseRepository newRepository) {

        Type current = repository.create(new Type("current"));
        Type saving  = repository.create(new Type("saving"));
        Type credit  = repository.create(new Type("credit"));

        Bank bnk = repository.create(new Bank("BNK"));
        Bank bn2 = repository.create(new Bank("BN2"));

        Currency eur = repository.create(new Currency("EUR"));
        Currency chf = repository.create(new Currency("CHF"));

        Category    invoices = repository.create(new Category("invoices"));
        SubCategory taxes    = repository.create(new SubCategory("taxes"), invoices);
        SubCategory energy   = repository.create(new SubCategory("energy"), invoices);

        Category    dayToDay = repository.create(new Category("day to day"));
        SubCategory food     = repository.create(new SubCategory("food"), dayToDay);
        SubCategory medics   = repository.create(new SubCategory("medics"), dayToDay);

        Account bnkCurrent = repository.create(new Account(
            "current", eur, bnk, current
        ));
        Account bnkSaving = repository.create(new Account(
            "pel", eur, bnk, saving
        ));
        Account bn2Current = repository.create(new Account(
            "current", chf, bn2, current
        ));
        Account bnkCredit = repository.create(new Account(
            "immo", chf, bnk, credit
        ));

        Transaction t1 = repository.create(new Transaction(
            "random 01", -150.43, new Date(),
            bnkCurrent, invoices, energy
        ));
        Transaction t2 = repository.create(new Transaction(
            "random 02", -50.89, new Date(),
            bnkCurrent, dayToDay, food
        ));
        Transaction t3 = repository.create(new Transaction(
            "random 03", -300, new Date(),
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

        assertEquals(bnk, newRepository.find(bnk));

        assertEquals(t1, newRepository.find(t1));
        assertEquals(bn2Current, newRepository.find(bn2Current));
        assertEquals(medics, newRepository.find(medics));

        assertEquals(current, newRepository.find(current));
        assertEquals(saving, newRepository.find(saving));
        assertEquals(credit, newRepository.find(credit));

        assertEquals(bn2, newRepository.find(bn2));

        assertEquals(eur, newRepository.find(eur));
        assertEquals(chf, newRepository.find(chf));

        assertEquals(bnkCurrent, newRepository.find(bnkCurrent));
        assertEquals(bnkSaving, newRepository.find(bnkSaving));
        assertEquals(bnkCredit, newRepository.find(bnkCredit));

        assertEquals(invoices, newRepository.find(invoices));
        assertEquals(dayToDay, newRepository.find(dayToDay));

    }

    public void clean() {

        Type current = repository.create(new Type("current"));
        Type saving  = repository.create(new Type("saving"));
        Type credit  = repository.create(new Type("credit"));

        Bank bnk = repository.create(new Bank("BNK"));
        Bank bn2 = repository.create(new Bank("BN2"));

        Currency eur = repository.create(new Currency("EUR"));
        Currency chf = repository.create(new Currency("CHF"));
        Currency usd = repository.create(new Currency("USD"));

        Category    invoices = repository.create(new Category("invoices"));
        SubCategory taxes    = repository.create(new SubCategory("taxes"), invoices);
        SubCategory energy   = repository.create(new SubCategory("energy"), invoices);

        Category    dayToDay = repository.create(new Category("day to day"));
        SubCategory food     = repository.create(new SubCategory("food"), dayToDay);
        SubCategory medics   = repository.create(new SubCategory("medics"), dayToDay);

        Category    unusedCat  = repository.create(new Category("..."));
        SubCategory unusedSCat = repository.create(new SubCategory("???"), unusedCat);


        Account bnkCurrent = repository.create(new Account(
            "current", eur, bnk, current
        ));
        Account bnkSaving = repository.create(new Account(
            "pel", eur, bnk, saving
        ));
        Account bn2Current = repository.create(new Account(
            "current", chf, bn2, current
        ));
        Account bnkCredit = repository.create(new Account(
            "immo", chf, bnk, credit
        ));

        Transaction t1 = repository.create(new Transaction(
            "random 01", -150.43, new Date(),
            bnkCurrent, invoices, energy
        ));
        Transaction t2 = repository.create(new Transaction(
            "random 02", -50.89, new Date(),
            bnkCurrent, dayToDay, food
        ));
        Transaction t3 = repository.create(new Transaction(
            "random 03", -300, new Date(),
            bn2Current, invoices, taxes
        ));

        repository.clean();

        assertEquals(2, repository.accounts().getAll().size());
        assertTrue(repository.accounts().getAll().contains(bnkCurrent));
        assertTrue(repository.accounts().getAll().contains(bn2Current));

        assertEquals(2, repository.banks().getAll().size());
        assertTrue(repository.banks().getAll().contains(bnk));
        assertTrue(repository.banks().getAll().contains(bn2));

        assertEquals(2, repository.currencies().getAll().size());
        assertTrue(repository.currencies().getAll().contains(eur));
        assertTrue(repository.currencies().getAll().contains(chf));

        assertEquals(2, repository.find(invoices).subCategories().getAll().size());
        assertEquals(1, repository.find(dayToDay).subCategories().getAll().size());

        assertEquals(2, repository.categories().getAll().size());
        assertTrue(repository.categories().getAll().contains(invoices));
        assertTrue(repository.categories().getAll().contains(dayToDay));

        assertEquals(1, repository.types().getAll().size());
        assertTrue(repository.types().getAll().contains(current));

    }
}
