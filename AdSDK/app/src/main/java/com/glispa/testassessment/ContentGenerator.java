package com.glispa.testassessment;

import java.util.ArrayList;
import java.util.Random;

public class ContentGenerator {

    private static final Content[] CONTENT_GENERATOR_LIST = {
            Content.CONTENT_1, Content.CONTENT_2, Content.CONTENT_3, Content.CONTENT_4,
            Content.CONTENT_5, Content.CONTENT_6, Content.CONTENT_7, Content.CONTENT_8,
            Content.CONTENT_9, Content.CONTENT_10, Content.CONTENT_11, Content.CONTENT_12,
            Content.CONTENT_13, Content.CONTENT_14, Content.CONTENT_15, Content.CONTENT_16
    };

    private ContentGenerator() {
    }

    public static void fillWithRandom(ArrayList<Content> contentList, int quantity) {
        Random random = new Random(System.currentTimeMillis() + quantity);
        for (int i = 0; i < quantity; i++) {
            contentList.add(CONTENT_GENERATOR_LIST[random.nextInt(CONTENT_GENERATOR_LIST.length)]);
        }
    }
}
