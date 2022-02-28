package request;

import org.json.JSONObject;
import utils.PropertiesHelper;

import java.io.FileWriter;
import java.io.IOException;

public class RequestApi extends Request {

  private static   PropertiesHelper propertiesHelper = new PropertiesHelper();

    public static void main(String[] args) throws IOException {

        FileWriter myWriter = new FileWriter("C:\\Users\\User\\newLoginProject\\testhttp\\Test.txt");

        JSONObject firstData = request(propertiesHelper.getUrl(0,0));

        int total = firstData.getInt("total");
        for (int i = 0; i < total; i += propertiesHelper.getRows()) {
            JSONObject mainData = request(propertiesHelper.getUrl(i,propertiesHelper.getRows() ));

            myWriter.append(parse(mainData, i, propertiesHelper ) + ("\n"));
            myWriter.flush();
        }
        myWriter.close();

    }


}



