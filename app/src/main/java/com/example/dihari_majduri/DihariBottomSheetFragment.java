package com.example.dihari_majduri;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
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
import com.android.volley.VolleyError;
import com.example.dihari_majduri.common.NetworkConnectivityManager;
import com.example.dihari_majduri.pojo.CropWorkType;
import com.example.dihari_majduri.pojo.Labour;
import com.example.dihari_majduri.pojo.LabourEmploymentPeriod;
import com.example.dihari_majduri.services.DashboardService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import com.example.dihari_majduri.pojo.Crop;


public class DihariBottomSheetFragment extends BottomSheetDialogFragment {
    private Spinner spinnerCrop;
    private Spinner spinnerCropWorkType;
    private TextView textViewSelectDate;
    private TextInputLayout textInputLayoutEmployee;
    private MultiAutoCompleteTextView multiAutoCompleteTextViewEmployee;
    private static List<String> cropsNameArray = new ArrayList<>();
    private static List<String> cropWorkTypesNameArray = new ArrayList<>();
    private static List<String> laboursNameArray =new ArrayList<>();
    private static List<Labour> laboursList=new ArrayList<>();
    private Button buttonSave;
    private Set<String> employeesSet;
    private NetworkConnectivityManager networkConnectivityManager;
    private Context context;
    private Activity activity;
    private DashboardService dashboardService;

    private List<LabourEmploymentPeriod> labourEmploymentPeriodList=new ArrayList<>();


    public DihariBottomSheetFragment(Context context, Activity activity,DashboardService dashboardService)
    {
        this.context=context;
        this.activity=activity;
        this.dashboardService=dashboardService;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.add_dihari_bottom_sheet, container, false);
         initCommponent(view);
         setListener();
         return view;
    }
    private void initCommponent(View view)
    {
        spinnerCrop = view.findViewById(R.id.spinnerCrop);
        spinnerCropWorkType = view.findViewById(R.id.spinnerCropWorkType);
        textViewSelectDate = view.findViewById(R.id.textViewSelectDate);
        buttonSave = view.findViewById(R.id.buttonSave);
        textInputLayoutEmployee = view.findViewById(R.id.textInputLayoutEmployee);
        multiAutoCompleteTextViewEmployee = view.findViewById(R.id.multiAutoCompleteTextViewEmployee);
        networkConnectivityManager=new NetworkConnectivityManager(context,activity);
        ArrayAdapter<String> cropAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, cropsNameArray);
        cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCrop.setAdapter(cropAdapter);
        ArrayAdapter<String> cropWorkTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, cropWorkTypesNameArray);
        cropWorkTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCropWorkType.setAdapter(cropWorkTypeAdapter);
        ArrayAdapter<String> employeeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, laboursNameArray);
        multiAutoCompleteTextViewEmployee.setAdapter(employeeAdapter);
        multiAutoCompleteTextViewEmployee.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiAutoCompleteTextViewEmployee.addTextChangedListener(new SearchEmployeeListener());
        employeesSet = new HashSet<>();
        for (String employee : laboursNameArray) {
            employeesSet.add(employee.toLowerCase());
        }
    }
    private void setListener()
    {
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

                if (crop.isEmpty() || cropWorkType.isEmpty() || date.isEmpty() || date.equalsIgnoreCase("Select Date") || employee.isEmpty()) {
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

                if(networkConnectivityManager.isConnected())
                {
                dashboardService.saveNewLabourInformation (crop, cropWorkType, date, selectedLabours);
                dashboardService.getAllLabourEmployments();
                }else {
                    networkConnectivityManager.showNetworkConnectivityDialog();
                }
            }
        });
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



    private void generateServerError(VolleyError error) {
        // Handle the error response here
        error.printStackTrace();
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
