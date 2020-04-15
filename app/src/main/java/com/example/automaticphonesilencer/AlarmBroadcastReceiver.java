package com.example.automaticphonesilencer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        Vibrator vibrator =(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);

    }
}
