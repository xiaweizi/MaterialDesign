package com.xiaweizi.materialdesign;

import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity-->";

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;

    private Meizhi[] meizhis = {new Meizhi("AngleBaby", R.drawable.angle), new Meizhi("古力娜扎", R.drawable.gulinazha),
            new Meizhi("林允儿", R.drawable.linyuner), new Meizhi("刘亦菲", R.drawable.liuyifei),
            new Meizhi("孙俪", R.drawable.suili), new Meizhi("佟丽娅", R.drawable.tongliya),
            new Meizhi("杨幂", R.drawable.yangmi), new Meizhi("赵丽颖", R.drawable.zhaoliyin),
            new Meizhi("李冰冰", R.drawable.libingbing), new Meizhi("唐嫣", R.drawable.tangyan)};

    private List<Meizhi> beautyList = new ArrayList<>();

    private MeizhiAdapter mAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main);
        mNavigationView = (NavigationView) findViewById(R.id.nv_left);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_main);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_main);

        initData();
        mAdpter = new MeizhiAdapter(beautyList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mAdpter);


        setSupportActionBar(mToolbar);


        //给NavigationView设置item选择事件
        mNavigationView.setCheckedItem(R.id.nav_call);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Snackbar snackbar = Snackbar.make(getCurrentFocus(), item.getTitle(), Snackbar.LENGTH_SHORT);
                snackbar.setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                snackbar.setActionTextColor(Color.BLUE);
                snackbar.show();
                return true;
            }
        });

        //给打开左侧菜单的按钮是指特效
        mToggle = new ActionBarDrawerToggle(MainActivity.this,
                                            mDrawerLayout,
                                            mToolbar,
                                            R.string.open, R.string.close);
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);

        mRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

    }

    /***************************
     * 创建菜单
     ***************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /***************************
     * 给菜单设置点击事件
     ***************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(MainActivity.this, "backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    private void initData() {
        beautyList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(meizhis.length);
            beautyList.add(meizhis[index]);
        }
    }

    private void refreshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        mAdpter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                        Toast.makeText(MainActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
}
