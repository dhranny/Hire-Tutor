package com.example.androiid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.androiid.databinding.LoginBinding;
import com.example.androiid.models.LoginModel;
import com.example.androiid.models.LoginResult;
import com.example.androiid.service.ApiSetup;
import com.example.androiid.service.RetroInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    RetroInterface retroInterface;
    LoginBinding loginBinding;
    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        retroInterface = ApiSetup.getClient().create(RetroInterface.class);
        loginBinding = LoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        loginBinding.regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

        loginBinding.button.setOnClickListener(this::submitListener);
    }

    private void submitListener(View button) {
        String username = loginBinding.username.getText().toString();
        String password = loginBinding.password.getText().toString();
        LoginModel user = new LoginModel();
        user.email = username;
        user.type = "tutor";
        user.password = password;
        login(user);
    }


    private void login(LoginModel user){
        Call<LoginResult> call1 = retroInterface.login(user);
        call1.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                    return;
                }
                LoginResult user1 = response.body();
                SharedPreferences tokenPref = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = tokenPref.edit().putString("token", user1.data);
                editor.commit();
                if(user.type.equalsIgnoreCase("student")){
                    Intent intent = new Intent(LoginActivity.this, StudentExplore.class);
                    LoginActivity.this.startActivity(intent);
                } else if (user.type.equalsIgnoreCase("student")) {
                    Intent intent = new Intent(LoginActivity.this, TutorExplore.class);
                    LoginActivity.this.startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

}