package accountancy.server;

import accountancy.model.Entity;
import accountancy.model.base.Category;
import accountancy.model.base.SubCategory;
import accountancy.server.errors.HttpError;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class SubCategories extends AppServlet {

    /**
     * Route GET /subcategory/{id}
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int         id          = Integer.parseInt(request.getPathInfo().substring(1));
        SubCategory subCategory = repository.find(new SubCategory(id, ""));
        Category    category    = null;
        for (Entity entity : repository.categories().getAll()) {

            if (((Category) entity).subCategories().getOne(subCategory.id()) != null) {
                category = (Category) entity;
            }
        }

        HashMap<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("subcategory", subCategory);
        jsonResponse.put("category", category);
        response.getWriter().println(gson.toJson(jsonResponse));
    }

    /**
     * Route POST /subcategory
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        SubCategory subCategory = gson.fromJson(request.getReader(), SubCategory.class);

        if (subCategory.id() == 0) {
            new HttpError(403, "ResourceDoesntExist - use PUT method instead", response);
            return;
        }

        repository.save(subCategory);

        response.getWriter().println(gson.toJson(subCategory));
    }

    /**
     * Route PUT /subcategory
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        JsonParser parser = new JsonParser();
        JsonObject json   = parser.parse(request.getReader()).getAsJsonObject();

        JsonObject jsonCategory  = json.get("category").getAsJsonObject();
        int        idCategory    = jsonCategory.get("id").getAsInt();
        String     titleCategory = jsonCategory.get("title").getAsString();
        Category   category      = new Category(idCategory, titleCategory);

        JsonObject  jsonSubCategory  = json.get("subcategory").getAsJsonObject();
        String      titleSubCategory = jsonSubCategory.get("title").getAsString();
        SubCategory subCategory      = new SubCategory(0, titleSubCategory);

        if (subCategory.id() > 0) {
            new HttpError(403, "ResourceAlreadyExist - use POST method instead", response);
            return;
        }

        subCategory = repository.create(subCategory, category);

        response.getWriter().println(gson.toJson(subCategory));
    }
}
