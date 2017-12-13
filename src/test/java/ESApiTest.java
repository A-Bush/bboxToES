import com.vg.elastic.model.*;
import com.vg.elastic.model.Image;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import static com.vg.elastic.Constants.*;
import static com.vg.elastic.Main.createBBox;
import static com.vg.elastic.Main.createFolder;
import static com.vg.elastic.Main.createImage;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.junit.Assert.assertTrue;

public class ESApiTest {
    static TransportClient client;
    static final DateTime DATE_TIME = DateTime.now();

    @BeforeClass
    public static void setClient() throws UnknownHostException {
        client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

    }

    @Test
    public void foldersGetTest(){
        SearchResponse sr = client.prepareSearch(INDEX)
                .setTypes(FOLDER)
                .setSize(100)
                .get();
        for (SearchHit hit: sr.getHits()){
            System.out.println(hit.getId() + "   " + hit.getSource());
        }
    }

    @Test
    public void folderIndexTest() throws IOException, ExecutionException, InterruptedException {
        String str = Long.valueOf(DATE_TIME.getMillis()).toString();
        Folder f = new Folder(str,str);
        assertTrue(createFolder(f, client));
    }

    @Test
    public void imagesGetTest(){
        SearchResponse sr = client.prepareSearch(INDEX)
                .setTypes(IMAGE)
                .setSize(100)
                .get();
        for (SearchHit hit: sr.getHits()){
            System.out.println(hit.getId() + "   " + hit.getSource());
        }
    }

    @Test
    public void imageIndexTest() throws IOException {
        Image i = new Image("sample-IMG-20161222-WA0127.jpg", "novus0", "http://localhost:1234/sample-sour_cream.jpg", 960, 1280);

        assertTrue(createImage(i, client));
    }

    @Test
    public void testBBoxGet(){
        SearchResponse sr = client.prepareSearch(INDEX)
                .setTypes(BBOX)
                .setSize(100)
                .get();
        SearchHits sh = sr.getHits();

        for (SearchHit hit: sh){
            System.out.println(hit.getId());
            System.out.println(hit.getSource());
        }

    }

    @Test
    public void testBBoxIndex() throws IOException {
        AImage i = new AImage("1493735057738464", "http://localhost:1234/sample-sour_cream.jpg");
        Owner o = new Owner("007", "Test user");
        Product p = new Product("test1", "test1");
        Rectangle r = new Rectangle(100,100,100,100);
        BoundingBox bb = new BoundingBox(i, o, p, r);

        assertTrue(createBBox(bb, client));


    }
}
