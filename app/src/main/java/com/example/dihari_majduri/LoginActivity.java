package com.example.dihari_majduri;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dihari_majduri.common.ApplicationSettings;
import com.example.dihari_majduri.common.NetworkConnectivityManager;
import com.example.dihari_majduri.common.NetworkSettings;
import com.example.dihari_majduri.network.pojo.LoginRequest;
import com.example.dihari_majduri.pojo.Owner;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText pin1;
    private EditText pin2;
    private EditText pin3;
    private EditText pin4;
    private TextView pinEntryMessage;
    private TextView errorMessage;
    private String pin;
    private int pinCount=0;
    private NetworkConnectivityManager networkConnectivityManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponent();
    }
    private void initComponent()
    {
        pin1=findViewById(R.id.pin1);
        pin2=findViewById(R.id.pin2);
        pin3=findViewById(R.id.pin3);
        pin4=findViewById(R.id.pin4);
        pinEntryMessage=findViewById(R.id.pinEntryMessage);
        errorMessage=findViewById(R.id.errorMessage);
        errorMessage.setVisibility(TextView.INVISIBLE);

        networkConnectivityManager=new NetworkConnectivityManager(this,this);

        pin1.addTextChangedListener(new LoginActivity.PinValidator(pin1,pin2,null));
        pin2.addTextChangedListener(new LoginActivity.PinValidator(pin2,pin3,pin1));
        pin3.addTextChangedListener(new LoginActivity.PinValidator(pin3,pin4,pin2));
        pin4.addTextChangedListener(new LoginActivity.PinValidator(pin4,null,pin3));
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void nextActivity()
    {
        System.out.println("****************************Generated PIN :"+pin);
        errorMessage.setVisibility(TextView.INVISIBLE);
        Intent intent1 = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent1);
        finish();

    }
    public void verifyPin(String pinStr)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Create an Employer object
        LoginRequest loginRequest = new LoginRequest(ApplicationSettings.ownerMobileNumber, pinStr);
        // Serialize the Employer object to JSON
        Gson gson = new Gson();
        String entityJSONString = gson.toJson(loginRequest);
        System.out.println("*******JSON STRING :"+entityJSONString);
        // Create a JSONObject from the JSON string
        JSONObject entityJSON = null;
        try {
            entityJSON = new JSONObject(entityJSONString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Define the URL to send the request to
        String url = NetworkSettings.OWNER_SERVER+"/validateOwnerPin";
        // Create a JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, entityJSON,
                response->{
                    try {
                        if(response.getBoolean("success"))
                        {
                            nextActivity();
                        }
                        System.out.println("**********Response :"+response);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error-> {
                    errorMessage.setVisibility(TextView.VISIBLE);
                    System.out.println("**********Response Error:"+error);
                    pin1.setText("");
                    pin2.setText("");
                    pin3.setText("");
                    pin4.setText("");
                    focusAndShowKeyboard(pin1);
                    pinCount=0;
                    generateServerError(error);
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        // Set retry policy
        jsonObjectRequest.setRetryPolicy(NetworkSettings.requestPolicy);
        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }
    private void generateServerError(VolleyError error) {
        // Handle the error response here
        error.printStackTrace();
    }
    public class PinValidator implements TextWatcher {
        private final EditText editText;
        private final EditText next;
        private final EditText prev;

        public PinValidator(EditText editText,EditText next,EditText prev)
        {
            this.editText = editText;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            if (count == 1) {
                pinCount++;
                focusAndShowKeyboard(next);
            } else if (count == 0) {
                pinCount--;
                focusAndShowKeyboard(prev);
            }
            if(pinCount==4)
            {
                String pin1Str=pin1.getText().toString().trim();
                String pin2Str=pin2.getText().toString().trim();
                String pin3Str=pin3.getText().toString().trim();
                String pin4Str=pin4.getText().toString().trim();
                pin=pin1Str+pin2Str+pin3Str+pin4Str;
                if(networkConnectivityManager.isConnected())
                {
                    verifyPin(pin);
                }else {
                    networkConnectivityManager.showNetworkConnectivityDialog();
                }


            }

        }

        private void closeKeyboard() {
            InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private void focusAndShowKeyboard(EditText target) {
        if (target != null) {
            target.requestFocus();
            showSoftInput(target);
        }
    }
    private void showSoftInput(EditText target) {
        InputMethodManager imm = (InputMethodManager) target.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(target, InputMethodManager.SHOW_IMPLICIT);
    }

}