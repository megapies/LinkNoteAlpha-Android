package com.example.megapiespt.linknotealpha.manager;

import android.util.Log;

import com.example.megapiespt.linknotealpha.dao.LinkCategory;
import com.example.megapiespt.linknotealpha.dao.LinkItem;
import com.example.megapiespt.linknotealpha.util.LogWrapper;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MegapiesPT on 10/6/2560.
 */

public class DatabaseManager {
    private final String CATE_DICT = "category_dict";

    private final String COUNT = "count";
    private final String CATEGORY = "category";
    private final String ITEM = "item";

    private static DatabaseManager instance;
    private LinkCollector collector;
    private DatabaseReference dataRef;
    private ArrayList<InitDataObserver> initObservers;
    private ChildEventListener categoryListener;
    private ChildEventListener itemListener;

    public static DatabaseManager getInstance(){
        if(instance == null) {
            LogWrapper.d("DatabaseManager-getInstance");
            instance = new DatabaseManager();
        }
        return instance;
    }

    private DatabaseManager() {
        LogWrapper.d("DatabaseManager-Constructor");

    }

    public void init(){
        String uid = UserManager.getnstance().getUserID();
        dataRef = FirebaseDatabase.getInstance().getReference().child(uid);
        checkForNewUser();
        collector = LinkCollector.getInstance();
        initObservers = new ArrayList<>();
        initListenerStep2();
    }

    private void checkForNewUser(){
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LogWrapper.d("checkForNewUser " + dataSnapshot.getValue());
                if(dataSnapshot.getValue() == null){
                    createDataNewUser();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createDataNewUser(){
        DummyNewUserDatabase dummy = new DummyNewUserDatabase();

        dummy.createCategoryDict(dataRef);
        dummy.createCount(dataRef);
    }

    public void regisInitObserver(InitDataObserver obs){
        initObservers.add(obs);
    }
    private void notifyAddInitCategory(){
        for (InitDataObserver obs : initObservers)
            obs.onAddCategory();
    }
    private void notifyAddInitItem(){
        for(InitDataObserver obs : initObservers)
            obs.onAddItem();
    }
    private void setInitDataCount(int category_count, int item_count){
        for(InitDataObserver obs : initObservers)
            obs.setInitDataCount(category_count, item_count);
    }

    public void addItem(LinkItem item){
        dataRef.child(ITEM).push().setValue(item);
    }
    public void addCategory(LinkCategory category){
        dataRef.child(CATE_DICT).push().setValue(category);
    }
    private void initListener() {
        Log.wtf("Link-Note", "DB Manager initListener");
        dataRef.child(COUNT).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
//                LogWrapper.d(map.toString());
                if(map != null && map.get(CATEGORY) != null && map.get(ITEM) != null){
                    int category_count = Integer.parseInt(map.get(CATEGORY).toString());
                    int item_count = Integer.parseInt(map.get(ITEM).toString());

//                    setInitDataCount(category_count, item_count);
                    LogWrapper.d("cate = " + category_count + ", item = " + item_count);


                }

                initListenerStep2();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO handle cannot load count
            }
        });


    }
    private void initListenerStep2(){
        categoryListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LinkCategory category = dataSnapshot.getValue(LinkCategory.class);
                if (category.name != null && category.color != null) {
                    category.id = dataSnapshot.getKey();
                    collector.addCategory(category);
                    LogWrapper.d("DB Manager add category " + category);
//                    notifyAddInitCategory();
                } else {
                    LogWrapper.d("DB Manager not have category");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // TODO handle category change
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // TODO handle remove category
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                // TODO handle move category
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO handle cannot load category
            }
        };
        itemListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LinkItem item = dataSnapshot.getValue(LinkItem.class);
                if (item.name != null && item.url != null && item.cate_id != null) {
                    item.id = dataSnapshot.getKey();
                    collector.addItem(item);
//                    LogWrapper.d("add item " + item);
//                    notifyAddInitItem();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        dataRef.child(CATE_DICT).addChildEventListener(categoryListener);
        dataRef.child(ITEM).addChildEventListener(itemListener);
    }

    public void clear(){
        dataRef.child(CATE_DICT).removeEventListener(categoryListener);
        dataRef.child(ITEM).removeEventListener(itemListener);
    }
    public interface InitDataObserver{
        void setInitDataCount(int categoryCount, int itemCount);
        void onAddCategory();
        void onAddItem();
    }

    private class DummyNewUserDatabase{
        private final String NAME = "name";
        private final String COLOR = "color";

        private void createCategoryDict(DatabaseReference userRoot){
            Map<String, String> movieCate = new HashMap<>();
            Map<String, String> eduCate = new HashMap<>();
            Map<String, String> techCate = new HashMap<>();

            movieCate.put(NAME, "Movie");
            movieCate.put(COLOR, "#ff0000");
            eduCate.put(NAME, "Education");
            eduCate.put(COLOR, "#00ff00");
            techCate.put(NAME, "Technology");
            techCate.put(COLOR, "#0000ff");

            userRoot.child(CATE_DICT).push().setValue(movieCate);
            userRoot.child(CATE_DICT).push().setValue(eduCate);
            userRoot.child(CATE_DICT).push().setValue(techCate);
        }

        private void createCount(DatabaseReference userRoot){
            Map<String, Integer> count = new HashMap<>();

            count.put(CATEGORY, 3);
            count.put(ITEM, 0);

            userRoot.child(COUNT).setValue(count);
        }
    }

}
