package com.glispa.testassessment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.glispa.testassessment.utils.GlideUtils;

import java.util.ArrayList;

public class FeedAdapter extends BaseAdapter {

    private ArrayList<Content> contentList;

    public FeedAdapter(ArrayList<Content> contentList) {
        this.contentList = contentList;
    }

    @Override
    public int getCount() {
        return contentList.size();
    }

    @Override
    public Object getItem(int position) {
        return contentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return buildView(parent, position, convertView);
    }

    private View buildView(ViewGroup parent, int position, View contentView) {
        if(contentView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            contentView = inflater.inflate(R.layout.content_view, parent, false);
            FeedViewHolder feedViewHolder = getHolder(contentView);
            contentView.setTag(feedViewHolder);
        }
        Content content = contentList.get(position);
        FeedViewHolder feedViewHolder = (FeedViewHolder) contentView.getTag();
        fillFeedDetails(parent, position, feedViewHolder, content);
        return contentView;
    }

    private void fillFeedDetails(ViewGroup parent, int position, FeedViewHolder feedViewHolder, Content content) {
        feedViewHolder.title.setText("position " + (position) + ": " + content.title);
        feedViewHolder.description.setText(content.description);
        Glide.clear(feedViewHolder.image);
        GlideUtils.loadImage(parent.getContext(), content.imageRes, feedViewHolder.image);
        feedViewHolder.priceBtn.setText(content.price);
    }

    private FeedViewHolder getHolder(View v) {
        FeedViewHolder holder = new FeedViewHolder();
        holder.title = (TextView) v.findViewById(R.id.title);
        holder.description = (TextView) v.findViewById(R.id.description);
        holder.priceBtn = (Button) v.findViewById(R.id.button);
        holder.image = (ImageView) v.findViewById(R.id.image);
        return holder;
    }

    private static class FeedViewHolder {
        TextView title;
        TextView description;
        Button priceBtn;
        ImageView image;
    }
}
