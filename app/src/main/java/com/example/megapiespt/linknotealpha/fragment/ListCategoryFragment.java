package com.example.megapiespt.linknotealpha.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.megapiespt.linknotealpha.R;
import com.example.megapiespt.linknotealpha.activity.NewCategoryActivity;
import com.example.megapiespt.linknotealpha.adapter.CategoryViewAdapter;
import com.example.megapiespt.linknotealpha.manager.AdapterFactory;
import com.example.megapiespt.linknotealpha.dao.LinkCategory;
import com.example.megapiespt.linknotealpha.manager.LinkCollector;
import com.example.megapiespt.linknotealpha.util.LogWrapper;
import com.example.megapiespt.linknotealpha.view.CategoryItemView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListCategoryFragment extends Fragment {

    private static final String CATE_TYPE = "categoryType";
    private ListView categoryListView;
    private String categoryType;
    private FloatingActionButton fab;

    public static ListCategoryFragment newInstance(String categoryType){
        ListCategoryFragment instance = new ListCategoryFragment();
        Bundle args = new Bundle();
        args.putString(CATE_TYPE, categoryType);
        instance.setArguments(args);
        return instance;
    }

    public ListCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // read value from Arguments to attribute
        categoryType = getArguments().getString(CATE_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_category, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {
        categoryListView = (ListView) rootView.findViewById(R.id.lv_category_list);
        BaseAdapter adapter = AdapterFactory.getInstance().getCategoryAdapter(categoryType);
        categoryListView.setAdapter(adapter);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LogWrapper.d("onClick Category " + (view instanceof CategoryItemView));
                if(view instanceof  CategoryItemView){
                    CategoryItemView categoryItemView = (CategoryItemView) view;
                    CategorySelectedListener listener = (CategorySelectedListener) getActivity();
                    LogWrapper.d((categoryItemView.getCategory() == LinkCollector.getInstance().getCategory(categoryItemView.getCategory().id)) + "");
                    LogWrapper.d(categoryItemView.getCategory().size + "");
                    listener.onCategorySelected(categoryItemView.getCategory());
                }
            }
        });

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    public interface CategorySelectedListener{
        void onCategorySelected(LinkCategory category);
    }

}
