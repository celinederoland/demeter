package accountancy.server;

import accountancy.model.Entity;

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

        action(request, response, () -> {
            this.repository.findAll();

            HashMap<String, ArrayList<Entity>> all = new HashMap<>();
            /*for(Entity account : repository.accounts().getAll()) {
                response.getWriter().println(((Account) account).type().id() + " " + ((Account) account).type().title());
            }*/
            all.put("accounts", repository.accounts().getAll());
            all.put("transactions", repository.transactions().getAll());
            all.put("banks", repository.banks().getAll());
            all.put("types", repository.types().getAll());
            all.put("currencies", repository.currencies().getAll());
            all.put("categories", repository.categories().getAll());

            return all;
        });
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

        action(request, response, () -> {

            this.repository.clean();
            this.repository.findAll();

            HashMap<String, ArrayList<Entity>> all = new HashMap<>();
            all.put("accounts", repository.accounts().getAll());
            all.put("transactions", repository.transactions().getAll());
            all.put("banks", repository.banks().getAll());
            all.put("types", repository.types().getAll());
            all.put("currencies", repository.currencies().getAll());
            all.put("categories", repository.categories().getAll());

            return all;
        });
    }
}
