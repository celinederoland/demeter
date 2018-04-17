package accountancy.model.selection;

import accountancy.model.Entity;
import accountancy.model.base.*;

import java.util.ArrayList;

public class Criteria {

    private boolean positive   = false;
    private boolean negative   = false;
    private boolean absolute   = false;
    private boolean cumulative = false;

    private final ArrayList<Entity> categories           = new ArrayList<>();
    private final ArrayList<Entity> excludeCategories    = new ArrayList<>();
    private final ArrayList<Entity> subCategories        = new ArrayList<>();
    private final ArrayList<Entity> excludeSubCategories = new ArrayList<>();
    private final ArrayList<Entity> accounts             = new ArrayList<>();
    private final ArrayList<Entity> excludeAccounts      = new ArrayList<>();
    private final ArrayList<Entity> types                = new ArrayList<>();
    private final ArrayList<Entity> excludeTypes         = new ArrayList<>();
    private final ArrayList<Entity> banks                = new ArrayList<>();
    private final ArrayList<Entity> excludeBanks         = new ArrayList<>();
    private final ArrayList<Entity> currencies           = new ArrayList<>();
    private final ArrayList<Entity> excludeCurrencies    = new ArrayList<>();


    /**
     * Criteria getter : the category of the transaction must be one of the added category
     * let empty to accept all categories
     *
     * @return ArrayList<Category>
     */
    public ArrayList<Entity> categories() {

        return this.categories;
    }

    /**
     * Criteria getter : the category of the transaction must not be one of the excluded category
     *
     * @return ArrayList<Category>
     */
    public ArrayList<Entity> excludeCategories() {

        return this.excludeCategories;
    }

    /**
     * Criteria getter : the subCategory of the transaction must be one of the added subCategory
     * let empty to accept all subCategories
     *
     * @return ArrayList<SubCategory>
     */
    public ArrayList<Entity> subCategories() {

        return this.subCategories;
    }

    /**
     * Criteria getter : the subCategory of the transaction must not be one of the excluded subCategory
     *
     * @return ArrayList<SubCategory>
     */
    public ArrayList<Entity> excludeSubCategories() {

        return this.excludeSubCategories;
    }

    /**
     * Criteria getter : the account of the transaction must be one of the added account
     * let empty to accept all accounts
     *
     * @return ArrayList<Account>
     */
    public ArrayList<Entity> accounts() {

        return this.accounts;
    }

    /**
     * Criteria getter : the account of the transaction must not be one of the excluded accounts
     *
     * @return ArrayList<Account>
     */
    public ArrayList<Entity> excludeAccounts() {

        return this.excludeAccounts;
    }

    /**
     * Criteria getter : the account of the transaction must have one of the added types
     * let empty to accept all types
     *
     * @return ArrayList<Type>
     */
    public ArrayList<Entity> types() {

        return this.types;
    }


    /**
     * Criteria getter : the account of the transaction must not be one of the excluded types
     *
     * @return ArrayList<Type>
     */
    public ArrayList<Entity> excludeTypes() {

        return this.excludeTypes;
    }

    /**
     * Criteria getter : the account of the transaction must have one of the added banks
     * let empty to accept all banks
     *
     * @return ArrayList<Bank>
     */
    public ArrayList<Entity> banks() {

        return this.banks;
    }

    /**
     * Criteria getter : the account of the transaction must not be one of the excluded banks
     *
     * @return ArrayList<Bank>
     */
    public ArrayList<Entity> excludeBanks() {

        return this.excludeBanks;
    }

    /**
     * Criteria getter : the account of the transaction must be on one of the added currencies
     * let empty to accept all currencies
     *
     * @return ArrayList<Currency>
     */
    public ArrayList<Entity> currencies() {

        return this.currencies;
    }

    /**
     * Criteria getter : the account of the transaction must not be on one of the excluded currencies
     *
     * @return ArrayList<Currency>
     */
    public ArrayList<Entity> excludeCurrencies() {

        return this.excludeCurrencies;
    }

    /**
     * Criteria getter : the transaction amount must be positive
     *
     * @return boolean
     */
    public boolean positive() {

        return this.positive;
    }

    /**
     * Criteria getter : the transaction amount must be negative
     *
     * @return boolean
     */
    public boolean negative() {

        return this.negative;
    }

    /**
     * Criteria getter : when doing the amounts sum, convert all amounts to positive value
     * let false to respect amount sign
     *
     * @return boolean
     */
    public boolean absolute() {

        return this.absolute;
    }


    /**
     * Criteria getter : when doing the amounts sum between 2 dates, add it to the previous sum
     * let false to have basic sum
     *
     * @return Criteria
     */
    public boolean cumulative() {

        return this.cumulative;
    }

    /**
     * Criteria fluent setter : the category of the transaction must be one of the added category
     * let empty to accept all categories
     *
     * @param category the category to add
     *
     * @return Criteria
     */
    public Criteria addCategory(Category category) {

        this.categories.add(category);
        return this;
    }

    /**
     * Criteria fluent setter : the category of the transaction must not be one of the excluded category
     *
     * @param category the category to exclude
     *
     * @return Criteria
     */
    public Criteria excludeCategory(Category category) {

        this.excludeCategories.add(category);
        return this;
    }

    /**
     * Criteria fluent setter : the subCategory of the transaction must be one of the added subCategory
     * let empty to accept all subCategories
     *
     * @param subCategory the subCategory to add
     *
     * @return Criteria
     */
    public Criteria addSubCategory(SubCategory subCategory) {

        this.subCategories.add(subCategory);
        return this;
    }

    /**
     * Criteria fluent setter : the subCategory of the transaction must not be one of the excluded subCategory
     *
     * @param subCategory the subCategory to exclude
     *
     * @return Criteria
     */
    public Criteria excludeSubCategory(SubCategory subCategory) {

        this.excludeSubCategories.add(subCategory);
        return this;
    }

    /**
     * Criteria fluent setter : the account of the transaction must be one of the added account
     * let empty to accept all accounts
     *
     * @param account the account to add
     *
     * @return Criteria
     */
    public Criteria addAccount(Account account) {

        this.accounts.add(account);
        return this;
    }

    /**
     * Criteria fluent setter : the account of the transaction must not be one of the excluded accounts
     *
     * @param account the account to exclude
     *
     * @return Criteria
     */
    public Criteria excludeAccount(Account account) {

        this.excludeAccounts.add(account);
        return this;
    }

    /**
     * Criteria fluent setter : the account of the transaction must have one of the added types
     * let empty to accept all types
     *
     * @param type the type to add
     *
     * @return Criteria
     */
    public Criteria addType(Type type) {

        this.types.add(type);
        return this;
    }

    /**
     * Criteria fluent setter : the account of the transaction must not be one of the excluded types
     *
     * @param type the type to exclude
     *
     * @return Criteria
     */
    public Criteria excludeType(Type type) {

        this.excludeTypes.add(type);
        return this;
    }

    /**
     * Criteria fluent setter : the account of the transaction must have one of the added banks
     * let empty to accept all banks
     *
     * @param bank the bank to add
     *
     * @return Criteria
     */
    public Criteria addBank(Bank bank) {

        this.banks.add(bank);
        return this;
    }

    /**
     * Criteria fluent setter : the account of the transaction must not be one of the excluded banks
     *
     * @param bank the bank to exclude
     *
     * @return Criteria
     */
    public Criteria excludeBank(Bank bank) {

        this.excludeBanks.add(bank);
        return this;
    }

    /**
     * Criteria fluent setter : the account of the transaction must be on one of the added currencies
     * let empty to accept all currencies
     *
     * @param currency the currency to add
     *
     * @return Criteria
     */
    public Criteria addCurrency(Currency currency) {

        this.currencies.add(currency);
        return this;
    }

    /**
     * Criteria fluent setter : the account of the transaction must not be on one of the excluded currencies
     *
     * @param currency the currency to exclude
     *
     * @return Criteria
     */
    public Criteria excludeCurrency(Currency currency) {

        this.excludeCurrencies.add(currency);
        return this;
    }

    /**
     * Criteria fluent setter : the transaction amount must be positive
     *
     * @return Criteria
     */
    public Criteria setPositiveOnly() {

        this.positive = true;
        this.negative = false;
        return this;
    }

    /**
     * Criteria fluent setter : the transaction amount must be negative
     *
     * @return Criteria
     */
    public Criteria setNegativeOnly() {

        this.negative = true;
        this.positive = false;
        return this;
    }

    /**
     * Criteria fluent setter : when doing the amounts sum, convert all amounts to positive value
     * let false to respect amount sign
     *
     * @return Criteria
     */
    public Criteria setAbsolute() {

        this.absolute = true;
        return this;
    }

    /**
     * Criteria fluent setter : when doing the amounts sum between 2 dates, add it to the previous sum
     * let empty to have basic sum
     *
     * @return Criteria
     */
    public Criteria setCumulative() {

        this.cumulative = true;
        return this;
    }
}
