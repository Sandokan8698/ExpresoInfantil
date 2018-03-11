package app.qk.teliver.utils;

import android.app.Activity;
import android.content.Context;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by denys on 04/03/2018.
 */

public interface Iconection {
    public void OnResponse(String response);
    public void OnFail(String menssage);
    public Context getContext();
}
