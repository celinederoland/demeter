package accountancy.view.actions;

import accountancy.repository.BaseRepository;
import accountancy.view.components.AbstractMultiListener;
import accountancy.view.components.PButton;
import accountancy.view.components.PLabel;
import accountancy.view.components.PPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Clean extends JFrame {

    private final BaseRepository repository;

    public Clean(BaseRepository repository) {

        this.repository = repository;

        this.setTitle("Nettoyer la base de données ?");
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);

        PPanel mainPane = new PPanel();
        this.setContentPane(mainPane);
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));

        PPanel contentPane = new PPanel(10, 10);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(
            new PLabel(
                "<html><p>Êtes vous sur de vouloir enlever les entrées inutiles de la base de données ?</p></html>"),
            BorderLayout.CENTER
        );

        PPanel controlPane = new PPanel(10, 10);
        controlPane.setLayout(new BoxLayout(controlPane, BoxLayout.LINE_AXIS));

        PButton accept = new PButton("Oui");
        accept.addActionListener(new AbstractMultiListener() {
            @Override public void actionPerformed(ActionEvent e) {

                (new CleanModel()).start();
                dispose();
            }
        });
        controlPane.add(accept);

        PButton close = new PButton("Non");
        close.addActionListener(new AbstractMultiListener() {
            @Override public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });
        controlPane.add(close);

        mainPane.add(contentPane);
        mainPane.add(controlPane);

        this.setVisible(true);
    }

    class CleanModel extends Thread {

        public void run() {

            repository.clean();
        }
    }
}
