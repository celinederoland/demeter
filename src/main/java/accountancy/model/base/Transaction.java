package accountancy.model.base;

import accountancy.model.ObservableEntity;

import java.util.Date;

/**
 * Represent one bank operation
 */
public class Transaction extends ObservableEntity {


    private double      amount;
    private Date        date;
    private Account     account;
    private Category    category;
    private SubCategory subCategory;

    /**
     * @param id          number
     * @param title       name
     * @param amount      amount
     * @param date        date
     * @param account     bank account
     * @param category    category
     * @param subCategory subcategory
     */
    public Transaction(
        int id, String title, double amount, Date date,
        Account account, Category category, SubCategory subCategory
    ) {

        super(id, title);
        this.amount = amount;
        this.date = date;
        this.account = account;
        this.category = category;
        this.subCategory = subCategory;
    }

    /**
     * amount getter
     *
     * @return amount
     */
    public double amount() {

        return this.amount;
    }

    /**
     * amount fluent setter
     *
     * @param amount amount
     *
     * @return Transaction
     */
    public Transaction amount(double amount) {

        if (this.amount != amount) {
            this.amount = amount;
            this.publish();
        }
        return this;
    }

    /**
     * date getter
     *
     * @return date
     */
    public Date date() {

        return this.date;
    }

    /**
     * date fluent setter
     *
     * @param date date
     *
     * @return Transaction
     */
    public Transaction date(Date date) {

        if (!this.date.equals(date)) {
            this.date = date;
            this.publish();
        }
        return this;
    }

    /**
     * account getter
     *
     * @return account
     */
    public Account account() {

        return this.account;
    }

    /**
     * account fluent setter
     *
     * @param account account
     *
     * @return Transaction
     */
    public Transaction account(Account account) {

        if (!this.account.equals(account)) {
            this.account = account;
            this.publish();
        }
        return this;
    }

    /**
     * category getter
     *
     * @return category
     */
    public Category category() {

        return this.category;
    }

    /**
     * category fluent setter
     * the subcategory is automatically set to the first subcategory of the selected category
     *
     * @param category category
     *
     * @return Transaction
     */
    public Transaction category(Category category) {

        if (!this.category.equals(category)) {
            try {
                if (category.subCategories().isEmpty())
                    throw new Exception("empty category");
                this.category = category;
                this.subCategory((SubCategory) category.subCategories().getOne());
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.publish();
        }
        return this;
    }

    /**
     * subcategory fluent setter (the subcategory must be into to this transaction's category
     *
     * @param subCategory subCategory
     *
     * @return Transaction
     */
    public Transaction subCategory(SubCategory subCategory) {

        if (!this.subCategory.equals(subCategory)) {
            try {
                if (category.subCategories().getOne(subCategory.id()) == null)
                    throw new Exception("invalid subCategory");
                this.subCategory = subCategory;
                this.publish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    /**
     * subcategory getter
     *
     * @return subCategory
     */
    public SubCategory subCategory() {

        return this.subCategory;
    }


}
