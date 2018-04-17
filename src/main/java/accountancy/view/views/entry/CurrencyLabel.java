package accountancy.view.views.entry;


import accountancy.framework.Observer;
import accountancy.model.base.Account;
import accountancy.view.components.PLabel;

public class CurrencyLabel extends PLabel implements Observer {

    private Account account;

    public CurrencyLabel(Account account) {

        super("");
        this.account = account;
        account.addObserver(this);
        this.update();
    }

    @Override public void update() {

        this.setText(account.currency().title());
    }

    public void setAccount(Account account) {

        if (this.account != account) {
            this.account = account;
            account.addObserver(this);
            this.update();
        }
    }
}
