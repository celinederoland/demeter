package accountancy.repository.rest;

import accountancy.model.base.Transaction;
import accountancy.model.selection.Criteria;
import accountancy.repository.BaseRepository;
import accountancy.repository.Selection;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class RestSelection implements Selection {

    private final BaseRepository repository;
    private final Criteria       criteria;
    private       Requester      requester;

    public RestSelection(String url, BaseRepository repository, Criteria criteria) {

        this.repository = repository;
        this.requester = new Requester(url, repository);
        this.criteria = criteria;
    }

    @Override public ArrayList<Transaction> getTransactions() {

        String response = this.requester.executePost("/selection/transactions", criteria);
        assert response != null;
        JsonParser             parser       = new JsonParser();
        JsonArray              json         = parser.parse(response).getAsJsonArray();
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (JsonElement jsonTransaction : json) {
            int id = jsonTransaction.getAsJsonObject().get("id").getAsInt();
            transactions.add(repository.find(new Transaction(id)));
        }
        return transactions;
    }

    @Override public double getAmount(Date start, Date end) {

        HashMap<String, Object> request = new HashMap<>();
        request.put("criteria", criteria);
        request.put("start", start);
        request.put("end", end);
        String response = this.requester.executePost("/selection/amounts", request);
        assert response != null;
        JsonParser parser = new JsonParser();
        return parser.parse(response).getAsDouble();
    }
}
