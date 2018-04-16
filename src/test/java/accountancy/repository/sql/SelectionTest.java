package accountancy.repository.sql;

import accountancy.model.base.*;
import accountancy.model.selection.AmountByDate;
import accountancy.model.selection.Criteria;
import accountancy.model.selection.DatesIterator;
import accountancy.repository.BaseRepository;
import accountancy.repository.Selection;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

@SuppressWarnings("RedundantCast") public class SelectionTest extends RepositoryTest {

    protected BaseRepository repository;

    public SelectionTest() throws Exception {

        super();
        ScriptRunner scriptRunner = new ScriptRunner(connectionProvider.getConnection(), true, true);
        String       file         = "datas/schema-base.sql";
        scriptRunner.runScript(new BufferedReader(new FileReader(file)));
    }

    @Before
    public void setUp() throws Exception {

        ScriptRunner scriptRunner = new ScriptRunner(connectionProvider.getConnection(), true, true);
        String       file         = "datas/fixture-base.sql";
        scriptRunner.runScript(new BufferedReader(new FileReader(file)));

        String file2 = "datas/fixture-selection.sql";
        scriptRunner.runScript(new BufferedReader(new FileReader(file2)));

        repository = new SqlBaseRepository(connectionProvider);
        repository.findAll();
    }

    @Test
    public void test1() throws Exception {

        //Define a criteria, check the selection transactions and amount.
        Criteria criteria = (new Criteria()).addAccount((Account) repository.accounts().getOne(1))
                                            .addAccount((Account) repository.accounts().getOne(6))
                                            .addCategory((Category) repository.categories().getOne(2));
        Selection selection = new SqlSelection(connectionProvider, repository, criteria);

        ArrayList<Transaction> transactions = selection.getTransactions();
        assertEquals(5, transactions.size());
        assertTrue(transactions.contains((Transaction) repository.transactions().getOne(20)));
        assertTrue(transactions.contains((Transaction) repository.transactions().getOne(23)));
        assertTrue(transactions.contains((Transaction) repository.transactions().getOne(43)));
        assertTrue(transactions.contains((Transaction) repository.transactions().getOne(8)));
        assertTrue(transactions.contains((Transaction) repository.transactions().getOne(41)));

        Date julyStart = (new SimpleDateFormat("yyyy-MM-dd")).parse("2017-07-01");
        Date julyEnd   = (new SimpleDateFormat("yyyy-MM-dd")).parse("2017-08-01");
        assertEquals(448.67, selection.getAmount(julyStart, julyEnd), 0);

        Date start = (new SimpleDateFormat("yyyy-MM-dd")).parse("2017-01-01");
        Date end   = (new SimpleDateFormat("yyyy-MM-dd")).parse("2018-01-01");
        assertEquals(1576.45, selection.getAmount(start, end), 0);

        DatesIterator iterator = new DatesIterator(2017, Calendar.JANUARY, 1);
        double        amount   = 0;
        while (iterator.hasNext()) {

            amount += selection.getAmount(iterator.current(), iterator.next());
        }
        assertEquals(1576.45, amount, 0);

    }

    @Test
    public void test2() throws ParseException {

        //Define a wide criteria to take have full result
        Criteria               criteria        = new Criteria();
        Selection              selection       = new SqlSelection(connectionProvider, repository, criteria);
        ArrayList<Transaction> allTransactions = selection.getTransactions();
        assertEquals(50, allTransactions.size(), 0);

        Date start = (new SimpleDateFormat("yyyy-MM-dd")).parse("2017-01-01");
        Date end   = (new SimpleDateFormat("yyyy-MM-dd")).parse("2018-01-01");

        double amountAll = selection.getAmount(start, end);
        assertEquals(607.26, amountAll, 0);
    }

    @Test
    public void test3() throws ParseException {

        //Define a criteria
        Criteria criteria = (new Criteria()).excludeAccount((Account) repository.accounts().getOne(1))
                                            .excludeAccount((Account) repository.accounts().getOne(6))
                                            .excludeCategory((Category) repository.categories().getOne(2));
        Selection selection = new SqlSelection(connectionProvider, repository, criteria);

        ArrayList<Transaction> inverseTransactions = selection.getTransactions();
        assertEquals(27, inverseTransactions.size());
        assertFalse(inverseTransactions.contains((Transaction) repository.transactions().getOne(20)));
        assertFalse(inverseTransactions.contains((Transaction) repository.transactions().getOne(23)));
        assertFalse(inverseTransactions.contains((Transaction) repository.transactions().getOne(43)));
        assertFalse(inverseTransactions.contains((Transaction) repository.transactions().getOne(8)));
        assertFalse(inverseTransactions.contains((Transaction) repository.transactions().getOne(41)));

        Date   start  = (new SimpleDateFormat("yyyy-MM-dd")).parse("2017-01-01");
        Date   end    = (new SimpleDateFormat("yyyy-MM-dd")).parse("2018-01-01");
        double amount = selection.getAmount(start, end);
        assertEquals(-713.14, amount, 0);
    }

    @Test
    public void test4() throws Exception {

        //Define a criteria
        Criteria criteria = (new Criteria()).addBank((Bank) repository.banks().getOne(2))
                                            .excludeType((Type) repository.types().getOne(2))
                                            .excludeCurrency((Currency) repository.currencies().getOne(3))
                                            .setAbsolute();
        Selection selection = new SqlSelection(connectionProvider, repository, criteria);

        ArrayList<Transaction> transactions = selection.getTransactions();
        assertEquals(17, transactions.size());

        Date   start  = (new SimpleDateFormat("yyyy-MM-dd")).parse("2017-01-01");
        Date   end    = (new SimpleDateFormat("yyyy-MM-dd")).parse("2018-01-01");
        double amount = selection.getAmount(start, end);
        assertEquals(656.77, amount, 0);
    }

    @Test
    public void test5() throws Exception {

        Criteria criteria = (new Criteria()).excludeBank((Bank) repository.banks().getOne(1))
                                            .addType((Type) repository.types().getOne(1))
                                            .addType((Type) repository.types().getOne(3))
                                            .addCurrency((Currency) repository.currencies().getOne(1))
                                            .addCurrency((Currency) repository.currencies().getOne(2));

        Selection selection = new SqlSelection(connectionProvider, repository, criteria);

        ArrayList<Transaction> transactions = selection.getTransactions();
        assertEquals(17, transactions.size());

        Date   start  = (new SimpleDateFormat("yyyy-MM-dd")).parse("2017-01-01");
        Date   end    = (new SimpleDateFormat("yyyy-MM-dd")).parse("2018-01-01");
        double amount = selection.getAmount(start, end);
        assertEquals(656.77, amount, 0);

        criteria.setPositiveOnly();
        double amountPositive = selection.getAmount(start, end);
        assertEquals(3180.59, amountPositive, 0);

        criteria.setNegativeOnly();
        double amountNegative = selection.getAmount(start, end);
        assertEquals(-2523.82, amountNegative, 0);

        assertEquals(amount, amountPositive + amountNegative, 0);

    }

    @Test
    public void test6() throws Exception {

        Criteria criteria = (new Criteria())
            .excludeSubCategory((SubCategory) ((Category) repository.categories().getOne(3)).subCategories().getOne(9))
            .excludeSubCategory((SubCategory) ((Category) repository.categories().getOne(3)).subCategories().getOne(8))
            .excludeCategory((Category) repository.categories().getOne(1))
            .excludeCategory((Category) repository.categories().getOne(2))
            .setCumulative();

        Selection selection = new SqlSelection(connectionProvider, repository, criteria);

        ArrayList<Transaction> transactions = selection.getTransactions();
        assertEquals(14, transactions.size());

        Date   start  = (new SimpleDateFormat("yyyy-MM-dd")).parse("2017-01-01");
        Date   end    = (new SimpleDateFormat("yyyy-MM-dd")).parse("2018-01-01");
        double amount = selection.getAmount(start, end);
        assertEquals(-1215.44, amount, 0);

        DatesIterator iterator       = new DatesIterator(2017, Calendar.JANUARY, 1);
        double        amountIterated = 0;
        while (iterator.hasNext()) {

            Date next = iterator.next();
            amountIterated = selection.getAmount(iterator.current(), next);
            double amountFromStart = selection.getAmount(start, next);
            assertEquals(amountFromStart, amountIterated, 0);
        }

        assertEquals(amountIterated, amount, 0);
    }

    @Test
    public void test7() throws Exception {

        Criteria criteria = (new Criteria())
            .addSubCategory((SubCategory) ((Category) repository.categories().getOne(3)).subCategories().getOne(7))
            .addSubCategory((SubCategory) ((Category) repository.categories().getOne(3)).subCategories().getOne(6))
            .setCumulative();

        Selection selection = new SqlSelection(connectionProvider, repository, criteria);

        ArrayList<Transaction> transactions = selection.getTransactions();
        assertEquals(14, transactions.size());

        Date   start  = (new SimpleDateFormat("yyyy-MM-dd")).parse("2017-01-01");
        Date   end    = (new SimpleDateFormat("yyyy-MM-dd")).parse("2018-01-01");
        double amount = selection.getAmount(start, end);
        assertEquals(-1215.44, amount, 0);

        DatesIterator iterator       = new DatesIterator(2017, Calendar.JANUARY, 1);
        double        amountIterated = 0;
        while (iterator.hasNext()) {

            Date next = iterator.next();
            amountIterated = selection.getAmount(iterator.current(), next);
        }

        assertEquals(amountIterated, amount, 0);
    }

    @Test
    public void testAmountByDate() throws Exception {

        Criteria criteria = (new Criteria())
            .addSubCategory((SubCategory) ((Category) repository.categories().getOne(3)).subCategories().getOne(7))
            .addSubCategory((SubCategory) ((Category) repository.categories().getOne(3)).subCategories().getOne(6))
            .setCumulative();

        Selection selection = new SqlSelection(connectionProvider, repository, criteria);

        Date start = (new SimpleDateFormat("yyyy-MM-dd")).parse("2017-01-01");
        Date end   = (new SimpleDateFormat("yyyy-MM-dd")).parse("2018-01-01");

        AmountByDate amountByDate = new AmountByDate(start, end, selection);

        assertEquals(-1215.44, amountByDate.amount(), 0);
        assertEquals(start, amountByDate.date());
    }
}