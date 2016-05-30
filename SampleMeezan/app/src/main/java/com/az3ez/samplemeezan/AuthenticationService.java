package com.az3ez.samplemeezan;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AuthenticationService {

    private static final String TAG = "AuthenticationService";

    public static void login(String customerId, String password, final ServiceListener listener) {

        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("CustomerID", customerId);
            jsonObj.put("password", password);
            String encryptedObject = Security.getInstance(ConfigConstants.SECRET_KEY, ConfigConstants.IV).encrypt(jsonObj.toString());
            Log.d(TAG, "" + encryptedObject);
            Log.d(TAG, "D : " + Security.getInstance(ConfigConstants.SECRET_KEY, ConfigConstants.IV).decrypt("Cz8rCh9OZqZEVJFCIDQeS885W6Cx2sIs467SLd5H2+g8xrdJdeyyvYZV2QNChvDiQFkLxyiYBuO1Maam9kvz6nTTBwNIB6AcFao6xRSIKyQA9OqueDCya+hsJlyQYi55LVL3trxfZ0PfVAWa7xXIpQ=="));
            JSONObject object = new JSONObject();
            object.put("keyvalue", encryptedObject);
            HttpRequestHandler.getInstance().postStringRequestWithBody(ConfigConstants.API_BASE_URL + "customerlogin", object,
                    new Response.Listener() {
                        @Override
                        public void onResponse(Object o) {

                            try {
                                String decrypted = null;
                                decrypted = Security.getInstance(ConfigConstants.SECRET_KEY, ConfigConstants.IV).decrypt(o.toString());
                                Log.d(TAG, "REDecrypted : " + decrypted);

                                JSONObject response = null;
                                response = new JSONArray(decrypted).getJSONObject(0);

                                if ((response.getString("statusCode").equals("001"))) {
                                    //final User u = GsonUtils.fromJSON(response.getJSONObject("user"), User.class);
                                    listener.success(response);

                                } else {
                                    listener.error(new ServiceError("Authentication Error"));
                                }
                            } catch (Exception e) {
                                Log.d(TAG, "Exception while trying to Login");
                                e.printStackTrace();
                                listener.error(new ServiceError(e.getMessage(), e));
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            listener.error(new ServiceError(volleyError.getMessage(), volleyError));
                            Log.d(TAG, "Exception while trying to Login " + volleyError.getMessage());

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            listener.error(new ServiceError(e.getMessage(), e));
            Log.d(TAG, "JSON Exception " + e.getMessage());

        }

    }
}
