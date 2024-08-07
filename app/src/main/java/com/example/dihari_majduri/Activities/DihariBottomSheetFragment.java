package com.example.dihari_majduri.Activities;

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
import com.example.dihari_majduri.R;
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
    private TextInputLayout textInputLayoutLabour;
    private MultiAutoCompleteTextView multiAutoCompleteTextViewLabour;
    private static List<String> cropsNameArray = new ArrayList<>();
    private static List<String> cropWorkTypesNameArray = new ArrayList<>();
    private static List<String> laboursNameArray =new ArrayList<>();
    private static List<Labour> laboursList=new ArrayList<>();
    private Button buttonSave;
    private Set<String> laboursSet;
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
        textInputLayoutLabour = view.findViewById(R.id.textInputLayoutLabour);
        multiAutoCompleteTextViewLabour = view.findViewById(R.id.multiAutoCompleteTextViewLabour);
        networkConnectivityManager=new NetworkConnectivityManager(context,activity);
        ArrayAdapter<String> cropAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, cropsNameArray);
        cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCrop.setAdapter(cropAdapter);
        ArrayAdapter<String> cropWorkTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, cropWorkTypesNameArray);
        cropWorkTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCropWorkType.setAdapter(cropWorkTypeAdapter);
        ArrayAdapter<String> labourAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, laboursNameArray);
        multiAutoCompleteTextViewLabour.setAdapter(labourAdapter);
        multiAutoCompleteTextViewLabour.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiAutoCompleteTextViewLabour.addTextChangedListener(new SearchLabourListener());
        laboursSet = new HashSet<>();
        for (String labour : laboursNameArray) {
            laboursSet.add(labour.toLowerCase());
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
                String labour = multiAutoCompleteTextViewLabour.getText().toString();

                if (crop.isEmpty() || cropWorkType.isEmpty() || date.isEmpty() || date.equalsIgnoreCase("Select Date") || labour.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle the save action
                    Toast.makeText(getContext(), "Form Submitted", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
                System.out.println("crop :"+crop);
                System.out.println("Crop work type :"+cropWorkType);
                System.out.println("Date :"+date);
                System.out.println("Labour :"+labour);
                // saveInformation(crop,cropWorkType,date,labour);
                List<Labour> selectedLabours = getSelectedLabours(labour, laboursList);

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
    private List<Labour> getSelectedLabours(String labourNames, List<Labour> labourList) {
        StringTokenizer tokenizer = new StringTokenizer(labourNames, ", ");
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
    public class SearchLabourListener implements TextWatcher {

        public SearchLabourListener() {
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            textInputLayoutLabour.setError(null); // Clear error message
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String input = s.toString().toLowerCase();
            String[] inputArray = input.split(",");
            boolean found = false;

            for (String word : inputArray) {
                word = word.trim();
                if (!word.isEmpty()) {
                    for (String labour : laboursSet) {
                        if (labour.contains(word)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        textInputLayoutLabour.setError("Labour not found");
                        return;
                    }
                }
            }
            textInputLayoutLabour.setError(null);
        }
        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
