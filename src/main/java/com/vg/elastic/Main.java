package com.vg.elastic;

import com.vg.elastic.model.Folder;
import com.vg.elastic.model.Image;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static com.vg.elastic.Constants.*;

public class Main {

    static TransportClient client;


    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {


        client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

        GetResponse res2 = client.prepareGet(INDEX, IMAGE, "1493735057403304").get();
        System.out.println(res2.toString());

        SearchResponse sr = client.prepareSearch(INDEX).setTypes(IMAGE).get();
        SearchHits sh = sr.getHits();

        for (SearchHit hit : sh) {
            System.out.println(hit.getId());
            Image img = new Image(hit.getSource());
            img.set_id(hit.getId());
            System.out.println(hit.getSource().toString());
            System.out.println(img.toString());
        }

        Folder f = new Folder("f2", "100500");

    }


    public static boolean createFolder(Folder folder, TransportClient client) throws IOException, ExecutionException, InterruptedException {

       RestStatus status = client.prepareIndex(INDEX, FOLDER)
                .setSource(folder.toJson())
                .setId(folder.get_id())
                .get()
                .status();

        return RestStatus.CREATED.equals(status);
    }

    public static boolean createImage(Image image) throws IOException {

        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("name", image.getName())
                .field("folder", image.getFolder())
                .field("url", image.getUrl())
                .field("height", image.getHeight())
                .field("width", image.getWidth())
                .endObject();

        RestStatus status = client.prepareIndex(INDEX, IMAGE)
                .setSource(builder)
                .get()
                .status();

        return RestStatus.CREATED.equals(status);
    }

//     public static boolean createBBox(BoundingBox bbox) throws IOException {
//
//        XContentBuilder builder = jsonBuilder()
//                .startObject()
//                .field("name", bbox.getName())
//                .field("folder", bbox.getFolder())
//                .field("url", bbox.getUrl())
//                .field("height", bbox.getHeight())
//                .field("width", bbox.getWidth())
//                .endObject();
//
//        RestStatus status = client.prepareIndex(INDEX, IMAGE)
//                .setSource(builder)
//                .get()
//                .status();
//
//        return RestStatus.CREATED.equals(status);
//    }


}
