package com.example.captionforu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ranking extends Fragment {
    public List<UserInfo> userList ;
    View rootview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_ranking, container, false);
        initLoadUserList();
        return rootview;
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
                    setRanking();
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
    public void setRanking(){
        Collections.sort(userList, new Comparator<UserInfo>() {
            @Override
            public int compare(UserInfo o1, UserInfo o2) { // 기대 점수가 높은 순으로 정렬
                return (o2.getregiseterNum().compareTo(o1.getregiseterNum()));
            }
        });

        TextView rangking1 = (TextView)rootview.findViewById(R.id.ranking1);
        rangking1.setText("1등      "+userList.get(0).getnn());
        TextView rangking2 = (TextView)rootview.findViewById(R.id.ranking2);
        rangking2.setText("2등      "+userList.get(1).getnn());
        TextView rangking3 = (TextView)rootview.findViewById(R.id.ranking3);
        rangking3.setText("3등      "+userList.get(2).getnn());
    }
}
