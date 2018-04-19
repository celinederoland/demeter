package accountancy.repository;

import accountancy.model.base.*;

public abstract class AbstractBaseRepository implements BaseRepository {

    private Accounts     accounts;
    private Banks        banks;
    private Currencies   currencies;
    private Categories   categories;
    private Types        types;
    private Transactions transactions;

    @Override public Types types() {

        if (this.types == null) {
            this.types = new Types();
        }
        return this.types;
    }

    @Override public Banks banks() {

        if (this.banks == null) {
            this.banks = new Banks();
        }
        return this.banks;
    }

    @Override public Currencies currencies() {

        if (this.currencies == null) {
            this.currencies = new Currencies();
        }
        return this.currencies;
    }

    @Override public Accounts accounts() {

        if (this.accounts == null) {
            this.accounts = new Accounts();
        }
        return this.accounts;
    }

    @Override public Categories categories() {

        if (this.categories == null) {
            this.categories = new Categories();
        }
        return this.categories;
    }

    @Override public Transactions transactions() {

        if (this.transactions == null) {
            this.transactions = new Transactions();
        }
        return this.transactions;
    }
}
