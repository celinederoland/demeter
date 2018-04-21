package accountancy.server;

import accountancy.model.Json;
import accountancy.repository.BaseRepository;
import accountancy.repository.CsvImportRepository;
import accountancy.repository.sql.ConnectionProvider;
import accountancy.repository.sql.SqlBaseRepository;
import accountancy.repository.sql.SqlCsvImportRepository;
import accountancy.server.errors.HttpError;
import accountancy.server.errors.HttpException;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AppServlet extends HttpServlet {

    protected final String              db;
    protected final ConnectionProvider  connectionProvider;
    protected final CsvImportRepository csvImportRepository;
    protected       BaseRepository      repository;
    protected       Gson                gson;

    public AppServlet() {

        super();
        String env = System.getenv("ACCOUNTANCY_DATABASE");
        if (env != null) this.db = env;
        else this.db = "localhost:3000/accountancy?user=root&password=secret&useSSL=false";

        this.connectionProvider = (new ConnectionProvider()).source("jdbc:mysql://" + db);
        this.csvImportRepository = new SqlCsvImportRepository(connectionProvider);
    }

    protected void action(HttpServletRequest request, HttpServletResponse response, Controller doIt)
        throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        this.repository = new SqlBaseRepository(connectionProvider);
        this.gson = Json.gson(repository);

        try {

            Object res  = doIt.run();
            String json = gson.toJson(res);
            response.getWriter().println(json);
        } catch (HttpException e) {

            new HttpError(e.code(), e.getMessage(), response);
        } catch (Exception e) {

            new HttpError(e, response);
        }
    }
}
