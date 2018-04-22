package accountancy.server.errors;

public class Http422 extends HttpException {

    public Http422(String message) {

        super(message);
        this.code = 422;
    }
}
