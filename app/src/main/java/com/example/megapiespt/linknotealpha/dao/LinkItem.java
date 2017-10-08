package com.example.megapiespt.linknotealpha.dao;

import android.support.annotation.NonNull;

/**
 * Created by MegapiesPT on 10/6/2560.
 */

public class LinkItem implements Comparable<LinkItem>{

    public String id;
    public String name;
    public String url;
    public String cate_id;

    @Override
    public String toString() {
        return "item(" + name + ":" + url;
    }

    @Override
    public int compareTo(@NonNull LinkItem item) {
        return this.id.compareTo(item.id);
    }
}
