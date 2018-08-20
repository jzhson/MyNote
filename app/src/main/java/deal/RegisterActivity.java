package deal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

import bmob.Users;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import dialog.Dialog_cantquit;
import top.bubble.bubblesunpim.Home;
import top.bubble.bubblesunpim.R;
import top.bubble.lovelytoast.LovelyToast;


/**
 * Created by Jzhson on 11/5/2017.
 */

public class RegisterActivity extends AppCompatActivity {

    private TextView email;
    private TextView password;
    private TextView nickname;
    private Button register;
    private SharedPreferences spre;
    private Dialog_cantquit Dialoghint_cantquit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);
        email=findViewById(R.id.register_account);
        password=findViewById(R.id.register_password);
        nickname=findViewById(R.id.register_nickname);
        register=findViewById(R.id.register_button_register);
        spre=getSharedPreferences("bubblesunpim-Users",MODE_PRIVATE);
        final SharedPreferences.Editor infoedit=spre.edit();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String memail=email.getText().toString();
                final String mpassword=password.getText().toString();
                String mnickname=nickname.getText().toString();
                boolean booemail=isEmail(memail);
                if(booemail==true){
                    if (!isPasswordRegex(password.getText().toString())) {
                        LovelyToast.makeText(RegisterActivity.this,getString(R.string.register_wrongformat),LovelyToast.LENGTH_LONG,LovelyToast.WARNING);
                    } else {
                        Users userinfoo=new Users();
                        userinfoo.setEmail(memail);
                        userinfoo.setPassword(mpassword);
                        userinfoo.setNickname(mnickname);
                        userinfoo.setNewuser(0);
                        userinfoo.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e==null){
                                    LovelyToast.makeText(RegisterActivity.this,getString(R.string.register_registersuccess),LovelyToast.LENGTH_LONG,LovelyToast.SUCCESS);
                                    infoedit.putString("login_account",memail);
                                    infoedit.putString("login_password",mpassword);
                                    infoedit.putBoolean("login_checkbox",true);
                                    infoedit.commit();
                                    Intent tohome=new Intent(RegisterActivity.this,Home.class);
                                    startActivity(tohome);
                                    RegisterActivity.this.finish();
                                }
                                else{
                                    LovelyToast.makeText(RegisterActivity.this,getString(R.string.register_userexist),LovelyToast.LENGTH_LONG,LovelyToast.ERROR);
                                }
                            }
                        });
                    }
                }
                else {
                    LovelyToast.makeText(RegisterActivity.this,getString(R.string.register_registfiled),LovelyToast.LENGTH_LONG,LovelyToast.ERROR);
                }
            }
        });

    }


    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return strEmail.matches(strPattern);
        }
    }
    private boolean isPasswordRegex(String password) {
        String passwordPattern = "^[a-zA-Z]\\w{5,17}$";
        return Pattern.matches(passwordPattern, password);
    }


    public void onBackPressed() {
        Dialoghint_cantquit=new Dialog_cantquit(RegisterActivity.this);
        Dialoghint_cantquit.setonokclickListerner(null, new Dialog_cantquit.onokclickListerner() {
            @Override
            public void okclick() {
                Dialoghint_cantquit.dismiss();
            }
        });
        Dialoghint_cantquit.setnookclickListerner(null, new Dialog_cantquit.onnoclickListerner() {
            @Override
            public void noclick() {
                Intent toback=new Intent(RegisterActivity.this,Login_action.class);
                startActivity(toback);
                RegisterActivity.this.finish();
            }
        });
        Dialoghint_cantquit.show();
    }
}
