package accountancy.repository;

import accountancy.model.base.Transaction;

import java.util.ArrayList;
import java.util.Date;

public interface Selection {

    ArrayList<Transaction> getEntries();

    double getAmount(Date start, Date end);
}
