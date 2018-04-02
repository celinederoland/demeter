package accountancy.model.selection;

import accountancy.model.base.Transaction;
import accountancy.repository.Selection;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Represent a set of points of view on data (ie : a set of criteria / selection)
 * Example :
 * - criteria 1 : all transactions from bank A with positive amount
 * - criteria 2 : all transactions from bank A with negative amount
 * - representation style : line
 * => This point of view will have to be represented as a linear graph,
 * one line will represent the amount matching criteria 1, by date
 * the other line will represent the amount matching criteria 2, by date
 */
public class OneAxeSelection extends AxialSelection {

    private LinkedHashMap<String, Selection> selections = new LinkedHashMap<>();

    public OneAxeSelection(Styles style) {

        super(style);
    }

    /**
     * add new criteria/selection to the set of points of view
     *
     * @param name      criteria's textual representation
     * @param selection one selection associated to the criteria
     *
     * @return OneAxeSelection
     */
    public OneAxeSelection add(String name, Selection selection) {

        selections.put(name, selection);
        return this;
    }

    /**
     * Get all amounts for each selection, organized by date
     *
     * @return LinkedHashMap : {
     * ----'1st criteria name' => { {date => amount}, {date2 => amount2}, ...},
     * ----'2nd criteria name' => { {date => amount}, {date2 => amount2}, ...},
     * ----...
     * ----}
     */
    public LinkedHashMap<String, ArrayList<AmountByDate>> amounts() {

        LinkedHashMap<String, ArrayList<AmountByDate>> datas = new LinkedHashMap<>();
        for (String name : selections.keySet()) {

            Selection               selection = selections.get(name);
            ArrayList<AmountByDate> amounts   = new ArrayList<>();
            DatesIterator           iterator  = new DatesIterator();
            while (iterator.hasNext()) {

                amounts.add(new AmountByDate(iterator.current(), iterator.next(), selection));
            }

            datas.put(name, amounts);
        }

        return datas;
    }

    /**
     * Get all transactions matching each criteria
     *
     * @return LinkedHashMap : {
     * ----'1st criteria name' => { transaction1, transaction2, ...},
     * ----'2nd criteria name' => { transaction1, transaction2, ...},
     * ----...
     * ----}
     */
    public LinkedHashMap<String, ArrayList<Transaction>> transactions() {

        LinkedHashMap<String, ArrayList<Transaction>> datas = new LinkedHashMap<>();
        for (String name : selections.keySet()) {

            Selection selection = selections.get(name);
            datas.put(name, selection.getEntries());
        }

        return datas;
    }
}
