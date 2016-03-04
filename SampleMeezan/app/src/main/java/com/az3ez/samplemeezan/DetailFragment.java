package com.az3ez.samplemeezan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Abdul Wahid on 2/9/2016.
 */
public class DetailFragment extends Fragment {

    TextView user, responseT;
    View view;
    EditText urlText, key, value;
    Button requestBtn, addBtn, removeBtn;
    LinearLayout layout;
    ArrayList<EditText> keyValueList = new ArrayList<>();

    //    Spinner apiSpinner;
    String[] API = {
            "https://members.almeezangroup.com/webapi/api/customerDetail",
            "https://members.almeezangroup.com/webapi/api/customerInvestment",
            "https://members.almeezangroup.com/webapi/api/NavPerformance",
            "https://members.almeezangroup.com/webapi/api/ConversionFund",
            "https://members.almeezangroup.com/webapi/api/MobileTransactions",
            "https://members.almeezangroup.com/webapi/api/Notification"

    };
    String[] names = {
            "Customer Detail",
            "Customer Investment",
            "Nav Performance",
            "Conversion Fund",
            "Mobile Transaction",
            "Notification"
    };
    JSONObject object;

    Security security = Security.getInstance(ConfigConstants.SECRET_KEY, ConfigConstants.IV);
    private JSONObject mainObject = new JSONObject();
    private String TAG = "DETAILS";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
//            object = new JSONObject(getArguments().getString("OBJECT"));
//            JSONObject customer = new JSONObject();
//
//
//            customer.put("Customerid", object.getString("Customerid"));
//            customer.put("AccessToken", object.getString("tokenID"));
//
//
//            Log.d(TAG, "Before Encryption " + customer);
//            String encrypted = security.encrypt(customer.toString());
//            mainObject = new JSONObject();
//            mainObject.put("keyvalue", encrypted);
//            Log.d(TAG, "After Encryption " + encrypted);

//            Toast.makeText(getActivity(), "" + mainObject, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail, container, false);
        urlText = (EditText) view.findViewById(R.id.api);
        key = (EditText) view.findViewById(R.id.key);
        layout = (LinearLayout) view.findViewById(R.id.layout);
        value = (EditText) view.findViewById(R.id.value);
        responseT = (TextView) view.findViewById(R.id.response);
        requestBtn = (Button) view.findViewById(R.id.btn_request);
        addBtn = (Button) view.findViewById(R.id.add);
        removeBtn = (Button) view.findViewById(R.id.remove);


//        try {
////            user.setText(object.getString("Customerid"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textKey = new EditText(getActivity());
                EditText textValue = new EditText(getActivity());
                textKey.setHint("Key");
                textValue.setHint("Value");
                layout.addView(textKey);
                layout.addView(textValue);
                keyValueList.add(textKey);
                keyValueList.add(textValue);

            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.removeViewAt(layout.getChildCount());
                layout.removeViewAt(layout.getChildCount());
                keyValueList.remove(keyValueList.size());
                keyValueList.remove(keyValueList.size());

            }
        });
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.d("MAINAA", "" + keyValueList.size());

                    mainObject.put(key.getText().toString(), value.getText().toString());
                    for (int i = 0; i < keyValueList.size(); i += 2) {
                        mainObject.put(keyValueList.get(i).getText().toString(), keyValueList.get(i + 1).getText().toString());
                    }
                    String encrypted = security.encrypt(mainObject.toString());
                    JSONObject requestObject = new JSONObject();
                    requestObject.put("keyvalue",encrypted);
                    Log.d("MAINAA", "" + mainObject);
                    Log.d("MAINAA", "" + requestObject);
                    HttpRequestHandler.getInstance().postStringRequestWithBody(urlText.getText().toString(), requestObject, new Response.Listener() {
                        @Override
                        public void onResponse(Object response) {
                            try {
                                String JSON = security.decrypt(response.toString());
                                responseT.setText("" + JSON);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        return view;
    }
}
