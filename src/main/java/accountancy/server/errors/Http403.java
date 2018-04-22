package accountancy.server.errors;

public class Http403 extends HttpException {

    public Http403() {

        super("Aunauthorize");
        this.code = 403;
    }
}
