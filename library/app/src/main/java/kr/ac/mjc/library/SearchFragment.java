package kr.ac.mjc.library;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import kr.ac.mjc.library.dto.Book;
import kr.ac.mjc.library.dto.BookResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment {

    ArrayList<Book> bookList=new ArrayList<>();
    BookAdapter bookAdapter=new BookAdapter(bookList);
    Handler handler=new Handler();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText keywordEt=view.findViewById(R.id.keyword_et);
        Button searchBtn=view.findViewById(R.id.search_btn);
        RecyclerView bookRv=view.findViewById(R.id.book_rv);

        bookRv.setAdapter(bookAdapter);
        bookRv.setLayoutManager(new LinearLayoutManager(getContext()));

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //검색 버튼이 클릭 되면

                //사용자가 입력한 검색어
                String keyword=keywordEt.getText().toString();

                //사용자가 검색어를입력하지 않았을때
                if(keyword.equals("")){
                    Toast.makeText(getContext(),"검색어를 입력해주세요",Toast.LENGTH_SHORT).show();
                    return; //검색 로직을 실행하지 않고 return
                }
                search(keyword);
            }
        });

    }
    //도서관 서버에 api 호출하기
    public void search(String keyword){
        String url="https://lib.mjc.ac.kr/pyxis-api/1/collections/1/search?all=k|a|"+keyword+"&abc=&rq=";

        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(url)
                .method("GET",null)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json=response.body().string();
                Log.d("SearchFragment",json);

                BookResponse bookResponse=new Gson().fromJson(json, BookResponse.class);
                for(Book book:bookResponse.getData().getList()){
                    Log.d("SearchFragment",book.getTitleStatement());
                }
                //이전 검색 결과를 모두 초기화
                bookList.clear();
                //api 에서 가져온 데이터를 모두 bookList 에 추가
                bookList.addAll(bookResponse.getData().getList());

                
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI 쓰레드에서 데이터가 바뀌어서 리사이클러뷰를 다시 그리라고하기
                        bookAdapter.notifyDataSetChanged();
                    }
                });
                
            }
        });
    }
}
