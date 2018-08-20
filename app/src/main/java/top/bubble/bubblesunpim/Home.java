package top.bubble.bubblesunpim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

import deal.NoteBean;
import deal.NoteDB;
import deal.NoteAdapter;
import deal.SyncActivity;
import deal.WelcomeActivity;
import dialog.Dialog_exit;
import dialog.Dialog_loginout;
import dialog.Dialog_share;
import top.bubblesun.floatbutton.FloatingActionButton;

public class Home extends AppCompatActivity{
    private Toolbar toolbar;
    private DrawerLayout home_drawerlayout;
    private Dialog_exit selfDialog;
    private Dialog_loginout loginoutDialog;
    private Dialog_share shareDialog;
    private SharedPreferences spre;

    ArrayList<NoteBean> notesList;
    NoteDB mdb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar = findViewById(R.id.home_toolbar);
        home_drawerlayout=findViewById(R.id.home_drawer_layout);

        NavigationView navigationview=findViewById(R.id.home_navigationview);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.img_menu_36pt);
        }

        FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent axx=new Intent(Home.this,NewNote.class);
                startActivity(axx);
                finish();
                Toast.makeText(Home.this,"hello ",Toast.LENGTH_SHORT).show();
            }
        });
         FloatingActionButton actionB = (FloatingActionButton) findViewById(R.id.action_b_share);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent axx=new Intent(Home.this,NewNote.class);
                startActivity(axx);
                finish();
                Toast.makeText(Home.this,"hello ",Toast.LENGTH_SHORT).show();
            }
        });

        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_menu_settings:
                        Intent newint=new Intent(Home.this, Settings.class);
                        startActivity(newint);
                        Home.this.finish();
                        break;

                    case R.id.nav_menu_about:
                        Intent tomenu=new Intent(Home.this,About.class);
                        startActivity(tomenu);
                        Home.this.finish();
                        break;

                    case R.id.nav_menu_sync:
                        Intent tosync=new Intent(Home.this, SyncActivity.class);
                        startActivity(tosync);
                        Home.this.finish();
                        break;

                    case R.id.nav_menu_loginout:
                        loginoutDialog=new Dialog_loginout(Home.this);
                        loginoutDialog.setYesOnclickListener(null, new Dialog_loginout.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                loginoutDialog.dismiss();
                                Intent towelcome=new Intent(Home.this, WelcomeActivity.class);
                                startActivity(towelcome);
                                Home.this.finish();
                                spre=getSharedPreferences("bubblesunpim-Users",MODE_PRIVATE);
                                final SharedPreferences.Editor infoedit=spre.edit();
                                infoedit.putString("login_password","");
                                infoedit.putBoolean("login_checkbox",false);
                                infoedit.commit();


                            }
                        });
                        loginoutDialog.setNoOnclickListener(null, new Dialog_loginout.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                                loginoutDialog.dismiss();

                            }
                        });
                        loginoutDialog.show();
                        break;
                }
                home_drawerlayout.closeDrawers();
                return false;
            }
        });


        RecyclerView recyclerView=findViewById(R.id.home_recyclerview);
        mdb=new NoteDB(this);
        notesList=mdb.getArray();
        //LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        NoteAdapter adapter=new NoteAdapter(this,notesList);
        recyclerView.setAdapter(adapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     //     Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                home_drawerlayout.openDrawer(GravityCompat.START);
                break;
            case R.id.toolbar_menu_share:
                shareDialog=new Dialog_share(Home.this);
                shareDialog.setYesOnclickListener(null, new Dialog_share.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        shareDialog.dismiss();
                        refresh();
                    }
                });
                shareDialog.setNoOnclickListener(null, new Dialog_share.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        shareDialog.dismiss();
                        refresh();
                    }
                });
                shareDialog.show();
                break;
            case R.id.toolbar_menu_expend:
                Intent axx=new Intent(Home.this,NewNote.class);
                startActivity(axx);
                finish();
                break;
        }
        return true;
    }



    public void onBackPressed() {
        selfDialog = new Dialog_exit(Home.this);
        selfDialog.setYesOnclickListener(null, new Dialog_exit.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                selfDialog.dismiss();
                Home.this.finish();
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


    public void refresh(){
        Intent fresh=new Intent(this,Home.class);
        startActivity(fresh);
        Home.this.overridePendingTransition(0, 0);
        Home.this.finish();
    }
}









/*
 @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



 */