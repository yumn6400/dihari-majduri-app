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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class DihariBottomSheetFragment extends BottomSheetDialogFragment {

    private Spinner spinnerCrop;
    private Spinner spinnerCropWorkType;
    private TextView textViewSelectDate;
    private Spinner spinnerEmployee;
    private TextInputLayout textInputLayoutEmployee;
    private MultiAutoCompleteTextView multiAutoCompleteTextViewEmployee;


    private String[] cropsArray = {"Crop 1", "Crop 2", "Crop 3", "Crop 4"};
    private String[] cropWorkTypesArray = {"Plowing", "Sowing", "Watering", "Harvesting"};
    private String[] employeesArray = {"Employee 1", "Employee 2", "Employee 3", "Employee 4"};

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
        multiAutoCompleteTextViewEmployee = view.findViewById(R.id.multiAutoCompleteTextViewEmployee);
        textInputLayoutEmployee = view.findViewById(R.id.textInputLayoutEmployee);
        // Initialize the spinners with the defined arrays
        ArrayAdapter<String> cropAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, cropsArray);
        cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCrop.setAdapter(cropAdapter);

        ArrayAdapter<String> cropWorkTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, cropWorkTypesArray);
        cropWorkTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCropWorkType.setAdapter(cropWorkTypeAdapter);

        // Initialize MultiAutoCompleteTextView for employees
        ArrayAdapter<String> employeeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, employeesArray);
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
                String employee = spinnerEmployee.getSelectedItem().toString();

                if (crop.isEmpty() || cropWorkType.isEmpty() || date.isEmpty() || employee.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle the save action
                    Toast.makeText(getContext(), "Form Submitted", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });


        return view;
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

    public class SearchEmployeeListener implements TextWatcher {

        public SearchEmployeeListener()
        {
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
