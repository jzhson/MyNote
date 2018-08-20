package dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import top.bubble.bubblesunpim.R;
/**
 * Created by Jzhson on 11/5/2017.
 */

public class Dialog_hintglobal extends Dialog {

    private Button ok;
    private TextView title;
    private TextView message;
    private String okstr;
    private String titlestr;
    private String messagestr;
    private onokclickListerner okclickListerner;


    public void setonokclickListerner(String str, onokclickListerner onokclickListerner) {
        if (str != null) {
            okstr = str;
        }
        this.okclickListerner = onokclickListerner;
    }



    public Dialog_hintglobal(Context context) {
        super(context,R.style.Dialog_exit);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_hint_global);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        setOnKeyListener(keylistener);
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    private void initData() {
        //如果用户自定了title和message
        if (titlestr != null) {
            title.setText(titlestr);
        }
        if (messagestr != null) {
            message.setText(messagestr);
        }
        //如果设置按钮的文字
        if (okstr != null) {
            ok.setText(okstr);
        }
    }

    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okclickListerner != null) {
                    okclickListerner.okclick();
                }
            }
        });
    }


    private void initView() {
        ok = (Button) findViewById(R.id.dialog_hintglobal_button);
        title = (TextView) findViewById(R.id.dialog_hintglobal_title);
        message = (TextView) findViewById(R.id.dialog_hintglobal_message);
    }

    public void setTitle(String title){
        titlestr=title;
    }

    public void setMessage(String message){
        messagestr=message;
    }

    public interface onokclickListerner{
        public void okclick();
    }


    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener(){
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    } ;
}
