package edu.java.login.controller;
// 로그인 창에서 입력 받은 아이디와 비밀번호를 테이블과 비교해서 
// 개인 페이지 띄우기 -> usertable를 받아야 함

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import edu.java.login.model.UserTable;
import oracle.jdbc.OracleDriver;

import static edu.java.ojdbc.OracleJdbc.*;
import static edu.java.ojdbc.JdbcSql.*;
import static edu.java.login.model.UserTable.Entity.*;


public class LoginDaoImpl {
    //TODO PUBLIC으로 해서 다른곳에서도 사용해도 되지 않을까
    
    private static LoginDaoImpl instance = null;
    
    private LoginDaoImpl() {}
    public static LoginDaoImpl getInstance() {
        if(instance == null) {
            instance = new LoginDaoImpl();
        }
        return instance;
    }
    
    private Connection getConnection() throws SQLException{
        DriverManager.registerDriver(new OracleDriver());
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    private void closeResources(Connection conn, Statement stmt) throws SQLException {
        stmt.close();
        conn.close();
    }
    
    private void closeResources(Connection conn, Statement stmt, ResultSet rs) 
            throws SQLException {
        rs.close();
        closeResources(conn, stmt);
    }
    
    public int mypagePasswordUpdate(UserTable user) {
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn =getConnection() ;
            stmt =conn.prepareStatement(SQL_USER_PASSWORD_UPDATE);
            stmt.setString(1,user.getPw());
            stmt.setInt(2,user.getUserNo());
       
            
            result = stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }
    
    
    public int mypageUpdate(UserTable user) {
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn =getConnection() ;
            stmt =conn.prepareStatement(SQL_USER_MY_PAGE_UPDATE);
            stmt.setString(1,user.getNick());
            stmt.setString(2,user.getAge());
            stmt.setString(3,user.getGender());
            stmt.setInt(4, user.getUserNo());
            
            result = stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return result;
    }
    
    
    
    
    
    public boolean confirmId(String id) {
    	boolean result = false;
    	 Connection conn = null;
         PreparedStatement stmt = null;
         ResultSet rs = null;
         
         try {
             conn = getConnection();
             stmt = conn.prepareStatement(String.format("select * from %s where %s = ? ", TBL_USER, COL_ID));
             stmt.setString(1, id);
             
             rs = stmt.executeQuery();
          result= rs.next(); // 존재하지 않다 false => 가입가능
         } catch (SQLException e) {
             e.printStackTrace();
         }finally{
             try {
                 closeResources(conn, stmt, rs);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
    	
         return result;
    }
    
    
    
    public boolean confirmEmail(String email) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(String.format("select * from %s where %s = ? ", TBL_USER, COL_EMAIL));
            stmt.setString(1, email);
            
            rs = stmt.executeQuery();
         result= rs.next(); // 존재하지 않다 => 가입가능
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
    
    
    
    
    
    public UserTable confirm(String userid, String password) {
        UserTable user = null;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setString(1, userid);
            
            rs=stmt.executeQuery();
            
            
            if(rs.next()) {
                Integer no = rs.getInt(COL_USER_NO);
                String id = rs.getString(COL_ID);
                String pw = rs.getString(COL_PW);
                String email = rs.getString(COL_EMAIL);
                String nick = rs.getString(COL_NICK);
                LocalDateTime created = rs.getTimestamp(COL_DATE).toLocalDateTime();
                String age = rs.getString(COL_AGE);
                String gender = rs.getString(COL_GENDER);
                
                if(pw.equals(password)) {
                    user = new UserTable(no, id, pw, email, nick, created, age, gender);
                }
            } 
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        
        
        return user;
    }
    
    
    
    


    public int insertUser(UserTable user) {
        int result = 0;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            System.out.println(SQL_INSERT);
            
            stmt=conn.prepareStatement(SQL_INSERT);
            
            stmt.setString(1,user.getId());
            stmt.setString(2, user.getPw());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getNick());
            stmt.setString(5, user.getAge());
            stmt.setString(6, user.getGender());
            
            result = stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
        	try {
				closeResources(conn, stmt);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return result;
    }
    
    
    
    // 아이디 비밀번호 찾기
    public String searchId(String email) {
        String id = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        
        try {
          conn = getConnection();
          stmt = conn.prepareStatement("select id from user_table where email = ?");
          stmt.setString(1, email);
          rs = stmt.executeQuery();
          if(rs.next()) {
             id = rs.getString("id");
          }
          
       } catch (SQLException e) {
          e.printStackTrace();
       } finally {
        try {
            closeResources(conn, stmt, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        return id;
    }
    
    public String searchPass(String email, String id) {
        String pass = null;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        
        try {
          conn = getConnection();
          stmt = conn.prepareStatement("select password from user_table where email = ? and id =?");
          stmt.setString(1, email);
          stmt.setString(2,  id);
          rs = stmt.executeQuery();
          if(rs.next()) {
             pass = rs.getString("password");
          }
          
       } catch (SQLException e) {
          e.printStackTrace();
       } finally {
           try {
            closeResources(conn, stmt, rs);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       }
        
        
        return pass;
     }
    
    
}
