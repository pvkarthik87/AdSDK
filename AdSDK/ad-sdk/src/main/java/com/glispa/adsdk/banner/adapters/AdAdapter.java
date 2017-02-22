package com.glispa.adsdk.banner.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.glispa.adsdk.banner.BaseAd;
import com.glispa.adsdk.network.RequestManager;
import com.glispa.adsdk.utils.AdHelper;

import java.util.Map;

/**
 * AdAdapter is wrapper for original BaseAdapter. It should serving ads inside of original adapter.
 */
public abstract class AdAdapter extends BaseAdapter {

    // requests ads from server
    RequestManager requestManager;
    // ads positions in adapter
    Map<Integer, BaseAd> adPositions;
    // original passed in adapter
    BaseAdapter original;
    // interval between ads
    int interval = 0;
    int startPos = 2;

    AdHelper mAdHelper;

    abstract View getAdView(BaseAd baseAd, View convertView, ViewGroup parent);

    /**
     * getView will return ad view if current position is ad position.
     * If there is need for pre-loading ad
     * new thread will be started to load new ad
     */
    @Override
    public final View getView(int position, View convertView, final ViewGroup parent) {
        if(adPositions.containsKey(position)) {
            // Ad Available
            BaseAd baseAd = adPositions.get(position);
            return getAdView(baseAd, convertView, parent);
        } else {
            int actualPos = getOriginalPosition(position);
            View adapterView = original.getView(actualPos, convertView, parent);
            // Request Ad
            if(position > 0 && (actualPos - startPos) >= 0 && (actualPos - startPos)%(interval) == 0)
            {
                mAdHelper.requestAd(actualPos + ((actualPos - startPos)/interval) + 1, parent);
            }
            return adapterView;
        }
    }

    final int getOriginalPosition(int position) {
        int originalPosition = position - getAdCountPlacedBefore(position);
        return originalPosition > 0 ? originalPosition : 0;
    }

    private int getAdCountPlacedBefore(int position) {
        int count = 0;
        for (int i : adPositions.keySet()) {
            if (i < position) {
                count++;
            }
        }
        return count;
    }
}
