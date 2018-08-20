package dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.TextView;

import top.bubble.bubblesunpim.R;

/**
 * Created by Jzhson on 11/7/2017.
 */

public class Dialog_Loging extends Dialog {

    private TextView titleTv;//消息标题文本
    private TextView messageTv;//消息提示文本
    private String titleStr;//从外界设置的title文本
    private String messageStr;//从外界设置的消息文本


    public Dialog_Loging(Context context) {
        super(context, R.style.Dialog_exit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_logining);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        setOnKeyListener(keylistener);
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();

    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (TextUtils.isEmpty(titleStr)) {

        }
        else{
            titleTv.setText(titleStr);
        }

        if (TextUtils.isEmpty(messageStr)) {

        }
        else{
            messageTv.setText(messageStr);
        }
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        messageTv = (TextView) findViewById(R.id.dialog_logining_hint);
    }

    public void setTitle(String title) {
        titleStr = title;
    }

    public void setMessage(String message) {
        messageStr = message;
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
