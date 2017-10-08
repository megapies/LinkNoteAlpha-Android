package com.example.megapiespt.linknotealpha.util;

import android.content.Context;

/**
 * Created by MegapiesPT on 15/6/2560.
 */

public class Contextor {
    private static Contextor instance;
    private Context context;

    public static Contextor getInstance(){
        if(instance == null)
            instance = new Contextor();
        return instance;
    }

    private Contextor(){}

    public Context getContext(){
        return context;
    }

    public void init(Context context){
        this.context = context;
    }
}
