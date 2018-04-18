package accountancy.server.errors;

public class Error {

    protected String message;
    protected String status = "error";

    public Error() {

        this.message = "UnexpectedError";
    }

    public Error(String message) {

        this.message = message;
    }
}
