package com.glispa.adsdk.utils;

import android.util.Log;
import android.view.ViewGroup;

import com.glispa.adsdk.banner.BaseAd;
import com.glispa.adsdk.banner.ImageAd;
import com.glispa.adsdk.network.RequestManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pvkarthik on 2017-02-21.
 */

public class AdHelper {


	/**
	 * Callback interface
	 */
	public interface AdListener {

		/**
		 * Ad loaded successfully
		 * @param adPos
		 * @param baseAd
		 */
		void onAdRequestSuccess(int adPos, BaseAd baseAd);

		/**
		 * Ad failed to load
		 * @param adPos
		 */
		void onAdRequestFailed(int adPos);

	}

	private RequestManager mRequestManager;
	private Map<Integer, BaseAd> mAdMap;
	private AdListener mAdListener;

	private static final String TAG = AdHelper.class.getSimpleName();

	public AdHelper(RequestManager requestManager, AdListener adListener) {
		mRequestManager = requestManager;
		mAdListener = adListener;
		mAdMap = new HashMap<>(8);
	}

	/**
	 *
	 * Requests new ad to load.
	 *
	 * @param adPosition
	 * @param parent
	 */
	public synchronized void requestAd(final int adPosition, final ViewGroup parent) {
		if (!mAdMap.containsKey(adPosition)) {
			Log.d(TAG, "Requesting Ad for position" + adPosition);
			mAdMap.put(adPosition, null);
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					if (mRequestManager != null) {
						final BaseAd ad = mRequestManager.getAd();
						if (ad == null) {
							mAdMap.remove(adPosition);
							if (parent != null) {
								parent.post(new Runnable() {
									@Override
									public void run() {
										mAdListener.onAdRequestFailed(adPosition);
									}
								});
							}
							return;
						}
						mAdMap.put(adPosition, ad);
						if (parent != null) {
							parent.post(new Runnable() {
								@Override
								public void run() {
									mAdListener.onAdRequestSuccess(adPosition, ad);
								}
							});
						}
					}
				}
			});
			thread.start();
		}
	}

}
