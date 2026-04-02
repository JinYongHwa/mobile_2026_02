package kc.ac.mjc.background;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ProgressBar downloadPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button downloadBtn=findViewById(R.id.download_btn);
        downloadPb=findViewById(R.id.download_pb);
        
        
        //다운로드 버튼이 클릭 됐을때
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,DownloadService.class);
                startService(intent);
                finish();   //액티비티 종료
            }
        });

        //알림 권한이 있는지 확인하기
        int status=checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS);
        if(status!= PERMISSION_GRANTED){    //사용자가 권한 허용을 하지 않은경우

            //사용자에게 권한 요청하기
            requestPermissions(new String[]{
                    Manifest.permission.POST_NOTIFICATIONS
            },1000);
        }

        //방송을 듣도록 하기
        IntentFilter filter=new IntentFilter();
        filter.addAction("download_progress");
        DownloadReceiver receiver=new DownloadReceiver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(receiver,filter,Context.RECEIVER_NOT_EXPORTED);
        }
    }
    
    //progress 를 듣는 역활
    class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //DownloadService에서 보낸 intent 에서 progress 꺼내기
            int progress=intent.getIntExtra("progress",0);
            downloadPb.setProgress(progress);

        }
    }

}