package com.example.androiid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androiid.databinding.RegisterBinding;
import com.example.androiid.models.*;
import com.example.androiid.service.ApiSetup;
import com.example.androiid.service.RetroInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity{

    String[] roles = {"Admin", "Student", "Tutor"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> stringArrayAdapter;
    RetroInterface retroInterface;
    List<Courses> courses = new ArrayList<>();
    RegisterBinding registerBinding;
    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        registerBinding = RegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());
        retroInterface = ApiSetup.getClient().create(RetroInterface.class);

        autoCompleteTextView = findViewById(R.id.auto_complete);
        stringArrayAdapter = new ArrayAdapter<>(this, R.layout.list_item, roles);
        autoCompleteTextView.setAdapter(stringArrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
            }
        });
        registerBinding.button.setOnClickListener(this::submitListener);
    }

    private void submitListener(View button) {
        String username = registerBinding.username.getText().toString();
        String password = registerBinding.password.getText().toString();
        String role = registerBinding.autoComplete.getText().toString();
        String firstname = registerBinding.firstname.getText().toString();
        String lastname = registerBinding.lastname.getText().toString();
        RegisterModel user = new RegisterModel();
        user.email = username;
        user.role = role;
        user.firstname = firstname;
        user.lastname = lastname;
        user.password = password;
        register(user);
    }

    private void register(RegisterModel user){
        Call<RegisterResult> call1 = retroInterface.register(user);
        call1.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                    return;
                }
                RegisterResult user1 = response.body();
                SharedPreferences tokenPref = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = tokenPref.edit().putString("token", user1.getData().getToken());
                editor.commit();
                if(user.role.equalsIgnoreCase("student")){
                    Intent intent = new Intent(RegisterActivity.this, StudentExplore.class);
                    RegisterActivity.this.startActivity(intent);
                } else if (user.role.equalsIgnoreCase("tutor")) {
                    Intent intent = new Intent(RegisterActivity.this, TutorExplore.class);
                    RegisterActivity.this.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }
}
