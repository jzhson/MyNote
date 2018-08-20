package bmob;

import cn.bmob.v3.BmobObject;

public class Users extends BmobObject{
    private String email;
    private String password;
    private String sex;
    private String nickname;
    private String registime;
    private String birth;
    private String phone;
    private int newuser;
    private String sync_status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRegistime() {
        return registime;
    }

    public void setRegistime(String registime) {
        this.registime = registime;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getNewuser(){
        return newuser;
    }
    public void setNewuser(int newuser){
        this.newuser=newuser;
    }

    public String getSync_status(){
        return sync_status;
    }
    public void setSync_status(String sync_status){
        this.sync_status=sync_status;
    }
}

