package kc.ac.mjc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        Log.d("MainActivity",name);
        TextView nameTv=findViewById(R.id.name_tv);
        nameTv.setText(name);
        Button renameBtn=findViewById(R.id.rename_btn);
        renameBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText renameEt=findViewById(R.id.rename_et);
        String rename=renameEt.getText().toString();
        Intent intent=new Intent();
        intent.putExtra("rename",rename);
        setResult(RESULT_OK,intent);    //결과값 저장
        finish();   //액티비티 종료
    }
}
