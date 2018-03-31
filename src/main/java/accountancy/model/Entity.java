package accountancy.model;

/**
 * Describe a basic model resource (typically an entry in a database table)
 */
public interface Entity {

    int id();

    Entity id(int id);

    String title();

    Entity title(String title);

}
