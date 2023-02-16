package edu.java.ojdbc;

import static edu.java.login.model.UserTable.Entity.*;
import static edu.java.page.model.BookTable.Entity.*;
import static edu.java.page.model.NoteTable.Entity.*;


public interface JdbcSql {
    // 정리하기!!!
    
    
    
    
	// delete, update User
	String SQL_USER_DELETE = String.format("delete from %s where %s = ?", TBL_USER, COL_USER_NO);
			
	String SQL_USER_UPDATE = String.format("update %s set"
			+ " %s = ? ,%s = ?, %s = ?,"
			+ "%s = ?,%s = ? "
			+ "where %s = ?  ",
			TBL_USER,
			COL_ID, COL_PW, COL_NICK,
			COL_AGE, COL_GENDER,
			COL_USER_NO);
	// 사용자가 mypage에서 수정
	   String SQL_USER_MY_PAGE_UPDATE = String.format("update %s set"
	            + "  %s = ?,"
	            + "%s = ?,%s = ? "
	            + "where %s = ?  ",
	            TBL_USER,
	             COL_NICK,
	            COL_AGE, COL_GENDER,
	            COL_USER_NO);
	   
       String SQL_USER_PASSWORD_UPDATE = String.format("update %s set  %s = ? where %s = ?  ",
               TBL_USER,
                COL_PW,
               COL_USER_NO);
	
	
	// email 중복 확인 
	String SQL_SELECT_EMAIL = String.format("select * from %s where %s = ? ", TBL_USER, COL_EMAIL);

    // user 검색? id 확인 로그인 
    // select * from userjdbc where id = 123;
    String SQL_SELECT_BY_ID = 
            String.format("select * from %s where %s = ? ", TBL_USER, COL_ID);
    
    // 회원 가입 할땐 
    String SQL_INSERT = 
            String.format("insert into %s (%s, %s, %s, %s,%s,%s) values (? ,? ,?, ?,?,?)",
                    TBL_USER ,COL_ID, COL_PW, COL_EMAIL, COL_NICK, COL_AGE, COL_GENDER);
    
    // 전체를 안해도 되지만 적어는 놔야겠지? TODO 안해도 되지 않나..?
    String SQL_PAGE_INSERT_ALL =
            String.format("insert into %s (%s, %s,%s, %s,%s) values (?,?,?,?,?) ",
                    TBL_PAGE, COL_BOOK_TITLE, COL_AUTHOR, COL_CATEGORY,COL_BOOK_PAGE, COL_USER_BOOK_NO);
    
    // TODO
    
    
    
    
    
    
    
    // 페이지에서 전체 테이블 보여주기
    String SQL_PAGE_SELECT_ALL = 
            String.format("select * from %s where %s = ? order by %s desc"
                    ,TBL_PAGE, COL_USER_BOOK_NO, COL_BOOK_NO);
    String SQL_PAGE_SELECT_ALL_NO = 
            String.format("select * from %s where %s = ? order by %s "
                    ,TBL_PAGE, COL_USER_BOOK_NO, COL_BOOK_NO);
//    select * from page_table p
//    join user_table u on p.user_no = u.user_no
//    where u.user_no  = 1
//    order by grade desc;
//    select *
//    from user_table u , page_table p
//    where u.user_no = p.user_no
//    and u.user_no =1;
    
    String SQL_PAGE_SELECT_STAR_H = 
            String.format("select * from %s , %s "
                    + "where  page_table.user_no = user_table.user_no  "
                    + "and  user_table.user_no = ?  order by page_table.grade desc"
                    ,TBL_PAGE,TBL_USER , COL_USER_BOOK_NO);
    
    String SQL_PAGE_SELECT_STAR_L = 
            String.format("select * from %s , %s "
                    + "where  page_table.user_no = user_table.user_no  "
                    + "and  user_table.user_no = ?  order by page_table.grade"
                    ,TBL_PAGE,TBL_USER , COL_USER_BOOK_NO);
    // select* from user_table, page_table where user_table.user_no = page_table.user_no 
//    and page_table.user_no = 1 
//            and page_table.status = '읽는 중';

  
    String SQL_PAGE_SELECT_STATUS_= 
            String.format("select * from %s , %s where user_table.user_no = page_table.user_no "
                    + "and page_table.user_no = ?"
                    + "and page_table.status = ?"
                    + "order by page_table.book_no desc", 
                    TBL_USER, TBL_PAGE );
    

    
    String SQL_PAGE_BOOK_INSERT =  //user번호 있어야 함 TODO
            String.format("insert into %s (%s, %s, %s, %s, %s, %s,%s,%s,%s) values (?,? ,? ,?, ?,?,?,?,?)",
                        TBL_PAGE, 
                        COL_BOOK_NO,COL_BOOK_TITLE, COL_AUTHOR, COL_CATEGORY, COL_BOOK_PAGE,
                        COL_USER_BOOK_NO, COL_BOOK_MEMO, COL_BOOK_STATUS,COL_BOOK_STAR );
    
    String SQL_PAGE_BOOK_UPDATE = 
            String.format("update %s set %s = ? ,%s = ? ,%s = ? ,%s = ? ,%s = ? ,%s = ? ,%s = ? where %s =? ",
                        TBL_PAGE, 
                        COL_BOOK_TITLE, COL_AUTHOR, COL_CATEGORY, COL_BOOK_PAGE,
                        COL_BOOK_MEMO, COL_BOOK_STATUS,COL_BOOK_STAR,
                        COL_BOOK_INDEX);
    
    
    
    String SQL_PAGE_BOOK_DELETE = 
            String.format("delete from %s where %s = ? and %s = ?",
                    TBL_PAGE, COL_BOOK_NO, COL_USER_NO);
    
    

    
    
    // 검색 select * from page_table
//    where user_no =3 and book_title like '%jus%'
    String SQL_BOOK_SELECT_TITLE = String.format("select * from %s where %s = ? and lower(%s) like lower(?)", 
    		TBL_PAGE, COL_USER_BOOK_NO, COL_BOOK_TITLE);
    String SQL_BOOK_SELECT_AUTHOR = String.format("select * from %s where %s = ? and lower(%s) like lower(?)",
    		TBL_PAGE, COL_USER_BOOK_NO, COL_AUTHOR);
    String SQL_BOOK_SELECT_MEMO = String.format("select * from %s where %s = ? and lower(%s) like lower(?)",
    		TBL_PAGE, COL_USER_BOOK_NO, COL_BOOK_MEMO);
    
    
    //select * from page_table where user_no =1  and( book_title like '%2%' or book_author like '%2%'  or memo like '%2%' )
    String SQL_BOOK_SELECT_BY_KEYWORD = String.format("select * from %s where %s = ?  "
    		+ "and( lower(%s) like lower(?) or lower(%s) like lower(?)  or lower(%s) like lower(?) )",
    		TBL_PAGE,COL_USER_BOOK_NO,
    		COL_BOOK_TITLE, COL_AUTHOR, COL_BOOK_MEMO);
    		
    
    
    // 독서 노트 작성 선택한 책번호를 통해 책 관련 내용 받아오기!
    String SQL_PAGE_SELECT_BOOK_INDEX = 
            String.format("select * from %s where %s = ? "
                    ,TBL_PAGE, COL_BOOK_INDEX);
    String SQL_PAGE_SELECT_BOOK_NO = 
    		String.format("select * from %s where %s = ? and %s = ?"
    				,TBL_PAGE, COL_BOOK_NO, COL_USER_NO);
    
    
    // 책에 따라 적힌 노트가 달라야함! where 써야행
    String SQL_NOTE_SELECT_ALL = 
            String.format("select * from %s  where %s = ? order by %s desc"
                    ,TBL_NOTE, COL_NOTE_BOOK_INDEX,COL_NOTE_NO );
    
    
    // 노트 상세 보기
    String SQL_NOTE_SELECT_NOTENO =  String.format("select * from %s where %s = ?",
            TBL_NOTE, COL_NOTE_NO);

    String SQL_NOTE_UPDATE= String.format("update %s  set %s = ?,  %s =? ,  %s = ? where %s =? and %s= ? ", 
            TBL_NOTE, COL_NOTE_ST_PAGE, COL_NOTE_ENDPAGE, COL_NOTE, COL_NOTE_NO , COL_NOTE_BOOK_INDEX);
    
    
    String SQL_NOTE_DELETE = String.format("delete from %s where %s =? ", 
            TBL_NOTE, COL_NOTE_NO);
    
    String SQL_NOTE_INSERT = 
            String.format("insert into %s (%s,%s, %s, %s, %s) values (? ,? ,?, ?,?)",
                        TBL_NOTE,COL_NOTE, COL_NOTE_ST_PAGE, COL_NOTE_ENDPAGE,COL_NOTE_BOOK_INDEX, COL_NOTE_NO
                    );
    
    
    
    //노트 번호 임의로 주기
    
    String SQL_NOTE_FIND_NO= String.format("select max(note_no) from note_table where book_index=?");
    
    
    // 노트의 개수에 따라 달라지는 등수
    
    String SQL_NOTE_RANK = String.format("select rank "
            + "from(select rank() over( order by count(*) desc) as rank, u.user_no "
            + "from user_table u, page_table p , note_table n "
            + "where u.user_no = p.user_no and p.book_index = n.book_index "
            + "group by u.user_no)"
            + "where user_no =?");
    

}