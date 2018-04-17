package accountancy.repository.selection;

import accountancy.model.Entity;
import accountancy.model.base.*;
import accountancy.model.selection.*;
import accountancy.repository.AxialSelectionFactory;
import accountancy.repository.BaseRepository;
import accountancy.repository.Selection;
import accountancy.repository.SelectionProvider;

import java.util.LinkedHashMap;

public class MySelectionFactory implements AxialSelectionFactory {

    protected final BaseRepository    repository;
    protected final SelectionProvider selectionFactory;

    public MySelectionFactory(SelectionProvider selectionFactory, BaseRepository repository) {

        this.selectionFactory = selectionFactory;
        this.repository = repository;
    }

    @Override public LinkedHashMap<String, AxialSelection> selections() {

        if (repository.categories().getOne("Transferts") == null)
            repository.categories().add(new Category(0, "Transferts"));
        int categoryTransfer = repository.categories().getOne("Transferts").id();

        if (repository.categories().getOne("Revenus") == null)
            repository.categories().add(new Category(0, "Revenus"));
        int categoryRevenus = repository.categories().getOne("Revenus").id();

        if (repository.types().getOne("Epargne") == null)
            repository.types().add(new Type(0, "Epargne"));
        int typeEpargne = repository.types().getOne("Epargne").id();

        if (repository.types().getOne("Crédit") == null)
            repository.types().add(new Type(0, "Crédit"));
        int typeCredit = repository.types().getOne("Crédit").id();

        LinkedHashMap<String, AxialSelection> selections = new LinkedHashMap<>();

        OneAxeSelection inOut = new OneAxeSelection(Styles.BAR);
        Selection revenus = selectionFactory.makeSelection(
            (new Criteria())
                .excludeCategory((Category) repository.categories().getOne(categoryTransfer))
                .excludeType((Type) repository.types().getOne(typeCredit))
                .setPositiveOnly()
        );
        Selection epargne = selectionFactory.makeSelection(
            (new Criteria())
                .addType((Type) repository.types().getOne(typeEpargne))
        );
        Selection expenditures = selectionFactory.makeSelection(
            (new Criteria())
                .excludeCategory((Category) repository.categories().getOne(categoryTransfer))
                .excludeType((Type) repository.types().getOne(typeEpargne))
                .excludeType((Type) repository.types().getOne(typeCredit))
                .setNegativeOnly()
                .setAbsolute()
        );
        inOut.add("Revenus", revenus);
        inOut.add("Epargne", epargne);
        inOut.add("Dépenses", expenditures);

        selections.put("IO", inOut);

        OneAxeSelection debts = new OneAxeSelection(Styles.PIE);
        Selection due = selectionFactory.makeSelection(
            (new Criteria())
                .addType((Type) repository.types().getOne(typeCredit))
                .setCumulative()
        );
        Selection have = selectionFactory.makeSelection(
            (new Criteria())
                .excludeType((Type) repository.types().getOne(typeCredit))
                .setCumulative()
        );
        debts.add("Dettes", due);
        debts.add("Avoirs", have);
        selections.put("Dettes", debts);

        OneAxeSelection soldes = new OneAxeSelection(Styles.LINE);
        for (Entity currency : repository.currencies().getAll()) {
            Selection solde = selectionFactory.makeSelection(
                (new Criteria())
                    .addCurrency((Currency) currency)
                    .excludeType((Type) repository.types().getOne(typeCredit))
                    .setCumulative()
            );
            soldes.add(currency.title(), solde);
        }
        selections.put("Soldes", soldes);


        OneAxeSelection categories = new OneAxeSelection(Styles.STACK);
        for (Entity category : repository.categories().getAll()) {
            if (category.id() != categoryRevenus && category.id() != categoryTransfer) {
                Selection selection = selectionFactory.makeSelection(
                    (new Criteria())
                        .addCategory((Category) category)
                        .excludeType((Type) repository.types().getOne(typeCredit))
                        .setNegativeOnly()
                        .setAbsolute()
                );
                categories.add(category.title(), selection);
            }
        }
        selections.put("Catégories", categories);

        TwoAxesSelection subCategories = new TwoAxesSelection(Styles.PIE);
        for (Entity category : repository.categories().getAll()) {
            if (category.id() != categoryRevenus && category.id() != categoryTransfer) {
                OneAxeSelection categorySubs = new OneAxeSelection(Styles.PIE);
                for (Entity subCategory : ((Category) category).subCategories().getAll()) {
                    Selection selection = selectionFactory.makeSelection(
                        (new Criteria())
                            .addSubCategory((SubCategory) subCategory)
                            .excludeType((Type) repository.types().getOne(typeCredit))
                            .setNegativeOnly()
                            .setAbsolute()
                    );
                    categorySubs.add(subCategory.title(), selection);
                }
                subCategories.add(category.title(), categorySubs);
            }
        }
        selections.put("Sous Catégories", subCategories);

        OneAxeSelection accounts = new OneAxeSelection(Styles.LINE);
        for (Entity account : repository.accounts().getAll()) {
            Selection selection = selectionFactory.makeSelection(
                (new Criteria())
                    .addAccount((Account) account)
                    .setCumulative()
            );
            accounts.add(account.toString(), selection);
        }
        selections.put("Comptes", accounts);

        return selections;
    }
}
