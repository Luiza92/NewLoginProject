package request;

import Model.Model;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Request extends Model {

    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(Request.class.getName());

    private static HttpURLConnection conn;


    static JSONObject request(String endpoint) {
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            URL url = new URL(endpoint);
            conn = (HttpURLConnection) url.openConnection();


            conn.setRequestMethod("GET");
            conn.setConnectTimeout(99999999);
            conn.setReadTimeout(99999999);

            int status = conn.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            log.info("response code: " + status);
            System.out.println(responseContent.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        System.out.println(responseContent.toString());
        return new JSONObject(responseContent.toString());
    }


}
