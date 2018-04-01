package accountancy.model.base;

import accountancy.framework.Observer;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TransactionTest {

    @Test
    public void gettersAndSetters() throws Exception {

        Currency eur = new Currency(3, "EUR");
        Currency usd = new Currency(2, "USD");

        Bank myBank      = new Bank(4, "Be-My-Bank");
        Bank myOtherBank = new Bank(5, "Be-My-Other-Bank");

        Type current = new Type(4, "current");
        Type saving  = new Type(7, "saving");

        Account account      = new Account(12, "my current account", eur, myBank, current);
        Account otherAccount = new Account(10, "other account", usd, myOtherBank, saving);

        Category    invoices       = new Category(12, "invoices");
        SubCategory energy         = new SubCategory(1, "energy");
        SubCategory communications = new SubCategory(2, "communications");
        SubCategory taxes          = new SubCategory(3, "taxes");
        invoices.subCategories().add(energy);
        invoices.subCategories().add(communications);
        invoices.subCategories().add(taxes);

        Category    dayToDay = new Category(13, "day to day");
        SubCategory food     = new SubCategory(4, "food");
        SubCategory medics   = new SubCategory(5, "medics");
        dayToDay.subCategories().add(food);
        dayToDay.subCategories().add(medics);

        Category empty = new Category(14, "empty category");

        Date now = new Date();
        Transaction transaction = new Transaction(
            5, "a transaction", 50.12, now, account, invoices, taxes
        );

        assertEquals(50.12, transaction.amount(), 0);
        assertEquals(account, transaction.account());
        assertEquals(invoices, transaction.category());
        assertEquals(taxes, transaction.subCategory());
        assertEquals(now, transaction.date());

        transaction.account(otherAccount)
                   .category(dayToDay)
                   .amount(60)
                   .date(new Date());

        assertEquals(60, transaction.amount(), 0);
        assertEquals(otherAccount, transaction.account());
        assertEquals(dayToDay, transaction.category());
        assertEquals(food, transaction.subCategory());
        assertNotEquals(now, transaction.date());

        //Doesn't work if I take a subCategory which doesn't belong to the transaction category
        transaction.subCategory(energy);
        assertEquals(food, transaction.subCategory());

        //Doesn't work if I take an empty category
        transaction.category(empty);
        assertEquals(dayToDay, transaction.category());
    }

    public void publish() {

        Currency eur = new Currency(3, "EUR");
        Currency usd = new Currency(2, "USD");

        Bank myBank      = new Bank(4, "Be-My-Bank");
        Bank myOtherBank = new Bank(5, "Be-My-Other-Bank");

        Type current = new Type(4, "current");
        Type saving  = new Type(7, "saving");

        Account account      = new Account(12, "my current account", eur, myBank, current);
        Account otherAccount = new Account(10, "other account", usd, myOtherBank, saving);

        Category    invoices       = new Category(12, "invoices");
        SubCategory energy         = new SubCategory(1, "energy");
        SubCategory communications = new SubCategory(2, "communications");
        SubCategory taxes          = new SubCategory(3, "taxes");
        invoices.subCategories().add(energy);
        invoices.subCategories().add(communications);
        invoices.subCategories().add(taxes);

        Category    dayToDay = new Category(13, "day to day");
        SubCategory food     = new SubCategory(4, "food");
        SubCategory medics   = new SubCategory(5, "medics");
        dayToDay.subCategories().add(food);
        dayToDay.subCategories().add(medics);


        Date now = new Date();
        Transaction transaction = new Transaction(
            5, "a transaction", 50.12, now, account, invoices, taxes
        );

        MockObserver observer = new MockObserver();
        transaction.addObserver(observer);

        transaction.id(5).title("a transaction");
        transaction.amount(50.12).date(now).account(account).category(invoices).subCategory(taxes);

        assertFalse(observer.hasBeenUpdated());

        transaction.startTransaction();
        transaction.id(6).title("an other transaction");
        assertFalse(observer.hasBeenUpdated());
        transaction.amount(50);
        assertFalse(observer.hasBeenUpdated());
        transaction.date(new Date());
        assertFalse(observer.hasBeenUpdated());
        transaction.account(otherAccount);
        assertFalse(observer.hasBeenUpdated());
        transaction.category(dayToDay).subCategory(taxes);
        transaction.commit();
        assertTrue(observer.hasBeenUpdated());
    }

    private final class MockObserver implements Observer {

        private boolean update = false;

        @Override public void update() {

            update = true;
        }

        public boolean hasBeenUpdated() {

            return update;
        }

        public void resetMock() {

            this.update = false;
        }
    }

}