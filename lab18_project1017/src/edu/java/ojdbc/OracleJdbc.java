package edu.java.ojdbc;

public interface OracleJdbc {
//
//    String URL = "jdbc:oracle:thin:@localhost:1521:xe";
//    String USER = "scott";
//    String PASSWORD = "tiger";
//    
    
    
    // Oracle DB에 접속하는 주소 (포트번호, SID)
    String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    // thin 오라클에서만 사용하는 접속 방식,  @ ip 주소, 1521 포트번호, xe SID
    
    
    // Oracle DB에 접속하는 사용자 계정
    String USER = "scott";
    
    // Oracle DB에 접속하는 사용자 비밀번호
    String PASSWORD = "tiger";
}
