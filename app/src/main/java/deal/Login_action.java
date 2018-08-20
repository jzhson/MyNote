package deal;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import bmob.Users;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import dialog.Dialog_Loging;
import dialog.Dialog_exit;
import dialog.Dialog_hintglobal;
import top.bubble.bubblesunpim.Home;
import top.bubble.bubblesunpim.R;
import top.bubble.lovelytoast.LovelyToast;
import top.bubblesun.submitbutton.SubmitButton;


public class Login_action extends Activity {
    private EditText user_email;
    private EditText user_password;
    private TextView iforgot;
    private CheckBox checkbox;
    private SharedPreferences spre;

    private SubmitButton login;
    private Dialog_exit selfDialog;
    private Dialog_hintglobal Dialoghint_iforgot;
    private Dialog_Loging LoginDailog;
    public static String APPID = "26f9d57dc57b35a53c63287ab15e19d8";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        login=findViewById(R.id.login_button_login);
        user_email=findViewById(R.id.login_account);
        user_password=findViewById(R.id.login_password);
        checkbox=findViewById(R.id.login_checkbox_rememberme);
        iforgot=findViewById(R.id.login_iforgot);

        Bmob.initialize(this, APPID);
        BmobUpdateAgent.update(this);
        BmobUpdateAgent.setUpdateOnlyWifi(false);
        spre=getSharedPreferences("bubblesunpim-Users",MODE_PRIVATE);
        user_email.setText(spre.getString("login_account",""));
        user_password.setText(spre.getString("login_password",""));
        checkbox.setChecked(spre.getBoolean("login_checkbox",false));

        if (spre.getBoolean("login_checkbox",true)){
            user_password.setText(spre.getString("login_password",""));
        }
        else{
            user_password.setText("");
        }


        ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi|internet){
            //执行相关操作
        }else{
            LovelyToast.makeText(Login_action.this,getString(R.string.networkavailable),LovelyToast.LENGTH_LONG,LovelyToast.ERROR);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginDailog=new Dialog_Loging(Login_action.this);
                LoginDailog.show();
                final String email=user_email.getText().toString();
                final String password=user_password.getText().toString();
                final SharedPreferences.Editor infoedit=spre.edit();
                if(email.equals("")||password.equals("")){
                    LovelyToast.makeText(Login_action.this,getString(R.string.userpwdcantblank),LovelyToast.LENGTH_LONG,LovelyToast.WARNING);
                    LoginDailog.dismiss();
                    return;
                }
                @SuppressLint("HandlerLeak") final Handler handler=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what){
                            case 0:
                                LovelyToast.makeText(Login_action.this,getString(R.string.loginsuccess),LovelyToast.LENGTH_LONG,LovelyToast.DEFAULT);
                                LoginDailog.dismiss();
                                if(checkbox.isChecked()==true){
                                    infoedit.putString("login_account",user_email.getText().toString());
                                    infoedit.putString("login_password",user_password.getText().toString());
                                    infoedit.putBoolean("login_checkbox",true);
                                    infoedit.putInt("sync",0);
                                    infoedit.commit();
                                }
                                else{
                                    infoedit.putBoolean("login_checkbox",false);
                                    infoedit.putInt("sync",0);
                                    infoedit.commit();
                                }
                                Intent intent=new Intent();
                                intent.setClass(Login_action.this, Home.class);
                                startActivity(intent);
                                finish();
                                break;
                            case 1:

                                break;
                            case 2:
                                LovelyToast.makeText(Login_action.this,getString(R.string.login_toast_wrongpwd),LovelyToast.LENGTH_LONG,LovelyToast.ERROR);
                                LoginDailog.dismiss();
                                break;
                            default:
                                break;

                        }
                    }
                };
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        for(int i =0;i<=5;i++) {
                            try {
                                Thread.sleep(300);
                                final Message msg = new Message();
                                if (i==3) {
                                    BmobQuery<Users> query=new BmobQuery<Users>();
                                    query.addWhereEqualTo("email",email);
                                    query.addWhereEqualTo("password",password);
                                    query.findObjects(new FindListener<Users>() {
                                        @Override
                                        public void done(List<Users> list, BmobException e) {
                                            try{
                                                String gemail=list.get(0).getEmail().toString();
                                            }
                                            catch (Exception o){
                                                msg.what=2;
                                                handler.sendMessage(msg);
                                            }
                                        }
                                    });
                                }

                                else if(i==5){
                                    BmobQuery<Users> query=new BmobQuery<Users>();
                                    query.addWhereEqualTo("email",email);
                                    query.addWhereEqualTo("password",password);
                                    query.findObjects(new FindListener<Users>() {
                                        @Override
                                        public void done(List<Users> list, BmobException e) {
                                            try{
                                                String gemail=list.get(0).getEmail().toString();
                                                msg.what=0;
                                                handler.sendMessage(msg);
                                            }
                                            catch (Exception o){

                                            }
                                        }
                                    });

                                }

                            }
                            catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                t.start();
            }
        });
        iforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialoghint_iforgot=new Dialog_hintglobal(Login_action.this);
                Dialoghint_iforgot.setonokclickListerner(null, new Dialog_hintglobal.onokclickListerner() {
                    @Override
                    public void okclick() {
                        Dialoghint_iforgot.dismiss();
                        Intent toregist=new Intent(Login_action.this, RegisterActivity.class);
                        startActivity(toregist);
                        Login_action.this.finish();
                    }
                });
                Dialoghint_iforgot.show();
            }
        });
    }



    public void onBackPressed() {
        selfDialog = new Dialog_exit(Login_action.this);
        selfDialog.setYesOnclickListener(null, new Dialog_exit.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                selfDialog.dismiss();
                Login_action.this.finish();
            }
        });
        selfDialog.setNoOnclickListener(null, new Dialog_exit.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                selfDialog.dismiss();
            }
        });
        selfDialog.show();
    }
}
