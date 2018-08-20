package top.bubble.bubblesunpim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import deal.NoteBean;
import deal.NoteDB;
import dialog.Dialog_Delete;

public class NotesDetails extends AppCompatActivity {
    TextView ed1,ed2;
    private ImageView img_back;
    private ImageView img_edit;
    private ImageView img_delete;
    private NoteBean cun;
    private int ids;
    NoteDB myDatabase;
    Dialog_Delete deleteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shownotes);
        ed1=(TextView) findViewById(R.id.notesdetails_textview_title);
        ed2=(TextView) findViewById(R.id.notesdetails_textview_content);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        img_back=findViewById(R.id.shownotes_img_back);
        img_edit=findViewById(R.id.shownotes_imageview_edit);
        img_delete=findViewById(R.id.shownotes_imageview_delete);
        myDatabase=new NoteDB(this);
        Intent intent=this.getIntent();
        ids=intent.getIntExtra("ids", 0);
        cun=myDatabase.getTiandCon(ids);
        ed1.setText(cun.getTitle());
        ed2.setText(cun.getContent());
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tohome = new Intent(NotesDetails.this, Home.class);
                startActivity(tohome);
                NotesDetails.this.finish();
            }
        });
        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),EditNotes.class);
                intent.putExtra("ids",ids);
                startActivity(intent);
                NotesDetails.this.finish();
            }
        });
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog=new Dialog_Delete(v.getContext());
                deleteDialog.setYesOnclickListener(null, new Dialog_Delete.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        myDatabase.toDelete(ids);
                        deleteDialog.dismiss();
                        Intent intent=new Intent(NotesDetails.this,Home.class);
                        startActivity(intent);
                        finish();
                    }
                });
                deleteDialog.setNoOnclickListener(null, new Dialog_Delete.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();
            }
        });
    }

    public void onBackPressed() {
        Intent tohome = new Intent(NotesDetails.this, Home.class);
        startActivity(tohome);
        NotesDetails.this.finish();
    }
}