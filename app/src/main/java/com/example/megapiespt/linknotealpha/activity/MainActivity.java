package com.example.megapiespt.linknotealpha.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.megapiespt.linknotealpha.R;
import com.example.megapiespt.linknotealpha.fragment.ListCategoryFragment;
import com.example.megapiespt.linknotealpha.fragment.PlaceholderFragment;
import com.example.megapiespt.linknotealpha.manager.AdapterFactory;
import com.example.megapiespt.linknotealpha.manager.DatabaseManager;
import com.example.megapiespt.linknotealpha.manager.LinkCollector;
import com.example.megapiespt.linknotealpha.dao.LinkCategory;
import com.example.megapiespt.linknotealpha.util.LogWrapper;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements ListCategoryFragment.CategorySelectedListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PlaceholderFragment listFragment;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if(savedInstanceState == null){
//            DatabaseManager.getInstance().initListener();
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.contentContainer, ListItemFragment.newInstance()).commit();
//        }
        initInstance();
    }

    private void initInstance() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listFragment = PlaceholderFragment.newInstance(LinkCollector.ALL_ID);
//        listFragment.showListItem(LinkCollector.ALL_ID);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        LogWrapper.d("Pager 0");
                        return listFragment;
                    case 1:
                        return ListCategoryFragment.newInstance(AdapterFactory.NORMAL_CATEGORY);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case 0:
                        return "List";
                    case 1:
                        return "Category";
                    default:
                        return null;
                }
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabBar);
        tabLayout.setupWithViewPager(viewPager);

        logoutBtn = (Button) findViewById(R.id.btn_logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        LogWrapper.d("Activity-onStart");
//        Log.wtf("Link-Note", LinkCollector.getInstance().getCategories() + "");

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCategorySelected(LinkCategory category) {
        viewPager.setCurrentItem(0);
        listFragment.showListItem(category.id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogWrapper.d("MainActivity onDestroy");

        LinkCollector.getInstance().clear();
        DatabaseManager.getInstance().clear();

    }

    public interface ItemListShowableFragment{
        void showListItem(String categoryID);
    }
}
