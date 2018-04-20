package accountancy.model.base;

import accountancy.model.ObservableEntity;

/**
 * Represent a bank account
 */
public final class Account extends ObservableEntity {

    private Currency currency;
    private Bank     bank;
    private Type     type;

    public Account(int id) {

        this(id, "", null, null, null);
    }

    /**
     * @param id       number of this account
     * @param title    name of this account
     * @param currency currency used
     * @param bank     bank establishment
     * @param type     what is the aim of this account (eg current, save money, credit etc)
     */
    public Account(int id, String title, Currency currency, Bank bank, Type type) {

        super(id, title);
        this.currency = currency;
        this.bank = bank;
        this.type = type;
    }

    public Account(String title, Currency currency, Bank bank, Type type) {

        this(0, title, currency, bank, type);
    }

    /**
     * currency getter
     *
     * @return Currency
     */
    public Currency currency() {

        return this.currency;
    }

    /**
     * currency fluent setter
     *
     * @param currency the new currency
     *
     * @return Account
     */
    public Account currency(Currency currency) {

        if (this.currency != currency) {
            this.currency = currency;
            this.publish();
        }
        return this;
    }

    /**
     * bank getter
     *
     * @return Bank
     */
    public Bank bank() {

        return this.bank;
    }

    /**
     * bank fluent setter
     *
     * @param bank the new bank
     *
     * @return Bank
     */
    public Account bank(Bank bank) {

        if (this.bank != bank) {
            this.bank = bank;
            this.publish();
        }
        return this;
    }

    /**
     * type getter
     *
     * @return Type
     */
    public Type type() {

        return this.type;
    }

    /**
     * type fluent setter
     *
     * @param type the new type
     *
     * @return Type
     */
    public Account type(Type type) {

        if (this.type != type) {
            this.type = type;
            this.publish();
        }
        return this;
    }


    @Override
    public String toString() {

        return this.bank.title() + " - " + this.title();
    }

    public double balance() {

        return 0;
    }
}
