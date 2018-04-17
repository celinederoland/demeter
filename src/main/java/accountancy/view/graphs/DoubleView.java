package accountancy.view.graphs;

import accountancy.model.selection.TwoAxesSelection;

public abstract class DoubleView extends View {

    protected TwoAxesSelection selections;

    public DoubleView(TwoAxesSelection selections) {

        super();
        this.selections = selections;
    }
}
