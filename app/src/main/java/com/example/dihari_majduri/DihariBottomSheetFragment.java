package com.example.dihari_majduri;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.dihari_majduri.common.ApplicationSettings;
import com.example.dihari_majduri.common.NetworkSettings;
import com.example.dihari_majduri.network.pojo.DihariRequest;
import com.example.dihari_majduri.network.pojo.LabourRequest;
import com.example.dihari_majduri.pojo.CropWorkType;
import com.example.dihari_majduri.pojo.Labour;
import com.example.dihari_majduri.pojo.Owner;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.example.dihari_majduri.pojo.Crop;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DihariBottomSheetFragment extends BottomSheetDialogFragment {

    private Spinner spinnerCrop;
    private Spinner spinnerCropWorkType;
    private TextView textViewSelectDate;
    private TextInputLayout textInputLayoutEmployee;
    private MultiAutoCompleteTextView multiAutoCompleteTextViewEmployee;

     private static List<Labour> laboursList=new ArrayList<>();
    private static List<String> cropsNameArray = new ArrayList<>();

    private static List<String> cropWorkTypesNameArray = new ArrayList<>();

    private static List<String> laboursNameArray =new ArrayList<>();
    private List<String> employeesArray = new ArrayList<>();

    private Button buttonSave;
    private Set<String> employeesSet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_dihari_bottom_sheet, container, false);

        spinnerCrop = view.findViewById(R.id.spinnerCrop);
        spinnerCropWorkType = view.findViewById(R.id.spinnerCropWorkType);
        textViewSelectDate = view.findViewById(R.id.textViewSelectDate);
        buttonSave = view.findViewById(R.id.buttonSave);
        textInputLayoutEmployee = view.findViewById(R.id.textInputLayoutEmployee);
        multiAutoCompleteTextViewEmployee = view.findViewById(R.id.multiAutoCompleteTextViewEmployee);

        // Initialize the spinners with the defined arrays
        ArrayAdapter<String> cropAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, cropsNameArray);
        cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCrop.setAdapter(cropAdapter);

        ArrayAdapter<String> cropWorkTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, cropWorkTypesNameArray);
        cropWorkTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCropWorkType.setAdapter(cropWorkTypeAdapter);

        // Initialize MultiAutoCompleteTextView for employees
        ArrayAdapter<String> employeeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, laboursNameArray);
        multiAutoCompleteTextViewEmployee.setAdapter(employeeAdapter);
        multiAutoCompleteTextViewEmployee.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiAutoCompleteTextViewEmployee.addTextChangedListener(new SearchEmployeeListener());

        employeesSet = new HashSet<>();
        for (String employee : employeesArray) {
            employeesSet.add(employee.toLowerCase());
        }

        // Set up date picker
        textViewSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the OK button click
                String crop = spinnerCrop.getSelectedItem().toString();
                String cropWorkType = spinnerCropWorkType.getSelectedItem().toString();
                String date = textViewSelectDate.getText().toString();
                String employee = multiAutoCompleteTextViewEmployee.getText().toString();

                if (crop.isEmpty() || cropWorkType.isEmpty() || date.isEmpty() || employee.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle the save action
                    Toast.makeText(getContext(), "Form Submitted", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
                System.out.println("crop :"+crop);
                System.out.println("Crop work type :"+cropWorkType);
                System.out.println("Date :"+date);
                System.out.println("Employee :"+employee);
               // saveInformation(crop,cropWorkType,date,employee);
                List<Labour> selectedLabours = getSelectedLabours(employee, laboursList);

                saveInformation(crop, cropWorkType, date, selectedLabours);
            }
        });

        return view;
    }

    private List<Labour> getSelectedLabours(String employeeNames, List<Labour> labourList) {
        StringTokenizer tokenizer = new StringTokenizer(employeeNames, ", ");
        Map<String, Labour> labourMap = new HashMap<>();

        for (Labour labour : labourList) {
            labourMap.put(labour.getName(), labour);
        }

        List<Labour> selectedLabours = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            String name = tokenizer.nextToken();
            if (labourMap.containsKey(name)) {
                selectedLabours.add(labourMap.get(name));
            }
        }

        return selectedLabours;
    }

    public void saveInformation(String crop, String cropWorkType, String date, List<Labour> selectedLabours) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        // Serialize the list of Labour objects to JSON

        DihariRequest dihariRequest=new DihariRequest(crop,cropWorkType,date,selectedLabours);
        Gson gson = new Gson();
        String entityJSONString = gson.toJson(dihariRequest);
        System.out.println("*******JSON STRING :"+entityJSONString);
        // Create a JSONObject from the JSON string
        JSONObject entityJSON = null;
        try {
            entityJSON = new JSONObject(entityJSONString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Define the URL to send the request to
        String url = NetworkSettings.LABOUR_EMPLOYMENT_PERIODS_SERVER+"/"+ ApplicationSettings.ownerId;

        // Create a JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, entityJSON,
                response -> {
                    System.out.println("**********Response :" + response);
                    // Handle the server's response here
                },
                error -> {
                    System.out.println("**********Response Error:" + error);
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

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textViewSelectDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public static void setCrops(List<Crop> crops) {
        cropsNameArray.clear();
        for (Crop crop : crops)
        {
            cropsNameArray.add(crop.getName());
        }
    }

    public static void setCropWorkTypes(List<CropWorkType> cropWorkTypes) {
        cropWorkTypesNameArray.clear();
        for (CropWorkType cropWorkType : cropWorkTypes)
        {
            cropWorkTypesNameArray.add(cropWorkType.getName());
        }
    }

    public static void setLabours(List<Labour> labours) {
        laboursList=labours;
        laboursNameArray.clear();
        for (Labour labour : labours)
        {
            laboursNameArray.add(labour.getName());
        }
    }

    public class SearchEmployeeListener implements TextWatcher {

        public SearchEmployeeListener() {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            textInputLayoutEmployee.setError(null); // Clear error message
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String input = s.toString().toLowerCase();
            String[] inputArray = input.split(",");
            boolean found = false;

            for (String word : inputArray) {
                word = word.trim();
                if (!word.isEmpty()) {
                    for (String employee : employeesSet) {
                        if (employee.contains(word)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        textInputLayoutEmployee.setError("Employee not found");
                        return;
                    }
                }
            }
            textInputLayoutEmployee.setError(null);
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
