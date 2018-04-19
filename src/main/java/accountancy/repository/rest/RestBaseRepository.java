package accountancy.repository.rest;

import accountancy.model.Entity;
import accountancy.model.Json;
import accountancy.model.base.*;
import accountancy.repository.AbstractBaseRepository;
import accountancy.repository.BaseRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Date;
import java.util.HashMap;

public class RestBaseRepository extends AbstractBaseRepository implements BaseRepository {

    private Requester requester;

    public RestBaseRepository(String url) {

        this.requester = new Requester(url);
    }

    @Override public void findAll() {

        String     response = this.requester.executeGet("/all");
        JsonParser parser   = new JsonParser();
        assert response != null;
        JsonObject json = parser.parse(response).getAsJsonObject();

        for (JsonElement type : json.get("types").getAsJsonArray()) {

            JsonObject jsonType = type.getAsJsonObject();
            int        id       = jsonType.get("id").getAsInt();
            String     title    = jsonType.get("title").getAsString();
            this.types().add(new Type(id, title));
        }

        for (JsonElement bank : json.get("banks").getAsJsonArray()) {

            JsonObject jsonBank = bank.getAsJsonObject();
            int        id       = jsonBank.get("id").getAsInt();
            String     title    = jsonBank.get("title").getAsString();
            this.banks().add(new Bank(id, title));
        }

        for (JsonElement currency : json.get("currencies").getAsJsonArray()) {

            JsonObject jsonCurrency = currency.getAsJsonObject();
            int        id           = jsonCurrency.get("id").getAsInt();
            String     title        = jsonCurrency.get("title").getAsString();
            this.currencies().add(new Currency(id, title));
        }

        for (JsonElement account : json.get("accounts").getAsJsonArray()) {

            JsonObject jsonAccount = account.getAsJsonObject();
            int        id          = jsonAccount.get("id").getAsInt();
            String     title       = jsonAccount.get("title").getAsString();
            int        currency_id = jsonAccount.get("currency").getAsJsonObject().get("id").getAsInt();
            int        bank_id     = jsonAccount.get("bank").getAsJsonObject().get("id").getAsInt();
            int        type_id     = jsonAccount.get("type").getAsJsonObject().get("id").getAsInt();
            this.accounts().add(
                new Account(
                    id, title,
                    (Currency) this.currencies().getOne(currency_id),
                    (Bank) this.banks().getOne(bank_id),
                    (Type) this.types().getOne(type_id)
                )
            );
        }

        for (JsonElement category : json.get("categories").getAsJsonArray()) {

            Category categoryObject = Json.gson().fromJson(category, Category.class);
            this.categories().add(categoryObject);
        }

        for (JsonElement transaction : json.get("transactions").getAsJsonArray()) {

            JsonObject jsonAccount    = transaction.getAsJsonObject();
            int        id             = jsonAccount.get("id").getAsInt();
            String     title          = jsonAccount.get("title").getAsString();
            Double     amount         = jsonAccount.get("amount").getAsDouble();
            Date       date           = new Date(jsonAccount.get("date").getAsInt());
            int        account_id     = jsonAccount.get("account").getAsJsonObject().get("id").getAsInt();
            int        category_id    = jsonAccount.get("category").getAsJsonObject().get("id").getAsInt();
            int        subcategory_id = jsonAccount.get("subCategory").getAsJsonObject().get("id").getAsInt();
            this.transactions().add(
                new Transaction(
                    id, title, amount, date,
                    (Account) this.accounts().getOne(account_id),
                    (Category) this.categories().getOne(category_id),
                    (SubCategory) ((Category) this.categories().getOne(category_id))
                        .subCategories()
                        .getOne(subcategory_id)
                )
            );
        }

    }

    @Override public void clean() {

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

    @Override public void save(Transaction transaction) {

    }

    @Override public Transaction create(Transaction transaction) {

        return null;
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
