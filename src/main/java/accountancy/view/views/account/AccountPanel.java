package accountancy.view.views.account;

import accountancy.model.base.Account;
import accountancy.model.base.Bank;
import accountancy.model.base.Currency;
import accountancy.model.base.Type;
import accountancy.observer.Observer;
import accountancy.repository.BaseRepository;
import accountancy.repository.SelectionProvider;
import accountancy.view.components.*;
import accountancy.view.config.Dimensions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;

public class AccountPanel extends PPanel implements Observer {

    private final BaseRepository    repository;
    private final SelectionProvider selectionProvider;
    private final PCombo            comboBank;
    private final PCombo            comboCurrency;
    private final PCombo            comboType;
    private final PTextField        titleField;
    private final PLabel            labelBalance;
    private       Account           account;

    public AccountPanel(Account account, BaseRepository repository, SelectionProvider selectionProvider) {

        super(0, 0, 0, 10);
        this.account = account;
        this.repository = repository;
        this.selectionProvider = selectionProvider;

        account.addObserver(this);

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setFixedSize(Dimensions.WEST_ACCOUNT);

        PPanel line1 = new PPanel();
        line1.setLayout(new BorderLayout());

        titleField = new PTextField();
        titleField.setMinimumSize(Dimensions.WEST_ACCOUNT_NAME_WIDTH);
        titleField.setPreferredSize(Dimensions.WEST_ACCOUNT_NAME);
        TitleListener titleListener = new TitleListener();
        titleField.addActionListener(titleListener);
        titleField.addFocusListener(titleListener);
        line1.add(titleField, BorderLayout.CENTER);

        this.add(line1);

        PPanel line2 = new PPanel();
        line2.setLayout(new BoxLayout(line2, BoxLayout.LINE_AXIS));

        comboBank = new ComboBank(repository);
        comboBank.setMinimumSize(Dimensions.WEST_ACCOUNT_BANK_WIDTH);
        comboBank.setPreferredSize(Dimensions.WEST_ACCOUNT_BANK);
        BankListener bankListener = new BankListener();
        comboBank.addActionListener(bankListener);
        comboBank.addFocusListener(bankListener);
        line2.add(comboBank);

        line2.add(Box.createRigidArea(new Dimension(5, 0)));

        comboCurrency = new ComboCurrency(repository);
        comboCurrency.setMinimumSize(Dimensions.WEST_ACCOUNT_CURRENCY_WIDTH);
        comboCurrency.setPreferredSize(Dimensions.WEST_ACCOUNT_CURRENCY);
        CurrencyListener currencyListener = new CurrencyListener();
        comboCurrency.addActionListener(currencyListener);
        comboCurrency.addFocusListener(currencyListener);
        line2.add(comboCurrency);

        line2.add(Box.createRigidArea(new Dimension(5, 0)));

        comboType = new ComboType(repository);
        comboType.setMinimumSize(Dimensions.WEST_ACCOUNT_TYPE_WIDTH);
        comboType.setPreferredSize(Dimensions.WEST_ACCOUNT_TYPE);
        TypeListener typeListener = new TypeListener();
        comboType.addActionListener(typeListener);
        comboType.addFocusListener(typeListener);
        line2.add(comboType);

        this.add(line2);

        PPanel line3 = new PPanel();
        line3.setLayout(new BorderLayout());

        labelBalance = new PLabel("");
        line3.setMinimumSize(Dimensions.WEST_ACCOUNT_BALANCE_WIDTH);
        line3.setPreferredSize(Dimensions.WEST_ACCOUNT_BALANCE);

        line3.add(labelBalance, BorderLayout.CENTER);

        this.add(line3);

        this.update();
    }

    @Override public void update() {

        titleField.setText(account.title());
        comboBank.setSelectedItem(account.bank());
        comboCurrency.setSelectedItem(account.currency());
        comboType.setSelectedItem(account.type());
        if (account.currency() != null)
            labelBalance.setText(
                "Solde : " + account.balance(this.selectionProvider) + " " + account.currency().toString());
    }

    public void requestFocus() {

        comboBank.requestFocus();
    }

    private void save() {

        if (account.id() > 0) {
            repository.save(account);
        }
        else if (!account.title().isEmpty() && account.currency() != null && account.bank() != null &&
                 account.type() != null) {
            account = repository.create(account);
        }
    }

    class BankListener extends AbstractMultiListener {


        public void actionPerformed(ActionEvent e) {

        }

        public void focusLost(FocusEvent e) {

            this.save();
        }

        private void save() {

            Bank bank = (Bank) comboBank.getValue();
            (new UpdateBankModel(bank)).start();
        }
    }

    class UpdateBankModel extends Thread {

        private Bank bank;

        public UpdateBankModel(Bank bank) {

            this.bank = bank;
        }

        public void run() {

            if (!bank.title().isEmpty()) {
                if (bank.id() == 0) {
                    bank = repository.create(bank);
                    if (bank.id() == 0) {
                        bank = (Bank) repository.banks().getOne(bank.title());
                    }
                }
                account.bank(bank);
                save();
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

            account.title(titleField.getText());
            save();
        }
    }

    class CurrencyListener extends AbstractMultiListener {


        public void actionPerformed(ActionEvent e) {

        }

        private void save() {

            Currency currency = (Currency) comboCurrency.getValue();
            (new UpdateCurrencyModel(currency)).start();
        }

        public void focusLost(FocusEvent e) {

            this.save();
        }
    }

    class UpdateCurrencyModel extends Thread {

        private Currency currency;

        public UpdateCurrencyModel(Currency currency) {

            this.currency = currency;
        }

        public void run() {

            if (!currency.title().isEmpty()) {
                if (currency.id() == 0) {
                    currency = repository.create(currency);
                    if (currency.id() == 0) {
                        currency = (Currency) repository.currencies().getOne(currency.title());
                    }
                }
                account.currency(currency);
                save();
            }
        }
    }

    class TypeListener extends AbstractMultiListener {


        public void actionPerformed(ActionEvent e) {

        }

        private void save() {

            Type type = (Type) comboType.getValue();
            (new UpdateTypeModel(type)).start();
        }

        public void focusLost(FocusEvent e) {

            this.save();
        }
    }

    class UpdateTypeModel extends Thread {

        private Type type;

        public UpdateTypeModel(Type type) {

            this.type = type;
        }

        public void run() {

            if (!type.title().isEmpty()) {
                if (type.id() == 0) {
                    type = repository.create(type);
                    if (type.id() == 0) {
                        type = (Type) repository.types().getOne(type.title());
                    }
                }
                account.type(type);
                save();
            }
        }
    }
}
