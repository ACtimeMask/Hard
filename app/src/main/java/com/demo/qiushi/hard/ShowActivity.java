package com.demo.qiushi.hard;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ShowActivity extends AppCompatActivity {
private EditText ShowTitle;
    private EditText ShowContent;
    private Myopenhelper myopenhelper;
    private SQLiteDatabase db;
    private Button buttonBc;
    private Button buttonFh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        myopenhelper=new Myopenhelper(this,"TEST.db",null,1);
        db=myopenhelper.getWritableDatabase();
        Intent intent=getIntent();
        final String title=intent.getStringExtra("extra_title");
        String content=intent.getStringExtra("extra_content");
        ShowTitle=(EditText)findViewById(R.id.show_title);
        ShowContent=(EditText)findViewById(R.id.show_content);
        ShowTitle.setText(title);
        ShowContent.setText(content);
        buttonBc=(Button)findViewById(R.id.show_bc);
        buttonBc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titles = ShowTitle.getText().toString();
                String contents=ShowContent.getText().toString();
                ContentValues values=new ContentValues();
                values.put("TITLE",titles);
                values.put("CONTENT", contents);
                db.update("TODO", values, "TITLE=?", new String[]{title});
                finish();
            }
        });
        buttonFh=(Button)findViewById(R.id.show_fh);
        buttonFh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
