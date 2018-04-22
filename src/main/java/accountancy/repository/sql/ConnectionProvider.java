package accountancy.repository.sql;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

    private java.sql.Connection connection;
    private String              source;

    public ConnectionProvider source(String source) {

        this.source = source;
        return this;
    }

    public java.sql.Connection getConnection() {

        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                this.connection = DriverManager.getConnection(source);
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        return this.connection;
    }
}
