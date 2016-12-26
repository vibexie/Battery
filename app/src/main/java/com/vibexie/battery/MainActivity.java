package com.vibexie.battery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vibexie.batterylib.BatteryView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BatteryView battaryView = (BatteryView) this.findViewById(com.vibexie.battery.R.id.bv);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        battaryView.setmPowerAmount(progress < 100 ? progress ++ : (progress = 0));
                    }
                });
            }
        }, 100, 100);
    }
}
