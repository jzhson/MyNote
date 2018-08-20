package deal;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Jzhson on 11/3/2017.
 */

public class NoteDB {

    Context context;
    NoteOpenHelp myHelper;
    SQLiteDatabase myDatabase;
    private SharedPreferences spre;

    /*
     * 别的类实例化这个类的同时，创建数据库
     */
    public NoteDB(Context con){
        this.context=con;
        myHelper=new NoteOpenHelp(context);
    }
    /*
     * 得到ListView的数据，从数据库里查找后解析
     */
    public ArrayList<NoteBean> getArray(){
        ArrayList<NoteBean> array = new ArrayList<NoteBean>();
        ArrayList<NoteBean> array1 = new ArrayList<NoteBean>();
        myDatabase = myHelper.getWritableDatabase();
        Cursor cursor=myDatabase.rawQuery("select ids,title,times from mynotes" , null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(cursor.getColumnIndex("ids"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String times = cursor.getString(cursor.getColumnIndex("times"));
            NoteBean cun = new NoteBean(id, title, times);
            array.add(cun);
            cursor.moveToNext();
        }
        myDatabase.close();
        for (int i = array.size(); i >0; i--) {
            array1.add(array.get(i-1));
        }
        return array1;
    }


    public ArrayList<NoteBean> getArray1(){
        ArrayList<NoteBean> array = new ArrayList<NoteBean>();
        ArrayList<NoteBean> array1 = new ArrayList<NoteBean>();
        myDatabase = myHelper.getWritableDatabase();
        Cursor cursor=myDatabase.rawQuery("select ids,title,times,content from mynotes" , null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            int id = cursor.getInt(cursor.getColumnIndex("ids"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String times = cursor.getString(cursor.getColumnIndex("times"));
            NoteBean cun = new NoteBean(title, id, content,times);
            array.add(cun);
            cursor.moveToNext();
        }
        myDatabase.close();
        for (int i = array.size(); i >0; i--) {
            array1.add(array.get(i-1));
        }
        return array1;
    }

    /*
     * 返回可能要修改的数据
     */
    public NoteBean getTiandCon(int id){
        myDatabase = myHelper.getWritableDatabase();
        Cursor cursor=myDatabase.rawQuery("select title,content from mynotes where ids='"+id+"'" , null);
        cursor.moveToFirst();
        String title=cursor.getString(cursor.getColumnIndex("title"));
        String content=cursor.getString(cursor.getColumnIndex("content"));
        NoteBean cun=new NoteBean(title,content);
        myDatabase.close();
        return cun;
    }

    public void toUpdate(NoteBean cun){
        myDatabase = myHelper.getWritableDatabase();
        myDatabase.execSQL(
                "update mynotes set title='"+ cun.getTitle()+
                        "',times='"+cun.getTimes()+
                        "',content='"+cun.getContent() +
                        "' where ids='"+ cun.getIds()+"'");
        myDatabase.close();
    }

    public void toInsert(NoteBean cun){
        myDatabase = myHelper.getWritableDatabase();
        myDatabase.execSQL("insert into mynotes(title,content,times)values('"
                + cun.getTitle()+"','"
                +cun.getContent()+"','"
                +cun.getTimes()
                +"')");
        myDatabase.close();
    }

    public void toDelete(int ids){
        myDatabase  = myHelper.getWritableDatabase();
        myDatabase.execSQL("delete from mynotes where ids="+ids+"");
        myDatabase.close();
    }
}