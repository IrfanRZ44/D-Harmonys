package com.exomatik.irfanrz.dharmonis.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.exomatik.irfanrz.dharmonis.Adapter.ViewPagerAdapter;
import com.exomatik.irfanrz.dharmonis.R;
import com.exomatik.irfanrz.dharmonis.fragment.fragmentLogin;
import com.exomatik.irfanrz.dharmonis.fragment.fragmentProfil;
import com.exomatik.irfanrz.dharmonis.fragment.fragmentVideo;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabKategori;
    private ViewPager viewKategori;
    private ViewPagerAdapter adapterKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabKategori = (TabLayout) findViewById(R.id.tab_Kategori);
        viewKategori = (ViewPager) findViewById(R.id.view_Kategori);
        adapterKategori = new ViewPagerAdapter(getSupportFragmentManager());

        adapterKategori.AddFragment(new fragmentVideo());
        adapterKategori.AddFragment(new fragmentProfil());
        adapterKategori.AddFragment(new fragmentLogin());
        FirebaseApp.initializeApp(this);

        viewKategori.setAdapter(adapterKategori);
        tabKategori.setupWithViewPager(viewKategori);
        tabKategori.getTabAt(0).setIcon(R.drawable.ic_video_yellow);
        tabKategori.getTabAt(1).setIcon(R.drawable.ic_people_white);
        tabKategori.getTabAt(2).setIcon(R.drawable.ic_setting_white);

        tabKategori.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    tabKategori.getTabAt(0).setIcon(R.drawable.ic_video_yellow);
                    tabKategori.getTabAt(1).setIcon(R.drawable.ic_people_white);
                    tabKategori.getTabAt(2).setIcon(R.drawable.ic_setting_white);
                }
                else if (tab.getPosition() == 1){
                    tabKategori.getTabAt(0).setIcon(R.drawable.ic_video_white);
                    tabKategori.getTabAt(1).setIcon(R.drawable.ic_people_yellow);
                    tabKategori.getTabAt(2).setIcon(R.drawable.ic_setting_white);
                }
                else if (tab.getPosition() == 2){
                    tabKategori.getTabAt(0).setIcon(R.drawable.ic_video_white);
                    tabKategori.getTabAt(1).setIcon(R.drawable.ic_people_white);
                    tabKategori.getTabAt(2).setIcon(R.drawable.ic_setting_yellow);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
