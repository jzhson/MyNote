package deal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import top.bubble.bubblesunpim.R;
import top.bubble.lovelytoast.LovelyToast;

/**
 * Created by Jzhson on 10/29/2017.
 */

public class WelcomeActivity extends BaseActivity {
    private SharedPreferences spre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome);
        spre=getSharedPreferences("bubblesunpim-Users",MODE_PRIVATE);
        String language=spre.getString("language","");
       if(!TextUtils.isEmpty(language)){
           switchLanguage(language);
       }
        ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi|internet){
            //执行相关操作
        }else{
            LovelyToast.makeText(WelcomeActivity.this,getString(R.string.networkavailable),LovelyToast.LENGTH_LONG,LovelyToast.ERROR);
        }
        final Intent it = new Intent(this, Login_action.class); //下一步转向Mainctivity
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行意图
                finish();
            }
        };
        timer.schedule(task, 1000 * 2);
    }


    public void onBackPressed() {
    }
}
