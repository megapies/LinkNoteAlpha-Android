package com.example.megapiespt.linknotealpha.manager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by MegapiesPT on 18/6/2560.
 */

public class UserManager {
    private static UserManager insatance;
    private FirebaseUser user;
    private String userID;
    public static UserManager getnstance(){
        if(insatance == null)
            insatance = new UserManager();
        return insatance;
    }

    private UserManager(){
        user = FirebaseAuth.getInstance().getCurrentUser();
    }


    public String getUserID(){
        return user.getUid();
//        return user.getUid();
    }
}
