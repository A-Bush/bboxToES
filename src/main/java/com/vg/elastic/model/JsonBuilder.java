package com.vg.elastic.model;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

public interface JsonBuilder {
    XContentBuilder toJson() throws IOException;
}
