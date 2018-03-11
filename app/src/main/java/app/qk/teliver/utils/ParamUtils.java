package app.qk.teliver.utils;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by denys on 05/03/2018.
 */

public class ParamUtils {

    public  static HashMap<String,String> loginParam(String username, String password)
    {
        HashMap<String,String> value = new HashMap<>();

        value.put("username", username);
        value.put("password", password);
        value.put("grant_type","password");

        return  value;
    }

    public  static HashMap<String,String> AutherizationHeader(Context context)
    {
        MPreference preference = new MPreference(context);
        String token = preference.getString(Constants.LOGGE_IN_TOKEN);

        HashMap<String,String> value = new HashMap<>();
        value.put("Authorization", "Bearer " + token);

        return  value;
    }
}
