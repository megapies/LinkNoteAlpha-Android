package com.example.megapiespt.linknotealpha.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.megapiespt.linknotealpha.manager.LinkCollector;
import com.example.megapiespt.linknotealpha.dao.LinkItem;
import com.example.megapiespt.linknotealpha.view.LinkItemView;

import java.util.ArrayList;

/**
 * Created by MegapiesPT on 11/6/2560.
 */

public class LinkItemAdapter extends BaseAdapter implements LinkCollector.ItemChangeListener{
    private String categoryID;
    private ArrayList<LinkItem> items;

    public LinkItemAdapter(String categoryID, ArrayList<LinkItem> items) {
        this.categoryID = categoryID;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
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
        LinkItemView viewItem;
        if(view != null)
            viewItem = (LinkItemView) view;
        else
            viewItem = new LinkItemView(viewGroup.getContext());
        viewItem.setItem(items.get(i));
        return viewItem;
    }

    @Override
    public String getCategoryID() {
        return categoryID;
    }

    @Override
    public void onItemAdd(LinkItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void onItemRemove(LinkItem item) {

    }

    @Override
    public void onItemChange(LinkItem item) {

    }

}
