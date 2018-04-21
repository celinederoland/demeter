package accountancy.model.selection;

import accountancy.repository.rest.RestSelectionTest;
import org.junit.Before;
import org.junit.Test;

public class AxialRestSelectionTest extends RestSelectionTest {

    public AxialRestSelectionTest() throws Exception {

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