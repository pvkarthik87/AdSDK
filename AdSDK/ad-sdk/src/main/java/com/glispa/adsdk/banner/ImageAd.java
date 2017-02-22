package com.glispa.adsdk.banner;

public class ImageAd extends BaseAd {

    String imageUrl;
    String title;

    public ImageAd(String id, String description, String image, String title) {
        super();
        this.id = id;
        this.description = description;
        this.imageUrl = image;
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
