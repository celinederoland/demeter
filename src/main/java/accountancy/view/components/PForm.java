package accountancy.view.components;

import javax.swing.*;
import java.awt.*;

public class PForm extends JFrame {

    private PPanel contentPane;
    private PPanel controlPane;

    public PForm(String title, int rows) {

        this.setTitle(title);
        this.setSize(300, rows * 50 + 70);
        this.setLocationRelativeTo(null);

        PPanel mainPane = new PPanel();
        this.setContentPane(mainPane);
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));

        this.contentPane = new PPanel(10, 10);
        this.contentPane.setLayout(new GridLayout(rows, 2, 10, 10));

        this.controlPane = new PPanel(10, 10);
        this.controlPane.setLayout(new BoxLayout(this.controlPane, BoxLayout.LINE_AXIS));

        mainPane.add(this.contentPane);
        mainPane.add(this.controlPane);

        this.setVisible(true);
    }

    public PForm addElement(String label, Component component) {

        this.contentPane.add(new PLabel(label));
        this.contentPane.add(component);

        return this;
    }

    public PForm addControl(Component component) {

        this.controlPane.add(component);

        return this;
    }
}
