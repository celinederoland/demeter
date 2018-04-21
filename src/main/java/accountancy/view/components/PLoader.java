package accountancy.view.components;

import javax.swing.*;
import java.awt.*;

public class PLoader extends JFrame {

    public PLoader() {

        super("En cours ... ");
        System.out.println("creating loader");
    }

    public PLoader(String title) {

        this.setTitle(title);
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);

        PPanel mainPane = new PPanel();
        this.setContentPane(mainPane);
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));

        PPanel contentPane = new PPanel(10, 10);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new PLabel("<html><p>Attendez SVP</p></html>"), BorderLayout.CENTER);

        mainPane.add(contentPane);

        this.setVisible(true);
    }

}
