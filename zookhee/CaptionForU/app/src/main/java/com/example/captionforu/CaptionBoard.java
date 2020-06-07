package com.example.captionforu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class CaptionBoard extends Fragment  {
        public List<SubBoard> boardList;
        ImageButton newreqButton ;
        ImageButton refButton ;
        Integer chk;
        String ID;
        String NN;
        View rootview;

        //YouTubePlayer.OnInitializedListener listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview= inflater.inflate(R.layout.fragment_captionboard, container, false);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            ID = bundle.getString("ID");
            NN = bundle.getString("NN");
        }
        newreqButton = (ImageButton)rootview.findViewById(R.id.newreqbutton);
        newreqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),newreqActivity.class);
                intent.putExtra("ID",ID);
                intent.putExtra("NN",NN);
                startActivityForResult(intent,0);
                createList();
            }
        });
        refButton = (ImageButton)rootview.findViewById(R.id.refreshbutton);
        refButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createList();
            }
        });

        createList();
        return rootview;
        }
        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode ==0) {
                if (resultCode == RESULT_OK) {
                    createList();
                }
            }
        }
        public void  createList() {
            ScrollView scroll = (ScrollView) rootview.findViewById(R.id.scroll);
            LinearLayout list = (LinearLayout) scroll.findViewById(R.id.list);
            DataAdapter mDbHelper = new DataAdapter(getActivity());
            mDbHelper.createDatabase();
            mDbHelper.open();
            list.removeAllViews();
            boardList = mDbHelper.getBoardData();


            for (int i = 0; i < boardList.size(); i++) {
                LinearLayout newlist = (LinearLayout) View.inflate(getActivity()  , R.layout.captionboardlayout, null);
                final String link = boardList.get(i).link.toString();
                ImageView img=(ImageView)newlist.findViewById(R.id.youtubeThumbnail);
                Glide.with(this).load("https://img.youtube.com/vi/"+link+"/hqdefault.jpg").into(img);
                TextView lang = (TextView) newlist.findViewById(R.id.lang);
                lang.setText("언어 : " + boardList.get(i).language);
                TextView pay = (TextView) newlist.findViewById(R.id.pay);
                pay.setText("페이 : " + boardList.get(i).tax+"원");
                TextView status = (TextView) newlist.findViewById(R.id.status);
                Log.d("youtubetest", "" + boardList.get(i).status);
                Integer stn = (Integer)boardList.get(i).status;
                if(stn==1)
                    status.setText("접수 필요");
                if(stn==2)
                    status.setText("접수 완료");
                if(stn==3)
                    status.setText("등록 완료");
                //YouTubePlayerView youTubePlayerView = (YouTubePlayerView) newlist.findViewById(R.id.youtubeView);
                //youTubePlayerView.play(link, null);
                list.addView(newlist);
            }
        }

    }
