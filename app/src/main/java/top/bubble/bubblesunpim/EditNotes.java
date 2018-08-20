package top.bubble.bubblesunpim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import deal.NoteBean;
import deal.NoteDB;
import dialog.Dialog_Cancel;



public class EditNotes extends AppCompatActivity {
    private Dialog_Cancel selfDialog;

    EditText ed1,ed2;
    private ImageView imageButton;
    private ImageView img_back;
    NoteDB myDatabase;
    private SharedPreferences spre_sync;
    NoteBean cun;
    int ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editnotes);
        ed1=(EditText) findViewById(R.id.editnotes_EditText_title);
        ed2=(EditText) findViewById(R.id.editnotes_EditText_content);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageButton=(ImageView) findViewById(R.id.editnotes_save);
        img_back=findViewById(R.id.editnotes_img_back);
        myDatabase=new NoteDB(this);
        spre_sync=getSharedPreferences("sync",MODE_PRIVATE);
        final SharedPreferences.Editor spre_sync_edit=spre_sync.edit();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        Intent intent=this.getIntent();
        ids=intent.getIntExtra("ids", 0);
        cun=myDatabase.getTiandCon(ids);
        ed1.setText(cun.getTitle());
        ed2.setText(cun.getContent());
        final String times = formatter.format(curDate);

        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String title=ed1.getText().toString();
                String content=ed2.getText().toString();
                if(TextUtils.isEmpty(title) ||title==null||TextUtils.isEmpty(content)||content==null){
                    Toast.makeText(EditNotes.this,"empty content, not save",Toast.LENGTH_SHORT).show();
                    Intent tohome=new Intent(EditNotes.this,NotesDetails.class);
                    tohome.putExtra("ids",ids);
                    startActivity(tohome);
                    EditNotes.this.finish();
                }
                else{
                    cun=new NoteBean(title,ids, content, times);
                    myDatabase.toUpdate(cun);
                    Intent tohome=new Intent(EditNotes.this,NotesDetails.class);
                    tohome.putExtra("ids",ids);
                    startActivity(tohome);
                    spre_sync_edit.putString("status","yes");
                    spre_sync_edit.commit();
                    EditNotes.this.finish();
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selfDialog = new Dialog_Cancel(EditNotes.this);
                selfDialog.setYesOnclickListener(null, new Dialog_Cancel.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        String title=ed1.getText().toString();
                        String content=ed2.getText().toString();
                        selfDialog.dismiss();
                        cun=new NoteBean(title,ids, content, times);
                        myDatabase.toUpdate(cun);
                        spre_sync_edit.putString("status","yes");
                        spre_sync_edit.commit();
                        Intent tohome=new Intent(EditNotes.this,NotesDetails.class);
                        tohome.putExtra("ids",ids);
                        startActivity(tohome);
                        EditNotes.this.finish();
                    }
                });
                selfDialog.setNoOnclickListener(null, new Dialog_Cancel.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        Intent tohome=new Intent(EditNotes.this,NotesDetails.class);
                        tohome.putExtra("ids",ids);
                        startActivity(tohome);
                        EditNotes.this.finish();
                        selfDialog.dismiss();
                    }
                });
                selfDialog.show();
            }
        });
    }


    public void onBackPressed() {
        Intent tohome=new Intent(EditNotes.this,NotesDetails.class);
        tohome.putExtra("ids",ids);
        startActivity(tohome);
        EditNotes.this.finish();
    }
}