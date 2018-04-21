package accountancy.repository.rest;

import accountancy.model.Json;
import accountancy.model.base.Transaction;
import accountancy.repository.BaseRepository;
import accountancy.repository.CsvImportRepository;
import accountancy.server.response.BoolResponse;

import java.util.HashMap;

public class RestCsvImportRepository implements CsvImportRepository {

    private Requester      requester;
    private BaseRepository repository;

    public RestCsvImportRepository(String url, BaseRepository repository) {

        this.requester = new Requester(url, repository);
        this.repository = repository;
    }

    @Override public boolean csvLineHasBeenImported(String dateField, String textField, double amount) {

        HashMap<String, Object> request = new HashMap<>();
        request.put("date", dateField);
        request.put("text", textField);
        request.put("amount", amount);
        String response = this.requester.executePost("/csv", request);
        assert response != null;
        return Json.gson(repository).fromJson(response, BoolResponse.class).bool();
    }

    @Override public void saveCsvImport(
        String dateField, String textField, double amount, Transaction transaction
    ) {

        HashMap<String, Object> request = new HashMap<>();
        request.put("date", dateField);
        request.put("text", textField);
        request.put("amount", amount);
        request.put("transaction", transaction);
        String response = this.requester.executePut("/csv", request);
        assert response != null;
    }
}
