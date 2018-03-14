package app.damg.expresoinfantil.utils;
import android.content.Context;
import org.json.JSONObject;
import app.damg.expresoinfantil.Models.User;

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

    public static User getUser(Context context)
    {
        try {

           MPreference preference = new MPreference(context);
           JSONObject userJson = new JSONObject(preference.getString(Constants.USER));

           User user = new User();
           user.setNombre(userJson.getString("Nombre"));
           user.setCedula(userJson.getString("Cedula"));
           user.setActorRole(userJson.getString("ActorRole"));

           return user;

        } catch (Throwable t) {}

        return null;
    }

    public static User getUser(String userString)
    {
        try {


            JSONObject userJson = new JSONObject(userString);

            User user = new User();
            user.setNombre(userJson.getString("Nombre"));
            user.setCedula(userJson.getString("Cedula"));
            user.setActorRole(userJson.getString("ActorRole"));

            return user;

        } catch (Throwable t) {}

        return null;
    }
}
