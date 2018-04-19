package accountancy.model;

import accountancy.model.base.Category;
import accountancy.model.base.SubCategory;
import accountancy.model.base.Transaction;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

public class Json {

    public static Gson gson() {

        return GsonHolder.gson;
    }

    private static class GsonHolder {

        /**
         * Instance unique non préinitialisée
         */
        private final static Gson gson = (new GsonBuilder())
            .registerTypeAdapter(Date.class, new DateSerializeAsTimestamp())
            .registerTypeAdapter(Transaction.class, new TransactionSerialize())
            .registerTypeAdapter(Category.class, new CategorySerialize())
            .registerTypeAdapter(Category.class, new CategoryDeserialize())
            .create();
    }

    private static class DateSerializeAsTimestamp implements JsonSerializer<Date> {

        @Override public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {

            return new JsonPrimitive(src.getTime());
        }
    }


    private static class TransactionSerialize implements JsonSerializer<Transaction> {

        @Override public JsonElement serialize(
            Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext
        ) {

            JsonObject serialized = (new Gson()).toJsonTree(transaction).getAsJsonObject();
            serialized.remove("date");
            serialized.add("date", new JsonPrimitive(transaction.date().getTime()));
            return serialized;
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
