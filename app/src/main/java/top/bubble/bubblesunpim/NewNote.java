package top.bubble.bubblesunpim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

import deal.NoteBean;
import deal.NoteDB;
import dialog.Dialog_Cancel;

/**
 * Created by Jzhson on 11/3/2017.
 */

public class NewNote extends AppCompatActivity {
    private Dialog_Cancel selfDialog;
    EditText ed1,ed2;
    ImageButton imageButton;
    private ImageView img_back;
    NoteDB myDatabase;
    private SharedPreferences spre_sync;
    NoteBean cun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_notes);
        ed1=(EditText) findViewById(R.id.newnotes_edittext_title);
        ed2=(EditText) findViewById(R.id.newnotes_edittext_content);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageButton=(ImageButton) findViewById(R.id.saveButton);
        img_back=findViewById(R.id.feedback_img_back);
        myDatabase=new NoteDB(this);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        final String times = formatter.format(curDate);
        spre_sync=getSharedPreferences("sync",MODE_PRIVATE);
        final SharedPreferences.Editor spre_sync_edit=spre_sync.edit();
        imageButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String title=ed1.getText().toString();
                String content=ed2.getText().toString();
                if(TextUtils.isEmpty(title) ||title==null||title.length() == 0||TextUtils.isEmpty(content)||content==null||content.length() == 0 ){
                //    Toast.makeText(NewNote.this,"empty content, not save",Toast.LENGTH_SHORT).show();
                    Intent tohome=new Intent(NewNote.this,Home.class);
                    startActivity(tohome);
                    NewNote.this.finish();
                }
                else{
                    cun=new NoteBean(title,content,times);
                    myDatabase.toInsert(cun);
                    spre_sync_edit.putString("status","yes");
                    spre_sync_edit.commit();
                    Intent tohome=new Intent(NewNote.this,Home.class);
                    startActivity(tohome);
                    NewNote.this.finish();
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selfDialog = new Dialog_Cancel(NewNote.this);
                selfDialog.setYesOnclickListener(null, new Dialog_Cancel.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        selfDialog.dismiss();
                        String title=ed1.getText().toString();
                        String content=ed2.getText().toString();
                        if(TextUtils.isEmpty(title) ||title==null||title.length() == 0||TextUtils.isEmpty(content)||content==null||content.length() == 0 ){
                        //    Toast.makeText(NewNote.this,"empty content, not save",Toast.LENGTH_SHORT).show();
                            Intent tohome=new Intent(NewNote.this,Home.class);
                            startActivity(tohome);
                            NewNote.this.finish();
                        }
                        else{
                            cun=new NoteBean(title,content,times);
                            myDatabase.toInsert(cun);
                            spre_sync_edit.putString("status","yes");
                            spre_sync_edit.commit();
                            Intent tohome=new Intent(NewNote.this,Home.class);
                            startActivity(tohome);
                            NewNote.this.finish();
                        }
                    }
                });
                selfDialog.setNoOnclickListener(null, new Dialog_Cancel.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        selfDialog.dismiss();
                        Intent tohome=new Intent(NewNote.this,Home.class);
                        startActivity(tohome);
                        NewNote.this.finish();
                    }
                });
                selfDialog.show();
            }
        });
    }

    public void onBackPressed() {

    }
}