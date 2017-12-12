package com.vg.elastic.model;

import java.awt.*;

public class BoundingBox {
    private String _id;
    private String boxId;
    private AImage image;
    private Owner owner;
    private Product product;
    private Rectangle coordinates;

//    public BoundingBox(Map<String, Object> map){
//        this.boxId = (String) map.get("boxId");
//        this.image = new AImage();
//        this.owner = new Owner();
//        this.product = product;
//        this.coordinates = (Rectangle) map.get("coordinates");
//    }

    public Rectangle getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Rectangle coordinates) {
        this.coordinates = coordinates;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

}
