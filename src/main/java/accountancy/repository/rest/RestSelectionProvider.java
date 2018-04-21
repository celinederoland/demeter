package accountancy.repository.rest;

import accountancy.model.selection.Criteria;
import accountancy.repository.BaseRepository;
import accountancy.repository.Selection;
import accountancy.repository.SelectionProvider;

public class RestSelectionProvider implements SelectionProvider {

    protected final BaseRepository baseRepository;
    private final   String         url;

    public RestSelectionProvider(String url, BaseRepository baseRepository) {

        this.baseRepository = baseRepository;
        this.url = url;
    }

    @Override public Selection makeSelection(Criteria criteria) {

        return new RestSelection(url, baseRepository, criteria);
    }

}
