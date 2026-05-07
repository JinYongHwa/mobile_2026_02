package kr.ac.mjc.library;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.ac.mjc.library.dto.Book;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{

    private ArrayList<Book> bookList;

    public BookAdapter(ArrayList<Book> bookList){
        this.bookList=bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book=bookList.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbnailIv;
        TextView titleTv;
        TextView authorTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailIv=itemView.findViewById(R.id.thumbnail_iv);
            titleTv=itemView.findViewById(R.id.title_tv);
            authorTv=itemView.findViewById(R.id.author_tv);
        }
        public void bind(Book book){
            titleTv.setText(book.getTitleStatement());
            authorTv.setText(book.getAuthor());

            Glide.with(thumbnailIv.getContext())
                    .load(book.getThumbnailUrl())
                    .into(thumbnailIv);
        }
    }
}
