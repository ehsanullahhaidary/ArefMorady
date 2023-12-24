package com.ehsanhaidary.arefmorady;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.ehsanhaidary.arefmorady.ApplicationClass.ACTION_CLEAR;
import static com.ehsanhaidary.arefmorady.ApplicationClass.ACTION_NEXT;
import static com.ehsanhaidary.arefmorady.ApplicationClass.ACTION_PLAY;
import static com.ehsanhaidary.arefmorady.ApplicationClass.ACTION_PREVIOUS;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String actionName = intent.getAction();
        Intent serviceIntent = new Intent(context, AudioService.class);
        if (actionName != null){
            switch (actionName){
                case ACTION_PLAY:
                    serviceIntent.putExtra("actionName", "playPause");
                    context.startService(serviceIntent);
                    break;
                case ACTION_NEXT:
                    serviceIntent.putExtra("actionName", "next");
                    context.startService(serviceIntent);
                    break;
                case ACTION_PREVIOUS:
                    serviceIntent.putExtra("actionName", "previous");
                    context.startService(serviceIntent);
                    break;
                case ACTION_CLEAR:
                    serviceIntent.putExtra("actionName", "clear");
                    context.startService(serviceIntent);
                    break;
            }
        }
    }
}
