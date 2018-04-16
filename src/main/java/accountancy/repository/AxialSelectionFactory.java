package accountancy.repository;

import accountancy.model.selection.AxialSelection;

import java.util.LinkedHashMap;

public interface AxialSelectionFactory {

    LinkedHashMap<String, AxialSelection> selections();
}
