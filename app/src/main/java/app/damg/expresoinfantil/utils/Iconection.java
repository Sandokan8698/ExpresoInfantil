package app.damg.expresoinfantil.utils;

import android.content.Context;

/**
 * Created by denys on 04/03/2018.
 */

public interface Iconection {
    public void OnResponse(String response);
    public void OnFail(String menssage);
    public Context getContext();
}
