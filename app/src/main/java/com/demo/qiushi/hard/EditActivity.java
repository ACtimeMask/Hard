package com.demo.qiushi.hard;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    private EditText Title_edit;
    private EditText Content_edit;
    private Button Bt_d;
    private Button Bt_x;
    private Myopenhelper myhelper;
    private SQLiteDatabase db;
    public static final int RESULT_UN_EDIT = 0;
    public static final int RESULT_EDIT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        myhelper=new Myopenhelper(this,"TEST.db",null,1);
        db=myhelper.getWritableDatabase();
        initview();
        setResult(RESULT_UN_EDIT);
    }

    private void initview() {
        Title_edit= (EditText) findViewById(R.id.title_edit);
        Content_edit=(EditText)findViewById(R.id.content_edit);
        Bt_d = (Button) findViewById(R.id.bt_d);
        Bt_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = Title_edit.getText().toString();
                String content=Content_edit.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("content",content);
                db.execSQL("INSERT INTO TODO(TITLE,CONTENT) VALUES (?,?)", new String[]{title,content});
                setResult(RESULT_EDIT, intent);
                finish();
            }
        });
        Bt_x=(Button)findViewById(R.id.bt_x);
        Bt_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
