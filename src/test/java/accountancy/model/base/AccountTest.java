package accountancy.model.base;

import accountancy.observer.Observer;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void gettersAndSetters() throws Exception {

        Currency eur = new Currency(3, "EUR");
        Currency usd = new Currency(2, "USD");

        Bank myBank      = new Bank(4, "Be-My-Bank");
        Bank myOtherBank = new Bank(5, "Be-My-Other-Bank");

        Type current = new Type(4, "current");
        Type saving  = new Type(7, "saving");

        Account account = new Account(
            12, "my current account",
            eur, myBank, current
        );

        assertEquals(eur, account.currency());
        assertEquals(usd, account.currency(usd).currency());

        assertEquals(myBank, account.bank());
        assertEquals(myOtherBank, account.bank(myOtherBank).bank());

        assertEquals(current, account.type());
        assertEquals(saving, account.type(saving).type());
    }

    @Test
    public void publish() {

        Currency eur = new Currency(3, "EUR");
        Currency usd = new Currency(2, "USD");

        Bank myBank      = new Bank(4, "Be-My-Bank");
        Bank myOtherBank = new Bank(5, "Be-My-Other-Bank");

        Type current = new Type(4, "current");
        Type saving  = new Type(7, "saving");

        Account account = new Account(
            12, "my current account",
            eur, myBank, current
        );

        MockObserver observer = new MockObserver();
        account.addObserver(observer);

        account.currency(eur);
        assertFalse(observer.hasBeenUpdated());
        account.currency(usd);
        assertTrue(observer.hasBeenUpdated());

        observer.resetMock();
        account.bank(myOtherBank);
        assertTrue(observer.hasBeenUpdated());
        observer.resetMock();
        account.bank(myOtherBank);
        assertFalse(observer.hasBeenUpdated());

        observer.resetMock();
        account.startTransaction();
        account.type(saving);
        assertFalse(observer.hasBeenUpdated());
        account.commit();
        assertTrue(observer.hasBeenUpdated());
    }


    @Test
    public void toStringT() throws Exception {

        Currency eur     = new Currency(3, "EUR");
        Bank     myBank  = new Bank(4, "Be-My-Bank");
        Type     current = new Type(4, "current");

        Account account = new Account(
            12, "my current account",
            eur, myBank, current
        );

        assertEquals("Be-My-Bank - my current account", account.toString());
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