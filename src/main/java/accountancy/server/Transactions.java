package accountancy.server;

import accountancy.model.base.Transaction;
import accountancy.server.errors.Http422;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Transactions extends AppServlet {

    /**
     * Route GET /transaction/{id}
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {

            int id = Integer.parseInt(request.getPathInfo().substring(1));
            return repository.find(new Transaction(id));
        });
    }

    /**
     * Route POST /transaction
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {

            Transaction transaction = gson.fromJson(request.getReader(), Transaction.class);

            if (transaction.id() == 0) {
                throw new Http422("ResourceDoesntExist - use PUT method instead");
            }

            repository.save(transaction);
            return transaction;
        });
    }

    /**
     * Route PUT /transaction
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {


            Transaction transaction = gson.fromJson(request.getReader(), Transaction.class);

            if (transaction.id() > 0) {
                throw new Http422("ResourceAlreadyExist - use POST method instead");
            }

            return repository.create(transaction);
        });
    }
}
