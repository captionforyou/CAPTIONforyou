package com.example.captionforu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    public List<UserInfo> userList ;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLoadUserList();
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                initLoadUserList();
                attemptLogin();
            }
        });

        Button registerButton = (Button) findViewById(R.id.registerbutton);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,0);
                initLoadUserList();
            }
        });

    }

    private void initLoadUserList() {

        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<List<UserInfo>> call =  retrofitConnection.server.get_Userinfo("json");


        call.enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
                try
                {
                    Log.e("succ","db 불러오기 성공");
                    userList=response.body();
                }
                catch (Exception e) {
                    Log.e("fail","db 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });
    }

    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        Integer cancel = 0;
        Log.d("logintest",""+userList.size());
        for(int i=0;i<userList.size();i++)
        {
            if(userList.get(i).id.equals(email)) {
                cancel=1;
                if(userList.get(i).password.equals((password)))
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("no",userList.get(i).no.toString());
                    intent.putExtra("ID",userList.get(i).id);
                    intent.putExtra("NN",userList.get(i).nickname);
                    startActivity(intent);
                    cancel=2;
                }
            }

        }
        if (cancel==0) {
            Toast.makeText(this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
        }
        else if (cancel==1) {
            Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            finish();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==0) {
            if (resultCode == RESULT_OK) {
                initLoadUserList();
                Toast.makeText(this, "회원가입을 완료하였습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

