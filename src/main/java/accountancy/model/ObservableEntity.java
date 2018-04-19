package accountancy.model;

/**
 * The representation of a basic model resource, with observable capacity
 */
public abstract class ObservableEntity extends ObservableModel implements Entity {

    private int    id;
    private String title;

    public ObservableEntity(int id, String title) {

        super();
        this.id = id;
        this.title = title;
    }

    public int id() {

        return this.id;
    }

    public Entity id(int id) {

        if (this.id != id) {
            this.id = id;
            this.publish();
        }
        return this;
    }

    public String title() {

        return this.title;
    }

    public Entity title(String title) {

        if (!this.title.equals(title)) {
            this.title = title;
            this.publish();
        }
        return this;
    }

    @Override
    public boolean equals(Object object) {

        boolean sameSame = false;

        if (object != null && object.getClass().equals(this.getClass())) {
            sameSame = this.id == ((Entity) object).id();
        }

        return sameSame;
    }


    @Override
    public String toString() {

        return this.title;
    }

}
