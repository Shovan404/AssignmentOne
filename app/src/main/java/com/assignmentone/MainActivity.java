package com.assignmentone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView tvCheckin, tvCheckout, tvTotal, tvVat, tvGross;
    Spinner spinRoom;
    EditText etAdult, etChild, etRoom;
    Button btnCal;
    AlertDialog.Builder builder;

    private String[] Room = {"Deluxe", "Presidential", "Premium"};
    double total, grosstotal, vat, roomcost, noofday;
    Date dateChecking, dateCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding
        builder = new AlertDialog.Builder(this);
        tvCheckin = findViewById(R.id.tvCheckin);
        tvCheckout = findViewById(R.id.tvCheckout);
        tvVat = findViewById(R.id.tvVat);
        tvTotal = findViewById(R.id.tvTotal);
        tvGross = findViewById(R.id.tvGross);
        etAdult = findViewById(R.id.etAdult);
        etChild = findViewById(R.id.etChild);
        etRoom = findViewById(R.id.etRoom);
        spinRoom = findViewById(R.id.spinRoom);
        btnCal = findViewById(R.id.btnCal);


        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, Room);
        spinRoom.setAdapter(adapter);

        tvCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCheckingDate();
            }
        });

        tvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCheckoutDate();
            }
        });
        //Button
        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etAdult.getText())) {
                    etAdult.setError("Enter number of adults");
                    return;
                }
                if (TextUtils.isEmpty(etChild.getText())) {
                    etChild.setError("Enter number of children");
                    return;
                }
                if (TextUtils.isEmpty(etRoom.getText())) {
                    etRoom.setError("Enter number of Rooms");
                    return;
                }
                if (etAdult.getText().toString().equals("0")) {
                    etAdult.setError("Adult cannot be 0");
                    return;
                }
                if (etRoom.getText().toString().equals("0")) {
                    etRoom.setError("Room cannot be 0");
                    return;
                }
                if (dateChecking.before(dateCheckout)) {
                    noofday = Double.parseDouble(CheckDate());
                    CalculateGross();
                }
            }
        });


    }

    private void loadCheckingDate() {
        final Calendar date1 = Calendar.getInstance();
        int year = date1.get(Calendar.YEAR);
        int month = date1.get(Calendar.MONTH);
        int day = date1.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerChecking = new DatePickerDialog(
                this, this, year, month, day);
        datePickerChecking.show();
    }

    private void loadCheckoutDate() {
        final Calendar date2 = Calendar.getInstance();
        int year = date2.get(Calendar.YEAR);
        int month = date2.get(Calendar.MONTH);
        int day = date2.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerCheckOut = new DatePickerDialog(
                this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "-" + month + "-" + year;
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    dateCheckout = format.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tvCheckout.setText(date);
            }
        }, year, month, day);
        datePickerCheckOut.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month = month + 1;
        String date = dayOfMonth + "-" + month + "-" + year;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            dateChecking = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvCheckin.setText(date);
    }

    private void CalculateGross() {
        if (spinRoom.getSelectedItem().toString().equals("Deluxe")) {
            roomcost = 2000;
        } else if (spinRoom.getSelectedItem().toString().equals("Presidential")) {
            roomcost = 5000;
        } else if (spinRoom.getSelectedItem().toString().equals("Premium")) {
            roomcost = 4000;
        }
        int noofroom = Integer.parseInt(etRoom.getText().toString());
        total = roomcost * noofroom * noofday;
        vat = (0.13 * total);
        grosstotal = total + vat;
        String setTotal = "Total : " + total;
        String setVat = "Vat (13%) : " + vat;
        String setGrossTotal = "Gross Total : " + grosstotal;
        tvTotal.setText(setTotal);
        tvVat.setText(setVat);
        tvGross.setText(setGrossTotal);
    }

    private String CheckDate() {
        Calendar cCal = Calendar.getInstance();
        cCal.setTime(dateChecking);
        int cYear = cCal.get(Calendar.YEAR);
        int cMonth = cCal.get(Calendar.MONTH);
        int cDay = cCal.get(Calendar.DAY_OF_MONTH);

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(dateCheckout);
        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();
        float dayCount = (float) diff / (24 * 60 * 60 * 1000);
        return dayCount + "";
    }
}













