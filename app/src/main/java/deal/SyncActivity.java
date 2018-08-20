package deal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import dialog.Dialog_Cancel;
import dialog.Dialog_Loging;
import bmob.Sync;
import top.bubble.bubblesunpim.Home;
import top.bubble.bubblesunpim.R;
import top.bubble.lovelytoast.LovelyToast;

/**
 * Created by Jzhson on 11/9/2017.
 */

public class SyncActivity extends AppCompatActivity {
    private TextView sync_net;
    private TextView sync_save;
    private TextView sync_help;
    private TextView sync_textview_status;
    private ImageView img_back;
    private SharedPreferences spre;
    private SharedPreferences spre_sync;
    NoteDB mdb;
    NoteBean cun;
    ArrayList<NoteBean> notesList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sync_notes);
        sync_net=findViewById(R.id.sync_textview_sync_network);
        sync_save=findViewById(R.id.sync_textview_sync_save);
        sync_help=findViewById(R.id.sync_textview_sync_helpme);
        sync_textview_status=findViewById(R.id.sync_status);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (isConnect(this)==false)
        {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.networkavailable))
                    .setMessage(getString(R.string.networkavailable))
                    .setPositiveButton(getString(R.string.dialog_sync_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Process.killProcess(Process.myPid());
                            System.exit(0);
                        }
                    }).show();
        }
        spre=getSharedPreferences("bubblesunpim-Users",MODE_PRIVATE);
        final String useremail=spre.getString("login_account","");
        mdb=new NoteDB(this);
        notesList=mdb.getArray1();
        if(TextUtils.isEmpty(useremail)){
            Toast.makeText(SyncActivity.this,"Initialization failure,please login again",Toast.LENGTH_LONG).show();
            Intent tologin=new Intent(this,Login_action.class);
            startActivity(tologin);
            SyncActivity.this.finish();
        }
        spre_sync=getSharedPreferences("sync",MODE_PRIVATE);
        String sync_status=spre_sync.getString("status","");
        if (TextUtils.isEmpty(sync_status)){
            //清空过数据 或者新用户
            Toast.makeText(SyncActivity.this,"Empty",Toast.LENGTH_SHORT).show();
        }
        else if(sync_status.equals("yes")){
            sync_textview_status.setText(getString(R.string.sync_textview_syncstatus_syncnow));

        }
        else if(sync_status.equals("no")){
            sync_textview_status.setText(getString(R.string.sync_textview_syncstatus_noneed));
        }
        

        sync_net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog_Cancel sync=new Dialog_Cancel(SyncActivity.this);
                sync.setTitle(getString(R.string.dialog_sync_sure_title));
                sync.setMessage(getString(R.string.dialog_sync_sure_network));
                sync.setYesOnclickListener(getString(R.string.dialog_sync_sure_sure), new Dialog_Cancel.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        sync.dismiss();
                        sync_net();
                    }
                });
                sync.setNoOnclickListener(getString(R.string.dialog_sync_sure_not), new Dialog_Cancel.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        sync.dismiss();
                    }
                });
                sync.show();

            }
        });
        sync_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog_Cancel sync=new Dialog_Cancel(SyncActivity.this);
                sync.setTitle(getString(R.string.dialog_sync_sure_title));
                sync.setMessage(getString(R.string.dialog_sync_sure_tonetwork));
                sync.setYesOnclickListener(getString(R.string.dialog_sync_sure_sure), new Dialog_Cancel.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        sync.dismiss();
                        save_tonet();
                    }
                });
                sync.setNoOnclickListener(getString(R.string.dialog_sync_sure_not), new Dialog_Cancel.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        sync.dismiss();
                    }
                });
                sync.show();

            }
        });
        sync_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Sync> query=new BmobQuery<Sync>();
                String[] useremailarry = {useremail};
                query.addWhereContainsAll("emailaddress", Arrays.asList(useremailarry));
                query.findObjects(new FindListener<Sync>() {
                    @Override
                    public void done(List<Sync> list, BmobException e) {

                        if(e==null){
                            String x=list.get(0).getTitle();
                            if (list.size()>notesList.size()){
                                }
                            else if (list.size()<notesList.size()){
                                }
                            else if (list.size()==notesList.size()){
                                 }
                        }
                        else{
                            if(notesList.size()!=0){

                            }
                            else{

                            }
                        }
                    }

                });

            }
        });

        img_back=findViewById(R.id.syncnotes_img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tohome = new Intent(SyncActivity.this, Home.class);
                startActivity(tohome);
                SyncActivity.this.finish();
            }
        });
    }






    public static boolean isConnect(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null&& info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
// TODO: handle exception
            Log.v("error",e.toString());
        }
        return false;
    }









    public void onBackPressed() {
        Intent tohome = new Intent(SyncActivity.this, Home.class);
        startActivity(tohome);
        SyncActivity.this.finish();
    }


    private void sync_net(){
        final Dialog_Loging syncload=new Dialog_Loging(SyncActivity.this);
        syncload.setTitle(getString(R.string.dialog_sync_running));
        syncload.setMessage(getString(R.string.dialog_sync_startservice));
        syncload.show();
        final Dialog_Loging syncdeletenet=new Dialog_Loging(SyncActivity.this);
        syncdeletenet.setTitle(getString(R.string.dialog_sync_running));
        syncdeletenet.setMessage(getString(R.string.dialog_sync_deletelocal));
        final Dialog_Loging syncupnet=new Dialog_Loging(SyncActivity.this);
        syncupnet.setTitle(getString(R.string.dialog_sync_running));
        syncupnet.setMessage(getString(R.string.dialog_sync_downnet));
        final Dialog_Loging syncinterdata=new Dialog_Loging(SyncActivity.this);
        syncinterdata.setTitle(getString(R.string.dialog_sync_running));
        syncinterdata.setMessage(getString(R.string.dialog_sync_interpretingdata));
        final Dialog_Loging syncsucc=new Dialog_Loging(SyncActivity.this);
        syncsucc.setTitle(getString(R.string.dialog_sync_running));
        syncsucc.setMessage(getString(R.string.dialog_sync_syncsucc));
        spre=getSharedPreferences("bubblesunpim-Users",MODE_PRIVATE);
        final String useremail=spre.getString("login_account","");
        spre_sync=getSharedPreferences("sync",MODE_PRIVATE);
        final SharedPreferences.Editor spre_sync_edit=spre_sync.edit();
        final BmobQuery<Sync> query=new BmobQuery<Sync>();
        final String[] useremailarry = {useremail};
        @SuppressLint("HandlerLeak") final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:   //网络错误  关闭Dialog 并且退出程序
                        syncload.dismiss();
                        Intent toHome=new  Intent(SyncActivity.this,Home.class);

                        startActivity(toHome);
                        finish();
                        LovelyToast.makeText(SyncActivity.this,getString(R.string.dialog_sync_nonetwork),LovelyToast.LENGTH_LONG,LovelyToast.ERROR);
                        break;
                    case 1:
                        syncload.dismiss();
                        syncdeletenet.show();
                        for(int i = 0;i < notesList.size(); i ++){
                            NoteBean Notes=notesList.get(i);
                            int notesid=Notes.getIds();
                            String notestitle=Notes.getTitle();
                            mdb.toDelete(notesid);
                        }
                        break;
                    case 2:   //覆盖
                        syncupnet.dismiss();
                        syncinterdata.show();
                        break;
                    case 3:
                        syncdeletenet.dismiss();
                        syncupnet.show();
                        query.addWhereContainsAll("emailaddress", Arrays.asList(useremailarry));
                        query.findObjects(new FindListener<Sync>() {
                            @Override
                            public void done(List<Sync> list, BmobException e) {
                                if(e == null){
                                    for(int i=0;i< list.size();i++){
                                        String gettitle=list.get(i).getTitle();
                                        String getcontent=list.get(i).getContent();
                                        String gettime=list.get(i).getTime();
                                        cun=new NoteBean(gettitle,getcontent,gettime);
                                        mdb.toInsert(cun);
                                    }
                                }
                            }
                        });
                        break;
                    case 4:
                        syncinterdata.dismiss();
                        syncsucc.show();
                        spre_sync_edit.putString("status","no");
                        spre_sync_edit.commit();
                        break;
                    case 5:
                        syncsucc.dismiss();
                        refresh();
                        LovelyToast.makeText(SyncActivity.this,getString(R.string.dialog_sync_syncsucc),LovelyToast.LENGTH_LONG,LovelyToast.SUCCESS);
                        break;
                    default:
                        break;

                }
            }
        };


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                for(int i =0;i<=17;i++) {
                    try {
                        Thread.sleep(500);
                        final Message msg = new Message();
                        if (i==1) {

                        }
                        if (i==2) {
                            msg.what=1;
                            handler.sendMessage(msg);
                        }

                        else if(i==6){
                            msg.what=3;
                            handler.sendMessage(msg);
                        }

                        else if(i==10){
                            msg.what=2;
                            handler.sendMessage(msg);
                        }


                        else if(i==15){
                            msg.what=4;
                            handler.sendMessage(msg);
                        }
                        else if(i==17){
                            msg.what=5;
                            handler.sendMessage(msg);
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
    private void save_tonet(){
        final Dialog_Loging syncload=new Dialog_Loging(SyncActivity.this);
        syncload.setTitle(getString(R.string.dialog_sync_running));
        syncload.setMessage(getString(R.string.dialog_sync_startservice));
        syncload.show();
        final Dialog_Loging syncdeletenet=new Dialog_Loging(SyncActivity.this);
        syncdeletenet.setTitle(getString(R.string.dialog_sync_running));
        syncdeletenet.setMessage(getString(R.string.dialog_sync_detelenet));
        final Dialog_Loging syncupnet=new Dialog_Loging(SyncActivity.this);
        syncupnet.setTitle(getString(R.string.dialog_sync_running));
        syncupnet.setMessage(getString(R.string.dialog_sync_upnet));
        final Dialog_Loging syncinterdata=new Dialog_Loging(SyncActivity.this);
        syncinterdata.setTitle(getString(R.string.dialog_sync_running));
        syncinterdata.setMessage(getString(R.string.dialog_sync_interpretingdata));
        final Dialog_Loging syncsucc=new Dialog_Loging(SyncActivity.this);
        syncsucc.setTitle(getString(R.string.dialog_sync_running));
        syncsucc.setMessage(getString(R.string.dialog_sync_syncsucc));
        spre=getSharedPreferences("bubblesunpim-Users",MODE_PRIVATE);
        final Sync syncprogress=new Sync();
        final String useremail=spre.getString("login_account","");
        spre_sync=getSharedPreferences("sync",MODE_PRIVATE);
        final SharedPreferences.Editor spre_sync_edit=spre_sync.edit();
        final BmobQuery<Sync> query=new BmobQuery<Sync>();
        String[] useremailarry = {useremail};
        query.addWhereContainsAll("emailaddress", Arrays.asList(useremailarry));
        @SuppressLint("HandlerLeak") final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:   //网络错误  关闭Dialog 并且退出程序
                        syncload.dismiss();
                        Intent toHome=new  Intent(SyncActivity.this,Home.class);
                        startActivity(toHome);
                        finish();
                        LovelyToast.makeText(SyncActivity.this,getString(R.string.dialog_sync_nonetwork),LovelyToast.LENGTH_LONG,LovelyToast.ERROR);
                        break;
                    case 1:
                        syncload.dismiss();
                        syncdeletenet.show();
                        query.findObjects(new FindListener<Sync>() {
                            @Override
                            public void done(List<Sync> list, BmobException e) {
                                if(e==null){
                                    for(int i=0;i< list.size();i++){
                                        String objectId=list.get(i).getObjectId();
                                        Sync deletid=new Sync();
                                        deletid.setObjectId(objectId);
                                        deletid.delete(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if(e==null){
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                        break;
                    case 2:   //覆盖
                        syncdeletenet.dismiss();
                        syncinterdata.show();
                        break;
                    case 3:
                        syncinterdata.dismiss();
                        syncupnet.show();
                        for(int i = 0;i < notesList.size(); i ++){
                            NoteBean Notes=notesList.get(i);
                            String notestitle=Notes.getTitle();
                            String notescontent=Notes.getContent();
                            String notestime=Notes.getTimes();
                            int notesid=Notes.getIds();
                            syncprogress.setEmailaddress(useremail);
                            syncprogress.setId(notesid);
                            syncprogress.setTitle(notestitle);
                            syncprogress.setContent(notescontent);
                            syncprogress.setTime(notestime);
                            syncprogress.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                }
                            });
                        }
                        break;
                    case 4:
                        syncupnet.dismiss();
                        syncsucc.show();
                        spre_sync_edit.putString("status","no");
                        spre_sync_edit.commit();
                        break;
                    case 5:
                        syncsucc.dismiss();
                        refresh();
                        LovelyToast.makeText(SyncActivity.this,getString(R.string.dialog_sync_syncsucc),LovelyToast.LENGTH_LONG,LovelyToast.SUCCESS
                        );
                        break;
                    default:
                        break;

                }
            }
        };


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                for(int i =0;i<=17;i++) {
                    try {
                        Thread.sleep(500);
                        final Message msg = new Message();
                        if (i==1) {
                            ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
                            boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
                            boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
                            if(wifi|internet){
                                //执行相关操作
                            }else{
                                msg.what=0;
                                handler.sendMessage(msg);
                            }
                        }
                        if (i==2) {
                            query.findObjects(new FindListener<Sync>() {
                                @Override
                                public void done(List<Sync> list, BmobException e) {
                                    if(e==null){
                                        msg.what=1;
                                        handler.sendMessage(msg);
                                    }
                                }
                            });
                        }

                        else if(i==6){
                            msg.what=2;
                            handler.sendMessage(msg);
                        }

                        else if(i==10){
                            msg.what=3;
                            handler.sendMessage(msg);
                        }

                        else if(i==15){
                            msg.what=4;
                            handler.sendMessage(msg);
                        }
                        else if(i==17){
                            msg.what=5;
                            handler.sendMessage(msg);
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


    public void refresh(){
        Intent fresh=new Intent(this, SyncActivity.class);
        startActivity(fresh);
        SyncActivity.this.overridePendingTransition(0, 0);
        SyncActivity.this.finish();
    }


}
