package com.glispa.testassessment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.glispa.adsdk.banner.adapters.ImageAdAdapter;

import java.util.ArrayList;

public class ImageFeedActivity extends AppCompatActivity {

    private FeedAdapter adapter;
    private ArrayList<Content> contentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        ListView listView = (ListView) findViewById(R.id.content_list);
        ContentGenerator.fillWithRandom(contentList, 100);
        adapter = new FeedAdapter(contentList);
        ImageAdAdapter adAdapter = new ImageAdAdapter().setAdapter(adapter).setAdInterval(10).setAdStartPosition(10);
        listView.setAdapter(adAdapter);
    }
}