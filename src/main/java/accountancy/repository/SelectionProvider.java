package accountancy.repository;

import accountancy.model.selection.Criteria;

public interface SelectionProvider {

    Selection makeSelection(Criteria criteria);
}
