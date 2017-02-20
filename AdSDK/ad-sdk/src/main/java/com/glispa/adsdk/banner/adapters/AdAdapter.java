package com.glispa.adsdk.banner.adapters;

import android.view.View;
import android.widget.BaseAdapter;

import com.glispa.adsdk.banner.BaseAd;
import com.glispa.adsdk.network.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * AdAdapter is wrapper for original BaseAdapter. It should serving ads inside of original adapter.
 */
public abstract class AdAdapter extends BaseAdapter {

    // requests ads from server
    protected RequestManager requestManager;
    // ads positions in adapter
    protected HashMap<Integer, BaseAd> adPositions;
    // storage for loaded ads
    protected ArrayList<BaseAd> loadedAds = new ArrayList<>();
    // original passed in adapter
    protected BaseAdapter original;
    // interval between ads
    protected int interval = 0;

    abstract View getAdView(BaseAd baseAd, View parent);
}
