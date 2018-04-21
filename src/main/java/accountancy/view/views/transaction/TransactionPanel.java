package accountancy.view.views.transaction;

import accountancy.model.base.Account;
import accountancy.model.base.Category;
import accountancy.model.base.SubCategory;
import accountancy.model.base.Transaction;
import accountancy.observer.Observer;
import accountancy.repository.BaseRepository;
import accountancy.view.components.*;
import accountancy.view.config.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionPanel extends PPanel implements Observer {

    private final ComboAccount        comboAccount;
    private final BaseRepository      repository;
    private       Transaction         transaction;
    private       PTextField          titleField;
    private       PFormattedTextField dateField;
    private       PTextField          amountField;
    private       CurrencyLabel       currencyLabel;
    private       ComboCategory       comboCategory;
    private       ComboSubCategory    comboSubCategory;

    public TransactionPanel(Transaction transaction, BaseRepository repository) {

        this.repository = repository;
        this.transaction = transaction;

        transaction.addObserver(this);

        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setMinimumSize(Dimensions.CENTER_ENTRY);
        this.setPreferredSize(Dimensions.CENTER_ENTRY);
        this.setMaximumSize(Dimensions.CENTER_ENTRY);

        dateField = new PFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        dateField.setMinimumSize(Dimensions.CENTER_ENTRY_DATE);
        dateField.setPreferredSize(Dimensions.CENTER_ENTRY_DATE);
        dateField.setMaximumSize(Dimensions.CENTER_ENTRY_DATE);
        DateListener dateListener = new DateListener();
        dateField.addActionListener(dateListener);
        dateField.addFocusListener(dateListener);
        dateField.addPropertyChangeListener(dateListener);
        this.add(dateField);

        this.add(Box.createRigidArea(new Dimension(5, 0)));

        comboAccount = new ComboAccount(repository);
        comboAccount.setMinimumSize(Dimensions.CENTER_ENTRY_ACCOUNT);
        comboAccount.setPreferredSize(Dimensions.CENTER_ENTRY_ACCOUNT);
        AccountListener accountListener = new AccountListener();
        comboAccount.addActionListener(accountListener);
        comboAccount.addFocusListener(accountListener);
        this.add(comboAccount);

        this.add(Box.createRigidArea(new Dimension(5, 0)));

        titleField = new PTextField();
        titleField.setMinimumSize(Dimensions.CENTER_ENTRY_TITLE);
        titleField.setPreferredSize(Dimensions.CENTER_ENTRY_TITLE);
        titleField.setMaximumSize(Dimensions.CENTER_ENTRY_TITLE);
        TitleListener titleListener = new TitleListener();
        titleField.addActionListener(titleListener);
        titleField.addFocusListener(titleListener);
        this.add(titleField);

        this.add(Box.createRigidArea(new Dimension(5, 0)));

        comboCategory = new ComboCategory(repository);
        comboCategory.setMinimumSize(Dimensions.CENTER_ENTRY_ACCOUNT);
        comboCategory.setPreferredSize(Dimensions.CENTER_ENTRY_ACCOUNT);
        CategoryListener categoryListener = new CategoryListener();
        comboCategory.addActionListener(categoryListener);
        comboCategory.addFocusListener(categoryListener);
        this.add(comboCategory);

        this.add(Box.createRigidArea(new Dimension(5, 0)));

        comboSubCategory = new ComboSubCategory(transaction.category());
        comboSubCategory.setMinimumSize(Dimensions.CENTER_ENTRY_ACCOUNT);
        comboSubCategory.setPreferredSize(Dimensions.CENTER_ENTRY_ACCOUNT);
        SubCategoryListener subCategoryListener = new SubCategoryListener();
        comboSubCategory.addActionListener(subCategoryListener);
        comboSubCategory.addFocusListener(subCategoryListener);
        this.add(comboSubCategory);

        this.add(Box.createRigidArea(new Dimension(5, 0)));

        amountField = new PTextField();
        amountField.setMinimumSize(Dimensions.CENTER_ENTRY_AMOUNT);
        amountField.setPreferredSize(Dimensions.CENTER_ENTRY_AMOUNT);
        amountField.setMaximumSize(Dimensions.CENTER_ENTRY_AMOUNT);
        AmountListener amountListener = new AmountListener();
        amountField.addActionListener(amountListener);
        amountField.addFocusListener(amountListener);
        this.add(amountField);

        this.add(Box.createRigidArea(new Dimension(5, 0)));

        PPanel currencyPanel = new PPanel();
        currencyPanel.setMinimumSize(Dimensions.CENTER_ENTRY_CURRENCY);
        currencyPanel.setPreferredSize(Dimensions.CENTER_ENTRY_CURRENCY);
        currencyPanel.setMaximumSize(Dimensions.CENTER_ENTRY_CURRENCY);
        currencyPanel.setLayout(new BorderLayout());
        currencyLabel = new CurrencyLabel(transaction.account());
        currencyPanel.add(currencyLabel, BorderLayout.CENTER);
        this.add(currencyPanel);

        this.update();
    }

    @Override public void update() {

        try {
            titleField.setText(transaction.title());
            dateField.setValue(transaction.date());
            amountField.setText(String.valueOf(transaction.amount()));
            comboAccount.setSelectedItem(transaction.account());
            currencyLabel.setAccount(transaction.account());
            comboCategory.setSelectedItem(transaction.category());
            comboSubCategory.setCategory(transaction.category());
            comboSubCategory.setSelectedItem(transaction.subCategory());
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    public void requestFocus() {

        comboCategory.requestFocus();
    }

    public void setForeground(Color fg) {

        super.setForeground(fg);
        if (titleField != null) titleField.setForeground(fg);
    }

    private void save() {

        if (transaction.id() > 0) {
            repository.save(transaction);
        }
        else if (!transaction.title().isEmpty() && transaction.amount() != 0) {
            transaction = repository.create(transaction);
        }
    }

    class DateListener extends AbstractMultiListener {


        public void actionPerformed(ActionEvent e) {

            this.save();
        }

        private void save() {

            (new UpdateDateModel()).start();
        }

        public void focusLost(FocusEvent e) {

            this.save();
        }

    }

    class UpdateDateModel extends Thread {

        public void run() {

            Date date = (Date) dateField.getValue();
            transaction.date(date);
            save();
        }
    }

    class AmountListener extends AbstractMultiListener {


        public void actionPerformed(ActionEvent e) {

            this.save();
        }

        private void save() {

            (new UpdateAmountModel()).start();
        }

        public void focusLost(FocusEvent e) {

            this.save();
        }
    }

    class UpdateAmountModel extends Thread {

        public void run() {

            try {
                Double amount = Double.valueOf(
                    amountField.getText()
                               .replace(" ", "")
                               .replace("'", "")
                               .replace("‘", "")
                               .replace(",", ".")
                );
                transaction.amount(amount);
                save();
            } catch (NumberFormatException e) {

                new PAlert("Invalide", "Veuillez entrer un montant correct");
            }
        }
    }

    class TitleListener extends AbstractMultiListener {


        public void actionPerformed(ActionEvent e) {

            this.save();
        }

        private void save() {

            (new UpdateTitleModel()).start();
        }

        public void focusLost(FocusEvent e) {

            this.save();
        }
    }

    class UpdateTitleModel extends Thread {

        public void run() {

            transaction.title(titleField.getText());
            save();
        }
    }

    class AccountListener extends AbstractMultiListener {

        public void focusLost(FocusEvent e) {

            this.save();
        }

        private void save() {

            Account account = comboAccount.getValue();
            (new UpdateAccountModel(account)).start();
        }
    }

    class UpdateAccountModel extends Thread {

        private Account account;

        public UpdateAccountModel(Account account) {

            this.account = account;
        }

        public void run() {

            if (account.id() > 0) {
                transaction.account(account);
                save();
            }
        }
    }

    class CategoryListener extends AbstractMultiListener {

        public void focusLost(FocusEvent e) {

            this.save();
        }

        private void save() {

            Category category = comboCategory.getValue();
            (new UpdateCategoryModel(category)).start();
        }
    }

    class UpdateCategoryModel extends Thread {

        private Category category;

        public UpdateCategoryModel(Category category) {

            this.category = category;
        }

        public void run() {

            if (category.id() > 0) {
                transaction.category(category);
                save();
            }
        }
    }

    class SubCategoryListener extends AbstractMultiListener {

        public void focusLost(FocusEvent e) {

            this.save();
        }

        private void save() {

            SubCategory subCategory = comboSubCategory.getValue();
            (new UpdateSubCategoryModel(subCategory)).start();
        }
    }

    class UpdateSubCategoryModel extends Thread {

        private SubCategory subCategory;

        public UpdateSubCategoryModel(SubCategory subCategory) {

            this.subCategory = subCategory;
        }

        public void run() {

            if (subCategory.id() > 0) {
                transaction.subCategory(subCategory);
                save();
            }
        }
    }
}
