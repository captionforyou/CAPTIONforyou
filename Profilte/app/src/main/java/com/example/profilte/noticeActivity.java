package com.example.profilte;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class noticeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private SQLiteDatabase profiledb;
    private static final String TAG = "noticeBoard";
    int cursorCount =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        // 리사이클러뷰의 notify()처럼 데이터가 변했을 때 성능을 높일 때 사용한다.

        DataBaseAdapter mDbHelper = new DataBaseAdapter(this);
        mDbHelper.createDatabase();
        profiledb = mDbHelper.open();

        String SQL = null;
        // TODO : notice를 얼마만큼 가져올지 정해야함 그리고 순서를 거꾸로 해야한다 nickname 부터 가져오는거 하기
        SQL = "select * from notice where nickname =  'moon'";

        Cursor profile = profiledb.rawQuery(SQL, null);
        cursorCount = profile.getCount();
        final ArrayList<noticeActivity.noticemenu> m_orders = new ArrayList<noticeActivity.noticemenu>();

        int i =0;

        if(profile != null)
        {
            while (profile.moveToNext())
            {
                noticeActivity.noticemenu m = new noticeActivity.noticemenu(profile.getInt(0), profile.getInt(2), profile.getInt(3));
                m_orders.add(m);
                i++;
            }
        }

        noticeActivity.MenuAdapting m_adapter = new noticeActivity.MenuAdapting(this, R.layout.noticedata_inrow, m_orders) ;
        ListView listview = (ListView) findViewById(R.id.noticelistview) ;
        listview.setAdapter(m_adapter) ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position,
                                    long id) {

                Intent intent = new Intent(noticeActivity.this, noticePopUp.class);
                //이거 맞는지는 확실히 모르겠음
                noticeActivity.noticemenu p = m_orders.get(position);
                intent.putExtra("subNumber", p.getSubBoardNum());
                startActivityForResult(intent, 1);
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //nothing to do
            }
        }
    }

    private class MenuAdapting extends ArrayAdapter<noticemenu> {
        private ArrayList<noticemenu> items;

        public MenuAdapting(Context context, int textViewResourceId, ArrayList<noticeActivity.noticemenu> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.noticedata_inrow, null);
            }

            noticeActivity.noticemenu p = items.get(position);

            if (p != null) {
                TextView noticeNum = (TextView)v.findViewById(R.id.noticeNumber);
                TextView noticeStatus =(TextView)v.findViewById(R.id.noticeStatus);

                noticeNum.setText("알림 "+p.getNum());
                if(p.getStatus() == 1){
                    noticeStatus.setText("자막 요청 접수");
                }
                else if(p.getStatus() == 2){
                    noticeStatus.setText("자막 등록 완료");
                }

            }
            return v;
            }

    }




    class noticemenu{
        private int num;
        private int status;
        private int subBoardnum;

        public noticemenu(int _num, int _status, int _subBoardnum){
            this.num = _num;
            this.status = _status;
            this.subBoardnum = _subBoardnum;
        }

        public int getNum(){return num;}
        public int getStatus(){return status;}
        public int getSubBoardNum(){return subBoardnum;}
    }

}

