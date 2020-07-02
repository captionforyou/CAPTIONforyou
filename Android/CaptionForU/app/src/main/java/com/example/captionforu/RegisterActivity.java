package com.example.captionforu;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText idtext=(EditText)findViewById(R.id.IDtext);
        final EditText pwtext=(EditText)findViewById(R.id.PWtext);
        final EditText pwwtext=(EditText)findViewById(R.id.PWWtext);
        final EditText nntext=(EditText)findViewById(R.id.NNtext);

        Button newaccountButton = (Button) findViewById(R.id.newaccountbutton);
        newaccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pwtext.getText().toString().equals(pwwtext.getText().toString())) {
                    if (!idtext.getText().toString().equals("") && !pwtext.getText().toString().equals("") && !pwwtext.getText().toString().equals("") && !nntext.getText().toString().equals("")) {
                        insertNewAccount(idtext.getText().toString(), pwtext.getText().toString(), nntext.getText().toString());
                    } else {
                        Toast.makeText(RegisterActivity.this, "빈칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(RegisterActivity.this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        RetrofitConnection retrofitConnection = new RetrofitConnection();
        Call<List<UserInfo>> getcall =  retrofitConnection.server.get_Userinfo("json");
        getcall.enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
                try
                {
                    Log.e("succ","reg db 불러오기 성공");
                    userList=response.body();
                }
                catch (Exception e) {
                    Log.e("fail","reg db 불러오기 실패");
                }
            }
            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {
                Log.e("fail",t.toString());
            }
        });

    }
    public List<UserInfo> userList ;
    private void insertNewAccount(String id,String password,String nickname) {

        Integer idchk=0;
        Integer nnchk=0;


        for(int i=0;i<userList.size();i++)
        {
            if(userList.get(i).id.equals(id)) {
                idchk=1;
            }
            if(userList.get(i).nickname.equals(nickname)){
                nnchk=2;
            }
        }
        if(idchk==0 && nnchk==0)
        {
            UserInfo userinfo = new UserInfo();
            userinfo.setid(id);
            userinfo.setpw(password);
            userinfo.setnn(nickname);
            userinfo.setpoints(0);
            userinfo.setmoney(0);
            userinfo.setrequestNum(0);
            userinfo.setregiseterNum(0);
            userinfo.setratingCompletenss(0);
            userinfo.setratingClarity(0);
            userinfo.setratingTime(0);
            userinfo.setnoticeCnt(0);
            userinfo.setratingCnt(0);
            RetrofitConnection retrofitConnection = new RetrofitConnection();
            Call<UserInfo> postcall =  retrofitConnection.server.post_Userinfo("json",userinfo);
            postcall.enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    try {
                        Log.e("succ","ID 등록 성공");
                    }
                    catch (Exception e) {
                        int StatusCode = response.code();
                        Log.e("fail","ID 등록 실패");
                    }

                }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {
                    Log.e("fail",t.toString());
                }
            });
            setResult(RESULT_OK);
            finish();
        }
        if(idchk==1)
        {
            Toast.makeText(this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
        }
        else if(nnchk==1)
        {
            Toast.makeText(this, "이미 존재하는 닉네임입니다.", Toast.LENGTH_SHORT).show();
        }

    }
}
