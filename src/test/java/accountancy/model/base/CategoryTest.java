package accountancy.model.base;

import accountancy.model.Entity;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

    @Test
    public void subCategories() throws Exception {


        Category category = new Category(12, "invoices");
        category.subCategories().add(new SubCategory(1, "energy"));
        category.subCategories().add(new SubCategory(2, "communications"));
        category.subCategories().add(new SubCategory(3, "taxes"));

        ArrayList<Entity> subCategories = category.subCategories().getAll();
        assertEquals(3, subCategories.size());

    }

}