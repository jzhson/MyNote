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
 * Created by Jzhson on 11/4/2017.
 */

public class Dialog_Language extends Dialog {



    private Button cancel;//取消按钮
    private TextView english;//消息标题文本
    private TextView chinese;//消息提示文本
    private TextView spanish;//消息标题文本
    private TextView chinese_tw;//消息提示文本


    private onEnglishOnclickListener EnglishOnclickListener;
    private onChineseOnclickListener ChineseOnclickListene;
    private onChinesetwOnclickListener ChinesetwOnclickListener;
    private onSpanishOnclickListener SpanishOnclickListener;
    private onCancelOnclickListener CancelOnclickListener;


    public void setonEnglishOnclickListener(onEnglishOnclickListener onEnglishOnclickListener) {
        this.EnglishOnclickListener = onEnglishOnclickListener;
    }

    public void setonChineseOnclickListener(onChineseOnclickListener onChineseOnclickListener) {
        this.ChineseOnclickListene = onChineseOnclickListener;
    }

    public void setonChinesetwOnclickListener(onChinesetwOnclickListener onChinesetwOnclickListener) {
        this.ChinesetwOnclickListener = onChinesetwOnclickListener;
    }

    public void setonSpanishOnclickListener(onSpanishOnclickListener onSpanishOnclickListener) {
        this.SpanishOnclickListener = onSpanishOnclickListener;
    }
    public void setonCancelOnclickListener(onCancelOnclickListener onCancelOnclickListener) {
        this.CancelOnclickListener = onCancelOnclickListener;
    }



    public Dialog_Language(Context context) {
        super(context, R.style.Dialog_exit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_languagechoose);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        setOnKeyListener(keylistener);
        //初始化界面控件
        initView();
        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EnglishOnclickListener != null) {
                    EnglishOnclickListener.onEnglishClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChineseOnclickListene != null) {
                    ChineseOnclickListene.onChineseClick();
                }
            }
        });
        chinese_tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ChinesetwOnclickListener != null) {
                    ChinesetwOnclickListener.onChinesetwClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        spanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpanishOnclickListener != null) {
                    SpanishOnclickListener.onSpanishClick();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CancelOnclickListener != null) {
                    CancelOnclickListener.onCancelClick();
                }
            }
        });
    }



    /**
     * 初始化界面控件
     */
    private void initView() {
        cancel=findViewById(R.id.languagechoose_button_cancel);
        english=findViewById(R.id.languagechoose_english);
        chinese=findViewById(R.id.languagechoose_chinese);
        spanish=findViewById(R.id.languagechoose_spanish);
        chinese_tw=findViewById(R.id.languagechoose_chinese_tw);
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onEnglishOnclickListener {
        public void onEnglishClick();
    }

    public interface onChineseOnclickListener {
        public void onChineseClick();
    }

    public interface onSpanishOnclickListener {
        public void onSpanishClick();
    }

    public interface onChinesetwOnclickListener {
        public void onChinesetwClick();
    }


    public interface onCancelOnclickListener {
        public void onCancelClick();
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
