package com.example.captionforu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

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

    }
    public List<UserInfo> userList ;

    private void insertNewAccount(String id,String password,String nickname) {
        DataAdapter mDbHelper = new DataAdapter(getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();


        userList = mDbHelper.getTableData();
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
            mDbHelper.insertNewUser(id,password,nickname);
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

        mDbHelper.close();
    }
}
