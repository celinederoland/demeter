package accountancy.server;

import accountancy.model.base.Currency;
import accountancy.server.errors.Http401;
import accountancy.server.errors.Http403;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Currencies extends AppServlet {

    /**
     * Route GET /currency/{id}
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {
            int id = Integer.parseInt(request.getPathInfo().substring(1));
            return repository.find(new Currency(id));
        });
    }

    /**
     * Route POST /currency
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {


            Currency currency = gson.fromJson(request.getReader(), Currency.class);

            if (currency.title().length() > 3) {
                throw new Http401("Currency title must have maximum 3 caracters");
            }

            if (currency.id() == 0) {
                throw new Http403("ResourceDoesntExist - use PUT method instead");
            }

            repository.save(currency);
            return currency;
        });
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

        action(request, response, () -> {

            Currency currency = gson.fromJson(request.getReader(), Currency.class);

            if (currency.title().length() > 3) {
                throw new Http401("Currency title must have maximum 3 caracters");
            }
            if (currency.id() > 0) {
                throw new Http403("ResourceAlreadyExist - use POST method instead");
            }

            return repository.create(currency);
        });
    }
}
