package app.qk.teliver.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by denys on 04/03/2018.
 */

public class Api {

    private Boolean isConected;
    private RequestQueue requestQueue;
    private  final String TAG = "beginRequest";

    private final String BASEURL = "http://192.168.1.4/webui";

    private static Api ourInstance = new Api();

    public static Api getInstance() {
        return ourInstance;
    }

    private Api(){}


    public void request(final Iconection responseHandler,int method,   String url, final HashMap<String, String>  params, final HashMap<String, String>  headers)
    {

        RequestQueue queue = Volley.newRequestQueue(responseHandler.getContext());

        String RequestUrl = BASEURL + url;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(method, RequestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                isConected = false;
                try {
                    responseHandler.OnResponse(response);
                } catch (Exception e) {
                    responseHandler.OnFail(handleServerError(e));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isConected = false;
                responseHandler.OnFail(handleServerError(error));
            }

        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getParams();

                if (headers == null)
                {
                    return  new HashMap<>();
                }

                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();

                if (params == null)
                {
                    return  new HashMap<>();
                }

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        stringRequest.setTag(TAG);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }


    private static String handleServerError(Object err) {
        VolleyError error = (VolleyError) err;

        NetworkResponse response = error.networkResponse;

        if (response != null) {
            switch (response.statusCode) {
                case 405:
                case 404:
                case 422:
                case 400:
                case 401:

                    try {
                        String string = new String(error.networkResponse.data);
                        JSONObject object = new JSONObject(string);
                        if (object.has("Message")) {
                            return object.get("Message").toString();
                        }
                        else if(object.has("error_description")) {
                            return object.get("error_description").toString();
                        }
                        else if(object.has("error")) {
                            return object.get("error").toString();
                        }
                    }catch (JSONException e)
                    {
                        return "Could not parse response";
                    }
                    // invalid request
                    return error.getMessage();

                default:
                    return error.getMessage();
            }
        }
        return "Ocurrio un error en la conexión intentelo nuevamente";
    }
}
