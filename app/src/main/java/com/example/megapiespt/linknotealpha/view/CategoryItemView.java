package com.example.megapiespt.linknotealpha.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.megapiespt.linknotealpha.R;
import com.example.megapiespt.linknotealpha.dao.LinkCategory;

/**
 * Created by MegapiesPT on 16/6/2560.
 */

public class CategoryItemView extends RelativeLayout {
    private TextView categoryNameTextView;
    private TextView categoryAmtTextView;
    private LinkCategory category;
    private CardView itemLayout;

    public CategoryItemView(Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public CategoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
        initWithAttrib(attrs);
    }

    public CategoryItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
        initWithAttrib(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CategoryItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
        initWithAttrib(attrs);
    }

    private void initInflate(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_category_item, this);
    }
    private void initInstance(){
        categoryNameTextView = (TextView) findViewById(R.id.tv_cate_name);
        categoryAmtTextView = (TextView) findViewById(R.id.tv_cate_amt);
        itemLayout = (CardView) findViewById(R.id.layout_item);
    }
    private void initWithAttrib(AttributeSet attrs){

    }

    public void setCategory(LinkCategory category){
        if(category != null){
            this.category = category;
            categoryNameTextView.setText(category.name);
            categoryAmtTextView.setText(category.size + "");
            itemLayout.setCardBackgroundColor(Color.parseColor(category.color));
        }
    }

    public LinkCategory getCategory(){
        return category;
    }

}
