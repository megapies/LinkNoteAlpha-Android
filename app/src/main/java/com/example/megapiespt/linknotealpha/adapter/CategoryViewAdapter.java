package com.example.megapiespt.linknotealpha.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.megapiespt.linknotealpha.manager.LinkCollector;
import com.example.megapiespt.linknotealpha.dao.LinkCategory;
import com.example.megapiespt.linknotealpha.util.LogWrapper;
import com.example.megapiespt.linknotealpha.view.CategoryItemView;

import java.util.ArrayList;

/**
 * Created by MegapiesPT on 14/6/2560.
 */

public class CategoryViewAdapter extends BaseAdapter implements LinkCollector.CategoryChangeListener {

    private ArrayList<LinkCategory> categories;

    public CategoryViewAdapter(ArrayList<LinkCategory> categories) {
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CategoryItemView categoryItemView;
        if(view != null)
            categoryItemView = (CategoryItemView) view;
        else
            categoryItemView = new CategoryItemView(viewGroup.getContext());
        categoryItemView.setCategory(categories.get(i));
        return categoryItemView;
    }



    @Override
    public void onCategoryAdd(LinkCategory category) {
        categories.add(category);
        notifyDataSetChanged();
    }

    @Override
    public void onCategoryRemove(LinkCategory category) {

    }

    @Override
    public void onCategoryChange(LinkCategory category) {
        LogWrapper.d("category adapter onChange");
        notifyDataSetChanged();
    }
}
