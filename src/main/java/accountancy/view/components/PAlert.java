package accountancy.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PAlert extends JFrame {

    public PAlert(String title, String message) {

        this.setTitle(title);
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);

        PPanel mainPane = new PPanel();
        this.setContentPane(mainPane);
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));

        PPanel contentPane = new PPanel(10, 10);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new PLabel("<html><p>" + message + "</p></html>"), BorderLayout.CENTER);

        PPanel controlPane = new PPanel(10, 10);
        controlPane.setLayout(new BoxLayout(controlPane, BoxLayout.LINE_AXIS));
        PButton close = new PButton("Compris");
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

}
