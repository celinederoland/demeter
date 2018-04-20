package accountancy.server;

import accountancy.model.base.Account;
import accountancy.server.errors.Http403;

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

        action(request, response, () -> {

            int id = Integer.parseInt(request.getPathInfo().substring(1));
            return repository.find(new Account(id));
        });
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

        action(request, response, () -> {

            Account account = gson.fromJson(request.getReader(), Account.class);

            if (account.id() == 0) {
                throw new Http403("ResourceDoesntExist - use PUT method instead");
            }

            repository.save(account);

            return account;
        });
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

        action(request, response, () -> {

            Account account = gson.fromJson(request.getReader(), Account.class);

            if (account.id() > 0) {
                throw new Http403("ResourceAlreadyExist - use POST method instead");
            }

            return repository.create(account);
        });
    }
}
