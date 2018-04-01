package accountancy.repository.sql;

import accountancy.model.base.Transaction;
import accountancy.repository.CsvImportRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This model represents the csv lines which are imported.
 */
public class SqlCsvImportRepository extends Repository implements CsvImportRepository {

    public SqlCsvImportRepository(ConnectionProvider connectionProvider) {

        super(connectionProvider);
    }

    /**
     * Given a line representing a transaction in one csv file, check if this line has been imported in the past.
     * We consider a transaction as identical to other one if they have same date, description and amount.
     *
     * @param dateField the transaction date
     * @param textField the transaction description
     * @param amount    the transaction amount
     *
     * @return boolean
     */
    @Override public boolean csvLineHasBeenImported(String dateField, String textField, double amount) {

        PreparedStatement statement;
        ResultSet         resultSet;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "SELECT COUNT(id) AS cnt FROM csv_remember WHERE dateField = ? AND textField = ? AND amount = ?"
            );

            statement.setString(1, dateField);
            statement.setString(2, textField);
            statement.setDouble(3, amount);
            resultSet = statement.executeQuery();

            resultSet.next();
            int count = resultSet.getInt(1);

            resultSet.close();
            statement.close();

            return (count > 0);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * We save the one line representing a transaction in a csv file has been imported
     *
     * @param dateField   the transaction date
     * @param textField   the transaction description
     * @param amount      the transaction amount
     * @param transaction the Transaction model created throw this import
     */
    @Override public void saveCsvImport(String dateField, String textField, double amount, Transaction transaction) {

        PreparedStatement statement;

        try {

            statement = this.connectionProvider.getConnection().prepareStatement(
                "INSERT INTO csv_remember VALUES (NULL, ?, ?, ?, ?)"
            );

            statement.setString(1, dateField);
            statement.setString(2, textField);
            statement.setDouble(3, amount);
            statement.setInt(4, transaction.id());

            statement.executeUpdate();

            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
