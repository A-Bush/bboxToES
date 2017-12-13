package com.vg.elastic.model;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.awt.*;
import java.io.IOException;

import static com.vg.elastic.Constants.*;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class BoundingBox implements JsonBuilder{
    private String _id;
    private AImage image;
    private Owner owner;
    private Product product;
    private Rectangle coordinates;
    private History history;

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public BoundingBox(String _id, AImage image, Owner owner, Product product, Rectangle coordinates, History history) {
        this._id = _id;
        this.image = image;
        this.owner = owner;
        this.product = product;
        this.coordinates = coordinates;
        this.history = history;

    }

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

    @Override
    public XContentBuilder toJson() throws IOException {
        return jsonBuilder()
                .startObject()
                .startObject(IMAGE)
                    .field("id", image.getId())
                    .field("url", image.getUrl())
                .endObject()
                .startObject(OWNER)
                    .field("id", owner.getId())
                    .field("owner", owner.getOwner())
                .endObject()
                .startObject(PRODUCT)
                    .field("id", product.getId())
                    .field("name", product.getName())
                .endObject()
                .startObject(COORDINATES)
                    .field("x", coordinates.x)
                    .field("y", coordinates.y)
                    .field("width", coordinates.width)
                    .field("height", coordinates.height)
                .endObject()
                .startObject(HISTORY)
                    .field("modified", history.getModified())
                    .field("version", history.getVersion())
                    .field("action", history.getVersion())
                .endObject()
                .endObject();
    }
}
