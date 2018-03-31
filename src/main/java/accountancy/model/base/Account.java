package accountancy.model.base;

import accountancy.model.ObservableEntity;

public class Account extends ObservableEntity {

    private Currency currency;
    private Bank     bank;
    private Type     type;

    public Account(int id, String title, Currency currency, Bank bank, Type type) {

        super(id,title);
        this.currency = currency;
        this.bank = bank;
        this.type = type;
    }

    public Currency currency() {

        return this.currency;
    }

    public Currency currency(Currency currency) {

        if (this.currency != currency) {
            this.currency = currency;
            this.publish();
        }
        return currency;
    }

    public Bank bank() {

        return this.bank;
    }

    public Bank bank(Bank bank) {

        if (this.bank != bank) {
            this.bank = bank;
            this.publish();
        }
        return bank;
    }

    public Type type() {

        return this.type;
    }

    public Type type(Type type) {

        if (this.type != type) {
            this.type = type;
            this.publish();
        }
        return type;
    }


    @Override
    public String toString() {

        return this.bank.title() + " - " + this.title();
    }
}
