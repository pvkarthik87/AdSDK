package com.glispa.adsdk;

import com.glispa.adsdk.banner.NativeAd;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@Config(sdk = 21)
@RunWith(RobolectricTestRunner.class)
public class NativeAdTest {

    NativeAd ad;

    @Before
    public void setUp() {
        ad = new NativeAd("test_id", "test_title", "test_image_url", "test_description", "test_cta_text", "test_cta_url");
    }

    @Test
    public void testAd() throws Exception {
        assertEquals(ad.getId(), "test_id");
        assertEquals(ad.getTitle(), "test_title");
        assertEquals(ad.getDescription(), "test_description");
        assertEquals(ad.getImageUrl(), "test_image_url");
        assertEquals(ad.getCallToActionText(), "test_cta_text");
        assertEquals(ad.getCallToActionUrl(), "test_cta_url");
    }
}