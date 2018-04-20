package accountancy.server;

import accountancy.model.base.Category;
import accountancy.server.errors.Http403;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Categories extends AppServlet {

    /**
     * Route GET /category/{id}
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {

            int id = Integer.parseInt(request.getPathInfo().substring(1));
            return repository.find(new Category(id));
        });
    }

    /**
     * Route POST /category
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {

            Category category = gson.fromJson(request.getReader(), Category.class);

            if (category.id() == 0) {
                throw new Http403("ResourceDoesntExist - use PUT method instead");
            }

            repository.save(category);
            return category;
        });
    }

    /**
     * Route PUT /category
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {

            Category category = gson.fromJson(request.getReader(), Category.class);

            if (category.id() > 0) {
                throw new Http403("ResourceAlreadyExist - use POST method instead");
            }

            return repository.create(category);
        });

    }
}
