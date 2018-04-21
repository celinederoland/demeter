package accountancy.repository.sql;

import accountancy.model.Entity;
import accountancy.model.base.Transaction;
import accountancy.model.selection.Criteria;
import accountancy.repository.BaseRepository;
import accountancy.repository.Selection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import static java.util.stream.Collectors.joining;

/**
 * Advanced requests to find transactions matching various criteria
 */
public class SqlSelection extends Repository implements Selection {

    private final BaseRepository repository;
    private final Criteria       criteria;

    public SqlSelection(ConnectionProvider connectionProvider, BaseRepository repository, Criteria criteria) {

        super(connectionProvider);
        this.repository = repository;
        this.criteria = criteria;
    }

    /**
     * Find all transactions matching the criteria
     *
     * @return ArrayList<Transaction>
     */
    @Override public ArrayList<Transaction> getTransactions() {

        Statement              statement;
        ResultSet              resultSet;
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {

            statement = connectionProvider.getConnection().createStatement();
            String query = "SELECT id FROM view_transactions_structure AS v " +
                           "WHERE " + buildWhereQuery() + " ORDER BY v.date ASC, v.id ASC";
            resultSet = statement.executeQuery(
                query
            );

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                transactions.add((Transaction) repository.transactions().getOne(id));
            }

            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return transactions;
    }

    /**
     * Get the balance of all transactions matching the criteria between 2 dates
     *
     * @param start Date
     * @param end   Date
     *
     * @return double
     */
    @Override public double getAmount(Date start, Date end) {

        Statement statement;
        ResultSet resultSet;
        double    amount = 0;

        try {

            statement = connectionProvider.getConnection().createStatement();
            String query = "SELECT SUM(amount) AS amount FROM view_transactions_structure AS v " +
                           "WHERE " + buildWhereQuery(start, end);
            resultSet = statement.executeQuery(query);

            resultSet.next();
            amount = resultSet.getDouble("amount");

            resultSet.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (criteria.absolute()) amount = Math.abs(amount);
        return amount;
    }

    private String buildWhereQuery() {

        return this.buildWhereQuery(null, null);
    }

    private String buildWhereQuery(Date start, Date end) {

        ArrayList<String> conditions = new ArrayList<>();

        if (!criteria.categories().isEmpty()) {
            conditions.add("v.category_id IN (" + concat(criteria.categories()) + ")");
        }
        if (!criteria.excludeCategories().isEmpty()) {
            conditions.add("v.category_id NOT IN (" + concat(criteria.excludeCategories()) + ")");
        }
        if (!criteria.subCategories().isEmpty()) {
            conditions.add("v.sub_category_id IN (" + concat(criteria.subCategories()) + ")");
        }
        if (!criteria.excludeSubCategories().isEmpty()) {
            conditions.add("v.sub_category_id NOT IN (" + concat(criteria.excludeSubCategories()) + ")");
        }
        if (!criteria.accounts().isEmpty()) {
            conditions.add("v.account_id IN (" + concat(criteria.accounts()) + ")");
        }
        if (!criteria.excludeAccounts().isEmpty()) {
            conditions.add("v.account_id NOT IN (" + concat(criteria.excludeAccounts()) + ")");
        }
        if (!criteria.types().isEmpty()) {
            conditions.add("v.type_id IN (" + concat(criteria.types()) + ")");
        }
        if (!criteria.excludeTypes().isEmpty()) {
            conditions.add("v.type_id NOT IN (" + concat(criteria.excludeTypes()) + ")");
        }
        if (!criteria.banks().isEmpty()) {
            conditions.add("v.bank_id IN (" + concat(criteria.banks()) + ")");
        }
        if (!criteria.excludeBanks().isEmpty()) {
            conditions.add("v.bank_id NOT IN (" + concat(criteria.excludeBanks()) + ")");
        }
        if (!criteria.currencies().isEmpty()) {
            conditions.add("v.currency_id IN (" + concat(criteria.currencies()) + ")");
        }
        if (!criteria.excludeCurrencies().isEmpty()) {
            conditions.add("v.currency_id NOT IN (" + concat(criteria.excludeCurrencies()) + ")");
        }

        if (criteria.positive()) {
            conditions.add("v.amount > 0");
        }
        if (criteria.negative()) {
            conditions.add("v.amount < 0");
        }

        if (start != null && !criteria.cumulative()) {
            conditions.add("v.date >= '" + (new java.sql.Date(start.getTime())).toString() + "'");
        }
        if (end != null) {
            conditions.add("v.date < '" + (new java.sql.Date(end.getTime())).toString() + "'");
        }

        if (conditions.isEmpty()) {
            conditions.add("1 = 1");
        }

        return String.join(" AND ", conditions);
    }

    private String concat(ArrayList<Entity> models) {

        return models.stream()
                     .map(Entity::id)
                     .map(String::valueOf)
                     .collect(joining(","));
    }
}
