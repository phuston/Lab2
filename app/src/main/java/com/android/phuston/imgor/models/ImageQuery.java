package com.android.phuston.imgor.models;

import java.util.ArrayList;
import java.util.List;

public class ImageQuery {

    // POJO mirroring JSON response from Google CSE

    private List<Item> items = new ArrayList<Item>();

    public List<Item> getItems() {
        return items;
    }

}