package com.khwopa.khabar.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.khwopa.khabar.R;
import com.khwopa.khabar.models.Articles;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.Collections;
import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(Articles article);
    }

    private List<Articles> articles;
    private View itemView;
    private OnItemClickListener listener;

    public NewsAdapter(List<Articles> articles, OnItemClickListener listener) {
        this.articles = articles;
        this.listener = listener;
        Collections.shuffle(this.articles);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.partial_news_list,
                viewGroup,
                false
        );
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String title = viewHolder.stripTitle(articles.get(i).getTitle());
        viewHolder.newsTitle.setText(title);
        viewHolder.newsDate.setText("Published At: " + articles.get(i).getPublishedAt().substring(0,10));
        Glide.with(itemView)
                .load(articles.get(i).getUrlToImage())
                .placeholder(new ColorDrawable(Color.BLACK))
                .into(viewHolder.newsImage);
        viewHolder.bind(articles.get(i), listener);
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView newsImage;
        private TextView newsTitle;
        private TextView newsDate;

        public ViewHolder(View itemView){
            super(itemView);
            newsImage = itemView.findViewById(R.id.newsImage);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsDate = itemView.findViewById(R.id.newsDate);
        }

        private void bind(final Articles article, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(article);
                }
            });
        }

        private String stripTitle(String title){
            if(title.length() > 120){
                title = title.substring(0, 120) + "...";
            }
            return title;
        }
    }
}
