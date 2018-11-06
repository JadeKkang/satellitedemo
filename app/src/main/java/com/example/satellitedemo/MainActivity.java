package com.example.satellitedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.satellite.ClickItem;
import com.example.satellite.SatelliteMenu;

public class MainActivity extends AppCompatActivity implements ClickItem {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SatelliteMenu state = (SatelliteMenu)this.findViewById(R.id.left_top);
        SatelliteMenu rightTop = (SatelliteMenu)this.findViewById(R.id.right_top);
        state.setClickItem(this);
        rightTop.setTexts(new String[]{"红色","绿色","蓝色","黄色","黄色"});
    }

    @Override
    public void clickItem(int i) {
        Toast.makeText(this,"点击了"+i+"个按钮",Toast.LENGTH_SHORT).show();
    }
}
