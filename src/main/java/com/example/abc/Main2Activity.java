package com.example.abc;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.*;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.abc.homefragment;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new homefragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment=null;
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    Intent intent=new Intent(getApplicationContext(),homefragment.class);
startActivity(intent);
                    break;
                case R.id.nav_gallery:
                   // Intent i=new Intent(getApplicationContext(),galleryfragment.class);
                   // startActivity(i);
                    fragment=new galleryfragment();
                    break;
                case R.id.nav_search:
                    fragment=new searchfragment();
                    break;
                case R.id.nav_profile:
                    fragment=new profilefragment();
                    break;
            }
            if(fragment!=null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }return true;
        }
    };
}

