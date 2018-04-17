package accountancy;


import accountancy.repository.BaseRepository;
import accountancy.repository.CsvImportRepository;
import accountancy.repository.sql.ConnectionProvider;
import accountancy.repository.sql.SqlBaseRepository;
import accountancy.repository.sql.SqlCsvImportRepository;
import accountancy.view.MainWindow;

import javax.swing.*;

public class App {

    public static void main(String args[]) {

        ConnectionProvider connectionProvider = (new ConnectionProvider()).source(
            "jdbc:mysql://localhost:3306/compta?user=root&password=secret&useSSL=false");
        BaseRepository      repository          = new SqlBaseRepository(connectionProvider);
        CsvImportRepository csvImportRepository = new SqlCsvImportRepository(connectionProvider);
        repository.findAll();

        SwingUtilities.invokeLater(() -> new MainWindow(repository, csvImportRepository));
    }
}
