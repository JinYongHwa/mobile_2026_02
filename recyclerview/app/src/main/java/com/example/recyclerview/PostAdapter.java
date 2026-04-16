package com.example.recyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    //그려야할 데이터를 저장하는 변수
    ArrayList<Post> postList=new ArrayList<>();
    OnPostClickListener listener;


    public PostAdapter(ArrayList<Post> postList){
        this.postList=postList;
    }

    //외부로부터 Listener 설정할수있게 하기
    public void setOnPostClickListener(OnPostClickListener listener){
        this.listener=listener;
    }


    //RecyclerView 가 그려야할 레이아웃 설정하는곳
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("PostAdapter","onCreateViewHolder");
        //xml 레이아웃을 읽어오는 객체
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        //item_post.xml 을 가져와서 view 에 저장
        View view=inflater.inflate(R.layout.item_post,parent,false);
        //레이아웃을 ViewHolder 로 감싸기
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    //위에서 가져온 레이아웃에 데이터를 연결
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("PostAdapter","onBindViewHolder"+position);
        Post post=postList.get(position);
        holder.bind(post);
    }
    
    //RecyclerView 가 몇개의 아이템을 그려야되는지 알려주기
    @Override
    public int getItemCount() {
        return postList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView profileIv;
        TextView nameTv;
        ImageView postIv;
        TextView bodyTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileIv=itemView.findViewById(R.id.profile_iv);
            nameTv=itemView.findViewById(R.id.name_tv);
            postIv=itemView.findViewById(R.id.post_iv);
            bodyTv=itemView.findViewById(R.id.body_tv);
        }
        public void bind(Post post){    //레이아웃에 데이터 연결시키기
            nameTv.setText(post.getName());
            bodyTv.setText(post.getBody());
            nameTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onPostNameClick(post);
                    }
                }
            });
            
            Glide.with(profileIv.getContext())  //Glide 객체 초기화
                    .load(post.getProfileUrl()) //Url 불러오기
                    .into(profileIv);           //imageView에 그리기

            Glide.with(postIv.getContext())
                    .load(post.getPostUrl())
                    .into(postIv);
        }
        
    }

    interface OnPostClickListener{
        public void onPostNameClick(Post post);
    }

}
