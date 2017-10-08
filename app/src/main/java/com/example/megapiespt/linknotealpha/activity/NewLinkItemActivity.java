package com.example.megapiespt.linknotealpha.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.megapiespt.linknotealpha.R;
import com.example.megapiespt.linknotealpha.dao.LinkItem;
import com.example.megapiespt.linknotealpha.manager.AdapterFactory;
import com.example.megapiespt.linknotealpha.manager.DatabaseManager;
import com.example.megapiespt.linknotealpha.util.LogWrapper;
import com.example.megapiespt.linknotealpha.view.CategoryItemView;

public class NewLinkItemActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button addBtn;
    private EditText nameEditText;
    private EditText urlEditText;
    private Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_link_item);

        initInstance();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    private void initInstance() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameEditText = (EditText) findViewById(R.id.et_name);
        urlEditText = (EditText) findViewById(R.id.et_url);
        addBtn = (Button) findViewById(R.id.btn_add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAdd();
            }
        });

        categorySpinner = (Spinner) findViewById(R.id.spinner_category);
        categorySpinner.setAdapter(AdapterFactory.getInstance().getCategoryAdapter(AdapterFactory.NORMAL_CATEGORY_WITH_OUT_ALL));




        String action = getIntent().getAction();
        if(action != null && action.equals(Intent.ACTION_VIEW)){
            String name = getIntent().getStringExtra(Intent.EXTRA_SUBJECT);
            String url = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            nameEditText.setText(name);
            urlEditText.setText(url);
        }else if(action != null && action.equals(Intent.ACTION_SEND)){
            String url = getIntent().getDataString();
            urlEditText.setText(url);
        }

        LogWrapper.d("action " + getIntent().getAction());
        LogWrapper.d("data string " + getIntent().getDataString());
        LogWrapper.d("extra text " + getIntent().getStringExtra(Intent.EXTRA_TEXT));
        LogWrapper.d("extra subject " + getIntent().getStringExtra(Intent.EXTRA_SUBJECT));
    }

    private void onClickAdd(){
        LinkItem item = new LinkItem();

        item.name = nameEditText.getText().toString();
        item.url = urlEditText.getText().toString();
        item.cate_id = ((CategoryItemView) categorySpinner.getSelectedView()).getCategory().id;


        DatabaseManager.getInstance().addItem(item);

        finish();
    }

}
