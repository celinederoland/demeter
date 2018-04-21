package accountancy.server;

import accountancy.model.selection.Criteria;
import accountancy.repository.sql.SqlSelection;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class SelectionAmounts extends AppServlet {

    /**
     * Route POST /selection/amounts
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {

            JsonParser parser = new JsonParser();
            JsonObject json   = parser.parse(request.getReader()).getAsJsonObject();

            JsonObject   jsonCriteria = json.get("criteria").getAsJsonObject();
            Criteria     criteria     = gson.fromJson(jsonCriteria, Criteria.class);
            Date         start        = new Date(json.get("start").getAsLong() * 1000);
            Date         end          = new Date(json.get("end").getAsLong() * 1000);
            SqlSelection selection    = new SqlSelection(connectionProvider, repository, criteria);
            return selection.getAmount(start, end);
        });
    }
}
