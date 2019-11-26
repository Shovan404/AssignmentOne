package com.assignmentone;

import androidx.appcompat.app.AppCompatActivity;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etAdult, etChild, etRoom;
    TextView tvCheckin, tvCheckout,tvVat, tvTotal, tvGross;
    Spinner spinRoom;
    DatePickerDialog datePickerDialog;
    Button btnCal,btnDiff;
    Calendar c1,c2;
    String strDiff;

    String[] Room={"Delux(2000)","Presenditial(5000)","Primium(7000)"};

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
        btnCal.setOnClickListener(this);

        btnDiff=findViewById(R.id.btnDiff);
        btnDiff.setOnClickListener(this);

        tvCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1 = Calendar.getInstance();
                int mYear = c1.get(Calendar.YEAR);
                int mMonth = c1.get(Calendar.MONTH);
                int mDay = c1.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(MainActivity.this,new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                        String mon=MONTHS[monthOfYear];

                        tvCheckin.setText(dayOfMonth + "-"
                                + (mon) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c2 = Calendar.getInstance();
                int mYear = c2.get(Calendar.YEAR);
                int mMonth = c2.get(Calendar.MONTH);
                int mDay = c2.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
                        String mon=MONTHS[monthOfYear];
                        tvCheckout.setText(dayOfMonth + "-" + (mon) + "-" + year);



                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        ArrayAdapter adapterRoom = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, Room);
        spinRoom.setAdapter(adapterRoom);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCal:
                double Vat, Total, GTotal;


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

                GTotal= Total + Vat;

                tvTotal.setText("Total: "+Total+"");
                tvVat.setText("Vat(13%): "+Vat+"");
                tvGross.setText("Gross Total: "+GTotal+"");

                break;

            case R.id.btnDiff:
                try {
                    String d1 = tvCheckin.getText ().toString ();
                    String d2 = tvCheckout.getText ().toString ();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                    Date date1 = simpleDateFormat.parse(d1);
                    Date date2 = simpleDateFormat.parse(d2);
                    long difference = Math.abs(date1.getTime() - date2.getTime());

                    long diffInDays = difference / (24 * 60 * 60 * 1000);

                    strDiff=String. valueOf(diffInDays);
                    Toast.makeText(MainActivity.this,strDiff,Toast.LENGTH_SHORT).show();

                }
                catch(Exception ex)
                {

                    ex.printStackTrace();
                }
                break;
        }
    }

}
