package model;

public class BoundingBox {
    private String _id;
    private int _version;
    private String boxId;
    private AImage image;
    private Owner owner;
    private Product product;
    private Coordinates coordinates;

    public BoundingBox(String _id, int _version, String boxId, AImage image, Owner owner, Product product, Coordinates coordinates) {
        this._id = _id;
        this._version = _version;
        this.boxId = boxId;
        this.image = image;
        this.owner = owner;
        this.product = product;
        this.coordinates = coordinates;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int get_version() {
        return _version;
    }

    public void set_version(int _version) {
        this._version = _version;
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public AImage getImage() {
        return image;
    }

    public void setImage(AImage image) {
        this.image = image;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
