package accountancy.model;

import accountancy.model.base.*;
import accountancy.model.selection.Criteria;
import accountancy.repository.BaseRepository;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

public class Json {

    public static Gson gson(BaseRepository repository) {

        return (new GsonBuilder())
            .registerTypeAdapter(Date.class, new DateSerializeAsTimestamp())
            .registerTypeAdapter(Transaction.class, new TransactionSerialize())
            .registerTypeAdapter(Transaction.class, new TransactionDeserialize(repository))
            .registerTypeAdapter(Account.class, new AccountDeserialize(repository))
            .registerTypeAdapter(Category.class, new CategorySerialize())
            .registerTypeAdapter(Category.class, new CategoryDeserialize())
            .registerTypeAdapter(Criteria.class, new CriteriaDeserialize(repository))
            .create();
    }

    private static class DateSerializeAsTimestamp implements JsonSerializer<Date> {

        @Override public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {

            return new JsonPrimitive(src.getTime() / 1000);
        }
    }

    private static class AccountDeserialize implements JsonDeserializer<Account> {

        private final BaseRepository repository;

        public AccountDeserialize(BaseRepository repository) {

            this.repository = repository;
        }

        @Override public Account deserialize(
            JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext
        ) throws JsonParseException {

            JsonObject jsonAccount = jsonElement.getAsJsonObject();
            int        id          = jsonAccount.get("id").getAsInt();
            String     title       = jsonAccount.get("title").getAsString();
            int        currency_id = jsonAccount.get("currency").getAsJsonObject().get("id").getAsInt();
            int        bank_id     = jsonAccount.get("bank").getAsJsonObject().get("id").getAsInt();
            int        type_id     = jsonAccount.get("type").getAsJsonObject().get("id").getAsInt();
            return new Account(
                id, title,
                repository.find(new Currency(currency_id)),
                repository.find(new Bank(bank_id)),
                repository.find(new accountancy.model.base.Type(type_id))
            );
        }
    }

    private static class TransactionSerialize implements JsonSerializer<Transaction> {

        @Override public JsonElement serialize(
            Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext
        ) {

            JsonObject serialized = (new Gson()).toJsonTree(transaction).getAsJsonObject();
            serialized.remove("date");
            serialized.add("date", new JsonPrimitive(transaction.date().getTime() / 1000));
            serialized.remove("category");
            serialized.add(
                "category",
                (new CategorySerialize()).serialize(transaction.category(), type, jsonSerializationContext)
            );
            return serialized;
        }
    }

    private static class TransactionDeserialize implements JsonDeserializer<Transaction> {

        private final BaseRepository repository;

        public TransactionDeserialize(BaseRepository repository) {

            this.repository = repository;
        }

        @Override public Transaction deserialize(
            JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext
        ) throws JsonParseException {

            JsonObject json = jsonElement.getAsJsonObject();
            int        id;
            if (json.has("id")) id = json.get("id").getAsInt();
            else id = 0;
            String title          = json.get("title").getAsString();
            Double amount         = json.get("amount").getAsDouble();
            Date   date           = new Date(json.get("date").getAsInt() * 1000);
            int    account_id     = json.get("account").getAsJsonObject().get("id").getAsInt();
            int    category_id    = json.get("category").getAsJsonObject().get("id").getAsInt();
            int    subcategory_id = json.get("subCategory").getAsJsonObject().get("id").getAsInt();

            return new Transaction(
                id, title, amount, date,
                repository.find(new Account(account_id)),
                repository.find(new Category(category_id)),
                repository.find(new SubCategory(subcategory_id))
            );

        }
    }

    private static class CategorySerialize implements JsonSerializer<Category> {

        @Override public JsonElement serialize(
            Category category, Type type, JsonSerializationContext jsonSerializationContext
        ) {

            JsonObject serialized = (new Gson()).toJsonTree(category).getAsJsonObject();
            serialized.remove("subCategories");
            JsonArray subCategories = (new Gson()).toJsonTree(category.subCategories().getAll()).getAsJsonArray();
            serialized.add("subCategories", subCategories);

            return serialized;
        }
    }

    private static class CategoryDeserialize implements JsonDeserializer<Category> {

        @Override public Category deserialize(
            JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext
        ) throws JsonParseException {

            JsonObject jsonCategory = jsonElement.getAsJsonObject();
            int        id           = jsonCategory.get("id").getAsInt();
            String     title        = jsonCategory.get("title").getAsString();

            Category categoryObject = new Category(id, title);
            for (JsonElement subCategory : jsonCategory.get("subCategories").getAsJsonArray()) {

                JsonObject jsonSubCategory  = subCategory.getAsJsonObject();
                int        subCategoryId    = jsonSubCategory.get("id").getAsInt();
                String     subCategoryTitle = jsonSubCategory.get("title").getAsString();
                categoryObject.subCategories().add(new SubCategory(subCategoryId, subCategoryTitle));
            }

            return categoryObject;
        }
    }


    private static class CriteriaDeserialize implements JsonDeserializer<Criteria> {

        private final BaseRepository repository;

        public CriteriaDeserialize(BaseRepository repository) {

            this.repository = repository;
        }

        @Override public Criteria deserialize(
            JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext
        ) throws JsonParseException {

            Criteria   criteria     = new Criteria();
            JsonObject jsonCriteria = jsonElement.getAsJsonObject();

            JsonArray jsonCategories = jsonCriteria.get("categories").getAsJsonArray();
            for (JsonElement json : jsonCategories) {
                criteria.addCategory(repository.find(new Category(json.getAsJsonObject().get("id").getAsInt())));
            }

            JsonArray jsonExcludeCategories = jsonCriteria.get("excludeCategories").getAsJsonArray();
            for (JsonElement json : jsonExcludeCategories) {
                criteria.excludeCategory(repository.find(new Category(json.getAsJsonObject().get("id").getAsInt())));
            }

            JsonArray jsonSubCategories = jsonCriteria.get("subCategories").getAsJsonArray();
            for (JsonElement json : jsonSubCategories) {
                criteria.addSubCategory(repository.find(new SubCategory(json.getAsJsonObject().get("id").getAsInt())));
            }

            JsonArray jsonExcludeSubCategories = jsonCriteria.get("excludeSubCategories").getAsJsonArray();
            for (JsonElement json : jsonExcludeSubCategories) {
                criteria.excludeSubCategory(
                    repository.find(new SubCategory(json.getAsJsonObject().get("id").getAsInt())));
            }

            JsonArray jsonAccounts = jsonCriteria.get("accounts").getAsJsonArray();
            for (JsonElement json : jsonAccounts) {
                criteria.addAccount(repository.find(new Account(json.getAsJsonObject().get("id").getAsInt())));
            }

            JsonArray jsonExcludeAccounts = jsonCriteria.get("excludeAccounts").getAsJsonArray();
            for (JsonElement json : jsonExcludeAccounts) {
                criteria.excludeAccount(repository.find(new Account(json.getAsJsonObject().get("id").getAsInt())));
            }

            JsonArray jsonTypes = jsonCriteria.get("types").getAsJsonArray();
            for (JsonElement json : jsonTypes) {
                criteria.addType(
                    repository.find(new accountancy.model.base.Type(json.getAsJsonObject().get("id").getAsInt())));
            }

            JsonArray jsonExcludeTypes = jsonCriteria.get("excludeTypes").getAsJsonArray();
            for (JsonElement json : jsonExcludeTypes) {
                criteria.excludeType(
                    repository.find(new accountancy.model.base.Type(json.getAsJsonObject().get("id").getAsInt())));
            }

            JsonArray jsonBanks = jsonCriteria.get("banks").getAsJsonArray();
            for (JsonElement json : jsonBanks) {
                criteria.addBank(repository.find(new Bank(json.getAsJsonObject().get("id").getAsInt())));
            }

            JsonArray jsonExcludeBanks = jsonCriteria.get("excludeBanks").getAsJsonArray();
            for (JsonElement json : jsonExcludeBanks) {
                criteria.excludeBank(repository.find(new Bank(json.getAsJsonObject().get("id").getAsInt())));
            }

            JsonArray jsonCurrencies = jsonCriteria.get("currencies").getAsJsonArray();
            for (JsonElement json : jsonCurrencies) {
                criteria.addCurrency(repository.find(new Currency(json.getAsJsonObject().get("id").getAsInt())));
            }

            JsonArray jsonExcludeCurrencies = jsonCriteria.get("excludeCurrencies").getAsJsonArray();
            for (JsonElement json : jsonExcludeCurrencies) {
                criteria.excludeCurrency(repository.find(new Currency(json.getAsJsonObject().get("id").getAsInt())));
            }

            boolean positive = jsonCriteria.get("positive").getAsBoolean();
            if (positive) criteria.setPositiveOnly();
            boolean negative = jsonCriteria.get("negative").getAsBoolean();
            if (negative) criteria.setNegativeOnly();
            boolean absolute = jsonCriteria.get("absolute").getAsBoolean();
            if (absolute) criteria.setAbsolute();
            boolean cumulative = jsonCriteria.get("cumulative").getAsBoolean();
            if (cumulative) criteria.setCumulative();

            return criteria;
        }
    }

}
