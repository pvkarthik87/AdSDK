package com.glispa.adsdk.banner;

import android.support.annotation.VisibleForTesting;

public class NativeAd extends BaseAd {

    String title;
    String imageUrl;
    String callToActionText;
    String callToActionUrl;

    public NativeAd(String id, String title, String imageUrl, String description, String callToActionText, String callToActionUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.callToActionText = callToActionText;
        this.callToActionUrl = callToActionUrl;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCallToActionText() {
        return callToActionText;
    }

    public void setCallToActionText(String callToActionText) {
        this.callToActionText = callToActionText;
    }

    public String getCallToActionUrl() {
        return callToActionUrl;
    }

    public void setCallToActionUrl(String callToActionUrl) {
        this.callToActionUrl = callToActionUrl;
    }

    @VisibleForTesting
    public String toJson() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"title\":\"" + title + "\"," +
                "\"imageUrl\":\"" + imageUrl + "\"," +
                "\"description\":\"" + description + "\"," +
                "\"callToActionText\":\"" + callToActionText + "\"," +
                "\"callToActionURL\":\"" + callToActionUrl + "\"" + "}";
    }
}
