package com.grupo2.hoycomo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;



public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.drawable.ic_action_name);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        if (!isOnline()){
            Context context = getApplicationContext();
            CharSequence text = "No se detectó conexión a internet, la aplicación no podrá utilizarse";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        } else {
            boolean loggedIn = AccessToken.getCurrentAccessToken() != null;
            setContentView(R.layout.activity_main);
            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            intent = new Intent(getApplicationContext(), Main2Activity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancel() {
                            ErrorManager.showToastError("Su solicitud de login fue cancelada por Facebook");
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            ErrorManager.showToastError("Se produjo un error al intentar conectarse con Facebook, pruebe más tarde");
                        }
                    });
            if (loggedIn) {
                intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}

