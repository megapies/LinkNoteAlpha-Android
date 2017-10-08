package com.example.megapiespt.linknotealpha.dao;

import android.support.annotation.NonNull;

/**
 * Created by MegapiesPT on 10/6/2560.
 */

public class LinkCategory implements Comparable<LinkCategory>{

    public String name;
    public String color;
    public String id;
    public int size;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(@NonNull LinkCategory category) {
        return this.id.compareTo(category.id);
    }
}
