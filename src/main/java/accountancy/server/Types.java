package accountancy.server;

import accountancy.model.base.Type;
import accountancy.server.errors.HttpError;

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

        int  id   = Integer.parseInt(request.getPathInfo().substring(1));
        Type type = repository.find(new Type(id));
        response.getWriter().println(gson.toJson(type));
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

        response.setContentType("application/json");

        Type type = gson.fromJson(request.getReader(), Type.class);

        if (type.id() == 0) {
            new HttpError(403, "ResourceDoesntExist - use PUT method instead", response);
            return;
        }

        repository.save(type);

        response.getWriter().println(gson.toJson(type));
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

        response.setContentType("application/json");

        Type type = gson.fromJson(request.getReader(), Type.class);

        if (type.id() > 0) {
            new HttpError(403, "ResourceAlreadyExist - use POST method instead", response);
            return;
        }

        type = repository.create(type);

        response.getWriter().println(gson.toJson(type));
    }
}
