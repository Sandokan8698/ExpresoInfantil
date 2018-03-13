package app.damg.expresoinfantil.utils;
import org.json.JSONObject;

/**
 * Created by denys on 05/03/2018.
 */

public class JsonUtils {


    public static String getTokenFromString(String stringObject)
    {
        try {

            JSONObject obj = new JSONObject(stringObject);
            String token = obj.getString("access_token");
            return token;

        } catch (Throwable t) {}

        return null;
    }

    public static String getActor(String stringObject)
    {
        try {

            JSONObject obj = new JSONObject(stringObject);
            String role = obj.getString("Name");
            return role;

        } catch (Throwable t) {}

        return null;
    }
}
