package com.glispa.adsdk;

import com.glispa.adsdk.banner.ImageAd;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@Config(sdk = 21)
@RunWith(RobolectricTestRunner.class)
public class ImageAdTest {

    ImageAd ad;

    @Before
    public void setUp() {
        ad = new ImageAd("test_id", "test_description", "test_image_url", "test_title");
    }

    @Test
    public void testAd() throws Exception {
        assertEquals(ad.getId(), "test_id");
        assertEquals(ad.getTitle(), "test_title");
        assertEquals(ad.getDescription(), "test_description");
        assertEquals(ad.getImageUrl(), "test_image_url");
    }
}