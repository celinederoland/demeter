package accountancy.model.base;

import accountancy.model.Entity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class TransactionsTest {

    @Test
    public void getAll() throws Exception {

        Date now = new Date();
        Date tomorrow = new Date(now.getTime() + 1000*25*3600);

        Currency eur = new Currency(3, "EUR");
        Bank myBank      = new Bank(4, "Be-My-Bank");
        Type current = new Type(4, "current");
        Account account      = new Account(12, "my current account", eur, myBank, current);

        Category    invoices       = new Category(12, "invoices");
        SubCategory energy         = new SubCategory(1, "energy");
        invoices.subCategories().add(energy);

        Transaction transaction1 = new Transaction(
            5, "a transaction", 50.12, now, account, invoices, energy
        );

        Transaction transaction2 = new Transaction(
            6, "a transaction", 50.12, now, account, invoices, energy
        );

        Transaction transaction3 =  new Transaction(
            2, "a transaction", 50.12, tomorrow, account, invoices, energy
        );

        Transactions list = new Transactions();
        list.add(transaction2);
        list.add(transaction3);
        list.add(transaction1);

        ArrayList<Entity> actual = list.getAll();

        assertEquals(3, actual.size());
        assertEquals(transaction1, actual.get(0));
        assertEquals(transaction2, actual.get(1));
        assertEquals(transaction3, actual.get(2));
    }

}