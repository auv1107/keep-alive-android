package com.antiless.template;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.antiless.template.adapter.MyFragmentPagerAdapter;
import com.antiless.template.fragment.AFragment;
import com.antiless.template.fragment.BFragment;
import com.antiless.template.fragment.CFragment;
import com.antiless.template.fragment.DFragment;
import com.antiless.template.fragment.EFragment;
import com.antiless.template.fragment.FFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gc on 16/10/21.
 */
public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private List<Fragment> listFragment;
    private List<String> listTitle;

    /**
     * 此处的众多Fragment由开发者自定义替换
     */
    private AFragment aFragment;
    private BFragment bFragment;
    private CFragment cFragment;
    private DFragment dFragment;
    private EFragment eFragment;
    private FFragment fFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show toolbar return arrow.
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        initView();
    }

    /**
     * 初始化各控件
     */
    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        // 初始化各fragment
        aFragment = new AFragment();
        bFragment = new BFragment();
        cFragment = new CFragment();
        dFragment = new DFragment();
        eFragment = new EFragment();
        fFragment = new FFragment();

        // 将fragment装进列表中
        listFragment = new ArrayList<>();
        listFragment.add(aFragment);
        listFragment.add(bFragment);
        listFragment.add(cFragment);
        listFragment.add(dFragment);
        listFragment.add(eFragment);
        listFragment.add(fFragment);

        // 将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        listTitle = new ArrayList<>();
        listTitle.add("热门免费");
        listTitle.add("热门付费");
        listTitle.add("创收最高");
        listTitle.add("畅销付费新品");
        listTitle.add("热门免费新品");
        listTitle.add("上升最快");

        // tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        // 设置TabLayout的模式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        // tabLayout.setTabMode(TabLayout.MODE_FIXED);
        // 为TabLayout添加tab名称
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(3)));
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(4)));
        tabLayout.addTab(tabLayout.newTab().setText(listTitle.get(5)));

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), listFragment, listTitle);

        viewPager.setAdapter(myFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
