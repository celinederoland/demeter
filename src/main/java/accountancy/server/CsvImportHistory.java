package accountancy.server;

import accountancy.model.base.Transaction;
import accountancy.server.response.BoolResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CsvImportHistory extends AppServlet {

    /**
     * Route POST /csv
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

            String dateField = json.get("date").getAsString();
            String textField = json.get("text").getAsString();
            double amount    = json.get("amount").getAsDouble();

            return new BoolResponse(csvImportRepository.csvLineHasBeenImported(dateField, textField, amount));
        });
    }

    /**
     * Route PUT /csv
     *
     * @param request  http request
     * @param response http response
     *
     * @throws IOException ioException
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        action(request, response, () -> {

            JsonParser parser = new JsonParser();
            JsonObject json   = parser.parse(request.getReader()).getAsJsonObject();

            String      dateField   = json.get("date").getAsString();
            String      textField   = json.get("text").getAsString();
            double      amount      = json.get("amount").getAsDouble();
            Transaction transaction = gson.fromJson(json.get("transaction"), Transaction.class);

            csvImportRepository.saveCsvImport(dateField, textField, amount, transaction);
            return new BoolResponse(true);
        });
    }

}
