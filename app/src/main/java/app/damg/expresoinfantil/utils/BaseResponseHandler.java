package app.damg.expresoinfantil.utils;

import android.content.Context;

import java.lang.reflect.Method;

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
