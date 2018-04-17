package accountancy.view.graphs;

import accountancy.model.selection.OneAxeSelection;

public abstract class SimpleView extends View {

    protected OneAxeSelection selections;

    public SimpleView(OneAxeSelection selections) {

        super();
        this.selections = selections;
    }
}
