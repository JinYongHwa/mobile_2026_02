package com.example.recyclerview;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PostAdapter.OnPostClickListener {

    RecyclerView postRv;
    ArrayList<Post> postList=new ArrayList<>();
    PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        postRv=findViewById(R.id.post_rv);
        for(int i=0;i<10000;i++){ //100개의 더미 데이터 채워넣기
            Post post=new Post();
            post.setName("홍길동"+i);
            post.setBody("안녕하세요"+i);
            post.setPostUrl("https://picsum.photos/seed/post"+i+"/500");
            post.setProfileUrl("https://picsum.photos/seed/profile"+i+"/100");
            postList.add(post);
        }
        adapter=new PostAdapter(postList);  //어뎁터에 데이터넣기
        postRv.setAdapter(adapter);         //RecyclerView 에 어뎁터 설정
        postRv.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnPostClickListener(this);
    }

    @Override
    public void onPostNameClick(Post post) {
        Toast.makeText(this, post.getBody(), Toast.LENGTH_SHORT).show();
    }
}