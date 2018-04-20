package accountancy.server;

import accountancy.model.base.Account;
import accountancy.server.errors.HttpError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Accounts extends AppServlet {

    /**
     * Route GET /account/{id}
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int     id      = Integer.parseInt(request.getPathInfo().substring(1));
        Account account = repository.find(new Account(id));
        response.getWriter().println(gson.toJson(account));
    }

    /**
     * Route POST /account
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        Account account = gson.fromJson(request.getReader(), Account.class);

        if (account.id() == 0) {
            new HttpError(403, "ResourceDoesntExist - use PUT method instead", response);
            return;
        }

        repository.save(account);

        response.getWriter().println(gson.toJson(account));
    }

    /**
     * Route PUT /account
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        Account account = gson.fromJson(request.getReader(), Account.class);

        if (account.id() > 0) {
            new HttpError(403, "ResourceAlreadyExist - use POST method instead", response);
            return;
        }

        account = repository.create(account);

        response.getWriter().println(gson.toJson(account));
    }
}
