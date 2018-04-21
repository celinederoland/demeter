package accountancy.model.selection;

import accountancy.model.base.*;
import accountancy.repository.BaseRepository;
import accountancy.repository.Selection;
import accountancy.repository.SelectionProvider;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class AnyAxialSelection {

    protected BaseRepository    repository;
    protected SelectionProvider provider;

    public AnyAxialSelection(BaseRepository repository, SelectionProvider provider) {

        this.repository = repository;
        this.provider = provider;
    }

    public void oneAxe() throws Exception {

        Criteria criteria1 = (new Criteria()).addAccount((Account) repository.accounts().getOne(1))
                                             .addAccount((Account) repository.accounts().getOne(6))
                                             .addCategory((Category) repository.categories().getOne(2));
        Selection selection1 = provider.makeSelection(criteria1);


        Criteria criteria2 = (new Criteria()).addBank((Bank) repository.banks().getOne(2))
                                             .excludeType((Type) repository.types().getOne(2))
                                             .excludeCurrency((Currency) repository.currencies().getOne(3))
                                             .setAbsolute()
                                             .setCumulative();
        Selection selection2 = provider.makeSelection(criteria2);

        Criteria criteria3 = (new Criteria())
            .excludeSubCategory((SubCategory) ((Category) repository.categories().getOne(3)).subCategories().getOne(9))
            .excludeSubCategory((SubCategory) ((Category) repository.categories().getOne(3)).subCategories().getOne(8))
            .excludeCategory((Category) repository.categories().getOne(1))
            .excludeCategory((Category) repository.categories().getOne(2))
            .setCumulative();
        Selection selection3 = provider.makeSelection(criteria3);


        OneAxeSelection oneAxeSelection = new OneAxeSelection(Styles.BAR);
        oneAxeSelection.add("One", selection1);
        oneAxeSelection.add("Two", selection2);
        oneAxeSelection.add("Three", selection3);

        assertEquals(Styles.BAR, oneAxeSelection.style());

        LinkedHashMap<String, ArrayList<Transaction>> transactions = oneAxeSelection.transactions();

        assertEquals(3, transactions.keySet().size());
        assertEquals(5, transactions.get("One").size());
        assertEquals(17, transactions.get("Two").size());
        assertEquals(14, transactions.get("Three").size());

        LinkedHashMap<String, ArrayList<AmountByDate>> amounts = oneAxeSelection.amounts();
        assertEquals(3, amounts.keySet().size());

        double amount1 = 0;
        for (AmountByDate amountByDate : amounts.get("One")) {
            amount1 += amountByDate.amount();
        }
        assertEquals(1576.45, amount1, 0);

        double amount2 = 0;
        for (AmountByDate amountByDate : amounts.get("Two")) {
            amount2 = amountByDate.amount();
        }
        assertEquals(656.77, amount2, 0);

        double amount3 = 0;
        for (AmountByDate amountByDate : amounts.get("Three")) {
            amount3 = amountByDate.amount();
        }
        assertEquals(-1215.44, amount3, 0);
    }

    public void twoAxes() throws Exception {

        Criteria criteria1 = (new Criteria()).addAccount((Account) repository.accounts().getOne(1))
                                             .addAccount((Account) repository.accounts().getOne(6))
                                             .addCategory((Category) repository.categories().getOne(2));
        Selection selection1 = provider.makeSelection(criteria1);


        Criteria criteria2 = (new Criteria()).addBank((Bank) repository.banks().getOne(2))
                                             .excludeType((Type) repository.types().getOne(2))
                                             .excludeCurrency((Currency) repository.currencies().getOne(3))
                                             .setAbsolute()
                                             .setCumulative();
        Selection selection2 = provider.makeSelection(criteria2);

        Criteria criteria3 = (new Criteria())
            .excludeSubCategory((SubCategory) ((Category) repository.categories().getOne(3)).subCategories().getOne(9))
            .excludeSubCategory((SubCategory) ((Category) repository.categories().getOne(3)).subCategories().getOne(8))
            .excludeCategory((Category) repository.categories().getOne(1))
            .excludeCategory((Category) repository.categories().getOne(2))
            .setCumulative();
        Selection selection3 = provider.makeSelection(criteria3);


        OneAxeSelection oneAxeSelection1 = new OneAxeSelection(Styles.LINE);
        oneAxeSelection1.add("One", selection1);
        oneAxeSelection1.add("Two", selection2);

        OneAxeSelection oneAxeSelection2 = new OneAxeSelection(Styles.LINE);
        oneAxeSelection2.add("Three", selection3);

        TwoAxesSelection twoAxesSelection = new TwoAxesSelection(Styles.LINE);
        twoAxesSelection.add("Axe 1", oneAxeSelection1);
        twoAxesSelection.add("Axe 2", oneAxeSelection2);

        assertEquals(Styles.LINE, twoAxesSelection.style());

        LinkedHashMap<String, LinkedHashMap<String, ArrayList<AmountByDate>>> amounts = twoAxesSelection.amounts();
        LinkedHashMap<String, LinkedHashMap<String, ArrayList<Transaction>>> transactions =
            twoAxesSelection.transactions();

        assertEquals(2, twoAxesSelection.amounts().keySet().size());
        assertEquals(2, twoAxesSelection.transactions().keySet().size());

        assertEquals(2, transactions.get("Axe 1").keySet().size());
        assertEquals(1, transactions.get("Axe 2").keySet().size());
        assertEquals(5, transactions.get("Axe 1").get("One").size());
        assertEquals(17, transactions.get("Axe 1").get("Two").size());
        assertEquals(14, transactions.get("Axe 2").get("Three").size());

        double amount1 = 0;
        for (AmountByDate amountByDate : amounts.get("Axe 1").get("One")) {
            amount1 += amountByDate.amount();
        }
        assertEquals(1576.45, amount1, 0);

        double amount2 = 0;
        for (AmountByDate amountByDate : amounts.get("Axe 1").get("Two")) {
            amount2 = amountByDate.amount();
        }
        assertEquals(656.77, amount2, 0);

        double amount3 = 0;
        for (AmountByDate amountByDate : amounts.get("Axe 2").get("Three")) {
            amount3 = amountByDate.amount();
        }
        assertEquals(-1215.44, amount3, 0);
    }
}
