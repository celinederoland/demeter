package accountancy;


import accountancy.repository.AxialSelectionFactory;
import accountancy.repository.BaseRepository;
import accountancy.repository.CsvImportRepository;
import accountancy.repository.SelectionProvider;
import accountancy.repository.rest.RestBaseRepository;
import accountancy.repository.rest.RestCsvImportRepository;
import accountancy.repository.rest.RestSelectionProvider;
import accountancy.repository.selection.MySelectionFactory;
import accountancy.view.MainWindow;

import javax.swing.*;

public class App {

    public static void main(String args[]) {

        String         url        = System.getenv("ACCOUNTANCY_URL");
        BaseRepository repository = new RestBaseRepository(url);
        CsvImportRepository csvImportRepository   = new RestCsvImportRepository(
            url, repository);
        SelectionProvider provider                = new RestSelectionProvider(
            url, repository);
        AxialSelectionFactory factory             = new MySelectionFactory(provider, repository);
        repository.findAll();

        SwingUtilities.invokeLater(() -> new MainWindow(repository, csvImportRepository, factory));
    }
}
