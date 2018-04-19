package accountancy.repository.rest;

import accountancy.model.base.*;
import accountancy.repository.AbstractBaseRepository;
import accountancy.repository.BaseRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Date;

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
            int        bank_id     = jsonAccount.get("currency").getAsJsonObject().get("id").getAsInt();
            int        type_id     = jsonAccount.get("currency").getAsJsonObject().get("id").getAsInt();
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

            JsonObject jsonCategory = category.getAsJsonObject();
            int        id           = jsonCategory.get("id").getAsInt();
            String     title        = jsonCategory.get("title").getAsString();

            Category categoryObject = new Category(id, title);
            for (JsonElement subCategory : jsonCategory.get("subCategories").getAsJsonArray()) {

                JsonObject jsonSubCategory  = subCategory.getAsJsonObject();
                int        subCategoryId    = jsonSubCategory.get("id").getAsInt();
                String     subCategoryTitle = jsonSubCategory.get("title").getAsString();
                categoryObject.subCategories().add(new SubCategory(subCategoryId, subCategoryTitle));
            }
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

    }

    @Override public Currency create(Currency currency) {

        return null;
    }

    @Override public void save(Transaction transaction) {

    }

    @Override public Transaction create(Transaction transaction) {

        return null;
    }

    @Override public void save(Category category) {

    }

    @Override public Category create(Category category) {

        return null;
    }

    @Override public void save(SubCategory subCategory) {

    }

    @Override public SubCategory create(SubCategory subCategory, Category category) {

        return null;
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

    }

    @Override public Account create(Account account) {

        return null;
    }
}
