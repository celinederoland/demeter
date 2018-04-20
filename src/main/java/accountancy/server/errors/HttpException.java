package accountancy.server.errors;

public abstract class HttpException extends Exception {

    protected int code;

    public HttpException(String message) {

        super(message);
    }

    public int code() {

        return this.code;
    }
}
