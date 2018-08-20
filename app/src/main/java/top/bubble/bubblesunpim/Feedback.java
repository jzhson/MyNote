package top.bubble.bubblesunpim;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import deal.WelcomeActivity;
import bmob.Report;
import top.bubble.lovelytoast.LovelyToast;

/**
 * Created by Jzhson on 11/6/2017.
 */

public class Feedback extends Activity {
    private ImageView img_back;
    private EditText title;
    private EditText content;
    private TextView email;
    private Button submit;
    private SharedPreferences spre;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        spre=getSharedPreferences("bubblesunpim-Users",MODE_PRIVATE);
        img_back=findViewById(R.id.feedback_img_back);
        title=findViewById(R.id.feedback_edittext_title);
        content=findViewById(R.id.feedback_edittext_content);
        email=findViewById(R.id.feedback_textview_email);
        submit=findViewById(R.id.feedback_button_submit);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Feedback.this.finish();
            }
        });
        try {
            email.setText(spre.getString("login_account",""));
            final String stremail=email.getText().toString();
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String strtitle=title.getText().toString();
                    final String strcontent=content.getText().toString();
                    if(TextUtils.isEmpty(strcontent)||TextUtils.isEmpty(strtitle)){
                        LovelyToast.makeText(Feedback.this,getString(R.string.feedback_empty),LovelyToast.LENGTH_LONG,LovelyToast.WARNING);
                    }
                    else{
                        Report report=new Report();
                        report.setEmailaddress(stremail);
                        report.setTitle(strtitle);
                        report.setContent(strcontent);
                        report.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e==null){
                                    LovelyToast.makeText(Feedback.this,getString(R.string.feedback_success),LovelyToast.LENGTH_LONG,LovelyToast.SUCCESS);
                                }
                                else{
                                    LovelyToast.makeText(Feedback.this,getString(R.string.feedback_error),LovelyToast.LENGTH_LONG,LovelyToast.ERROR);
                                }
                            }
                        });

                    }
                }
            });


        }
        catch (Exception e){
            LovelyToast.makeText(Feedback.this,getString(R.string.feedback_error),LovelyToast.LENGTH_LONG,LovelyToast.ERROR);
            finish();
            Intent it = new Intent(Feedback.this, WelcomeActivity.class);
            startActivity(it);
        }

    }
}
