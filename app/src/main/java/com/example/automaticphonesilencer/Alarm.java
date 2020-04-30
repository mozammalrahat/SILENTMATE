package com.example.automaticphonesilencer;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

public class Alarm extends AlarmBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        Vibrator vibrator =(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);

    }

}
