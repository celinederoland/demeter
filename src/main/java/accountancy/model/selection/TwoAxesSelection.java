package accountancy.model.selection;

import accountancy.model.base.Transaction;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Represent a set of points of view on data (ie : a set of criteria / selection)
 * <p>
 * On a 1 axe selection, criteria are structures in a flat list like in this example :
 * - criteria 1 : all transactions from bank A
 * - criteria 2 : all transactions from bank B
 * - representation style : line
 * <p>
 * On a 2 axes selection, criteria are structured in a 1 depth tree like in this example :
 * - criteria 1.a : all transactions from bank A with positive amount
 * - criteria 1.b : all transactions from bank A with negative amount
 * - criteria 2.a : all transactions from bank B with positive amount
 * - criteria 2.b : all transactions from bank B with negative amount
 * - representation style : line
 * <p>
 * We can see the 2 axes selection as a list of 1 axe selections
 */
public class TwoAxesSelection extends AxialSelection {

    private final LinkedHashMap<String, OneAxeSelection> selections = new LinkedHashMap<>();

    public TwoAxesSelection(Styles style) {

        super(style);
    }

    /**
     * Add a new 1 axe selection
     *
     * @param name      textual representation of this 1 axe selection
     * @param selection the 1 axe selection
     *
     * @return TwoAxesSelection
     */
    public TwoAxesSelection add(String name, OneAxeSelection selection) {

        selections.put(name, selection);
        return this;
    }

    /**
     * Get all amounts for each selection, organized by date
     *
     * @return {
     * ----'1st 1 axe selection name' => {
     * --------'1st criteria name' => { {date => amount}, {date2 => amount2}, ...},
     * --------'2nd criteria name' => { {date => amount}, {date2 => amount2}, ...},
     * --------...
     * ----},
     * ----'2nd 1 axe selection name' => {
     * --------'1st criteria name' => { {date => amount}, {date2 => amount2}, ...},
     * --------'2nd criteria name' => { {date => amount}, {date2 => amount2}, ...},
     * --------...
     * ----},
     * ----...
     * ----}
     */
    public LinkedHashMap<String, LinkedHashMap<String, ArrayList<AmountByDate>>> amounts() {

        LinkedHashMap<String, LinkedHashMap<String, ArrayList<AmountByDate>>> data = new LinkedHashMap<>();

        for (String name : selections.keySet()) {

            data.put(name, selections.get(name).amounts());
        }

        return data;
    }

    /**
     * Get all transactions matching each criteria
     *
     * @return LinkedHashMap : {
     * ----'1st 1 axe selection name' => {
     * --------'1st criteria name' => { transaction1, transaction2, ...},
     * --------'2nd criteria name' => { transaction1, transaction2, ...},
     * --------...
     * --------}
     * ----},
     * ----'2nd 1 axe selection name' => {
     * --------'1st criteria name' => { transaction1, transaction2, ...},
     * --------'2nd criteria name' => { transaction1, transaction2, ...},
     * --------...
     * --------}
     * ----},
     */
    public LinkedHashMap<String, LinkedHashMap<String, ArrayList<Transaction>>> transactions() {

        LinkedHashMap<String, LinkedHashMap<String, ArrayList<Transaction>>> data = new LinkedHashMap<>();
        for (String name : selections.keySet()) {

            OneAxeSelection selection = selections.get(name);
            data.put(name, selection.transactions());
        }

        return data;
    }
}
