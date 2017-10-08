package com.example.megapiespt.linknotealpha.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.megapiespt.linknotealpha.R;
import com.example.megapiespt.linknotealpha.manager.DatabaseManager;
import com.example.megapiespt.linknotealpha.manager.LinkCollector;
import com.example.megapiespt.linknotealpha.util.LogWrapper;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements DatabaseManager.InitDataObserver{

    private ProgressBar pregress;
    private TextView loadingInfoTextView;
    private int categoryCount;
    private int itemCount;
    private int categoryLoaded;
    private int itemLoaded;
    private CallbackManager callBackManager;
    private LoginButton facebookLoginBtn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initInstance();
    }

    private void initInstance() {

        auth = FirebaseAuth.getInstance();

        callBackManager = CallbackManager.Factory.create();
        facebookLoginBtn = (LoginButton) findViewById(R.id.btn_facebook_login);
        facebookLoginBtn.setReadPermissions("email", "public_profile");
        facebookLoginBtn.registerCallback(callBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                facebookLoginBtn.setClickable(false);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                // TODO handle cannot login
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        LogWrapper.d("AccessToken " + accessToken);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            LogWrapper.d("sign in complete uid=" + user.getUid());

                            DatabaseManager.getInstance().init();
                            getProfile();

                            gotoMainActivity();
                        }else{
                            LogWrapper.d("sign in failure");
                            Log.wtf("Link-Note", "sign in failure", task.getException());
                        }
                    }
                });
    }
    private void getProfile(){
        LogWrapper.d("Token user id " + AccessToken.getCurrentAccessToken().getUserId());
        Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        LogWrapper.d("profile response " + response);
                        try{
                            if(response != null){
                            /* handle the result */
                                JSONObject data = response.getJSONObject();
                                LogWrapper.d("Json data " + data);
                                if(data.has("picture")){
                                    String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                    LogWrapper.d("profile url " + profilePicUrl);
                                }

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }
        ).executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callBackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
    }

    private void onClickLogin(){
        pregress.setVisibility(View.VISIBLE);
        DatabaseManager.getInstance();
//        DatabaseManager.getInstance("user001").initListener();

        gotoMainActivity();
    }

    @Override
    public void setInitDataCount(int categoryCount, int itemCount) {
        LogWrapper.d("Login setInitDataCount");
        this.categoryCount = categoryCount;
        this.itemCount = itemCount;
    }

    @Override
    public void onAddCategory() {
        categoryLoaded++;
    }

    @Override
    public void onAddItem() {
        itemLoaded++;
    }


    private void gotoMainActivity(){
        LogWrapper.d("Login goto Main");
        LogWrapper.d("cateLoaded " + categoryLoaded + " cateCount " + categoryCount);
        LogWrapper.d("itemLoaded " + itemLoaded + " itemCount " + itemCount);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
