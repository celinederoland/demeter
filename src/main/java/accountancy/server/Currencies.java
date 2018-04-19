package accountancy.server;

import accountancy.model.base.Currency;
import accountancy.server.errors.HttpError;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Currencies extends AppServlet {

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

        Gson     gson     = new Gson();
        Currency currency = gson.fromJson(request.getReader(), Currency.class);

        if (currency.title().length() > 3) {
            new HttpError(401, "Currency title must have maximum 3 caracters", response);
            return;
        }

        if (currency.id() == 0) {
            new HttpError(403, "ResourceDoesntExist - use PUT method instead", response);
            return;
        }

        repository.save(currency);

        response.getWriter().println(gson.toJson(currency));
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

        Gson     gson     = new Gson();
        Currency currency = gson.fromJson(request.getReader(), Currency.class);

        if (currency.title().length() > 3) {
            new HttpError(401, "Currency title must have maximum 3 caracters", response);
            return;
        }
        if (currency.id() > 0) {
            new HttpError(403, "ResourceAlreadyExist - use POST method instead", response);
            return;
        }

        currency = repository.create(currency);

        response.getWriter().println(gson.toJson(currency));
    }
}