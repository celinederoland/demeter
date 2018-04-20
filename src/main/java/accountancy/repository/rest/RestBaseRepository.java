package accountancy.repository.rest;

import accountancy.model.Entity;
import accountancy.model.Json;
import accountancy.model.base.*;
import accountancy.repository.AbstractBaseRepository;
import accountancy.repository.BaseRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Date;
import java.util.HashMap;

public class RestBaseRepository extends AbstractBaseRepository implements BaseRepository {

    private Requester requester;

    public RestBaseRepository(String url) {

        this.requester = new Requester(url, this);
    }

    @Override public void findAll() {

        String     response = this.requester.executeGet("/all");
        JsonParser parser   = new JsonParser();
        assert response != null;
        parseAll(response, parser);
    }

    private void parseAll(String response, JsonParser parser) {

        JsonObject json = parser.parse(response).getAsJsonObject();

        for (JsonElement type : json.get("types").getAsJsonArray()) {

            this.types().add(Json.gson(this).fromJson(type, Type.class));
        }

        for (JsonElement bank : json.get("banks").getAsJsonArray()) {

            this.banks().add(Json.gson(this).fromJson(bank, Bank.class));
        }

        for (JsonElement currency : json.get("currencies").getAsJsonArray()) {

            this.currencies().add(Json.gson(this).fromJson(currency, Currency.class));
        }

        for (JsonElement account : json.get("accounts").getAsJsonArray()) {

            this.accounts().add(Json.gson(this).fromJson(account, Account.class));
        }

        for (JsonElement category : json.get("categories").getAsJsonArray()) {

            this.categories().add(Json.gson(this).fromJson(category, Category.class));
        }

        for (JsonElement transaction : json.get("transactions").getAsJsonArray()) {

            this.transactions().add(Json.gson(this).fromJson(transaction, Transaction.class));
        }
    }

    @Override public void clean() {

        String     response = this.requester.executeDelete("/all", null);
        JsonParser parser   = new JsonParser();
        assert response != null;

        this.types().removeAll();
        this.currencies().removeAll();
        this.banks().removeAll();
        for (Entity category : this.categories().getAll()) {
            ((Category) category).subCategories().removeAll();
        }
        this.categories().removeAll();
        this.accounts().removeAll();
        this.transactions().removeAll();

        parseAll(response, parser);
    }

    @Override public Type find(Type type) {

        if (this.types().getOne(type.id()) != null) {
            return (Type) this.types().getOne(type.id());
        }

        String     response = this.requester.executeGet("/type/" + type.id());
        JsonParser parser   = new JsonParser();
        assert response != null;
        JsonObject json = parser.parse(response).getAsJsonObject();

        if (!json.isJsonNull()) {
            int    id    = json.get("id").getAsInt();
            String title = json.get("title").getAsString();
            this.types().add(new Type(id, title));
        }

        return (Type) this.types().getOne(type.id());
    }

    @Override public void save(Type type) {

        String response = this.requester.executePost("/type", type);
        assert response != null;
    }

    @Override public Type create(Type type) {

        String response = this.requester.executePut("/type", type);
        assert response != null;
        JsonParser parser  = new JsonParser();
        JsonObject json    = parser.parse(response).getAsJsonObject();
        int        id      = json.get("id").getAsInt();
        String     title   = json.get("title").getAsString();
        Type       newType = new Type(id, title);
        this.types().add(newType);
        return newType;
    }

    @Override public Currency find(Currency currency) {

        if (this.currencies().getOne(currency.id()) != null) {
            return (Currency) this.currencies().getOne(currency.id());
        }

        String     response = this.requester.executeGet("/currency/" + currency.id());
        JsonParser parser   = new JsonParser();
        assert response != null;
        JsonObject json = parser.parse(response).getAsJsonObject();

        if (!json.isJsonNull()) {
            int    id    = json.get("id").getAsInt();
            String title = json.get("title").getAsString();
            this.currencies().add(new Currency(id, title));
        }

        return (Currency) this.currencies().getOne(currency.id());
    }

    @Override public void save(Currency currency) {

        String response = this.requester.executePost("/currency", currency);
        assert response != null;
    }

    @Override public Currency create(Currency currency) {

        String response = this.requester.executePut("/currency", currency);
        assert response != null;
        JsonParser parser      = new JsonParser();
        JsonObject json        = parser.parse(response).getAsJsonObject();
        int        id          = json.get("id").getAsInt();
        String     title       = json.get("title").getAsString();
        Currency   newCurrency = new Currency(id, title);
        this.currencies().add(newCurrency);
        return newCurrency;
    }

    @Override public Transaction find(Transaction transaction) {

        if (this.transactions().getOne(transaction.id()) != null) {
            return (Transaction) this.transactions().getOne(transaction.id());
        }

        String     response = this.requester.executeGet("/transaction/" + transaction.id());
        JsonParser parser   = new JsonParser();
        assert response != null;
        JsonObject json = parser.parse(response).getAsJsonObject();

        if (!json.isJsonNull()) {

            int    id     = json.get("id").getAsInt();
            String title  = json.get("title").getAsString();
            Double amount = json.get("amount").getAsDouble();
            Date   date   = new Date(json.get("date").getAsInt());

            JsonObject jsonAccount = json.get("account").getAsJsonObject();

            int    account_id    = jsonAccount.get("id").getAsInt();
            String account_title = jsonAccount.get("title").getAsString();

            int    bank_id    = jsonAccount.get("bank").getAsJsonObject().get("id").getAsInt();
            String bank_title = jsonAccount.get("bank").getAsJsonObject().get("title").getAsString();
            Bank   bank       = (Bank) this.banks().add(new Bank(bank_id, bank_title));

            int    type_id    = jsonAccount.get("type").getAsJsonObject().get("id").getAsInt();
            String type_title = jsonAccount.get("type").getAsJsonObject().get("title").getAsString();
            Type   type       = (Type) this.types().add(new Type(type_id, type_title));

            int      currency_id    = jsonAccount.get("currency").getAsJsonObject().get("id").getAsInt();
            String   currency_title = jsonAccount.get("currency").getAsJsonObject().get("title").getAsString();
            Currency currency       = (Currency) this.currencies().add(new Currency(currency_id, currency_title));

            Account account = (Account) this.accounts().add(
                new Account(account_id, account_title, currency, bank, type));


            JsonObject jsonCategory   = json.get("category").getAsJsonObject();
            int        category_id    = jsonCategory.get("id").getAsInt();
            String     category_title = jsonCategory.get("title").getAsString();

            Category category = (Category) this.categories().add(new Category(category_id, category_title));

            JsonArray subCategories = jsonCategory.get("subCategories").getAsJsonArray();
            for (JsonElement subCategory : subCategories) {
                int    subId    = subCategory.getAsJsonObject().get("id").getAsInt();
                String subTitle = subCategory.getAsJsonObject().get("title").getAsString();
                category.subCategories().add(new SubCategory(subId, subTitle));
            }

            int         subcategory_id = json.get("subCategory").getAsJsonObject().get("id").getAsInt();
            SubCategory subCategory    = (SubCategory) category.subCategories().getOne(subcategory_id);

            this.transactions().add(
                new Transaction(
                    id, title, amount, date,
                    account,
                    category,
                    subCategory
                )
            );
        }

        return (Transaction) this.transactions().getOne(transaction.id());
    }

    @Override public void save(Transaction transaction) {

        String response = this.requester.executePost("/transaction", transaction);
        assert response != null;
    }

    @Override public Transaction create(Transaction transaction) {

        String response = this.requester.executePut("/transaction", transaction);
        assert response != null;
        JsonParser parser         = new JsonParser();
        JsonObject json           = parser.parse(response).getAsJsonObject();
        int        id             = json.get("id").getAsInt();
        String     title          = json.get("title").getAsString();
        Double     amount         = json.get("amount").getAsDouble();
        Date       date           = new Date(json.get("date").getAsInt());
        int        account_id     = json.get("account").getAsJsonObject().get("id").getAsInt();
        int        category_id    = json.get("category").getAsJsonObject().get("id").getAsInt();
        int        subcategory_id = json.get("subCategory").getAsJsonObject().get("id").getAsInt();

        Transaction newTransaction = new Transaction(
            id, title, amount, date,
            (Account) this.accounts().getOne(account_id),
            (Category) this.categories().getOne(category_id),
            (SubCategory) ((Category) this.categories().getOne(category_id))
                .subCategories()
                .getOne(subcategory_id)
        );

        this.transactions().add(newTransaction);
        return newTransaction;
    }

    @Override public Category find(Category category) {

        if (this.categories().getOne(category.id()) != null) {
            return (Category) this.categories().getOne(category.id());
        }

        String     response = this.requester.executeGet("/category/" + category.id());
        JsonParser parser   = new JsonParser();
        assert response != null;
        JsonObject json = parser.parse(response).getAsJsonObject();

        if (!json.isJsonNull()) {

            int    id    = json.get("id").getAsInt();
            String title = json.get("title").getAsString();

            Category found = (Category) this.categories().add(new Category(id, title));

            JsonArray subCategories = json.get("subCategories").getAsJsonArray();
            for (JsonElement subCategory : subCategories) {
                int    subId    = subCategory.getAsJsonObject().get("id").getAsInt();
                String subTitle = subCategory.getAsJsonObject().get("title").getAsString();
                found.subCategories().add(new SubCategory(subId, subTitle));
            }
        }

        return (Category) this.categories().getOne(category.id());
    }

    @Override public void save(Category category) {

        String response = this.requester.executePost("/category", category);
        assert response != null;
    }

    @Override public Category create(Category category) {

        String response = this.requester.executePut("/category", category);
        assert response != null;
        JsonParser parser      = new JsonParser();
        JsonObject json        = parser.parse(response).getAsJsonObject();
        int        id          = json.get("id").getAsInt();
        String     title       = json.get("title").getAsString();
        Category   newCategory = new Category(id, title);
        this.categories().add(newCategory);
        return newCategory;
    }

    @Override public SubCategory find(SubCategory subCategory) {

        String     response = this.requester.executeGet("/subcategory/" + subCategory.id());
        JsonParser parser   = new JsonParser();
        assert response != null;

        JsonObject json = parser.parse(response).getAsJsonObject();

        if (!json.isJsonNull()) {

            JsonObject jsonCategory    = json.get("category").getAsJsonObject();
            JsonObject jsonSubCategory = json.get("subcategory").getAsJsonObject();

            int    id    = jsonCategory.get("id").getAsInt();
            String title = jsonCategory.get("title").getAsString();

            Category category = (Category) this.categories().add(new Category(id, title));

            int    subId    = jsonSubCategory.getAsJsonObject().get("id").getAsInt();
            String subTitle = jsonSubCategory.getAsJsonObject().get("title").getAsString();

            return (SubCategory) category.subCategories().add(new SubCategory(subId, subTitle));
        }

        return null;
    }

    @Override public void save(SubCategory subCategory) {

        String response = this.requester.executePost("/subcategory", subCategory);
        assert response != null;
    }

    @Override public SubCategory create(SubCategory subCategory, Category category) {

        HashMap<String, Entity> request = new HashMap<>();
        request.put("subcategory", subCategory);
        request.put("category", category);
        String response = this.requester.executePut("/subcategory", request);
        assert response != null;
        JsonParser  parser         = new JsonParser();
        JsonObject  json           = parser.parse(response).getAsJsonObject();
        int         id             = json.get("id").getAsInt();
        String      title          = json.get("title").getAsString();
        SubCategory newSubCategory = new SubCategory(id, title);
        category.subCategories().add(newSubCategory);
        return newSubCategory;
    }

    @Override public Bank find(Bank bank) {

        if (this.banks().getOne(bank.id()) != null) {
            return (Bank) this.banks().getOne(bank.id());
        }

        String     response = this.requester.executeGet("/bank/" + bank.id());
        JsonParser parser   = new JsonParser();
        assert response != null;
        JsonObject json = parser.parse(response).getAsJsonObject();

        if (!json.isJsonNull()) {
            int    id    = json.get("id").getAsInt();
            String title = json.get("title").getAsString();
            this.banks().add(new Bank(id, title));
        }

        return (Bank) this.banks().getOne(bank.id());
    }

    @Override public void save(Bank bank) {

        String response = this.requester.executePost("/bank", bank);
        assert response != null;
    }

    @Override public Bank create(Bank bank) {

        String response = this.requester.executePut("/bank", bank);
        assert response != null;
        JsonParser parser  = new JsonParser();
        JsonObject json    = parser.parse(response).getAsJsonObject();
        int        id      = json.get("id").getAsInt();
        String     title   = json.get("title").getAsString();
        Bank       newBank = new Bank(id, title);
        this.banks().add(newBank);
        return newBank;
    }

    @Override public Account find(Account account) {

        if (this.accounts().getOne(account.id()) != null) {
            return (Account) this.accounts().getOne(account.id());
        }

        String     response = this.requester.executeGet("/account/" + account.id());
        JsonParser parser   = new JsonParser();
        assert response != null;
        JsonObject json = parser.parse(response).getAsJsonObject();

        if (!json.isJsonNull()) {

            int    account_id    = json.get("id").getAsInt();
            String account_title = json.get("title").getAsString();

            int    bank_id    = json.get("bank").getAsJsonObject().get("id").getAsInt();
            String bank_title = json.get("bank").getAsJsonObject().get("title").getAsString();
            Bank   bank       = (Bank) this.banks().add(new Bank(bank_id, bank_title));

            int    type_id    = json.get("type").getAsJsonObject().get("id").getAsInt();
            String type_title = json.get("type").getAsJsonObject().get("title").getAsString();
            Type   type       = (Type) this.types().add(new Type(type_id, type_title));

            int      currency_id    = json.get("currency").getAsJsonObject().get("id").getAsInt();
            String   currency_title = json.get("currency").getAsJsonObject().get("title").getAsString();
            Currency currency       = (Currency) this.currencies().add(new Currency(currency_id, currency_title));

            this.accounts().add(new Account(account_id, account_title, currency, bank, type));
        }

        return (Account) this.accounts().getOne(account.id());
    }

    @Override public void save(Account account) {

        String response = this.requester.executePost("/account", account);
        assert response != null;
    }

    @Override public Account create(Account account) {

        String response = this.requester.executePut("/account", account);
        assert response != null;
        JsonParser parser      = new JsonParser();
        JsonObject json        = parser.parse(response).getAsJsonObject();
        int        id          = json.get("id").getAsInt();
        String     title       = json.get("title").getAsString();
        int        currency_id = json.get("currency").getAsJsonObject().get("id").getAsInt();
        int        bank_id     = json.get("bank").getAsJsonObject().get("id").getAsInt();
        int        type_id     = json.get("type").getAsJsonObject().get("id").getAsInt();

        Account newAccount = new Account(
            id, title,
            (Currency) this.currencies().getOne(currency_id),
            (Bank) this.banks().getOne(bank_id),
            (Type) this.types().getOne(type_id)
        );

        this.accounts().add(newAccount);
        return newAccount;
    }
}
