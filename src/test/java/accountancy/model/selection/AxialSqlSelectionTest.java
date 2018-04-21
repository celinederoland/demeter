package accountancy.model.selection;

import accountancy.repository.sql.SqlSelectionTest;
import org.junit.Before;
import org.junit.Test;

public class AxialSqlSelectionTest extends SqlSelectionTest {

    public AxialSqlSelectionTest() throws Exception {

        super();
    }

    @Before
    @Override public void setUp() throws Exception {

        super.setUp();
    }

    @Test
    public void oneAxe() throws Exception {

        (new AnyAxialSelection(repository, provider)).oneAxe();
    }

    @Test
    public void twoAxes() throws Exception {

        (new AnyAxialSelection(repository, provider)).twoAxes();
    }

}