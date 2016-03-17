package com.demo.qiushi.hard;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private List<Todo> mDatas = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TodoAdapter mAdapter;
    private Myopenhelper dbHelper;
    private SQLiteDatabase db;

    public static final int REQUEST_EDIT = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initdata();
        initview();
    }
        private void initdata(){
            dbHelper = new Myopenhelper(this, "TEST.db", null, 1);
            db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM TODO", null);
            if (cursor.moveToFirst()) {
                do {
                    mDatas.add(new Todo(cursor.getString(cursor.getColumnIndex("TITLE")),cursor.getString(cursor.getColumnIndex("CONTENT"))));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    private void initview() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, EditActivity.class), REQUEST_EDIT);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.re);
        mAdapter = new TodoAdapter(mDatas, new TodoAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v) {
                TextView Tv_title = (TextView) v.findViewById(R.id.item_title);
                TextView Tv_content = (TextView) v.findViewById(R.id.item_content);
                Intent intent=new Intent(MainActivity.this,ShowActivity.class);
                intent.putExtra("extra_title",Tv_title.getText());
                intent.putExtra("extra_content", Tv_content.getText());
                startActivity(intent);
            }
        }, new TodoAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(View v) {
                TextView textTitle=(TextView)findViewById(R.id.item_title);
                String strtitle = (String) textTitle.getText();
                TextView textContent=(TextView)findViewById(R.id.item_content);
                String strcontent = (String) textContent.getText();
                db = dbHelper.getWritableDatabase();
                db.delete("TODO", "TITLE=?",new String[]{strtitle});
                db.delete("TODO", "CONTENT=?", new String[]{strcontent});
                mAdapter.deleteDate(0);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerLine dividerLine = new DividerLine(DividerLine.VERTICAL);
        dividerLine.setSize(1);
        dividerLine.setColor(0xFFDDDDDD);
        mRecyclerView.addItemDecoration(dividerLine);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sx) {return true;
        }else if(id==R.id.action_classtable){return true;}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_EDIT:
                if (resultCode == EditActivity.RESULT_EDIT) {
                    mDatas.add(new Todo(data.getStringExtra("title"),data.getStringExtra("content")));
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
