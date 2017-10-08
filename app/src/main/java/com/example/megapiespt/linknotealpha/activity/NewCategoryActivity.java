package com.example.megapiespt.linknotealpha.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.megapiespt.linknotealpha.R;
import com.example.megapiespt.linknotealpha.dao.LinkCategory;
import com.example.megapiespt.linknotealpha.manager.DatabaseManager;

public class NewCategoryActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText colorEditText;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        initInstance();
    }

    private void initInstance() {
        nameEditText = (EditText) findViewById(R.id.et_name);
        colorEditText = (EditText) findViewById(R.id.et_color);
        addBtn = (Button) findViewById(R.id.btn_add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAdd();
            }
        });
    }

    private void onClickAdd(){
        LinkCategory category = new LinkCategory();
        category.name = nameEditText.getText().toString();
        category.color = colorEditText.getText().toString();

        DatabaseManager.getInstance().addCategory(category);
        finish();
    }
}
