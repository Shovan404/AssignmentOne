package com.assignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DatePickerDialog .OnDateSetListener {

    EditText etAdult, etChild, etRoom;
    TextView tvCheckin, tvCheckout, tvVat, tvTotal, tvGross;
    Spinner spinRoom;
    Button btnCal;
    String[] Room={"Delux","Presenditial","Primium"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding
        tvCheckin = findViewById(R.id.tvCheckin);
        tvCheckout = findViewById(R.id.tvCheckout);
        tvVat = findViewById(R.id.tvVat);
        tvTotal = findViewById(R.id.tvTotal);
        tvGross = findViewById(R.id.tvGross);
        etAdult = findViewById(R.id.etAdult);
        etChild = findViewById(R.id.etChild);
        etRoom = findViewById(R.id.etRoom);
        spinRoom=findViewById(R.id.spinRoom);
        btnCal=findViewById(R.id.btnCal);

        btnCal .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double Vat, Total;
                Total=Double.parseDouble(tvTotal.getText().toString());

                if (TextUtils.isEmpty(etAdult.getText())){
                    etAdult.setError("Please enter number of adults");
                    return;
                }
                if (TextUtils.isEmpty(etRoom.getText())){
                    etRoom.setError("Please enter number of room");
                    return;
                }
                Vat= (Total / 100.0f) * 13;
                

            }
        });

        tvCheckin .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatePicker();

            }
        });
        tvCheckout .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDatePicker2();
            }
        });

        ArrayAdapter adapterRoom = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, Room);
        spinRoom.setAdapter(adapterRoom);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month+1;
        String date = "Dat/Month/Year:" + dayOfMonth + "/" + month + "/" + year;
        tvCheckin.setText(date);

    }

    private void loadDatePicker() {
        final java.util.Calendar date = java.util.Calendar.getInstance();
        int year = date.get(java.util.Calendar.YEAR);
        int month = date.get(java.util.Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, this, year, month, day
        );
        datePickerDialog.show();
    }

    private void loadDatePicker2() {
        final java.util.Calendar date = java.util.Calendar.getInstance();
        int year = date.get(java.util.Calendar.YEAR);
        int month = date.get(java.util.Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = "Dat/Month/Year:" + dayOfMonth + "/" + month + "/" + year;
                tvCheckout.setText(date);
            }
        },year,month,day
        );
        datePickerDialog.show();
    }

    public void diff(Date tvCheckin, Date tvCheckout){
        long difference = tvCheckout.getTime()-tvCheckin.getTime();

    }



}
