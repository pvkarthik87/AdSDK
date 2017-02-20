package com.glispa.adsdk;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.glispa.adsdk.banner.adapters.NativeAdAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@Config(sdk = 21)
@RunWith(RobolectricTestRunner.class)
public class NativeAdAdapterTest {
    NativeAdAdapter adAdapter;
    @Mock
    BaseAdapter adapter;
    @Mock
    ViewGroup parent;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(adapter.getCount()).thenReturn(100);
        when(adapter.getView(anyInt(), any(View.class), any(ViewGroup.class))).thenReturn(new View(RuntimeEnvironment.application));
        adAdapter = spy(new NativeAdAdapter().setAdapter(adapter).setAdInterval(2));
    }

    @Test
    public void getView() throws Exception {
        assertNotNull(adAdapter.getView(1, null, parent) != null);
    }

    @Test
    public void getView_shouldReturnOriginalView() throws Exception {
        View originalView = new View(RuntimeEnvironment.application);
        when(adapter.getView(anyInt(), nullable(View.class), any(ViewGroup.class))).thenReturn(originalView);
        assertEquals(adAdapter.getView(1, null, parent), originalView);
    }
}