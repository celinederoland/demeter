package accountancy.repository;

import accountancy.model.base.*;

public interface BaseRepository {

    /**
     * Retrieve all data
     */
    void findAll();

    /**
     * Clean unused data
     */
    void clean();

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
     * Find one instance of Type
     *
     * @param type the type searched (by id)
     *
     * @return type the type found
     */
    Type find(Type type);

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
     * Find one instance of Currency
     *
     * @param currency the currency searched (by id)
     *
     * @return currency the currency found
     */
    Currency find(Currency currency);

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
     * Find one instance of Transaction
     *
     * @param transaction the transaction searched (by id)
     *
     * @return transaction the transaction found
     */
    Transaction find(Transaction transaction);

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
     * Find one instance of Category
     *
     * @param category the category searched (by id)
     *
     * @return category the category found
     */
    Category find(Category category);

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
     * Find one instance of SubCategory
     *
     * @param subCategory the subCategory searched (by id)
     *
     * @return subCategory the subCategory found
     */
    SubCategory find(SubCategory subCategory);

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
     * Find one instance of Bank
     *
     * @param bank the bank searched (by id)
     *
     * @return bank the bank found
     */
    Bank find(Bank bank);

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
     * Find one instance of Account
     *
     * @param account the account searched (by id)
     *
     * @return account the account found
     */
    Account find(Account account);

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
