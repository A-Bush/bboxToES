package com.vg.elastic.model;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Folder implements JsonBuilder {
    private String name;
    private String _id;

    public Folder(String name, String _id) {
        this.name = name;
        this._id = _id;
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

    @Override
    public XContentBuilder toJson() throws IOException {
        return jsonBuilder()
                .startObject()
                .field("name", name)
                .endObject();

    }
}
