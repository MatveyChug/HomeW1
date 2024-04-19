package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    //поля
    private ImageView buttonMenu;
    private LinearLayout buttons;
    private boolean buttonsCheck = false; //поле включения кнопок
    private ImageView buttonPallete,buttonClear;
    private ArtView art;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //привязка кнопок к разметке
        buttonMenu = findViewById(R.id.buttonMenu);
        buttons = findViewById(R.id.buttons);
        buttonPallete = findViewById(R.id.buttonPallete);
        buttonClear = findViewById(R.id.buttonClear);
        art = findViewById(R.id.art);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //обработка нажатия
        buttonMenu.setOnClickListener(listener);
        buttonPallete.setOnClickListener(listener);
        buttonClear.setOnClickListener(listener);
    }
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            Sensor multisensor = sensorEvent.sensor;
            if (multisensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float xAccelerometr = sensorEvent.values[0];
                float yAccelerometr = sensorEvent.values[1];
                float zAccelerometr = sensorEvent.values[2];
                float medianAccelerometr = (xAccelerometr + yAccelerometr + zAccelerometr) / 3;
                if (medianAccelerometr > 10) {
                    if (buttonsCheck){
                        buttonsCheck = false;
                        buttons.setVisibility(View.INVISIBLE);
                    }else {
                        buttonsCheck = true;
                        buttons.setVisibility(View.VISIBLE);
                    }

                }

        }}

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.buttonMenu:
                    if (buttonsCheck) {
                        buttonsCheck = false;
                        buttons.setVisibility(View.INVISIBLE);
                    } else {
                        buttonsCheck = true;
                        buttons.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.buttonClear:
                    //метод очитски рисунка
                    AlertDialog.Builder broomDialog = new AlertDialog.Builder(MainActivity.this); //создание окна
                    broomDialog.setTitle("Очистка рисунка");
                    broomDialog.setMessage("Очистить область рисования (имеющийся рисунок будет удалён)?");

                    broomDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            art.erase();
                            dialog.dismiss();
                        }
                    });
                    broomDialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    broomDialog.show();
                    break;
        }}
    };
}