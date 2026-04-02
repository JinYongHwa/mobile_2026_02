package kc.ac.mjc.broadcast;


import static android.content.Intent.ACTION_BATTERY_CHANGED;
import static android.content.Intent.ACTION_PICK;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView pickIv;
    Button pickBtn;

    BatteryReceiver batteryReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        pickIv=findViewById(R.id.pick_iv);
        pickBtn=findViewById(R.id.pick_btn);

        //사진선택버튼 클릭시
        pickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1001);
            }
        });

        //문자 받기 권한 있는지 체크
        int status=checkSelfPermission(Manifest.permission.RECEIVE_SMS);
        if(status!= PERMISSION_GRANTED){ //사용자가 권한 허용을 하지 않았으면
            //사용자에게 권한 요청하기
            requestPermissions(new String[]{
                    Manifest.permission.RECEIVE_SMS
            },1000);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        batteryReceiver=new BatteryReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver,intentFilter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1000){
            if(grantResults[0]==PERMISSION_GRANTED){    //사용자가 권한을 허용했으면
                Toast.makeText(this,"권한을 허용했습니다",Toast.LENGTH_SHORT).show();
            }
        }
    }

    class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //배터리 잔량(%)
            int batteryLevel=intent.getIntExtra(BatteryManager.EXTRA_LEVEL,100);
            String message=String.format("배터리 잔량 %d%%",batteryLevel);
            if(batteryLevel<50){
                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        //사진선택 버튼 눌러서 사용자가 사진선택을 완료했을때
        if(requestCode==1001&&resultCode== Activity.RESULT_OK){
            Uri imagePath=data.getData();
            pickIv.setImageURI(imagePath);
        }
    }
}