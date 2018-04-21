package accountancy.server.errors;

public class Http500 extends HttpException {

    public Http500(String message) {

        super(message);
        this.code = 500;
    }
}
