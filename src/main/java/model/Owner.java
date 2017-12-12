package model;

public class Owner {
    private String id;
    private String owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Owner(String id, String owner) {

        this.id = id;
        this.owner = owner;
    }
}
