package app.qk.teliver.utils;

import android.content.Context;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by denys on 05/03/2018.
 */

public interface BaseResponseHandler {
    public void OnBginConnection();
    public void OnEndConnection();
    public void OnFail(String error);
    public Method CurrentMethod();
    public Context Context();
}
