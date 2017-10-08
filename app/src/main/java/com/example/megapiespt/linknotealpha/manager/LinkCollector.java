package com.example.megapiespt.linknotealpha.manager;

import com.example.megapiespt.linknotealpha.dao.LinkCategory;
import com.example.megapiespt.linknotealpha.dao.LinkItem;
import com.example.megapiespt.linknotealpha.util.LogWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by MegapiesPT on 10/6/2560.
 */

public class LinkCollector {
    public static String ALL_ID = "*All";
    private static LinkCollector instance;
    private Map<String, LinkCategory> categories;
    private ArrayList<LinkItem> items;
    private Map<String, Integer> categorySize;
    private Set<ItemChangeListener> itemListeners;
    private Set<CategoryChangeListener> categoryListeners;

    public static LinkCollector getInstance(){
        if (instance == null)
            instance = new LinkCollector();
        return instance;
    }

    private LinkCollector(){
        categories = new TreeMap<>();
        items = new ArrayList<>();
        categorySize = new HashMap<>();
        itemListeners = new HashSet<>();
        categoryListeners = new HashSet<>();

        LinkCategory allCategory = new LinkCategory();
        allCategory.name  = ".All";
        allCategory.color = "#ff5555";
        allCategory.id = ALL_ID;

        addCategory(allCategory);
    }
    /*
        Listener method
     */
    public void regisItemListener(ItemChangeListener listener){
        itemListeners.add(listener);
    }
    public void removeItemListener(ItemChangeListener listener){
        itemListeners.remove(listener);
    }
    private void notifyOnItemAdd(LinkItem item){
        for(ItemChangeListener listener : itemListeners){
            String itemCate = item.cate_id;
            String listenerCate = listener.getCategoryID();
            if(listenerCate.equals(ALL_ID) || listenerCate.equals(itemCate))
                listener.onItemAdd(item);
        }
    }
    private void notifyOnItemRemove(LinkItem item){
        // TODO notify item remove
    }
    private void notifyOnItemChange(LinkItem item){
        // TODO notify item change
    }

    public void regisCategoryListener(CategoryChangeListener listener){
        categoryListeners.add(listener);
    }
    public void removeCAtegoryListener(CategoryChangeListener listener){
        categoryListeners.remove(listener);
    }
    private void notifyCategoryAdd(LinkCategory category){
        LogWrapper.d("cate listener " + categoryListeners);
        for(CategoryChangeListener listener : categoryListeners){
                listener.onCategoryAdd(category);
        }
    }
    private void notifyOnCategoryRemove(LinkCategory category){
        // TODO notify category remove
    }
    private void notifyOnCategoryChange(LinkCategory category){
        for(CategoryChangeListener listener : categoryListeners)
            listener.onCategoryChange(category);
    }

    /*
        categories method
     */
    public int getCategoriesSize(){
        return categories.size();
    }

    public LinkCategory getCategory(String cate_id){
        return categories.get(cate_id);
    }

    public void addCategory(LinkCategory category){
        if(categories.get(category.id) != null){
            categories.get(category.id).name = category.name;
            categories.get(category.id).color = category.color;
            category = categories.get(category.id);
        }else
            categories.put(category.id, category);
//            category.size = categories.get(category.id).size;

        LogWrapper.d("add category " + category.name + " " + category.size);
        LogWrapper.d("categories " + categories);

        notifyCategoryAdd(category);
//        categories.put(category.id, category);
//        if(categorySize.get(category.id) == null)
//            categorySize.put(category.id, 0);

    }
    public Collection<LinkCategory> getCategories(){
        return categories.values();
    }
    public Map<String, Integer> getCategorySize() {
        return categorySize;
    }

    /*
        items method
     */
    public int getItemCount(String cate_id){
        if(cate_id == ALL_ID){
            return items.size();
        }
        return categorySize.get(cate_id);
    }

    public void addItem(LinkItem item){
        items.add(item);

        if(categories.get(item.cate_id) == null)
            categories.put(item.cate_id, new LinkCategory());
        categories.get(item.cate_id).size++;
        categories.get(ALL_ID).size++;
        LogWrapper.d("add item in " + categories.get(item.cate_id).name + " " + categories.get(item.cate_id).size);
        notifyOnItemAdd(item);
        notifyOnCategoryChange(categories.get(item.cate_id));
//        if(categorySize.get(item.cate_id) == null)
//            categorySize.put(item.cate_id, 0);
//        categorySize.put(item.cate_id, categorySize.get(item.cate_id)+1);
    }
    public LinkItem getItem(int i){
        return items.get(i);
    }

    public interface ItemChangeListener{
        String getCategoryID();
        void onItemAdd(LinkItem item);
        void onItemRemove(LinkItem item);
        void onItemChange(LinkItem item);
    }

    public interface CategoryChangeListener{
        void onCategoryAdd(LinkCategory category);
        void onCategoryRemove(LinkCategory category);
        void onCategoryChange(LinkCategory category);
    }

    public void clear(){
        categories = new TreeMap<>();
        items = new ArrayList<>();
        categorySize = new HashMap<>();
        itemListeners = new HashSet<>();
        categoryListeners = new HashSet<>();

        LinkCategory allCategory = new LinkCategory();
        allCategory.name  = ".All";
        allCategory.color = "#ff5555";
        allCategory.id = ALL_ID;

        addCategory(allCategory);
    }

}
