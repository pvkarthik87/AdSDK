package com.glispa.adsdk;

import com.glispa.adsdk.banner.BaseAd;
import com.glispa.adsdk.banner.ImageAd;
import com.glispa.adsdk.banner.NativeAd;
import com.glispa.adsdk.network.RequestManager;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@Config(sdk = 23)
@RunWith(RobolectricTestRunner.class)
public class RequestManagerTest {
    @Mock
    RequestManager requestManager;
    @Mock
    NativeAd nativeAd;
    @Mock
    ImageAd imageAd;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAd_shouldReturnNativeAd() throws Exception {
        requestManager = spy(new RequestManager(RequestManager.RequestType.Native));
        when(requestManager.getAd()).thenReturn(nativeAd);
        BaseAd ad = requestManager.getAd();
        Assert.assertNotNull(ad);
    }

    @Test
    public void getAd_shouldReturnImageAd() throws Exception {
        requestManager = spy(new RequestManager(RequestManager.RequestType.Image));
        when(requestManager.getAd()).thenReturn(imageAd);
        BaseAd ad = requestManager.getAd();
        Assert.assertNotNull(ad);
    }
}