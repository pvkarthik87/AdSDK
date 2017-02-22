package com.glispa.testassessment;

public class Content {

    static final Content CONTENT_1 = new Content("yacht 1", "yacht 1 description", "$200M", R.drawable.yacht_1);
    static final Content CONTENT_2 = new Content("yacht 2", "yacht 2 description", "$50000", R.drawable.yacht_2);
    static final Content CONTENT_3 = new Content("yacht 3", "yacht 3 description", "$10M", R.drawable.yacht_3);
    static final Content CONTENT_4 = new Content("yacht 4", "yacht 4 description", "$100M", R.drawable.yacht_4);
    static final Content CONTENT_5 = new Content("yacht 5", "yacht 5 description", "$10M", R.drawable.yacht_5);
    static final Content CONTENT_6 = new Content("yacht 6", "yacht 6 description", "$100M", R.drawable.yacht_6);
    static final Content CONTENT_7 = new Content("yacht 7", "yacht 7 description", "$50M", R.drawable.yacht_7);
    static final Content CONTENT_8 = new Content("yacht 8", "yacht 8 description", "$80M", R.drawable.yacht_8);
    static final Content CONTENT_9 = new Content("yacht 9", "yacht 9 description", "$150M", R.drawable.yacht_9);
    static final Content CONTENT_10 = new Content("yacht 10", "yacht 10 description", "$60M", R.drawable.yacht_10);
    static final Content CONTENT_11 = new Content("yacht 11", "yacht 11 description", "$20M", R.drawable.yacht_11);
    static final Content CONTENT_12 = new Content("yacht 12", "yacht 12 description", "$120M", R.drawable.yacht_12);
    static final Content CONTENT_13 = new Content("yacht 13", "yacht 13 description", "$1500M", R.drawable.yacht_13);
    static final Content CONTENT_14 = new Content("yacht 14", "yacht 14 description", "$250M", R.drawable.yacht_14);
    static final Content CONTENT_15 = new Content("yacht 15", "yacht 15 description", "$55M", R.drawable.yacht_15);
    static final Content CONTENT_16 = new Content("yacht 16", "yacht 16 description", "$1500M", R.drawable.yacht_16);


    String title;
    String description;
    String price;
    int imageRes;

    Content(String title, String description, String price, int imageRes) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageRes = imageRes;
    }
}
