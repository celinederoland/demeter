package accountancy.server.errors;

public class Error {

    protected String message = "vide";
    protected String status  = "error";
    protected transient StackTraceElement[] context;

    public Error() {

        this.message = "UnexpectedError";
    }

    public Error(String message) {

        this.message = message;
    }

    public Error(String message, StackTraceElement[] context) {

        this.message = message;
        this.context = context;
    }
}
