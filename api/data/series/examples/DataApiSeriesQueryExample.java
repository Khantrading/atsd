import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.xml.bind.DatatypeConverter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

public class DataApiSeriesQueryExample {

    public static void main(String[] args) throws Exception {

        String databaseUrl = "http://10.102.0.6:8088";
        String userName = "axibase";
        String password = "********";
        String query = "[{ \"startDate\": \"current_minute\", \"endDate\": \"now\", \"entity\": \"*\", \"metric\": \"cpu_busy\", \"limit\": 100 }]";

        System.out.println("Execute series query:\r\n" + query);

        URL url = new URL(databaseUrl + "/api/v1/series/query");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        System.out.println("Connection established to " + databaseUrl);

        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Accept-Encoding", "gzip");
        conn.setRequestProperty("Content-Type", "application/json");

        String authString = userName + ":" + password;
        String authEncoded = DatatypeConverter.printBase64Binary(authString.getBytes(StandardCharsets.UTF_8));
        conn.setRequestProperty("Authorization", "Basic " + authEncoded);
        byte[] payload = query.getBytes(StandardCharsets.UTF_8);

        conn.setUseCaches(false);

        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(payload);

            System.out.println("Request sent");

            int code = conn.getResponseCode();

            System.out.println("Response code: " + code);

            if (code == HttpURLConnection.HTTP_OK) {
                InputStreamReader reader;
                if ("gzip".equals(conn.getContentEncoding())) {
                    reader = new InputStreamReader(new GZIPInputStream(conn.getInputStream()), StandardCharsets.UTF_8);
                } else {
                    reader = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
                }
                JSONTokener tokener = new JSONTokener(reader);
                JSONArray resultArray = new JSONArray(tokener);

                for (int i = 0; i < resultArray.length(); i++) {
                    JSONObject result = (JSONObject) resultArray.get(i);
                    JSONArray data = (JSONArray) result.get("data");

                    System.out.println("series: entity= " + result.get("entity") + " : tags=" + result.get("tags"));

                    for (int y = 0; y < data.length(); y++) {
                        JSONObject sample = (JSONObject) data.get(y);
                        System.out.println("\t" + sample.get("d") + " = " + sample.get("v"));
                    }
                }
            } else {
                System.out.println(conn.getResponseMessage());
            }
        }
    }
}