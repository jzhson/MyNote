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
 * Created by Jzhson on 10/31/2017.
 */
public class Dialog_Download extends Dialog {
    private Button background;//确定按钮
    private TextView titleTv;//消息标题文本
    private String titleStr;//从外界设置的title文本
    private TextView hint1;//消息提示文本
    private TextView hint2;//消息提示文本
    private TextView hint3;//消息提示文本
    private String hint1str;//从外界设置的消息文本
    private String hint2str;//从外界设置的消息文本
    private String hint3str;//从外界设置的消息文本
    //确定文本和取消文本的显示内容
    private String backgroundStr;
    private onubackgroundOnclickListener backgroundOnclickListener;


    /**
     * 设置后台更新按钮的显示内容和监听
     *
     * @param str
     * @param onbackgroundOnclickListener
     */
    public void setupdateOnclickListener(String str, onubackgroundOnclickListener onbackgroundOnclickListener) {
        if (str != null) {
            backgroundStr = str;
        }
        this.backgroundOnclickListener = onbackgroundOnclickListener;
    }

    public Dialog_Download(Context context) {
        super(context,R.style.Dialog_NewVersion);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_download);
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

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backgroundOnclickListener != null) {
                    backgroundOnclickListener.backgroundOnclick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (titleStr != null) {
            titleTv.setText(titleStr);
        }
        if (hint1str != null) {
            hint1.setText(hint1str);
        }
        if (hint2str != null) {
            hint2.setText(hint2str);
        }
        if (hint3str != null) {
            hint3.setText(hint3str);
        }
        //如果设置按钮的文字
        if (backgroundStr != null) {
            background.setText(backgroundStr);
        }
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        background = (Button) findViewById(R.id.dialog_download_button_update);
        titleTv = (TextView) findViewById(R.id.dialog_download_title);
        hint1 = (TextView) findViewById(R.id.dialog_download_button_hint1);
        hint2 = (TextView) findViewById(R.id.dialog_download_button_hint2);
        hint3 = (TextView) findViewById(R.id.dialog_download_button_hint3);
    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        titleStr = title;
    }

    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param hint1
     */
    public void setHint1(String hint1) {
        hint1str = hint1;
    }
    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param hint2
     */
    public void setHint2(String hint2) {
        hint2str = hint2;
    }
    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param hint3
     */
    public void setHint3(String hint3) {
        hint3str = hint3;
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onubackgroundOnclickListener {
        public void backgroundOnclick();
    }

    OnKeyListener keylistener = new DialogInterface.OnKeyListener(){
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
