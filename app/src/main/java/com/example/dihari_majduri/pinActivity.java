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

public class pinActivity extends AppCompatActivity {

    private String firstName;
    private String lastName;
    private String mobileNumber;
    private EditText pin1;
    private EditText pin2;
    private EditText pin3;
    private EditText pin4;
    private TextView pinEntryMessage;
    private TextView errorMessage;
    private String pin;
    private int pinCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent=getIntent();
        this.firstName=intent.getStringExtra("firstName");
        this.lastName=intent.getStringExtra("lastName");
        this.mobileNumber=intent.getStringExtra("mobileNumber");
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

        pin1.addTextChangedListener(new PinValidator(pin1,pin2,null));
        pin2.addTextChangedListener(new PinValidator(pin2,pin3,pin1));
        pin3.addTextChangedListener(new PinValidator(pin3,pin4,pin2));
        pin4.addTextChangedListener(new PinValidator(pin4,null,pin3));
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


    public void addEmployee()
    {
    System.out.println("****************************Generated PIN :"+pin);
    // Network call to send data to server
        Intent intent1 = new Intent(pinActivity.this, dashboardActivity.class);
        intent1.putExtra("firstName",firstName);
        intent1.putExtra("lastName",lastName);
        intent1.putExtra("mobileNumber",mobileNumber);
        startActivity(intent1);
        finish();

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
                        addEmployee();
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
}