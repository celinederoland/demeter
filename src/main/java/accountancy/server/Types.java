package accountancy.server;

import accountancy.model.base.Type;
import accountancy.server.errors.Http422;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Types extends AppServlet {

    /**
     * Route GET /type/{id}
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {

            int id = Integer.parseInt(request.getPathInfo().substring(1));
            return repository.find(new Type(id));
        });
    }

    /**
     * Route POST /type
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {

            Type type = gson.fromJson(request.getReader(), Type.class);

            if (type.id() == 0) {
                throw new Http422("ResourceDoesntExist - use PUT method instead");
            }

            repository.save(type);
            return type;
        });
    }

    /**
     * Route PUT /type
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {


            Type type = gson.fromJson(request.getReader(), Type.class);

            if (type.id() > 0) {
                throw new Http422("ResourceAlreadyExist - use POST method instead");
            }

            return repository.create(type);
        });
    }
}
