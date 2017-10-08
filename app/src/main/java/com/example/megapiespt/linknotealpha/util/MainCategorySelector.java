package com.example.megapiespt.linknotealpha.util;

import com.example.megapiespt.linknotealpha.R;
import com.example.megapiespt.linknotealpha.manager.LinkCollector;
import java.util.ArrayList;

/**
 * Created by MegapiesPT on 15/6/2560.
 */

public class MainCategorySelector {

    ArrayList<String> categories;

    public MainCategorySelector() {
        categories = new ArrayList<>();
        String all = Contextor.getInstance().getContext().getResources().getString(R.string.All);
        categories.add(all);
    }


}
