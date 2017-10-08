package com.example.megapiespt.linknotealpha.fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.megapiespt.linknotealpha.R;
import com.example.megapiespt.linknotealpha.activity.NewLinkItemActivity;
import com.example.megapiespt.linknotealpha.adapter.LinkItemAdapter;
import com.example.megapiespt.linknotealpha.dao.LinkItem;
import com.example.megapiespt.linknotealpha.manager.AdapterFactory;
import com.example.megapiespt.linknotealpha.manager.LinkCollector;
import com.example.megapiespt.linknotealpha.dao.LinkCategory;
import com.example.megapiespt.linknotealpha.util.LogWrapper;
import com.example.megapiespt.linknotealpha.view.LinkItemView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListItemFragment extends Fragment {

    private ListView linkItemListView;
    private static final String CATE_ID = "categoryID";
    private String categoryID;
    private TextView categoryNameTextView;
    private RelativeLayout headerLayout;
    private FloatingActionButton addBtn;

    public static ListItemFragment newInstance(String categoryID){
        ListItemFragment instance = new ListItemFragment();
        Bundle args = new Bundle();
        args.putString(CATE_ID, categoryID);
        instance.setArguments(args);
        return instance;
    }

    public ListItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Read value from Argument to attribute
        Bundle args = getArguments();
        categoryID = args.getString(CATE_ID);
        LogWrapper.d("ListItemFragment onCreate() : categeoryID=" + categoryID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_item, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {
        linkItemListView = (ListView) rootView.findViewById(R.id.lv_link_items);
        LinkItemAdapter adapter = AdapterFactory.getInstance().getItemAdapter(categoryID);
        linkItemListView.setAdapter(adapter);
        linkItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(view instanceof LinkItemView){
                    LinkItemView itemView = (LinkItemView) view;
                    openURL(itemView);
                }
            }
        });

        LinkCategory category = LinkCollector.getInstance().getCategory(categoryID);
//        categoryNameTextView = (TextView) rootView.findViewById(R.id.tv_cate_name);
//        headerLayout = (RelativeLayout) rootView.findViewById(R.id.layout_header);
//        categoryNameTextView.setText(category.name);
//        headerLayout.setBackgroundColor(Color.parseColor(category.color));

        addBtn = (FloatingActionButton) rootView.findViewById(R.id.fab);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Add", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), NewLinkItemActivity.class);
                startActivity(intent);
            }
        });

    }

    private void openURL(LinkItemView itemView){
        LinkItem item = itemView.getItem();

        Toast.makeText(getContext(), "open " + item.url, Toast.LENGTH_SHORT).show();
        String url = item.url;
        if(!url.startsWith("http"))
            url = "http://" + url;
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        try {
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getContext(), "Cannot open link, Wrong URL", Toast.LENGTH_SHORT).show();
            LogWrapper.d("cannot open url", e);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogWrapper.d("ListItemFragment-onActivityCreated");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogWrapper.d("ListItemFragment onDestroy [" + categoryID + "]");
    }
}
