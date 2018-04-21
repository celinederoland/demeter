package accountancy.server;

import accountancy.model.selection.Criteria;
import accountancy.repository.Selection;
import accountancy.repository.sql.SqlSelection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SelectionTransactions extends AppServlet {

    /**
     * Route POST /selection/transactions
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {

            Criteria  criteria  = gson.fromJson(request.getReader(), Criteria.class);
            Selection selection = new SqlSelection(connectionProvider, repository, criteria);
            return selection.getTransactions();
        });
    }
}
