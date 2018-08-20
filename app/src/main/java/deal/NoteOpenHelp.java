package deal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jzhson on 11/3/2017.
 */

public class NoteOpenHelp extends SQLiteOpenHelper {

    public NoteOpenHelp(Context context) {
        super(context, "mynotes", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table mynotes( ids integer PRIMARY KEY autoincrement,title text,content text,times text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}