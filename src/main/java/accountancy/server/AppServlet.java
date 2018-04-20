package accountancy.server;

import accountancy.model.Json;
import accountancy.repository.BaseRepository;
import accountancy.repository.sql.ConnectionProvider;
import accountancy.repository.sql.SqlBaseRepository;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;

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

        this.gson = Json.gson(repository);
    }
}
