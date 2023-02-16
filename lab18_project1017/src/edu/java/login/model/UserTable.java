package edu.java.login.model;

import java.time.LocalDateTime;
// 생성자 getter, toString
// user 테이블, 객체 
public class UserTable {

    public interface Entity {
        String TBL_USER = "USER_TABLE";
        String COL_USER_NO = "USER_NO";
        String COL_ID = "ID";
        String COL_PW= "PASSWORD";
        String COL_EMAIL= "EMAIL";
        String COL_NICK= "NICKNAME";
        String COL_DATE= "CREATE_DATE";  
        String COL_AGE ="AGE";
        String COL_GENDER = "GENDER";
    }
    // 테이블 객체
    private Integer userNo ;
    private String userId;
    private String pw;
    private String email;
    private String nick;
    private LocalDateTime createddate;
    private String age;
    private String gender;
    
    public UserTable() {}

    public UserTable(Integer userNo, String id, String pw, String email, String nick,
    		LocalDateTime createddate,String age,String gender) {
        this.userNo = userNo;
        this.userId = id;
        this.pw = pw;
        this.email = email;
        this.nick = nick;
        this.createddate = createddate;
        this.age=age;
        this.gender=gender;
    }

    public Integer getUserNo() {
        return userNo;
    }

    public String getId() {
        return userId;
    }

    public String getPw() {
        return pw;
    }

    public String getEmail() {
        return email;
    }

    public String getNick() {
        return nick;
    }

    public LocalDateTime getCreateddate() {
        return createddate;
    }
    
    public String getAge() {
    	return age;
    }
    
    public String getGender() {
    	return gender;
    }
    
    // 생성자가 고민이야.. 그냥 setter도 다 만들까? 
    
    
    @Override
    public String toString() {
        return  String.format("User(No=%d, id=%s, pw=%s, email=%s, name=%s, create=%s , gender=%s, age=%s)",
                userNo, userId, pw,email, nick, createddate, gender, age);
    }
    
    
    
}
