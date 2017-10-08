package com.example.megapiespt.linknotealpha.manager;

import android.widget.BaseAdapter;

import com.example.megapiespt.linknotealpha.adapter.CategoryArrayAdapter;
import com.example.megapiespt.linknotealpha.adapter.CategoryViewAdapter;
import com.example.megapiespt.linknotealpha.adapter.LinkItemAdapter;
import com.example.megapiespt.linknotealpha.dao.LinkCategory;
import com.example.megapiespt.linknotealpha.dao.LinkItem;
import com.example.megapiespt.linknotealpha.util.Contextor;

import java.util.ArrayList;

/**
 * Created by MegapiesPT on 16/6/2560.
 */

public class AdapterFactory {
    public static final String NORMAL_CATEGORY = "normal category";
    public static final String NORMAL_CATEGORY_WITH_OUT_ALL = "normal not all";
    private static AdapterFactory instance;
    private LinkCollector collector;
    public static AdapterFactory getInstance(){
        if (instance == null)
            instance = new AdapterFactory();
        return instance;
    }

    public AdapterFactory() {
        collector = LinkCollector.getInstance();
    }

    public LinkItemAdapter getItemAdapter(String categoryID){
        ArrayList<LinkItem> items = new ArrayList<>();
        for(int i=0; i<collector.getItemCount(LinkCollector.ALL_ID); i++){
            LinkItem item = collector.getItem(i);
            if(categoryID.equals(LinkCollector.ALL_ID) || categoryID.equals(item.cate_id))
                items.add(item);
        }
        LinkItemAdapter adapter = new LinkItemAdapter(categoryID, items);
        collector.regisItemListener(adapter);
        return adapter;
    }

    public BaseAdapter getCategoryAdapter(String categoryType){

        if(categoryType.equals(NORMAL_CATEGORY)){
            ArrayList<LinkCategory> categories = new ArrayList<>(collector.getCategories());
            CategoryViewAdapter adapter = new CategoryViewAdapter(categories);
            collector.regisCategoryListener(adapter);
            return adapter;
        }else if(categoryType.equals(NORMAL_CATEGORY_WITH_OUT_ALL)){
            ArrayList<LinkCategory> categories = new ArrayList<>(collector.getCategories());
            for(LinkCategory category : categories)
                if(category.id.equals(LinkCollector.ALL_ID)){
                    categories.remove(category);
                    break;
                }
//            CategoryArrayAdapter adapter = new CategoryArrayAdapter(Contextor.getInstance().getContext(), android.R.layout.simple_spinner_dropdown_item, categories);
            CategoryViewAdapter adapter = new CategoryViewAdapter(categories);
            collector.regisCategoryListener(adapter);
            return adapter;
        }

        return null;
    }

}
