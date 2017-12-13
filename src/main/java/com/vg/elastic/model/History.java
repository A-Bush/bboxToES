package com.vg.elastic.model;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class History implements JsonBuilder{
    private long modified;
    private int version;
    private String action;

    public History(long modified, int version, String action) {
        this.modified = modified;
        this.version = version;
        this.action = action;
    }

    public long getModified() {
        return modified;
    }

    public void setModified(long modified) {
        this.modified = modified;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public XContentBuilder toJson() throws IOException {
        return jsonBuilder()
                .startObject()
                .field("modified", modified)
                .field("version", version)
                .field("action", action)
                .endObject();
    }

    //history?: { modified: Date | number; version: number; action: string };
}
