package accountancy.repository.sql;

public abstract class Repository {

    protected ConnectionProvider connectionProvider;

    public Repository(ConnectionProvider connectionProvider) {

        this.connectionProvider = connectionProvider;
    }


}
