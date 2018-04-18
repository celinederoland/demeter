package accountancy.server;

import accountancy.model.EntityList;
import accountancy.repository.BaseRepository;
import accountancy.repository.sql.ConnectionProvider;
import accountancy.repository.sql.SqlBaseRepository;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class App extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("application/json");

        String db;
        String env = System.getenv("ACCOUNTANCY_DATABASE");
        if (env != null) db = env;
        else db = "localhost:3001/accountancy?user=root&password=secret&useSSL=false";
        ConnectionProvider connectionProvider = (new ConnectionProvider()).source("jdbc:mysql://" + db);
        BaseRepository     repository         = new SqlBaseRepository(connectionProvider);

        repository.findAll();

        HashMap<String, EntityList> all = new HashMap<>();
        all.put("accounts", repository.accounts());
        all.put("transactions", repository.transactions());
        all.put("banks", repository.banks());
        all.put("types", repository.types());
        all.put("currencies", repository.currencies());

        String json = (new Gson()).toJson(all);
        response.getWriter().println(json);
    }
}
