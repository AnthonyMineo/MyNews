package com.denma.mynews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.denma.mynews.Models.TopStoriesAPI.TopStoriesArticles;
import com.denma.mynews.R;

import java.util.List;

public class TopStoriesAdapter extends RecyclerView.Adapter<ArticlesViewHolder> {

    // FOR DATA
    private List<TopStoriesArticles> topStoriesArticles;
    private RequestManager glide;

    // CONSTRUCTOR
    public TopStoriesAdapter(List<TopStoriesArticles> topstoriesArticles, RequestManager glide) {
        this.topStoriesArticles = topstoriesArticles;
        this.glide = glide;
    }

    @Override
    public ArticlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_recycle_item, parent, false);

        return new ArticlesViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A TopStoriesArticles
    @Override
    public void onBindViewHolder(ArticlesViewHolder viewHolder, int position) {
        viewHolder.updateWithTopStories(this.topStoriesArticles.get(position), this.glide);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.topStoriesArticles.size();
    }

    public TopStoriesArticles getArticle(int position){
        return this.topStoriesArticles.get(position);
    }
}