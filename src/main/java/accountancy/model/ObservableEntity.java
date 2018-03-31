package accountancy.model;

public abstract class ObservableEntity extends ObservableModel {

    private int    id;
    private String title;

    public ObservableEntity(int id, String title) {

        this.id = id;
        this.title = title;
    }

    public int id() {

        return this.id;
    }

    public int id(int id) {

        if (this.id != id) {
            this.id = id;
            this.publish();
        }
        return id;
    }

    public String title() {

        return this.title;
    }

    public String title(String title) {

        if (!this.title.equals(title)) {
            this.title = title;
            this.publish();
        }
        return title;
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
