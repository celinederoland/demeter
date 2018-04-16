package accountancy.repository.sql;

import accountancy.model.selection.Criteria;
import accountancy.repository.BaseRepository;
import accountancy.repository.Selection;
import accountancy.repository.SelectionProvider;

public class SqlSelectionProvider extends Repository implements SelectionProvider {

    protected BaseRepository baseRepository;

    public SqlSelectionProvider(ConnectionProvider connectionProvider, BaseRepository baseRepository) {

        super(connectionProvider);
        this.baseRepository = baseRepository;
    }

    @Override public Selection makeSelection(Criteria criteria) {

        return new SqlSelection(connectionProvider, baseRepository, criteria);
    }
}
