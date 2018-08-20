package deal;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import java.util.Locale;

public class BaseActivity extends Activity {
    Locale spanish = new Locale("es", "ES");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//初始化PreferenceUtil
		PreferenceUtil.init(this);
		//根据上次的语言设置，重新设置语言
	    switchLanguage(PreferenceUtil.getString("language", "en"));
	}
	
	
	protected void switchLanguage(String language) {
		//设置应用语言类型
		Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
       if (language.equals("en")) {
            config.locale = Locale.ENGLISH;
        }
        else if (language.equals("cn")){
        	 config.locale = Locale.SIMPLIFIED_CHINESE;
        }
       else if (language.equals("es")){
           config.locale = spanish;
       }
       else if (language.equals("cn_tw")){
           config.locale = Locale.TRADITIONAL_CHINESE;
       }
        resources.updateConfiguration(config, dm);
        
        //保存设置语言的类型
        PreferenceUtil.commitString("language", language);
    }
}
