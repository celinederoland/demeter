package accountancy;


import accountancy.repository.AxialSelectionFactory;
import accountancy.repository.BaseRepository;
import accountancy.repository.CsvImportRepository;
import accountancy.repository.SelectionProvider;
import accountancy.repository.rest.RestBaseRepository;
import accountancy.repository.selection.MySelectionFactory;
import accountancy.repository.sql.ConnectionProvider;
import accountancy.repository.sql.SqlCsvImportRepository;
import accountancy.repository.sql.SqlSelectionProvider;
import accountancy.view.MainWindow;

import javax.swing.*;

public class App {

    public static void main(String args[]) {

        ConnectionProvider connectionProvider = (new ConnectionProvider()).source(
            "jdbc:mysql://localhost:3306/compta?user=root&password=secret&useSSL=false");
        BaseRepository        repository          = new RestBaseRepository("http://accountancy.localhost");
        CsvImportRepository   csvImportRepository = new SqlCsvImportRepository(connectionProvider);
        SelectionProvider     provider            = new SqlSelectionProvider(connectionProvider, repository);
        AxialSelectionFactory factory             = new MySelectionFactory(provider, repository);
        repository.findAll();

        SwingUtilities.invokeLater(() -> new MainWindow(repository, csvImportRepository, factory));
    }
}
