package com.glispa.adsdk.banner.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.glispa.adsdk.R;
import com.glispa.adsdk.banner.BaseAd;
import com.glispa.adsdk.banner.ImageAd;
import com.glispa.adsdk.network.RequestManager;
import com.glispa.adsdk.utils.AdHelper;
import com.glispa.adsdk.utils.GlideUtils;

import java.util.HashMap;

/**
 * NativeAdAdapter should adding native ads{@link com.glispa.adsdk.banner.ImageAd} inside of original adapter.
 */
public class ImageAdAdapter extends AdAdapter implements AdHelper.AdListener {

    private static final String TAG = ImageAdAdapter.class.getSimpleName();

    public ImageAdAdapter() {
        requestManager = new RequestManager(RequestManager.RequestType.Image);
        mAdHelper = new AdHelper(requestManager, this);
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
            adPositions = new HashMap<>(8);
        }
        return this;
    }

    /**
     * setUp Ad Starting Position
     *
     * @param position should be > 0
     * @return same adapter
     */
    public ImageAdAdapter setAdStartPosition(int position) {
        this.startPos = position;
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
    View getAdView(BaseAd baseAd, View convertView, ViewGroup parent) {
        if(convertView == null) {
            // recycled view not available so inflate new view
            final Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ad_view, parent, false);
            ImageAdViewHolder imageAdViewHolder = getHolder(convertView);
            convertView.setTag(imageAdViewHolder);
        }
        ImageAdViewHolder imageAdViewHolder = (ImageAdViewHolder) convertView.getTag();
        fillImageAdDetails(parent, imageAdViewHolder, (ImageAd) baseAd);
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 1 + ((original != null)?original.getViewTypeCount():0);
    }

    @Override
    public int getItemViewType(int position) {
        if (adPositions.containsKey(position)) {
            return original.getViewTypeCount();
        }
        return original.getItemViewType(getOriginalPosition(position));
    }

    private void fillImageAdDetails(ViewGroup parent, ImageAdViewHolder imageAdViewHolder, ImageAd ad) {
        imageAdViewHolder.title.setText(ad.getTitle());
        imageAdViewHolder.description.setText(ad.getDescription());
        Glide.clear(imageAdViewHolder.image);
        GlideUtils.loadImage(parent.getContext(), ad.getImageUrl(), imageAdViewHolder.image);
    }

    private ImageAdViewHolder getHolder(View v) {
        ImageAdViewHolder holder = new ImageAdViewHolder();
        holder.title = (TextView) v.findViewById(R.id.title);
        holder.description = (TextView) v.findViewById(R.id.description);
        holder.image = (ImageView) v.findViewById(R.id.image);
        return holder;
    }

    // For caching views
    private static class ImageAdViewHolder {
        TextView title;
        TextView description;
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
