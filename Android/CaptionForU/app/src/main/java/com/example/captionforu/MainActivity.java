package com.example.captionforu;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private CaptionBoard captionboard = new CaptionBoard();
    private Study study = new Study();
    private Ranking ranking = new Ranking();
    private Event event = new Event();
    private Profile profile = new Profile();
    String ID;
    String NN;
    String NO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ID = getIntent().getStringExtra("ID");
        NN = getIntent().getStringExtra("NN");
        NO = getIntent().getStringExtra("no");
        Bundle Bundle=new Bundle(2);
        Bundle.putString("ID",ID);
        Bundle.putString("NN",NN);
        Bundle.putString("NO",NO);
        captionboard.setArguments(Bundle);
        profile.setArguments(Bundle);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, captionboard).commitAllowingStateLoss();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.boardtab: {
                        transaction.replace(R.id.nav_host_fragment, captionboard).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.studytab: {
                        transaction.replace(R.id.nav_host_fragment, study).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.rankingtab: {
                        transaction.replace(R.id.nav_host_fragment, ranking).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.eventtab: {
                        transaction.replace(R.id.nav_host_fragment, event).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.profiletab: {
                        transaction.replace(R.id.nav_host_fragment, profile).commitAllowingStateLoss();
                        break;
                    }
                }

                return true;
            }
        });
    }

}
