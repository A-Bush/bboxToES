package com.vg.elastic;

import com.google.common.io.Files;
import com.vg.elastic.model.*;
import com.vg.elastic.model.Image;
import com.vg.ts.RendersToBBoxes;
import com.vg.ts.VGBBox;
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
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static com.vg.elastic.Constants.*;
import static java.util.stream.StreamSupport.stream;

public class Main {

    static TransportClient client;
    static int COUNTER = 0;
    static final DateTime DATE_TIME = DateTime.now();


    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {


        client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));


        final File flythroughDir = new File("/Users/abush/renders/test");
        Iterable<File> files = Files.fileTraverser().breadthFirst(flythroughDir);
        ArrayList<String> labels = new ArrayList<>();

        Folder folder = new Folder("test", "test");
        System.out.println("folder created: " + createFolder(folder, client));

        stream(files.spliterator(), false)
                .peek(file -> {
                    System.out.println("file=" + file.getName());
                })
                .filter(file -> file.isFile() && file.getName().endsWith(".PNG"))
                .filter(file -> {
                    String name = file.getName();
                    return !(name.contains(COLOR_KEY) || name.contains(DEPTH) || name.contains(NORMALS));
                })
                .map(file -> {
                    Image image = new Image(file.getName(), file.getName(), folder.getName(), BASE_URL + file.getName(), 1280, 720);
                    System.out.println(image.toString());
                    try {
                        boolean b = createImage(image, client);
                        System.out.println("image created: " + b);
                        return b;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .forEach(x -> System.out.println("image created: " + x));

        Stream<VGBBox> bboxes = RendersToBBoxes.parseFlyThrough(flythroughDir);

        bboxes.map(vgbBox -> {
            COUNTER++;
            System.out.println(Thread.currentThread() + ". " + vgbBox);
            AImage i = new AImage(vgbBox.imageFile.getName(), BASE_URL + vgbBox.imageFile.getName());
            Owner o = new Owner("Generated", "Generated");
            Product p = new Product(vgbBox.nameMask.Name, vgbBox.nameMask.Name);
            History h = new History(DATE_TIME.getMillis(), 0,"empty");
            Rectangle c = vgbBox.rectBbox;


            BoundingBox bb = new BoundingBox(COUNTER + vgbBox.imageFile.getName(), i, o, p, c, h);

            try {
                boolean b = createBBox(bb, client);
                System.out.println(bb.toJson().string());
                return b;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        })
                .forEach(x -> System.out.println("bbox created: " + x));

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
                .setRouting(image.getFolder())
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


}
