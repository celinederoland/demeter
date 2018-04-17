package accountancy.repository;

import accountancy.model.base.*;

public interface BaseRepository {

    /**
     * Retrieve all data
     */
    void findAll();

    /**
     * The list of account types managed by the repo
     *
     * @return Types
     */
    Types types();

    /**
     * The list of bank establishments managed by the repo
     *
     * @return Banks
     */
    Banks banks();

    /**
     * The list of currencies managed by the repo
     *
     * @return Currencies
     */
    Currencies currencies();

    /**
     * The list of accounts managed by the repo
     *
     * @return Accounts
     */
    Accounts accounts();

    /**
     * The list of categories managed by the repo
     * (the subcategories are accessed throw the categories)
     *
     * @return Categories
     */
    Categories categories();

    /**
     * The list of transactions managed by the repo
     *
     * @return Transactions
     */
    Transactions transactions();

    /**
     * Update a type
     *
     * @param type the modified type
     */
    void save(Type type);

    /**
     * Create a new type in database
     *
     * @param type the new type with id 0
     *
     * @return the new type with it's id set
     */
    Type create(Type type);

    /**
     * Update a currency
     *
     * @param currency the modified currency
     */
    void save(Currency currency);

    /**
     * Create a new currency in database
     *
     * @param currency the new currency with id 0
     *
     * @return the new currency with it's id set
     */
    Currency create(Currency currency);

    /**
     * Update a transaction
     *
     * @param transaction the modified transaction
     */
    void save(Transaction transaction);

    /**
     * Create a new transaction in database
     *
     * @param transaction the new transaction with id 0
     *
     * @return the new transaction with it's id set
     */
    Transaction create(Transaction transaction);

    /**
     * Update a category
     *
     * @param category the modified category
     */
    void save(Category category);

    /**
     * Create a new category in database
     *
     * @param category the new category with id 0
     *
     * @return the new category with it's id set
     */
    Category create(Category category);

    /**
     * Update a subCategory
     *
     * @param subCategory the modified subCategory
     */
    void save(SubCategory subCategory);

    /**
     * Create a new subCategory in database, will definitely belong to the category specified
     *
     * @param subCategory the new subCategory with id 0
     * @param category    the category it will belong to
     *
     * @return the new subCategory with it's id set
     */
    SubCategory create(SubCategory subCategory, Category category);

    /**
     * Update a bank
     *
     * @param bank the modified bank
     */
    void save(Bank bank);

    /**
     * Create a new bank in database
     *
     * @param bank the new bank with id 0
     *
     * @return the new bank with it's id set
     */
    Bank create(Bank bank);

    /**
     * Update an account
     *
     * @param account the modified account
     */
    void save(Account account);

    /**
     * Create a new account in database
     *
     * @param account the new account with id 0
     *
     * @return the new account with it's id set
     */
    Account create(Account account);
}
