package kc.ac.mjc.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SMSReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //문자가 왔을때
        Toast.makeText(context,"문자가 왔습니다", Toast.LENGTH_SHORT).show();
    }
}
