package kr.ac.mjc.library;

import static android.view.View.GONE;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import kr.ac.mjc.library.dto.Notice;
import kr.ac.mjc.library.dto.NoticeResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment {

    ArrayList<Notice> noticeList=new ArrayList<>();
    NoticeAdapter noticeAdapter;
    RecyclerView noticeRv;
    ProgressBar loadingPb;

    Handler handler=new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //fragment 레이아웃 가져오기
        return inflater.inflate(R.layout.fragment_home,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noticeRv=view.findViewById(R.id.notice_rv);
        loadingPb=view.findViewById(R.id.loading_pb);

        noticeAdapter=new NoticeAdapter(noticeList);
        noticeRv.setAdapter(noticeAdapter);
        noticeRv.setLayoutManager(new LinearLayoutManager(getContext()));


        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url("https://lib.mjc.ac.kr/pyxis-api/1/bulletin-boards/1/bulletins?max=20&offset=0")
                .build();

        client.newCall(request).enqueue(new Callback() {

            //요청이 실패했을경우
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("HomeFragment",e.getMessage());
            }
            //요청이 성공적으로 응답이 왔을경우
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result=response.body().string();
                Log.d("HomeFragment",result);
                //json 문자열을 클래스 형태로 변환
                NoticeResponse noticeResponse=new Gson().fromJson(result, NoticeResponse.class);
                noticeList.addAll(noticeResponse.getData().getList());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //이부분이 UI 쓰레드에서 실행될 코드
                        noticeAdapter.notifyDataSetChanged();
                        loadingPb.setVisibility(GONE);
                    }
                });

                for(Notice notice:noticeList){  //공지사항 제목만 로그로 찍어보기
                    Log.d("HomeFragment",notice.getTitle());
                }
            }
        });


    }
}
