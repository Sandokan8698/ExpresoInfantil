package app.qk.teliver.utils;

import android.content.Context;

import com.android.volley.Request;

import java.util.HashMap;

/**
 * Created by denys on 04/03/2018.
 */

public class Repository implements Iconection{


    private final BaseResponseHandler Handler;


    public Repository(BaseResponseHandler handler)
    {
        Handler = handler;

    }

    public void Login(String username, String password)
    {
        Handler.OnBginConnection();
        HashMap<String , String> param =  ParamUtils.loginParam(username,password);

        try {

            Api.getInstance().request(this, Request.Method.POST, "/token",param, null);

        } catch (Exception e) {
            Handler.OnFail(e.toString());
        }
    }

    public void GetLoginActor()
    {
        try {

            HashMap<String, String> headers = ParamUtils.AutherizationHeader(this.getContext());
            Api.getInstance().request(this, Request.Method.GET, "/api/accounts/navigation/role",null, headers);

        } catch (Exception e) {
            Handler.OnFail(e.toString());
        }
    }

    @Override
    public void OnResponse(String response) {

        Object[] parameters = new Object[1];
        parameters[0] = response;

        try {
            Handler.CurrentMethod().invoke(this.getContext(),parameters);
        }
        catch (Exception e) {
            OnFail(e.getMessage());
        }

    }

    @Override
    public void OnFail(String mensage) {
        Handler.OnFail(mensage);
    }

    @Override
    public Context getContext() {

        return Handler.Context();
    }

}
