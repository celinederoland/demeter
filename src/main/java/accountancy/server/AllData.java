package accountancy.server;

import accountancy.model.Entity;
import accountancy.server.errors.HttpError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AllData extends AppServlet {

    /**
     * Route GET /all
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            response.setContentType("application/json");

            this.repository.findAll();

            HashMap<String, ArrayList<Entity>> all = new HashMap<>();
            all.put("accounts", repository.accounts().getAll());
            all.put("transactions", repository.transactions().getAll());
            all.put("banks", repository.banks().getAll());
            all.put("types", repository.types().getAll());
            all.put("currencies", repository.currencies().getAll());
            all.put("categories", repository.categories().getAll());

            String json = gson.toJson(all);
            response.getWriter().println(json);

        } catch (Exception e) {

            new HttpError(e, response);
        }
    }

    /**
     * Route DELETE /all
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        try {

            response.setContentType("application/json");

            this.repository.clean();

            HashMap<String, ArrayList<Entity>> all = new HashMap<>();
            all.put("accounts", repository.accounts().getAll());
            all.put("transactions", repository.transactions().getAll());
            all.put("banks", repository.banks().getAll());
            all.put("types", repository.types().getAll());
            all.put("currencies", repository.currencies().getAll());

            //String json = gsonBuilder.create().toJson(all);
            String json = gson.toJson(all);
            response.getWriter().println(json);

        } catch (Exception e) {

            new HttpError(e, response);
        }
    }
}
