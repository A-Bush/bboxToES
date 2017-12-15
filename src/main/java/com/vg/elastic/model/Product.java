package com.vg.elastic.model;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Product implements JsonBuilder {
    private String _id;
    private String name;
    private String sku;


    public Product(String _id, String name, String sku) {
        this._id = _id;
        this.name = name;
        this.sku = sku;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public XContentBuilder toJson() throws IOException {
        return jsonBuilder()
                .startObject()
                .field("sku", sku)
                .field("name", name)
                .startArray("cat")
                    .value("TALESPIN")
                .endArray()
                .endObject();
    }
}
