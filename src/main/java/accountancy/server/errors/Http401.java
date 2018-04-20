package accountancy.server.errors;

public class Http401 extends HttpException {

    public Http401(String message) {

        super(message);
        this.code = 401;
    }
}
