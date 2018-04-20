package accountancy.server;

import accountancy.model.base.Bank;
import accountancy.server.errors.HttpError;

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

        int  id   = Integer.parseInt(request.getPathInfo().substring(1));
        Bank bank = repository.find(new Bank(id));
        response.getWriter().println(gson.toJson(bank));
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

        response.setContentType("application/json");

        Bank bank = gson.fromJson(request.getReader(), Bank.class);

        if (bank.id() == 0) {
            new HttpError(403, "ResourceDoesntExist - use PUT method instead", response);
            return;
        }

        repository.save(bank);

        response.getWriter().println(gson.toJson(bank));
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

        response.setContentType("application/json");

        Bank bank = gson.fromJson(request.getReader(), Bank.class);

        if (bank.id() > 0) {
            new HttpError(403, "ResourceAlreadyExist - use POST method instead", response);
            return;
        }

        bank = repository.create(bank);

        response.getWriter().println(gson.toJson(bank));
    }
}
