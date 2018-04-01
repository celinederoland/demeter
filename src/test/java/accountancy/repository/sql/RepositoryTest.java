package accountancy.repository.sql;

public class RepositoryTest {

    protected ConnectionProvider connectionProvider;

    public RepositoryTest() throws Exception {

        connectionProvider = (new ConnectionProvider()).source(
            "jdbc:mysql://localhost:3307/compta?user=root&password=secret&useSSL=false");
    }

}