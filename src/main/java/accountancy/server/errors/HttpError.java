package accountancy.server.errors;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpError {

    public HttpError(int code, String message, HttpServletResponse response) throws IOException {

        response.setStatus(code);
        response.getWriter().println((new Gson()).toJson(new Error(message)));
    }

    public HttpError(int code, HttpServletResponse response) throws IOException {

        response.setStatus(code);
        response.getWriter().println((new Gson()).toJson(new Error()));
    }
}
