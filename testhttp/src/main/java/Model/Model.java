package Model;

import org.json.JSONObject;
import utils.PropertiesHelper;

import java.util.Iterator;
import java.util.StringJoiner;

public class Model {


    public static String parse(JSONObject responseData, int i,  PropertiesHelper propertiesHelper ) {

        JSONObject albums = responseData.getJSONObject("projects");
        try {
            Iterator<String> idItr = albums.keys();
            StringJoiner lines = new StringJoiner("\n");
            while (idItr.hasNext()) {
                String id = idItr.next();
                JSONObject value = albums.getJSONObject(id);
                for (String fields : propertiesHelper.getFields()) {
                    lines.add( value.getString(fields));
                }
            }
            System.err.println(lines);
            return lines.toString();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return "";
    }
}


