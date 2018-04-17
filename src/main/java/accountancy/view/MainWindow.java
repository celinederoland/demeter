package accountancy.view;

import accountancy.repository.BaseRepository;
import accountancy.view.components.PPanel;
import accountancy.view.components.PPanelVerticalScroll;
import accountancy.view.config.Dimensions;
import accountancy.view.views.account.AccountsOuterPanel;
import accountancy.view.views.actions.ActionsOuterPanel;
import accountancy.view.views.category.CategoriesOuterPanel;
import accountancy.view.views.entry.EntriesOuterPanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow(BaseRepository repository) {

        this.setTitle("Comptabilité");
        this.setPreferredSize(Dimensions.MAIN);
        this.setLocationRelativeTo(null);
        this.setLocation(0, 0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PPanel mainPanel = new PPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(new ActionsOuterPanel(), BorderLayout.NORTH);
        mainPanel.add(new PPanelVerticalScroll(new CategoriesOuterPanel()), BorderLayout.EAST);
        mainPanel.add(new PPanelVerticalScroll(new EntriesOuterPanel()), BorderLayout.CENTER);
        mainPanel.add(new PPanelVerticalScroll(new AccountsOuterPanel(repository)), BorderLayout.WEST);

        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);
    }

}
