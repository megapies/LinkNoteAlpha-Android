package com.example.megapiespt.linknotealpha.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.megapiespt.linknotealpha.R;
import com.example.megapiespt.linknotealpha.activity.MainActivity;
import com.example.megapiespt.linknotealpha.util.LogWrapper;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceholderFragment extends Fragment implements MainActivity.ItemListShowableFragment{
    private static final String CATE_ID = "categoryID";
    private FrameLayout container;
    private String categoryID;

    public static PlaceholderFragment newInstance(String categoryID){
        PlaceholderFragment instance = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putString(CATE_ID, categoryID);
        instance.setArguments(args);
        return instance;
    }


    public PlaceholderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // unpack args
        categoryID = getArguments().getString(CATE_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_placeholder, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {
        LogWrapper.d("Placeholder initInstance " + (getActivity() == null));
        container = (FrameLayout) rootView.findViewById(R.id.contentContainer);
        show();
    }


    @Override
    public void onStart() {
        super.onStart();
        LogWrapper.d("Placeholder onStart");
    }

    @Override
    public void showListItem(String categoryID) {
//        ListItemFragment fragment = ListItemFragment.newInstance(categoryID);
//        getChildFragmentManager().beginTransaction()
//                                .add(R.id.contentContainer, fragment, categoryID)
//                                .detach(fragment)
//                                .commit();
        LogWrapper.d("Placeholder show " + (getActivity() == null));
        this.categoryID = categoryID;
        getArguments().putString(CATE_ID, categoryID);
        if(getActivity() != null)
            show();
    }

    private void show(){
        if(categoryID != null)
            getChildFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, ListItemFragment.newInstance(categoryID), categoryID)
                .commit();
    }
}
