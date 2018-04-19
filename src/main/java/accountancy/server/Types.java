package accountancy.server;

import accountancy.model.base.Type;
import accountancy.server.errors.HttpError;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Types extends AppServlet {

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

        Gson gson = new Gson();
        Type type = gson.fromJson(request.getReader(), Type.class);

        if (type.id() > 0) {
            new HttpError(403, "ResourceAlreadyExist - use POST method instead", response);
            return;
        }

        type = repository.create(type);

        response.getWriter().println(gson.toJson(type));
    }
}
