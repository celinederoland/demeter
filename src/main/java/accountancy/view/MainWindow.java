package accountancy.view;

import accountancy.view.components.PPanel;
import accountancy.view.config.Dimensions;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {

        this.setTitle("Comptabilité");
        this.setPreferredSize(Dimensions.MAIN);
        this.setLocationRelativeTo(null);
        this.setLocation(0, 0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PPanel mainPanel = new PPanel();
        mainPanel.setLayout(new BorderLayout());

        /*mainPanel.add(new NorthPanel(), BorderLayout.NORTH);
        mainPanel.add(new PPanelVerticalScroll(new EastPanel()), BorderLayout.EAST);
        mainPanel.add(new PPanelVerticalScroll(new WestPanel()), BorderLayout.WEST);
        mainPanel.add(new PPanelVerticalScroll(new CenterPanel()), BorderLayout.CENTER);*/

        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);
    }

}
