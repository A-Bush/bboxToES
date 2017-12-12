package model;

public class Folder {
    private static Integer COUNTER = 0;
    private String name;
    private String _id;

    public Folder(String name) {
        COUNTER++;
        this.name = name;
        this._id = COUNTER.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
