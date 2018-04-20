package accountancy.server.errors;

public class Http403 extends HttpException {

    public Http403(String message) {

        super(message);
        this.code = 403;
    }
}
