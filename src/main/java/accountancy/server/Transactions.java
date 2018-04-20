package accountancy.server;

import accountancy.model.base.Transaction;
import accountancy.server.errors.HttpError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Transactions extends AppServlet {

    /**
     * Route POST /currency
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        repository.findAll();
        Transaction transaction = gson.fromJson(request.getReader(), Transaction.class);

        if (transaction.id() == 0) {
            new HttpError(403, "ResourceDoesntExist - use PUT method instead", response);
            return;
        }

        repository.save(transaction);

        response.getWriter().println(gson.toJson(transaction));
    }

    /**
     * Route PUT /currency
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        repository.findAll();
        Transaction transaction = gson.fromJson(request.getReader(), Transaction.class);

        if (transaction.id() > 0) {
            new HttpError(403, "ResourceAlreadyExist - use POST method instead", response);
            return;
        }

        transaction = repository.create(transaction);

        response.getWriter().println(gson.toJson(transaction));
    }
}
