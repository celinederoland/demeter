package accountancy.model.selection;


import accountancy.repository.Selection;

import java.util.Date;

public class AmountByDate {

    private final Date   start;
    private final double amount;

    public AmountByDate(Date start, Date end, Selection selection) {

        this.start = start;
        this.amount = selection.getAmount(start, end);
    }

    public Date date() {

        return this.start;
    }

    public double amount() {

        return amount;
    }
}
