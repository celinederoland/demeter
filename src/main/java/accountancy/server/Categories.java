package accountancy.server;

import accountancy.model.base.Category;
import accountancy.server.errors.HttpError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Categories extends AppServlet {

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

        Category category = gson.fromJson(request.getReader(), Category.class);

        if (category.id() == 0) {
            new HttpError(403, "ResourceDoesntExist - use PUT method instead", response);
            return;
        }

        repository.save(category);

        response.getWriter().println(gson.toJson(category));
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

        Category category = gson.fromJson(request.getReader(), Category.class);

        if (category.id() > 0) {
            new HttpError(403, "ResourceAlreadyExist - use POST method instead", response);
            return;
        }

        category = repository.create(category);

        response.getWriter().println(gson.toJson(category));
    }
}
