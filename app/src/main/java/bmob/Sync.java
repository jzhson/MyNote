package bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by Jzhson on 11/8/2017.
 */

public class Sync extends BmobObject{
    private String emailaddress;
    private String title;
    private String notes_con;
    private String time;
    private int id;


    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return notes_con;
    }

    public void setContent(String content) {
        this.notes_con = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
