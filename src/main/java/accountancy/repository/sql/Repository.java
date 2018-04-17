package accountancy.repository.sql;

public abstract class Repository {

    protected final ConnectionProvider connectionProvider;

    public Repository(ConnectionProvider connectionProvider) {

        this.connectionProvider = connectionProvider;
    }

}
