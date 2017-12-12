import model.Folder;
import model.Image;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
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
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Main {

    final static String INDEX = "test_truth";
    final static String IMAGE = "image";
    final static String FOLDER = "folder";

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

        Folder f = new Folder("f2");

        IndexResponse response = createFolder(f);
        RestStatus status = response.status();
        System.out.println(status.equals(RestStatus.CREATED));
    }


    public static IndexResponse createFolder(Folder folder) throws IOException, ExecutionException, InterruptedException {

        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("name", folder.getName())
                .endObject();

        return client.prepareIndex(INDEX, FOLDER)
                .setSource(builder)
                .get();
    }

    public static IndexResponse createImage(Image image){



        return null;
    }
}
