package com.example.megapiespt.linknotealpha.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.example.megapiespt.linknotealpha.dao.LinkCategory;
import com.example.megapiespt.linknotealpha.dao.LinkItem;
import com.example.megapiespt.linknotealpha.manager.LinkCollector;
import com.example.megapiespt.linknotealpha.util.LogWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MegapiesPT on 23/6/2560.
 */

public class CategoryArrayAdapter extends ArrayAdapter<LinkCategory> implements LinkCollector.CategoryChangeListener{

    private List<LinkCategory> categories;

    public CategoryArrayAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public CategoryArrayAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public CategoryArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull LinkCategory[] objects) {
        super(context, resource, objects);
    }

    public CategoryArrayAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull LinkCategory[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public CategoryArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<LinkCategory> objects) {
        super(context, resource, objects);
        categories = objects;
    }

    public CategoryArrayAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<LinkCategory> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public void onCategoryAdd(LinkCategory category) {
        categories.add(category);
        notifyDataSetChanged();
        LogWrapper.d("Category Array onAdd");
    }

    @Override
    public void onCategoryRemove(LinkCategory category) {
        notifyDataSetChanged();
        categories.remove(category);
    }

    @Override
    public void onCategoryChange(LinkCategory category) {
        notifyDataSetChanged();
        // TODO handle category change
    }


}
