package com.glispa.adsdk.banner.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.glispa.adsdk.R;
import com.glispa.adsdk.banner.BaseAd;
import com.glispa.adsdk.banner.NativeAd;
import com.glispa.adsdk.network.RequestManager;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * NativeAdAdapter should adding native ads{@link com.glispa.adsdk.banner.NativeAd} inside of original adapter.
 */
public class NativeAdAdapter extends AdAdapter {

    public NativeAdAdapter() {
        requestManager = new RequestManager(RequestManager.RequestType.Native);
        if (loadedAds.isEmpty()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    NativeAd ad = (NativeAd) requestManager.getAd();
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

    /**
     * setup adapter to pass ads into it
     *
     * @param adapter
     * @return
     */
    public NativeAdAdapter setAdapter(BaseAdapter adapter) {
        original = adapter;
        return this;
    }


    /**
     * setup interval between ads
     *
     * @param interval should be > 0
     * @return same adapter
     */
    public NativeAdAdapter setAdInterval(int interval) {
        this.interval = interval;
        if (interval > 0) {
            adPositions = new HashMap<>(original.getCount() / interval + 1);
        }
        return this;
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
    public long getItemId(int position) {
        return 0;
    }

    /**
     * getView should return ad view if current position is ad position.
     * If there is no loaded ad to add into the listView,
     * the new thread will be started to load new ad
     */
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (isAdPosition(position)) {
            if (adPositions.containsKey(position)) {
                return getAdView(adPositions.get(position), parent);
            } else {
                if (loadedAds.isEmpty()) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (requestManager != null) {
                                NativeAd ad = (NativeAd) requestManager.getAd();
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
                    NativeAd ad = (NativeAd) loadedAds.remove(0);
                    adPositions.put(position, ad);
                    return getAdView(ad, parent);
                }
            }
        }
        return original.getView(getOriginalPosition(position), convertView, parent);
    }

    private boolean isAdPosition(int position) {
        return position % interval == 0;
    }

    @Override
    public int getItemViewType(final int position) {
        if (adPositions.containsKey(position)) {
            return original.getViewTypeCount();
        }
        return original.getItemViewType(getOriginalPosition(position));
    }

    /**
     * creates view representation of an ad
     *
     * @param baseAd ad to display
     * @param parent
     * @return ad view
     */
    public View getAdView(BaseAd baseAd, View parent) {
        final Context context = parent.getContext();
        final NativeAd ad = (NativeAd) baseAd;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View adView = inflater.inflate(R.layout.ad_view, (ViewGroup) parent, false);
        TextView title = (TextView) adView.findViewById(R.id.title);
        TextView description = (TextView) adView.findViewById(R.id.description);
        Button callToAction = (Button) adView.findViewById(R.id.button);
        ImageView image = (ImageView) adView.findViewById(R.id.image);
        title.setText(ad.getTitle());
        description.setText(ad.getDescription());
        Picasso.with(parent.getContext()).load(ad.getImageUrl()).into(image);
        callToAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(ad.getCallToActionUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return adView;
    }

    private int getOriginalPosition(int position) {
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
