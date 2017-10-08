package com.example.megapiespt.linknotealpha.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.megapiespt.linknotealpha.R;
import com.example.megapiespt.linknotealpha.manager.LinkCollector;
import com.example.megapiespt.linknotealpha.dao.LinkItem;
import com.example.megapiespt.linknotealpha.util.LogWrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by MegapiesPT on 11/6/2560.
 */

public class LinkItemView extends RelativeLayout {
    private TextView nameTextView;
    private TextView categoryTextView;
    private TextView urlTextView;
    private LinkItem item;
    private LoadDocTask loadDocTask;
    private ImageView pictureImageView;

    public LinkItemView(Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public LinkItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
        initWithAttrs(attrs);
    }

    public LinkItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
        initWithAttrs(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LinkItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
        initWithAttrs(attrs);
    }

    private void initInflate(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_link_item, this);
    }
    private void initInstance(){
        nameTextView = (TextView) findViewById(R.id.tv_name);
        categoryTextView = (TextView) findViewById(R.id.tv_cate);
        urlTextView = (TextView) findViewById(R.id.tv_url);
        pictureImageView = (ImageView) findViewById(R.id.iv_item_pic);
    }
    private void initWithAttrs(AttributeSet attrs){

    }

    public void setItem(LinkItem item){
        this.item = item;
        nameTextView.setText(item.name);
        categoryTextView.setText(LinkCollector.getInstance().getCategory(item.cate_id).name);
        urlTextView.setText(item.url);
        setImage();
    }

    public LinkItem getItem() {
        return item;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * 2 / 3;
        int newHeightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        // child
        super.onMeasure(widthMeasureSpec, newHeightSpec);
        // self
        setMeasuredDimension(width, height);
    }

    private void setImage(){
        String url = item.url;
        if(!url.startsWith("http"))
            url = "http://" + url;
        loadDocTask = new LoadDocTask();
        loadDocTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }

    private class LoadDocTask extends AsyncTask<String, Void, Document>{

        @Override
        protected Document doInBackground(String... strings) {
            String url = strings[0];
            try {
                Document doc = Jsoup.connect(url).get();
                return doc;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            Elements meta = document.select("meta[property=\"og:image\"]");
            String content = meta.attr("content");
            LogWrapper.d(item.name + " content " + content);

            try{
                if(!content.equals(""))
                    Glide.with(getContext())
                            .load(content)
                            .into(pictureImageView);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


}
