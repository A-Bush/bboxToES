package com.vg.elastic;

import com.vg.elastic.model.*;
import com.vg.elastic.model.Image;
import com.vg.nn.VGBBox;
import com.vg.ts.RendersToBBoxes;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.joda.time.DateTime;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static com.vg.elastic.Constants.*;

public class Main {

    static TransportClient client;
    static int COUNTER = 0;
    static final DateTime DATE_TIME = DateTime.now();


    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {


        client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));


        final File flythroughDir = new File("/Users/abush/renders/test");
        Set<String> labels = new HashSet<>();
        Set<String> images = new HashSet<>();

        Folder folder = new Folder("test", "test");
        System.out.println("folder created: " + createFolder(folder, client));

        Stream<VGBBox> bboxes = RendersToBBoxes.parseFlyThrough(flythroughDir);

        bboxes.map(vgbBox -> {
            COUNTER++;
            String labelName = vgbBox.nameMask.Name;
            labels.add(labelName);

            String imgName = vgbBox.imageFile.getName();
            images.add(imgName);

            System.out.println(Thread.currentThread() + ". " + vgbBox);
            AImage i = new AImage(vgbBox.imageFile.getName(), BASE_URL + vgbBox.imageFile.getName());
            Owner o = new Owner("Generated", "Generated");
            Product p = new Product(vgbBox.nameMask.Name, vgbBox.nameMask.Name, vgbBox.nameMask.Name);
            Rectangle c = vgbBox.rectBbox;
            History h = new History(DATE_TIME.getMillis(), 0, "empty");


            BoundingBox bb = new BoundingBox(COUNTER + vgbBox.imageFile.getName(), i, o, p, c, h);

            try {
                boolean b = createBBox(bb, client);
                System.out.println(bb.toJson().string());
                return b;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }).forEach(x -> System.out.println("bbox created: " + x));

        images.stream()
                .map(img -> {
                    Image i = new Image(img, img, folder.getName(), BASE_URL+img, 1280, 720);
                    try {
                        return createImage(i, client);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .forEach(x -> System.out.println("image created: " + x));

        labels.stream()
                .map(label -> {
                    Product p = new Product(label, label, label);
                    try {
                        return createProduct(p, client);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .forEach(x -> System.out.println("product created: " + x));


    }


    public static boolean createFolder(Folder folder, TransportClient client) throws IOException, ExecutionException, InterruptedException {
        RestStatus status = client.prepareIndex(INDEX, FOLDER)
                .setSource(folder.toJson())
                .setId(folder.get_id())
                .get()
                .status();

        return RestStatus.CREATED.equals(status);
    }

    public static boolean createImage(Image image, TransportClient client) throws IOException {
        System.out.println(image.toJson().string());
        RestStatus status = client.prepareIndex(INDEX, IMAGE)
                .setSource(image.toJson())
                .setId(image.get_id())
                .get()
                .status();

        return RestStatus.CREATED.equals(status);
    }

    public static boolean createBBox(BoundingBox bbox, TransportClient client) throws IOException {
        System.out.println(bbox.toJson().string());
        RestStatus status = client.prepareIndex(INDEX, BBOX)
                .setSource(bbox.toJson())
                .setId(bbox.get_id())
                .get()
                .status();

        return RestStatus.CREATED.equals(status);
    }

    public static boolean createProduct(Product p, TransportClient client) throws IOException {
        System.out.println(p.toJson().string());
        RestStatus status = client.prepareIndex(INDEX, PRODUCT)
                .setSource(p.toJson())
                .setId(p.get_id())
                .get()
                .status();

        return RestStatus.CREATED.equals(status);

    }

}
