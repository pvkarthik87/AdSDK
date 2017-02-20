package com.glispa.testassessment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.glispa.adsdk.banner.adapters.NativeAdAdapter;

import java.util.ArrayList;

public class NativeActivity extends AppCompatActivity {

    private FeedAdapter adapter;
    private ArrayList<Content> contentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ListView listView = (ListView) findViewById(R.id.content_list);
        ContentGenerator.fillWithRandom(contentList, 100);
        adapter = new FeedAdapter(contentList);
        NativeAdAdapter adAdapter = new NativeAdAdapter().setAdapter(adapter).setAdInterval(5);
        listView.setAdapter(adAdapter);
    }
}