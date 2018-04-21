package accountancy.view.actions;

import accountancy.repository.BaseRepository;
import accountancy.repository.CsvImportRepository;
import accountancy.repository.csv.CsvRepository;
import accountancy.view.components.*;
import accountancy.view.views.transaction.ComboAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class CSVImport extends PForm {

    private final BaseRepository      repository;
    private final CsvImportRepository csvImportRepository;
    private       JFileChooser        fc;
    private       ComboAccount        comboAccount;
    private       PPanel              panelFile;
    private       File                file;
    private       PLoader             loader;

    public CSVImport(BaseRepository repository, CsvImportRepository csvImportRepository) {

        super("CSV", 2);

        this.repository = repository;
        this.csvImportRepository = csvImportRepository;

        this.comboAccount = new ComboAccount(repository);
        this.addElement("Compte", this.comboAccount);


        panelFile = new PPanel();
        panelFile.setLayout(new BorderLayout());

        //Create a file chooser
        fc = new JFileChooser();
        JButton openButton = new PButton("Select ...");
        openButton.addActionListener(e -> {

            int returnVal = fc.showOpenDialog(CSVImport.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                SwingUtilities.invokeLater(() -> {

                    panelFile.removeAll();
                    panelFile.add(new PLabel(file.getName()));
                    panelFile.revalidate();
                    panelFile.repaint();
                });
            }
            else {
                System.err.println(returnVal);
            }
        });
        panelFile.add(openButton);
        this.addElement("Fichier", panelFile);

        PButton accept = new PButton("Oui");
        accept.addActionListener(new AbstractMultiListener() {
            @Override public void actionPerformed(ActionEvent e) {

                (new Import()).start();
                dispose();
            }
        });
        this.addControl(accept);

        PButton close = new PButton("Non");
        close.addActionListener(new AbstractMultiListener() {
            @Override public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });
        this.addControl(close);
    }

    class Import extends Thread {

        public void run() {

            SwingUtilities.invokeLater(() -> loader = new PLoader());
            System.out.println("loader invoke");
            (new CsvRepository(repository, csvImportRepository, file, comboAccount.getValue())).doImport();
            SwingUtilities.invokeLater(() -> loader.dispose());
        }
    }
}
