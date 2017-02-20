package com.glispa.adsdk.banner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.glispa.adsdk.R;
import com.glispa.adsdk.banner.BaseAd;
import com.glispa.adsdk.banner.ImageAd;
import com.glispa.adsdk.network.RequestManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * NativeAdAdapter should adding native ads{@link com.glispa.adsdk.banner.ImageAd} inside of original adapter.
 */
public class ImageAdAdapter extends AdAdapter {

    public ImageAdAdapter() {
        requestManager = new RequestManager(RequestManager.RequestType.Image);
        if (loadedAds.isEmpty()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    ImageAd ad = (ImageAd) requestManager.getAd();
                    if (ad == null) {
                        return;
                    }
                    loadedAds.add(ad);
                    original.notifyDataSetChanged();
                }
            });
            thread.start();
        }
    }

    @Override
    public int getCount() {
        return original.getCount() + adPositions.size();
    }

    @Override
    public Object getItem(int position) {
        if (adPositions.containsKey(position)) {
            return adPositions.get(position);
        } else {
            return original.getItem(position);
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * getView should return ad view if current position is ad position.
     * If there is no loaded ad to add into the listView,
     * the new thread will be started to load new ad
     */
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (position % interval == 0) {
            if (adPositions.containsKey(position)) {
                return getAdView(adPositions.get(position), parent);
            } else {
                if (loadedAds.isEmpty()) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (requestManager != null) {
                                ImageAd ad = (ImageAd) requestManager.getAd();
                                if (ad == null) {
                                    return;
                                }
                                loadedAds.add(ad);
                                if (parent != null) {
                                    parent.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            notifyDataSetChanged();
                                        }
                                    });
                                }
                            }
                        }
                    });
                    thread.start();
                } else {
                    ImageAd ad = (ImageAd) loadedAds.remove(0);
                    adPositions.put(position, ad);
                    return getAdView(ad, parent);
                }
            }
        }

        Integer count = 0;
        for (int i : adPositions.keySet()) {
            if (i < position) {
                count++;
            }
        }
        Integer pos = position - count;
        if (pos < 0) {
            pos = 0;
        }
        return original.getView(pos, convertView, parent);
    }

    /**
     * setup adapter to pass ads into it
     *
     * @param adapter
     * @return
     */
    public ImageAdAdapter setAdapter(BaseAdapter adapter) {
        original = adapter;
        return this;
    }


    /**
     * setUp interval between ads
     *
     * @param interval should be > 0
     * @return same adapter
     */
    public ImageAdAdapter setAdInterval(int interval) {
        this.interval = interval;
        if (interval > 0) {
            adPositions = new HashMap<>(original.getCount() / interval + 1);
        }
        return this;
    }


    /**
     * creates view representation of an ad
     *
     * @param baseAd ad to display
     * @param parent
     * @return ad view
     */
    @Override
    View getAdView(BaseAd baseAd, View parent) {
        final Context context = parent.getContext();
        final ImageAd ad = (ImageAd) baseAd;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View adView = inflater.inflate(R.layout.ad_view, (ViewGroup) parent, false);
        TextView title = (TextView) adView.findViewById(R.id.title);
        TextView description = (TextView) adView.findViewById(R.id.description);
        ImageView image = (ImageView) adView.findViewById(R.id.image);
        title.setText(ad.getTitle());
        description.setText(ad.getDescription());
        Picasso.with(parent.getContext()).load(ad.getImageUrl()).into(image);
        return adView;
    }
}
