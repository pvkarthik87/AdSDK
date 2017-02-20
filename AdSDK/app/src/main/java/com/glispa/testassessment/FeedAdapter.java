package com.glispa.testassessment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
        return buildView(parent, position);
    }

    private View buildView(ViewGroup parent, int position) {
        Content content = contentList.get(position);
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.content_view, parent, false);
        ((TextView) contentView.findViewById(R.id.title)).setText("position " + (position + 1) + ": " + content.title);
        ((TextView) contentView.findViewById(R.id.description)).setText(content.description);
        ((ImageView) contentView.findViewById(R.id.image)).setImageResource(content.imageRes);
        ((Button) contentView.findViewById(R.id.button)).setText(content.price);
        return contentView;
    }
}
