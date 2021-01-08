package com.peace.ttd.ui.create;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.peace.ttd.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateFragment extends Fragment {

    private CreateViewModel createViewModel;
    Button buttonCreate, butAdd, btnTime;
    Switch Mswitch;
    EditText nameCom, teamLead;
    CalendarView calendarView;
    private List<String> mass = new ArrayList();
    Intent intent = new Intent();
    Calendar dateAndTime = Calendar.getInstance();
    TextView currentDateTime;
    String selectDate, isAWork;
    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createViewModel =
                new ViewModelProvider(this).get(CreateViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create, container, false);
        currentDateTime = (TextView)root.findViewById(R.id.currentDateTime);
        setInitialDateTime();
        calendarView = root.findViewById(R.id.calendarView2);
        calendarView.setOnDateChangeListener(((view, year, month, dayOfMonth) -> selectDate = dayOfMonth + "." + (month + 1)+"."+year+ " "));
        buttonCreate = root.findViewById(R.id.buttonCreate);
        btnTime = root.findViewById(R.id.btnTime);
        butAdd = root.findViewById(R.id.addBut);
        nameCom = root.findViewById(R.id.comName);
        teamLead = root.findViewById(R.id.lead);
        Mswitch = root.findViewById(R.id.checkMet);
//        Intent intent = new Intent(root.this, participants.class);
        //buttonCreate - knopka gotovo
        //butAdd - dobavit Ychastnica
        //nameCom - imya comands
        //teamLead - glavnii
        //Mswitch - checkpoint meropriyatia
//        buttonCreate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(getActivity().getApplicationContext(), getActivity().class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                PendingIntent pendingIntent = PendingIntent.getActivity(getActivity().getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                NotificationCompat.Builder notificationBuilder =
//                        new NotificationCompat.Builder(getActivity().getApplicationContext(), CHANNEL_ID)
//                                .setAutoCancel(false)
//                                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                                .setContentIntent(pendingIntent)
//                                .setContentTitle("")
//                                .setContentText("Какой то текст.............")
//                                .setPriority(Notification.PRIORITY_HIGH);
//                createChannelIfNeeded(notificationManager);
//                notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
//            }});



//        butAdd.setOnClickListener(v -> {
//            if (!(nameCom.equals("")) & !(teamLead.equals(""))) {
//                startActivity(intent);
//            }
//
//        });

        btnTime.setOnClickListener(v -> {
            setTime(v);
        });
        return root;
    }

    // отображаем диалоговое окно для выбора даты

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(getActivity(), t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }
    // установка начальных даты и времени
    private void setInitialDateTime() {

        currentDateTime.setText(DateUtils.formatDateTime(getActivity(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));


    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };
    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}

class task {
    String name, leadName, bd;
    boolean type;

    public task(String name, String leadName, String bd, boolean type) {
        this.name = name;
        this.leadName = leadName;
        this.bd = bd;
        this.type = type;

    }
}