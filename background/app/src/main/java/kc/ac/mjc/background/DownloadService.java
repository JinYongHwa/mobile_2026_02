package kc.ac.mjc.background;

import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class DownloadService extends Service {

    int progress=0;

    NotificationCompat.Builder builder;
    final int NOTIFICATION_ID=1000;
    final String CHANNEL_NAME="download";

    Notification notification;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //백그라운드에서 실행되는 코드
        NotificationManager nm=getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel=new NotificationChannel(CHANNEL_NAME,CHANNEL_NAME,NotificationManager.IMPORTANCE_LOW);
            nm.createNotificationChannel(channel);
        }
        builder=new NotificationCompat.Builder(this,CHANNEL_NAME)
                .setContentTitle("다운로드 중")
                .setProgress(100,progress,false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true);
        notification=builder.build();
        nm.notify(NOTIFICATION_ID,notification);     //알림을 띄운다

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            startForeground(NOTIFICATION_ID,notification,FOREGROUND_SERVICE_TYPE_DATA_SYNC);
        }
        else{
            startForeground(NOTIFICATION_ID,notification);
        }




        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<=100;i+=1){
                    progress=i;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Log.d("DownloadService","progress:"+progress);

                    //알림차에 바뀐 progress 적용하기
                    builder.setProgress(100,progress,false);
                    notification=builder.build();
                    nm.notify(NOTIFICATION_ID,notification);

                    if(progress>=90){   //progress 90이되면
                        nm.cancel(NOTIFICATION_ID);
                    }
                    
                    Intent intent=new Intent();
                    intent.setAction("download_progress");  //이 채널명으로 방송
                    intent.putExtra("progress",progress);
                    intent.setPackage(getPackageName());
                    sendBroadcast(intent);      //progress 값 앱내에 송출
                }
            }
        });
        t.start();

        return super.onStartCommand(intent, flags, startId);
    }
}
