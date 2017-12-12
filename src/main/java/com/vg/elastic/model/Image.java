package com.vg.elastic.model;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Map;

import static com.vg.elastic.Constants.BASE_URL;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Image implements JsonBuilder {
    private String name;
    private String _id;
    private String folder;
    private String url;
    private int height;
    private int width;

    public Image(String name, String _id, String folder, String url, int height, int width) {
        this.name = name;
        this._id = _id;
        this.folder = folder;
        this.url = url;
        this.height = height;
        this.width = width;
    }

    public Image(Map<String, Object> map){
        this.name = (String) map.get("name");
        this.folder = (String) map.get("folder");
        this.url = (String) map.get("url");
        this.height = (Integer) map.get("height");
        this.width = (Integer) map.get("width");
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

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public XContentBuilder toJson() throws IOException {
        return jsonBuilder()
                .startObject()
                .field("name", name)
                .field("folder", folder)
                .field("url", BASE_URL + name)
                .field("height", height)
                .field("width", width)
                .endObject();
    }
}
