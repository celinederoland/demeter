package accountancy.server;

import accountancy.model.base.Bank;
import accountancy.server.errors.Http403;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Banks extends AppServlet {

    /**
     * Route GET /bank/{id}
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {

            int id = Integer.parseInt(request.getPathInfo().substring(1));
            return repository.find(new Bank(id));
        });
    }

    /**
     * Route POST /bank
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {

            response.setContentType("application/json");

            Bank bank = gson.fromJson(request.getReader(), Bank.class);

            if (bank.id() == 0) {
                throw new Http403("ResourceDoesntExist - use PUT method instead");
            }

            repository.save(bank);
            return bank;
        });
    }

    /**
     * Route PUT /bank
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {

            Bank bank = gson.fromJson(request.getReader(), Bank.class);

            if (bank.id() > 0) {
                throw new Http403("ResourceAlreadyExist - use POST method instead");
            }

            return repository.create(bank);
        });

    }
}
