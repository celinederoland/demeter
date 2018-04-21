package accountancy.repository;

import accountancy.repository.sql.ConnectionProvider;

public class RepositoryTest {

    protected ConnectionProvider connectionProvider;

    public RepositoryTest() throws Exception {

        String db;
        String env = System.getenv("ACCOUNTANCY_DATABASE");
        if (env != null) db = env;
        else db = "localhost:3001/accountancy?user=root&password=secret&useSSL=false";
        connectionProvider = (new ConnectionProvider()).source(
            "jdbc:mysql://" + db);
    }

}