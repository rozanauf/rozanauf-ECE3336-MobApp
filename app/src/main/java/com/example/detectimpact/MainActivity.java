package com.example.detectimpact;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private Vibrator vibrator;
    private TextView text,text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        text = findViewById(R.id.textView);
        text1 = findViewById(R.id.textView2);

        text.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                vibrator.cancel();
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float accel_x = sensorEvent.values[0];
        float accel_y = sensorEvent.values[1];
        float accel_z = sensorEvent.values[2];
        double magnitude = Math.sqrt(accel_x*accel_x + accel_y*accel_y + accel_z*accel_z);
        text.setText("Current Magnitude: "+String.valueOf(magnitude));
        text1.setText("Gravity: "+String.valueOf(SensorManager.GRAVITY_EARTH));



        if(magnitude > 3*SensorManager.GRAVITY_EARTH) {
            long[] pattern = {0, 1000, 500, 2000, 500, 1000};
            vibrator.vibrate(pattern,1);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }
}