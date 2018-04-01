package accountancy.repository;

import accountancy.model.base.Transaction;

public interface CsvImportRepository {

    boolean csvLineHasBeenImported(String dateField, String textField, double amount);

    void saveCsvImport(String dateField, String textField, double amount, Transaction transaction);
}
