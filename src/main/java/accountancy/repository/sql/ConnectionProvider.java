package accountancy.repository.sql;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private java.sql.Connection connection;
    private String source = "jdbc:mysql://localhost:3306/compta?user=root&password=secret&useSSL=false";

    public ConnectionProvider source(String source) {

        this.source = source;
        return this;
    }

    public java.sql.Connection getConnection() {

        if (connection == null) {
            try {
                this.connection = DriverManager.getConnection(source);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return this.connection;
    }
}
