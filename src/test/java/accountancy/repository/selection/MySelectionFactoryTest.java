package accountancy.repository.selection;

import accountancy.model.base.Transaction;
import accountancy.model.selection.AmountByDate;
import accountancy.model.selection.AxialSelection;
import accountancy.model.selection.OneAxeSelection;
import accountancy.model.selection.TwoAxesSelection;
import accountancy.repository.BaseRepository;
import accountancy.repository.RepositoryTest;
import accountancy.repository.SelectionProvider;
import accountancy.repository.sql.ScriptRunner;
import accountancy.repository.sql.SqlBaseRepository;
import accountancy.repository.sql.SqlSelectionProvider;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class MySelectionFactoryTest extends RepositoryTest {

    protected BaseRepository    repository;
    protected SelectionProvider provider;

    public MySelectionFactoryTest() throws Exception {

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

        String file2 = "datas/fixture-myselection.sql";
        scriptRunner.runScript(new BufferedReader(new FileReader(file2)));

        repository = new SqlBaseRepository(connectionProvider);
        repository.findAll();

        provider = new SqlSelectionProvider(connectionProvider, repository);
    }

    @Test
    public void selections() throws Exception {

        MySelectionFactory                    factory    = new MySelectionFactory(provider, repository);
        LinkedHashMap<String, AxialSelection> selections = factory.selections();

        assertEquals(6, selections.keySet().size());

        OneAxeSelection ioView = (OneAxeSelection) selections.get("IO");
        LinkedHashMap<String, ArrayList<Transaction>> ioTransactions =
            ioView.transactions();
        LinkedHashMap<String, ArrayList<AmountByDate>> ioAmounts = ioView.amounts();

        ArrayList<Transaction> incomeTransactions = ioTransactions.get("Revenus");
        assertEquals(6, incomeTransactions.size());
        ArrayList<AmountByDate> incomeAmounts = ioAmounts.get("Revenus");
        assertEquals(13034.65, incomeAmounts.get(7).amount(), 0);
        assertEquals(6224.92, incomeAmounts.get(8).amount(), 0);

        ArrayList<Transaction> savingTransactions = ioTransactions.get("Epargne");
        assertEquals(3, savingTransactions.size());
        ArrayList<AmountByDate> savingAmounts = ioAmounts.get("Epargne");
        assertEquals(283.99, savingAmounts.get(7).amount(), 0);
        assertEquals(275.89, savingAmounts.get(8).amount(), 0);

        ArrayList<Transaction> expenditureTransactions = ioTransactions.get("Dépenses");
        assertEquals(25, expenditureTransactions.size());
        ArrayList<AmountByDate> expenditureAmounts = ioAmounts.get("Dépenses");
        assertEquals(2687.48, expenditureAmounts.get(7).amount(), 0);
        assertEquals(1490.24, expenditureAmounts.get(8).amount(), 0);

        OneAxeSelection soldesView = (OneAxeSelection) selections.get("Soldes");
        LinkedHashMap<String, ArrayList<Transaction>> soldesTransactions =
            soldesView.transactions();
        LinkedHashMap<String, ArrayList<AmountByDate>> soldesAmounts = soldesView.amounts();

        ArrayList<Transaction> balanceEURTransactions = soldesTransactions.get("EUR");
        assertEquals(27, balanceEURTransactions.size());
        ArrayList<AmountByDate> balanceEURAmounts = soldesAmounts.get("EUR");
        assertEquals(3981.5, balanceEURAmounts.get(7).amount(), 0);
        assertEquals(4826.6, balanceEURAmounts.get(8).amount(), 0);

        ArrayList<Transaction> balanceUSDTransactions = soldesTransactions.get("USD");
        assertEquals(13, balanceUSDTransactions.size());
        ArrayList<AmountByDate> balanceUSDAmounts = soldesAmounts.get("USD");
        assertEquals(2392.66, balanceUSDAmounts.get(7).amount(), 0);
        assertEquals(3368.68, balanceUSDAmounts.get(8).amount(), 0);

        ArrayList<Transaction> balanceCHFTransactions = soldesTransactions.get("CHF");
        assertEquals(0, balanceCHFTransactions.size());
        ArrayList<AmountByDate> balanceCHFAmounts = soldesAmounts.get("CHF");
        assertEquals(0, balanceCHFAmounts.get(7).amount(), 0);
        assertEquals(0, balanceCHFAmounts.get(8).amount(), 0);

        OneAxeSelection debtsView = (OneAxeSelection) selections.get("Dettes");
        LinkedHashMap<String, ArrayList<Transaction>> debtsTransactions =
            debtsView.transactions();
        LinkedHashMap<String, ArrayList<AmountByDate>> debtsAmounts = debtsView.amounts();

        ArrayList<Transaction> balanceTransactions = debtsTransactions.get("Avoirs");
        assertEquals(40, balanceTransactions.size());
        ArrayList<AmountByDate> balanceAmounts = debtsAmounts.get("Avoirs");
        assertEquals(6374.16, balanceAmounts.get(7).amount(), 0);
        assertEquals(8195.28, balanceAmounts.get(8).amount(), 0);

        ArrayList<Transaction> balanceDebtsTransactions = debtsTransactions.get("Dettes");
        assertEquals(10, balanceDebtsTransactions.size());
        ArrayList<AmountByDate> balanceDebtsAmounts = debtsAmounts.get("Dettes");
        assertEquals(-141156.33, balanceDebtsAmounts.get(7).amount(), 0);
        assertEquals(-137070.67, balanceDebtsAmounts.get(8).amount(), 0);

        OneAxeSelection categoriesView = (OneAxeSelection) selections.get("Catégories");
        LinkedHashMap<String, ArrayList<Transaction>> categoriesTransactions =
            categoriesView.transactions();
        LinkedHashMap<String, ArrayList<AmountByDate>> categoriesAmounts = categoriesView.amounts();

        ArrayList<Transaction> othersTransactions = categoriesTransactions.get("Autres");
        assertEquals(10, othersTransactions.size());
        ArrayList<AmountByDate> othersAmounts = categoriesAmounts.get("Autres");
        assertEquals(1473.77, othersAmounts.get(7).amount(), 0);
        assertEquals(932.3, othersAmounts.get(8).amount(), 0);

        ArrayList<Transaction> billsTransactions = categoriesTransactions.get("Factures");
        assertEquals(15, billsTransactions.size());
        ArrayList<AmountByDate> billsAmounts = categoriesAmounts.get("Factures");
        assertEquals(1213.71, billsAmounts.get(7).amount(), 0);
        assertEquals(557.94, billsAmounts.get(8).amount(), 0);

        TwoAxesSelection subCategoriesView = (TwoAxesSelection) selections.get("Sous Catégories");
        LinkedHashMap<String, LinkedHashMap<String, ArrayList<Transaction>>> subCategoriesTransactions =
            subCategoriesView.transactions();
        LinkedHashMap<String, LinkedHashMap<String, ArrayList<AmountByDate>>> subCategoriesAmounts =
            subCategoriesView.amounts();

        LinkedHashMap<String, ArrayList<Transaction>> othersCategoryTransactions = subCategoriesTransactions.get(
            "Autres");
        LinkedHashMap<String, ArrayList<AmountByDate>> othersCategoryAmounts = subCategoriesAmounts.get("Autres");

        ArrayList<Transaction> othersATransactions = othersCategoryTransactions.get("Autres-A");
        assertEquals(6, othersATransactions.size());
        ArrayList<AmountByDate> othersAAmounts = othersCategoryAmounts.get("Autres-A");
        assertEquals(254.8, othersAAmounts.get(7).amount(), 0);
        assertEquals(289.17, othersAAmounts.get(8).amount(), 0);

        ArrayList<Transaction> othersBTransactions = othersCategoryTransactions.get("Autres-B");
        assertEquals(1, othersBTransactions.size());
        ArrayList<AmountByDate> othersBAmounts = othersCategoryAmounts.get("Autres-B");
        assertEquals(0, othersBAmounts.get(7).amount(), 0);
        assertEquals(152.81, othersBAmounts.get(8).amount(), 0);

        ArrayList<Transaction> othersCTransactions = othersCategoryTransactions.get("Autres-C");
        assertEquals(3, othersCTransactions.size());
        ArrayList<AmountByDate> othersCAmounts = othersCategoryAmounts.get("Autres-C");
        assertEquals(1218.97, othersCAmounts.get(7).amount(), 0);
        assertEquals(490.32, othersCAmounts.get(8).amount(), 0);

        LinkedHashMap<String, ArrayList<Transaction>> billsCategoryTransactions = subCategoriesTransactions.get(
            "Factures");
        LinkedHashMap<String, ArrayList<AmountByDate>> billsCategoryAmounts = subCategoriesAmounts.get("Factures");

        ArrayList<Transaction> billsATransactions = billsCategoryTransactions.get("Factures-A");
        assertEquals(7, billsATransactions.size());
        ArrayList<AmountByDate> billsAAmounts = billsCategoryAmounts.get("Factures-A");
        assertEquals(494.96, billsAAmounts.get(7).amount(), 0);
        assertEquals(286.72, billsAAmounts.get(8).amount(), 0);

        ArrayList<Transaction> billsBTransactions = billsCategoryTransactions.get("Factures-B");
        assertEquals(8, billsBTransactions.size());
        ArrayList<AmountByDate> billsBAmounts = billsCategoryAmounts.get("Factures-B");
        assertEquals(718.75, billsBAmounts.get(7).amount(), 0);
        assertEquals(271.22, billsBAmounts.get(8).amount(), 0);

        OneAxeSelection accountsView = (OneAxeSelection) selections.get("Comptes");
        LinkedHashMap<String, ArrayList<Transaction>> accountsTransactions =
            accountsView.transactions();
        LinkedHashMap<String, ArrayList<AmountByDate>> accountsAmounts = accountsView.amounts();

        ArrayList<Transaction> c1Transactions = accountsTransactions.get("B1 - C1");
        assertEquals(1, c1Transactions.size());
        ArrayList<AmountByDate> c1Amounts = accountsAmounts.get("B1 - C1");
        assertEquals(0, c1Amounts.get(7).amount(), 0);
        assertEquals(127.57, c1Amounts.get(8).amount(), 0);

        ArrayList<Transaction> c2Transactions = accountsTransactions.get("B1 - C2");
        assertEquals(1, c2Transactions.size());
        ArrayList<AmountByDate> c2Amounts = accountsAmounts.get("B1 - C2");
        assertEquals(283.99, c2Amounts.get(7).amount(), 0);
        assertEquals(283.99, c2Amounts.get(8).amount(), 0);

        ArrayList<Transaction> c3Transactions = accountsTransactions.get("B2 - C3");
        assertEquals(1, c3Transactions.size());
        ArrayList<AmountByDate> c3Amounts = accountsAmounts.get("B2 - C3");
        assertEquals(0, c3Amounts.get(7).amount(), 0);
        assertEquals(148.32, c3Amounts.get(8).amount(), 0);

        ArrayList<Transaction> c4ransactions = accountsTransactions.get("B1 - C4");
        assertEquals(25, c4ransactions.size());
        ArrayList<AmountByDate> c4Amounts = accountsAmounts.get("B1 - C4");
        assertEquals(3697.51, c4Amounts.get(7).amount(), 0);
        assertEquals(4415.04, c4Amounts.get(8).amount(), 0);

        ArrayList<Transaction> c5Transactions = accountsTransactions.get("B2 - C5");
        assertEquals(12, c5Transactions.size());
        ArrayList<AmountByDate> c5Amounts = accountsAmounts.get("B2 - C5");
        assertEquals(2392.66, c5Amounts.get(7).amount(), 0);
        assertEquals(3220.36, c5Amounts.get(8).amount(), 0);

        ArrayList<Transaction> c6Transactions = accountsTransactions.get("B1 - C6");
        assertEquals(3, c6Transactions.size());
        ArrayList<AmountByDate> c6Amounts = accountsAmounts.get("B1 - C6");
        assertEquals(-22040.52, c6Amounts.get(7).amount(), 0);
        assertEquals(-21550.2, c6Amounts.get(8).amount(), 0);

        ArrayList<Transaction> c7Transactions = accountsTransactions.get("B1 - C7");
        assertEquals(2, c7Transactions.size());
        ArrayList<AmountByDate> c7Amounts = accountsAmounts.get("B1 - C7");
        assertEquals(-104845.89, c7Amounts.get(7).amount(), 0);
        assertEquals(-104845.89, c7Amounts.get(8).amount(), 0);

        ArrayList<Transaction> c8Transactions = accountsTransactions.get("B2 - C8");
        assertEquals(5, c8Transactions.size());
        ArrayList<AmountByDate> c8Amounts = accountsAmounts.get("B2 - C8");
        assertEquals(-14269.92, c8Amounts.get(7).amount(), 0);
        assertEquals(-10674.58, c8Amounts.get(8).amount(), 0);
    }

}