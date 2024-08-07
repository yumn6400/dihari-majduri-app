package com.example.dihari_majduri.Activities;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.dihari_majduri.R;
import com.example.dihari_majduri.common.ApplicationSettings;
import com.example.dihari_majduri.common.NetworkConnectivityManager;
import com.example.dihari_majduri.pojo.ChangePin;
import com.example.dihari_majduri.common.NetworkSettings;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class ChangePinActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_change_pin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComponent();
        setListener();
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
        TextView activityName = findViewById(R.id.tvActivityName);
        activityName.setText("Change Pin");
        ImageView backArrow = findViewById(R.id.ivToolbarBack);
        backArrow.setOnClickListener(view -> finish());
    }

    private void setListener()
    {
        pin1.addTextChangedListener(new PinValidator(pin1,pin2,null));
        pin2.addTextChangedListener(new PinValidator(pin2,pin3,pin1));
        pin3.addTextChangedListener(new PinValidator(pin3,pin4,pin2));
        pin4.addTextChangedListener(new PinValidator(pin4,null,pin3));
    }


    public void addFarmer() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ChangePin changePin = new ChangePin(ApplicationSettings.farmerId, this.pin);
        Gson gson = new Gson();
        String entityJSONString = gson.toJson(changePin);
        System.out.println("*******JSON STRING :" + entityJSONString);
        JSONObject entityJSON = null;
        try {
            entityJSON = new JSONObject(entityJSONString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = NetworkSettings.FARMER_SERVER+"/changePin";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                entityJSON,
                response -> {
                    try {
                        System.out.println("**********Response :" + response.toString());
                        if (response.getBoolean("success")) {
                            finish();
                        }
                        else {
                            //some code if success is false
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    System.out.println("**********Response Error:" + error.toString());
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
        jsonObjectRequest.setRetryPolicy(NetworkSettings.requestPolicy);
        requestQueue.add(jsonObjectRequest);
    }


    // Method to handle server errors
    private void generateServerError(VolleyError error) {
        // Handle the error response here
        error.printStackTrace();
    }
    public void networkCall()
    {
        System.out.println("****************************Generated PIN :"+pin);
        if(networkConnectivityManager.isConnected())
        {
            addFarmer();
        }else {
            networkConnectivityManager.showNetworkConnectivityDialog();
        }
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
                if(pin==null)
                {
                    pin=pin1Str+pin2Str+pin3Str+pin4Str;
                    System.out.println("PIN :"+pin);
                    pin1.setText("");
                    pin2.setText("");
                    pin3.setText("");
                    pin4.setText("");
                    focusAndShowKeyboard(pin1);
                    pinEntryMessage.setText("Confirm MPIN");
                    pinCount=0;
                }else {
                    if(!pin.equals(pin1Str+pin2Str+pin3Str+pin4Str))
                    {
                        errorMessage.setVisibility(TextView.VISIBLE);
                    }
                    else {
                        errorMessage.setVisibility(TextView.INVISIBLE);
                        closeKeyboard();
                        networkCall();
                    }

                }

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
        private void closeKeyboard() {
            InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
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

}