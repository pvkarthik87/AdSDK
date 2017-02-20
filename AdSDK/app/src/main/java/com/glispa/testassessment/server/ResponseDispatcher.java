package com.glispa.testassessment.server;

import java.net.HttpURLConnection;
import java.util.UUID;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import static java.lang.Thread.currentThread;

/**
 * Response dispatcher for MockWebServer to dispatch mocked responses with image and native ads
 */
public class ResponseDispatcher extends Dispatcher {

    @Override
    public MockResponse dispatch(final RecordedRequest request) throws InterruptedException {
        switch (request.getPath()) {
            case "/nativeAd":
                return nativeAdResponse();
            case "/imageAd":
                return imageAdResponse();
            default:
                return errorResponse();
        }
    }

    private MockResponse imageAdResponse() {
        return adResponse("image.json");
    }

    private MockResponse nativeAdResponse() {
        return adResponse("native.json");
    }

    private MockResponse adResponse(final String fileName) {
        final String id = UUID.randomUUID().toString();
        final String body = String.format(ResponseUtils.convertStreamToString(currentThread().getContextClassLoader().getResourceAsStream(fileName)), id);
        return new MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(body);
    }

    private MockResponse errorResponse() {
        return new MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("Your request is broken");
    }
}
