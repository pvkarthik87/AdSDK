package com.glispa.testassessment.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by pvkarthik on 2017-02-21.
 *
 * Used to load images.
 */
public class GlideUtils {

	public static void loadImage(Context ctx, int rsc, ImageView gifView) {
		Glide
				.with(ctx)
				.load(rsc)
				.asBitmap()
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.into(gifView);
	}

}
