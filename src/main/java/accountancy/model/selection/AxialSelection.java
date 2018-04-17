package accountancy.model.selection;

/**
 * Represent a set of points of view on data (ie : a set of criteria / selection)
 * Example :
 * - criteria 1 : all transactions from bank A with positive amount
 * - criteria 2 : all transactions from bank A with negative amount
 * - representation style : line
 * => This point of view will have to be represented as a linear graph,
 * one line will represent the amount matching criteria 1, by date
 * the other line will represent the amount matching criteria 2, by date
 * <p>
 * It is an abstraction : this class represents the graph we wan't to draw but it won't draw it
 * <p>
 * We call it AxialSelection because the criteria can be represented on 1 or 2 axes.
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
 */
public abstract class AxialSelection {

    private final Styles style;

    public AxialSelection(Styles style) {

        this.style = style;
    }

    public Styles style() {

        return this.style;
    }
}
