package accountancy.repository.sql;

import accountancy.model.base.*;
import accountancy.repository.BaseRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Request the database to CRUD the model
 */
public final class SqlBaseRepository extends Repository implements BaseRepository {

    private Accounts     accounts;
    private Banks        banks;
    private Currencies   currencies;
    private Categories   categories;
    private Types        types;
    private Transactions transactions;

    public SqlBaseRepository(ConnectionProvider connectionProvider) {

        super(connectionProvider);
    }

    @Override public void findAll() {

        Statement statement;
        ResultSet resultSet;
        try {

            statement = this.connectionProvider.getConnection().createStatement();


            resultSet = statement.executeQuery("SELECT * FROM types");
            while (resultSet.next()) {
                int    id    = resultSet.getInt("id");
                String title = resultSet.getString("title");
                this.types().add(new Type(id, title));
            }

            resultSet = statement.executeQuery("SELECT * FROM banks");
            while (resultSet.next()) {
                int    id    = resultSet.getInt("id");
                String title = resultSet.getString("title");
                this.banks().add(new Bank(id, title));
            }

            resultSet = statement.executeQuery("SELECT * FROM currencies");
            while (resultSet.next()) {
                int    id    = resultSet.getInt("id");
                String title = resultSet.getString("title");
                this.currencies().add(new Currency(id, title));
            }

            resultSet = statement.executeQuery("SELECT * FROM accounts");
            while (resultSet.next()) {
                int    id    = resultSet.getInt("id");
                String title = resultSet.getString("title");

                int bankId     = resultSet.getInt("bank_id");
                int currencyId = resultSet.getInt("currency_id");
                int typeId     = resultSet.getInt("type_id");
                this.accounts().add(new Account(
                    id, title,
                    (Currency) this.currencies().getOne(currencyId),
                    (Bank) this.banks().getOne(bankId),
                    (Type) this.types().getOne(typeId)
                ));
            }

            resultSet = statement.executeQuery(
                "SELECT categories.*, " +
                "subcategories.id AS subId, subcategories.title AS subTitle " +
                "FROM categories " +
                "LEFT JOIN subcategories ON subcategories.category_id = categories.id");

            while (resultSet.next()) {

                int      categoryId    = resultSet.getInt("id");
                String   categoryTitle = resultSet.getString("title");
                Category category      = (Category) this.categories().add(new Category(categoryId, categoryTitle));

                int subCategoryId = resultSet.getInt("subId");
                if (subCategoryId > 0) {
                    String subCategoryTitle = resultSet.getString("subTitle");
                    category.subCategories().add(new SubCategory(subCategoryId, subCategoryTitle));
                }
            }

            resultSet = statement.executeQuery(
                "SELECT entries.*, categories.id AS categoryId FROM entries " +
                "LEFT JOIN subcategories ON subcategories.id = entries.sub_category_id " +
                "LEFT JOIN categories ON categories.id = subcategories.category_id");

            while (resultSet.next()) {

                int    id            = resultSet.getInt("id");
                String title         = resultSet.getString("title");
                Double amount        = resultSet.getDouble("amount");
                Date   date          = resultSet.getDate("date");
                int    accountId     = resultSet.getInt("account_id");
                int    subCategoryId = resultSet.getInt("sub_category_id");
                int    categoryId    = resultSet.getInt("categoryId");

                this.transactions().add(new Transaction(
                    id, title, amount, date,
                    (Account) this.accounts().getOne(accountId),
                    (Category) this.categories().getOne(categoryId),
                    (SubCategory) ((Category) this.categories().getOne(categoryId)).subCategories()
                                                                                   .getOne(subCategoryId)
                ));
            }

            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

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

    @Override public void save(Type type) {

        PreparedStatement statement;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "UPDATE types SET title = ? WHERE id = ?"
            );

            statement.setString(1, type.title());
            statement.setInt(2, type.id());

            statement.executeUpdate();

            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override public Type create(Type type) {

        PreparedStatement statement;
        ResultSet         resultSet;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "INSERT INTO types VALUES (NULL, ?)",
                RETURN_GENERATED_KEYS
            );

            statement.setString(1, type.title());

            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            int id = -1;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
                type.id(id);
            }

            type = (Type) this.types().add(new Type(id, type.title()));

            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return type;
    }

    @Override public void save(Currency currency) {

        PreparedStatement statement;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "UPDATE currencies SET title = ? WHERE id = ?"
            );

            statement.setString(1, currency.title());
            statement.setInt(2, currency.id());

            statement.executeUpdate();

            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override public Currency create(Currency currency) {

        PreparedStatement statement;
        ResultSet         resultSet;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "INSERT INTO currencies VALUES (NULL, ?)",
                RETURN_GENERATED_KEYS
            );

            statement.setString(1, currency.title());

            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            int id = -1;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
                currency.id(id);
            }

            currency = (Currency) this.currencies().add(new Currency(id, currency.title()));

            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return currency;
    }

    @Override public void save(Transaction transaction) {

        PreparedStatement statement;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "UPDATE entries SET " +
                "title = ?, amount = ?, date = ?, account_id = ?, sub_category_id = ? " +
                "WHERE id = ?"
            );

            statement.setString(1, transaction.title());
            statement.setDouble(2, transaction.amount());
            statement.setDate(3, new java.sql.Date(transaction.date().getTime() + 3600 * 3 * 1000));
            statement.setInt(4, transaction.account().id());
            statement.setInt(5, transaction.subCategory().id());
            statement.setInt(6, transaction.id());

            statement.executeUpdate();

            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override public Transaction create(Transaction transaction) {

        PreparedStatement statement;
        ResultSet         resultSet;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "INSERT INTO entries VALUES (NULL, ?, ?, ?, ?, ?)",
                RETURN_GENERATED_KEYS
            );

            statement.setString(1, transaction.title());
            statement.setDouble(2, transaction.amount());
            statement.setDate(3, new java.sql.Date(transaction.date().getTime()));
            statement.setInt(4, transaction.subCategory().id());
            statement.setInt(5, transaction.account().id());

            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            int id = -1;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
                transaction.id(id);
            }

            transaction = (Transaction) this.transactions().add(new Transaction(
                id, transaction.title(), transaction.amount(), transaction.date(),
                transaction.account(), transaction.category(), transaction.subCategory()
            ));

            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return transaction;
    }

    @Override public void save(Category category) {

        PreparedStatement statement;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "UPDATE categories SET title = ? WHERE id = ?"
            );

            statement.setString(1, category.title());
            statement.setInt(2, category.id());

            statement.executeUpdate();

            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override public Category create(Category category) {

        PreparedStatement statement;
        ResultSet         resultSet;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "INSERT INTO categories VALUES (NULL, ?)",
                RETURN_GENERATED_KEYS
            );

            statement.setString(1, category.title());

            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            int id = -1;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
                category.id(id);
            }

            category = (Category) this.categories().add(new Category(id, category.title()));

            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return category;
    }

    @Override public void save(SubCategory subCategory) {

        PreparedStatement statement;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "UPDATE subcategories SET title = ? WHERE id = ?"
            );

            statement.setString(1, subCategory.title());
            statement.setInt(2, subCategory.id());

            statement.executeUpdate();

            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override public SubCategory create(SubCategory subCategory, Category category) {

        PreparedStatement statement;
        ResultSet         resultSet;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "INSERT INTO subcategories VALUES (NULL, ?, ?)",
                RETURN_GENERATED_KEYS
            );

            statement.setString(1, subCategory.title());
            statement.setInt(2, category.id());

            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            int id = -1;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
                subCategory.id(id);
            }

            subCategory = (SubCategory) category.subCategories().add(new SubCategory(id, subCategory.title()));

            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return subCategory;
    }

    @Override public void save(Bank bank) {

        PreparedStatement statement;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "UPDATE banks SET title = ? WHERE id = ?"
            );

            statement.setString(1, bank.title());
            statement.setInt(2, bank.id());

            statement.executeUpdate();

            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override public Bank create(Bank bank) {

        PreparedStatement statement;
        ResultSet         resultSet;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "INSERT INTO banks VALUES (NULL, ?)",
                RETURN_GENERATED_KEYS
            );

            statement.setString(1, bank.title());

            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            int id = -1;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
                bank.id(id);
            }

            bank = (Bank) this.banks().add(new Bank(id, bank.title()));

            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return bank;
    }

    @Override public void save(Account account) {

        PreparedStatement statement;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "UPDATE accounts SET title = ?, currency_id = ?, bank_id = ?, type_id = ? WHERE id = ?"
            );

            statement.setString(1, account.title());
            statement.setInt(2, account.currency().id());
            statement.setInt(3, account.bank().id());
            statement.setInt(4, account.type().id());
            statement.setInt(5, account.id());

            statement.executeUpdate();

            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override public Account create(Account account) {

        PreparedStatement statement;
        ResultSet         resultSet;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "INSERT INTO accounts VALUES (NULL, ?, ?, ?, ?)",
                RETURN_GENERATED_KEYS
            );

            statement.setString(1, account.title());
            statement.setInt(2, account.bank().id());
            statement.setInt(3, account.currency().id());
            statement.setInt(4, account.type().id());

            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            int id = -1;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
                account.id(id);
            }

            account = (Account) this.accounts().add(new Account(
                id, account.title(),
                account.currency(), account.bank(), account.type()
            ));

            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return account;
    }
}