package accountancy.model;

import accountancy.model.base.*;
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
            .create();
    }

    private static class DateSerializeAsTimestamp implements JsonSerializer<Date> {

        @Override public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {

            return new JsonPrimitive(src.getTime());
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
            serialized.add("date", new JsonPrimitive(transaction.date().getTime()));
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
            Date   date           = new Date(json.get("date").getAsInt());
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


}
