package accountancy.server;

import accountancy.model.base.Category;
import accountancy.model.base.Transaction;
import accountancy.repository.BaseRepository;
import accountancy.repository.sql.ConnectionProvider;
import accountancy.repository.sql.SqlBaseRepository;
import com.google.gson.*;

import javax.servlet.http.HttpServlet;
import java.lang.reflect.Type;
import java.util.Date;

public class AppServlet extends HttpServlet {

    protected final String             db;
    protected final ConnectionProvider connectionProvider;
    protected final BaseRepository     repository;
    protected final Gson               gson;

    public AppServlet() {

        super();
        String env = System.getenv("ACCOUNTANCY_DATABASE");
        if (env != null) this.db = env;
        else this.db = "localhost:3000/accountancy?user=root&password=secret&useSSL=false";

        this.connectionProvider = (new ConnectionProvider()).source("jdbc:mysql://" + db);
        this.repository = new SqlBaseRepository(connectionProvider);

        this.gson = (new GsonBuilder())
            .registerTypeAdapter(Date.class, new DateSerializeAsTimestamp())
            .registerTypeAdapter(Transaction.class, new TransactionSerialize())
            .registerTypeAdapter(Category.class, new CategorySerialize())
            .create();
    }

    protected class DateSerializeAsTimestamp implements JsonSerializer<Date> {

        @Override public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {

            return new JsonPrimitive(src.getTime());
        }
    }


    protected class TransactionSerialize implements JsonSerializer<Transaction> {

        @Override public JsonElement serialize(
            Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext
        ) {

            JsonObject serialized = (new Gson()).toJsonTree(transaction).getAsJsonObject();
            serialized.remove("date");
            serialized.add("date", new JsonPrimitive(transaction.date().getTime()));
            return serialized;
        }
    }

    protected class CategorySerialize implements JsonSerializer<Category> {

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
}
