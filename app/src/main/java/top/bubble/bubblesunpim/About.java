package top.bubble.bubblesunpim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by Jzhson on 11/6/2017.
 */

public class About extends Activity {
    private ImageView img_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        img_back=findViewById(R.id.about_img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tohome=new Intent(About.this,Home.class);
                startActivity(tohome);
                About.this.finish();
            }
        });
    }


    public void onBackPressed() {
        Intent tohome=new Intent(About.this,Home.class);
        startActivity(tohome);
        About.this.finish();
    }
}
