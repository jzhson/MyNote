package top.bubble.bubblesunpim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;
import deal.BaseActivity;
import deal.WelcomeActivity;
import dialog.Dialog_Language;



/**
 * Created by Jzhson on 11/4/2017.
 */

public class Settings extends BaseActivity {
    private ImageView img_back;
    private TextView languagechanage;
    private TextView checkupdate;
    private TextView feedback;
    private SharedPreferences spre;
    private Dialog_Language DialogLanguage;
    public static String APPID = "26f9d57dc57b35a53c63287ab15e19d8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        spre=getSharedPreferences("bubblesunpim-Users",MODE_PRIVATE);
        Bmob.initialize(this, APPID);

        languagechanage=findViewById(R.id.setting_language);
        img_back=findViewById(R.id.settings_img_back);
        checkupdate=findViewById(R.id.settings_checkupdate);
        feedback=findViewById(R.id.settings_feedback);
        final SharedPreferences.Editor infoedit=spre.edit();
        languagechanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLanguage=new Dialog_Language(Settings.this);
                DialogLanguage.setonEnglishOnclickListener(new Dialog_Language.onEnglishOnclickListener() {
                    @Override
                    public void onEnglishClick() {
                        switchLanguage("en");
                        infoedit.putString("language","en");
                        infoedit.commit();
                        finish();
                        Intent it = new Intent(Settings.this, WelcomeActivity.class);
                        startActivity(it);
                        DialogLanguage.dismiss();
                    }
                });
                DialogLanguage.setonChineseOnclickListener(new Dialog_Language.onChineseOnclickListener() {
                    @Override
                    public void onChineseClick() {
                        switchLanguage("cn");
                        infoedit.putString("language","cn");
                        infoedit.commit();
                        finish();
                        Intent it = new Intent(Settings.this, WelcomeActivity.class);
                        startActivity(it);
                        DialogLanguage.dismiss();
                    }
                });
               DialogLanguage.setonSpanishOnclickListener(new Dialog_Language.onSpanishOnclickListener() {
                   @Override
                   public void onSpanishClick() {
                       switchLanguage("es");
                       infoedit.putString("language","es");
                       infoedit.commit();
                       finish();
                       Intent it = new Intent(Settings.this, WelcomeActivity.class);
                       startActivity(it);
                       DialogLanguage.dismiss();
                   }
               });

               DialogLanguage.setonChinesetwOnclickListener(new Dialog_Language.onChinesetwOnclickListener() {
                   @Override
                   public void onChinesetwClick() {
                       switchLanguage("cn_tw");
                       infoedit.putString("language","cn_tw");
                       infoedit.commit();
                       finish();
                       Intent it = new Intent(Settings.this, WelcomeActivity.class);
                       startActivity(it);
                       DialogLanguage.dismiss();
                   }
               });
               DialogLanguage.setonCancelOnclickListener(new Dialog_Language.onCancelOnclickListener() {
                   @Override
                   public void onCancelClick() {
                       Toast.makeText(Settings.this,"Cancel action!",Toast.LENGTH_SHORT).show();
                       DialogLanguage.dismiss();
                   }
               });
               DialogLanguage.show();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tohome=new Intent(Settings.this,Home.class);
                startActivity(tohome);
                Settings.this.finish();
            }
        });
        checkupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUpdateAgent.forceUpdate(Settings.this);
                /*BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                        // TODO Auto-generated method stub
                        if (updateStatus == UpdateStatus.Yes) {//版本有更新
                            Toast.makeText(Settings.this, "版本有更新", Toast.LENGTH_SHORT).show();
                        }else if(updateStatus == UpdateStatus.No){
                            Toast.makeText(Settings.this, "版本无更新", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Settings.this, Feedback.class);
                startActivity(it);
            }
        });
    }

    public void onBackPressed() {
        Intent tohome=new Intent(Settings.this,Home.class);
        startActivity(tohome);
        Settings.this.finish();
    }
}
