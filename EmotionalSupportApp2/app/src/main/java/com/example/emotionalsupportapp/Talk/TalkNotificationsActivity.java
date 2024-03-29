package com.example.emotionalsupportapp.Talk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emotionalsupportapp.R;

import java.util.Calendar;

public class TalkNotificationsActivity extends AppCompatActivity {

    private TextView selectDateTurnOffNotifications;
    private DatePickerDialog.OnDateSetListener notificationsDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_notifications);

        selectDateTurnOffNotifications = findViewById(R.id.tvDate);

        selectDateTurnOffNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        TalkNotificationsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        notificationsDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        notificationsDateSetListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = "Turn off until " + month + "/" + dayOfMonth + "/" + year;
                selectDateTurnOffNotifications.setText(date);
            }
        };
    }

    public void turnOffNotificationsForever(View view) {
        Toast.makeText(this, "You have selected turn off Talk notifications forever", Toast.LENGTH_SHORT).show();
    }
}
