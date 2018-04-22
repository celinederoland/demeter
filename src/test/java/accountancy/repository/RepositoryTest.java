package accountancy.repository;

import accountancy.repository.sql.ConnectionProvider;

public class RepositoryTest {

    protected ConnectionProvider connectionProvider;

    public RepositoryTest() throws Exception {

        String db = System.getenv("ACCOUNTANCY_DATABASE");
        connectionProvider = (new ConnectionProvider()).source("jdbc:mysql://" + db);
    }

}