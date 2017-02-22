package com.glispa.adsdk.banner.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.glispa.adsdk.R;
import com.glispa.adsdk.banner.BaseAd;
import com.glispa.adsdk.banner.NativeAd;
import com.glispa.adsdk.network.RequestManager;
import com.glispa.adsdk.utils.AdHelper;
import com.glispa.adsdk.utils.GlideUtils;

import java.util.HashMap;

/**
 * NativeAdAdapter should adding native ads{@link com.glispa.adsdk.banner.NativeAd} inside of original adapter.
 */
public class NativeAdAdapter extends AdAdapter implements AdHelper.AdListener {

    private static final String TAG = NativeAdAdapter.class.getSimpleName();

    public NativeAdAdapter() {
        requestManager = new RequestManager(RequestManager.RequestType.Native);
        mAdHelper = new AdHelper(requestManager, this);
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

    /**
     * setUp Ad Starting Position
     *
     * @param position should be > 0
     * @return same adapter
     */
    public NativeAdAdapter setAdStartPosition(int position) {
        this.startPos = position;
        return this;
    }

    @Override
    public int getCount() {
        return ((original != null)?original.getCount():0) + adPositions.size();
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

    @Override
    public int getViewTypeCount() {
        return 1 + ((original != null)?original.getViewTypeCount():0);
    }

    @Override
    public int getItemViewType(final int position) {
        if (adPositions.containsKey(position)) {
            return original.getViewTypeCount();
        }
        return (original != null)?original.getItemViewType(getOriginalPosition(position)):0;
    }

    /**
     * creates view representation of an ad
     *
     * @param baseAd ad to display
     * @param parent
     * @return ad view
     */
    public View getAdView(BaseAd baseAd, View convertView, ViewGroup parent) {
        if(convertView == null) {
            // recycled view not available so inflate new view
            final Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ad_view, parent, false);
            NativeAdViewHolder nativeAdViewHolder = getHolder(convertView);
            convertView.setTag(nativeAdViewHolder);
        }
        NativeAdViewHolder nativeAdViewHolder = (NativeAdViewHolder) convertView.getTag();
        fillNativeAdDetails(parent, nativeAdViewHolder, (NativeAd) baseAd);
        return convertView;
    }

	/**
     *
     * fills view with Native Ad details
     *
     * @param parent
     * @param nativeAdViewHolder
     * @param ad
     */
    private void fillNativeAdDetails(ViewGroup parent, NativeAdViewHolder nativeAdViewHolder, final NativeAd ad) {
        nativeAdViewHolder.title.setText(ad.getTitle());
        nativeAdViewHolder.description.setText(ad.getDescription());
        Glide.clear(nativeAdViewHolder.image);
        GlideUtils.loadImage(parent.getContext(), ad.getImageUrl(), nativeAdViewHolder.image);
        final Context context = parent.getContext();
        nativeAdViewHolder.callToAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(ad.getCallToActionUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private NativeAdViewHolder getHolder(View v) {
        NativeAdViewHolder holder = new NativeAdViewHolder();
        holder.title = (TextView) v.findViewById(R.id.title);
        holder.description = (TextView) v.findViewById(R.id.description);
        holder.image = (ImageView) v.findViewById(R.id.image);
        holder.callToAction = (Button) v.findViewById(R.id.button);
        return holder;
    }

    // For caching views
    private static class NativeAdViewHolder {
        TextView title;
        TextView description;
        Button callToAction;
        ImageView image;
    }

    @Override
    public void onAdRequestFailed(int adPos) {
        Log.d(TAG, "Failed to load for position" + adPos);
    }

    @Override
    public void onAdRequestSuccess(int adPos, BaseAd baseAd) {
        Log.d(TAG, "Loaded ad for position" + adPos);
        adPositions.put(adPos, baseAd);
        notifyDataSetChanged();
    }
}
