package edu.java.page.controller;

import static edu.java.ojdbc.OracleJdbc.*;
import static edu.java.ojdbc.JdbcSql.*;
import static edu.java.page.model.BookTable.Entity.*;
import static edu.java.page.model.NoteTable.Entity.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.java.login.model.UserTable;
import edu.java.page.model.NoteTable;
import edu.java.page.model.BookTable;
import oracle.jdbc.driver.OracleDriver;



public class PageDaoImpl {
    
	private int a =0;
	// Singleton
	
    private static PageDaoImpl instance = null;
    
    private PageDaoImpl() {   }
    
    public static PageDaoImpl getInstance() {
        if(instance == null) {
            instance = new PageDaoImpl();
        }
        return instance;
    }
    
    private Connection getConnection() throws SQLException{
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        
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
 
    
    // 날짜 시간 예쁘게 출력
    private String[] dateTimeLocal(String localtime) {
        String d;
        String t;
        
        String array[] = localtime.split("T");
        String arrayD[] = array[0].split("-");
        String date = arrayD[0]+"년"+arrayD[1]+"월"+arrayD[2]+"일";
        String arrayT[]  = array[1].split(":");
        String time = arrayT[0]+"시"+arrayT[1]+"분";
        
        String[] dateAndTime = {date,time};

        return dateAndTime;
    }
    
    public List<BookTable> selectStatus(UserTable user, String status){
        List<BookTable> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer userNo = user.getUserNo();

        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(SQL_PAGE_SELECT_STATUS_);
            stmt= conn.prepareStatement(SQL_PAGE_SELECT_STATUS_);
            stmt.setInt(1, userNo);
            stmt.setString(2, status);

            rs = stmt.executeQuery();
            
            while(rs.next()) {
                
                Integer bookIndex = rs.getInt(COL_BOOK_INDEX);
                Integer bookNo = rs.getInt(COL_BOOK_NO);
                String title = rs.getString(COL_BOOK_TITLE);
                String author = rs.getString(COL_AUTHOR);
                String category = rs.getString(COL_CATEGORY);
                Integer pages = rs.getInt(COL_BOOK_PAGE);
                LocalDateTime startdate = rs.getTimestamp(COL_START_DATE).toLocalDateTime();
                String statusI = rs.getString(COL_BOOK_STATUS);
                Integer star = rs.getInt(COL_BOOK_STAR);
                
                        
                BookTable book = new BookTable(bookIndex, bookNo, title, author, category, pages, startdate, userNo, null, statusI, star);
                
                String[] t = dateTimeLocal(startdate.toString());
                book.setDateTest(t[0]+"  "+ t[1]);
                
                list.add(book);
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
        return list;
    }
    
    
    
    
    
    
    
    /**
     * 사용자 전용페이지에서 bookTable를 보여줌
     * 
     * @param user 로그인할때 들어오는 user객체
     * @return 전체 책 테이블
     */
    public List<BookTable> select(UserTable user, int orderIndex){
        List<BookTable> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer userNo = user.getUserNo();
        
        String sql= null;
        
        switch(orderIndex) {
        case 0:
            sql = SQL_PAGE_SELECT_ALL;
            break;
        case 1:
            sql = SQL_PAGE_SELECT_ALL_NO;
            
            break;
        case 2:
            sql = SQL_PAGE_SELECT_STAR_H;
            break;
        case 3:
            sql = SQL_PAGE_SELECT_STAR_L;
            break;
        default:
            break;
        }
        
        
        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(sql);
            stmt= conn.prepareStatement(sql);
            stmt.setInt(1, userNo);

            rs = stmt.executeQuery();
            
            while(rs.next()) {
            	
            	Integer bookIndex = rs.getInt(COL_BOOK_INDEX);
                Integer bookNo = rs.getInt(COL_BOOK_NO);
                String title = rs.getString(COL_BOOK_TITLE);
                String author = rs.getString(COL_AUTHOR);
                String category = rs.getString(COL_CATEGORY);
                Integer pages = rs.getInt(COL_BOOK_PAGE);
                LocalDateTime startdate = rs.getTimestamp(COL_START_DATE).toLocalDateTime();
                String status = rs.getString(COL_BOOK_STATUS);
                Integer star = rs.getInt(COL_BOOK_STAR);
                
                        
                BookTable book = new BookTable(bookIndex, bookNo, title, author, category, pages, startdate, userNo, null, status, star);
                
                String[] t = dateTimeLocal(startdate.toString());
                book.setDateTest(t[0]+"  "+ t[1]);
                
                list.add(book);
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
        return list;
    }
    
    /**
     * 책의 고유번호(모든유저의 책에 있는 PK)
     * 사용자의 페이지에선 index가 보이지 않을 예정임. 
     * 완전히 동일한 메서드가 두개인데,, 고민 해봐
     * @param bookIndex 전체 책에서 고유의 값
     * @return
     */
    public BookTable selectBookIndex(int bookIndex) {
        BookTable book = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.prepareStatement(SQL_PAGE_SELECT_BOOK_INDEX);
            stmt.setInt(1, bookIndex);
            
            rs = stmt.executeQuery();
            
            while(rs.next()) {
            	Integer bookNo = rs.getInt(COL_BOOK_NO);
                String title = rs.getString(COL_BOOK_TITLE);
                String author = rs.getString(COL_AUTHOR);
                String category = rs.getString(COL_CATEGORY);
                Integer userNo = rs.getInt(COL_USER_BOOK_NO);
                Integer pages = rs.getInt(COL_BOOK_PAGE);
                LocalDateTime startdate = rs.getTimestamp(COL_START_DATE).toLocalDateTime();
                String memo = rs.getString(COL_BOOK_MEMO);
                
                
//                book = new PageBookTable(bookIndex, book_no, title, author, category, pages, startdate, userNo, memo);
                 book = new BookTable(bookIndex, bookNo, title, author, category, pages, startdate, userNo, null, null, 0);
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
        
        
        return book ;
    }
    
    /**
     * 테이블에서 선택한 행에서 첫 컬럼 값을 bookNo임! 
     * 사용자 페이지에 있는 테이블에서 책을 선택할때 사용함.
     * @param bookNo
     * @return 책 객체를 내보냄
     */
    public BookTable selectBook(int bookNo, int userNo) {
        BookTable book = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.prepareStatement(SQL_PAGE_SELECT_BOOK_NO);
            stmt.setInt(1, bookNo);
            stmt.setInt(2, userNo);
            
            rs = stmt.executeQuery();
            
            if(rs.next()) {
            	Integer book_index = rs.getInt(COL_BOOK_INDEX);
            	Integer book_no = rs.getInt(COL_BOOK_NO);
                String title = rs.getString(COL_BOOK_TITLE);
                String author = rs.getString(COL_AUTHOR);
                String category = rs.getString(COL_CATEGORY);
                Integer pages = rs.getInt(COL_BOOK_PAGE);
                LocalDateTime startdate = rs.getTimestamp(COL_START_DATE).toLocalDateTime();
                String memo = rs.getString(COL_BOOK_MEMO);
                String status = rs.getString(COL_BOOK_STATUS);
                Integer star =rs.getInt(COL_BOOK_STAR);
                
                
                book = new BookTable(book_index, book_no, title, author, category, pages, startdate, userNo, memo, status,star);
                
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
        
        
        return book ;
    }
    
    
    
    
    
    public List<BookTable> selectByComboBox(UserTable user, String keyword , int index){
    	
        List<BookTable> list = new ArrayList<>();
        Integer userNo = user.getUserNo();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        
        try { // 0,1,2,3 제목, 작가, 메모, 전체검색 
        	// sql 문장 select * from where user_no = ?  and 제목, 작가, 메모, like keyword
        	conn = getConnection();
            
            switch (index) {
    		case 0:
    			stmt = conn.prepareStatement(SQL_BOOK_SELECT_TITLE);
    			break;
    		case 1:
    			stmt = conn.prepareStatement(SQL_BOOK_SELECT_AUTHOR);
    			break;
    		case 2:
    			stmt = conn.prepareStatement(SQL_BOOK_SELECT_MEMO);
    			break;
    		case 3:
    			stmt = conn.prepareStatement(SQL_BOOK_SELECT_BY_KEYWORD);
    			break;
    	} 
            stmt.setInt(1,userNo);
            
            if(index==3) {
            	stmt.setString(2, "%"+keyword+"%");
            	stmt.setString(3, "%"+keyword+"%");
            	stmt.setString(4, "%"+keyword+"%");
            }else {
            	stmt.setString(2, "%"+keyword+"%");
            }
            
            rs = stmt.executeQuery();
            
            while(rs.next()) {
            	Integer bookIndex = rs.getInt(COL_BOOK_INDEX);
                Integer bookNo = rs.getInt(COL_BOOK_NO);
                String title = rs.getString(COL_BOOK_TITLE);
                String author = rs.getString(COL_AUTHOR);
                String category = rs.getString(COL_CATEGORY);
                Integer pages = rs.getInt(COL_BOOK_PAGE);
                LocalDateTime startdate = rs.getTimestamp(COL_START_DATE).toLocalDateTime();
                String memo= rs.getString(COL_BOOK_MEMO);
                String status = rs.getString(COL_BOOK_STATUS);
                Integer star =rs.getInt(COL_BOOK_STAR);
                
                
                BookTable book = new BookTable(bookIndex, bookNo, title, author, category, pages, startdate, userNo, memo, status,star);
                
                String[] t = dateTimeLocal(startdate.toString());
                book.setDateTest(t[0]+"  "+ t[1]);
                
                list.add(book);
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
        return list;
    }
    
    
    
    //bookno구하기 TODO
    //user를 받고 인티저를 내보내
    public int findBookNo(int userNo) {
    	int no= 0;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        
        try {
        	
        	//SELECT book_title FROM page_table WHERE ROWNUM = 1 and user_no =1 order by book_index desc;
        	conn= getConnection();
        	stmt = conn.prepareStatement("select max(book_no) from page_table where user_no=?");
            stmt.setInt(1,userNo);
            
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                no = rs.getInt("max(book_no)");
            		System.out.println(no+"= pdao");
            	 
            } else {
            	no =0;	
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

    	return no;
    }
    
    
    
    
    
    public int bookInsert(BookTable book) {
        // 
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;

        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_PAGE_BOOK_INSERT);// 문장을 고쳐야 함.
            stmt.setInt(1, book.getBookNo());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getCategory());
            stmt.setInt(5, book.getPages());
            stmt.setInt(6, book.getUserNo());
            stmt.setString(7, book.getMemo());
            stmt.setString(8, book.getStatus());
            stmt.setInt(9, book.getStar());
            
            result = stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }      
        return result;
    }
    
    
    
    public int bookUpdate(BookTable book) {
        // 넣어야할 값이 총 8개임 북넘버, 날짜 빼고 6개임 
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;

        System.out.println((Integer)book.getPages());
        System.out.println(SQL_PAGE_BOOK_UPDATE);
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_PAGE_BOOK_UPDATE);// 문장을 고쳐야 함.
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getCategory());
            stmt.setInt(4, (Integer)book.getPages()); // 왜????? 
            stmt.setString(5, book.getMemo());
            stmt.setString(6, book.getStatus());
            stmt.setInt(7, book.getStar());
            stmt.setInt(8, book.getBookIndex());
            
            result = stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }      
        return result;
    }
    
    
    
    public int deleteBook(int bookNo, int userNo ) {
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_PAGE_BOOK_DELETE);
            stmt.setInt(1, bookNo);
            stmt.setInt(2, userNo);
            
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
        
    }
    
    
    
    
    
    
    // 독서 노트 페이지에서 사용할 메서드
    /**
     * 노트 페이지에서 노트 테이블 전체
     * @param bookIndex 어떤 책인지 특정한 값을 받음
     * @return
     */
    public List<NoteTable> noteSelect(int bookIndex){
        List<NoteTable> list = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_NOTE_SELECT_ALL);
            
            stmt.setInt(1, bookIndex);
            rs = stmt.executeQuery();
            
            while(rs.next()) {
                Integer noteNo = rs.getInt(COL_NOTE_NO);
                String note = rs.getString(COL_NOTE);
                Integer stPage = rs.getInt(COL_NOTE_ST_PAGE);
                Integer endPage = rs.getInt(COL_NOTE_ENDPAGE);
                LocalDateTime date = rs.getTimestamp(COL_NOTE_DATE).toLocalDateTime();
                Integer bookno = rs.getInt(COL_NOTE_BOOK_INDEX);
                
                NoteTable noteT = new NoteTable(noteNo, note, stPage, endPage, date, bookno);
                
                list.add(noteT);
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
        return list;
    }
    
    // 최근 날짜  TODO
    
    
    // 노트를 선택하고 노트 자세히 보기  TODO
    /**
     * 선택한 노트를 자세히 보거나, 삭제하거나 수정
     * @param noteNo
     * @return
     */
    public NoteTable  selectNote(int noteNo, int bookIndex) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        NoteTable note = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(String.format("select * from %s where %s = ? and %s = ?",
                    TBL_NOTE, COL_NOTE_NO, COL_BOOK_INDEX));
            stmt.setInt(1, noteNo);
            stmt.setInt(2, bookIndex);
            
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                String notet = rs.getString(COL_NOTE);
                Integer stPage = rs.getInt(COL_NOTE_ST_PAGE);
                Integer endPage = rs.getInt(COL_NOTE_ENDPAGE);
                LocalDateTime date = rs.getTimestamp(COL_NOTE_DATE).toLocalDateTime();
                Integer bookNo = rs.getInt(COL_NOTE_BOOK_INDEX);
                
                note = new NoteTable(noteNo, notet, stPage, endPage, date, bookNo);
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        
        
        return note;
    }
    
    
    public int noteUpdate(NoteTable note, int noteNo, int bookIndex) {
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_NOTE_UPDATE);
            stmt.setInt(1,  note.getStPage());
            stmt.setInt(2, note.getEndPage());
            stmt.setString(3, note.getNote() );
            stmt.setInt(4, noteNo);
            stmt.setInt(5, bookIndex);
            
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        
        return result;
    }
    
    
    public int noteDelete(int noteNo) {
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_NOTE_DELETE);
            stmt.setInt(1, noteNo);
            
            result = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        
        
        return result;
    }
    
    
    
    
    
    
    //  노트 데이터 
//    public int noteNo ()
    public int findNoteNo(int bookIndex) {
        int no= 0;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        
        try {
            
            //SELECT book_title FROM page_table WHERE ROWNUM = 1 and user_no =1 order by book_index desc;
            conn= getConnection();
            stmt = conn.prepareStatement("select max(note_no) from note_table where book_index=?");
            stmt.setInt(1,bookIndex);
            
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                no = rs.getInt("max(note_no)") +1 ;
                    System.out.println(no+"= noteNo");
                 
            } else {
                no =1;  
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

        return no;
    }
    
    
    
    
    public int noteInsert(NoteTable note) {
        // 넣어야할 값이 총 8개임 북넘버, 날짜 빼고 6개임 
        int result = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_NOTE_INSERT);
            stmt.setString(1, note.getNote());
            stmt.setInt(2, note.getStPage());
            stmt.setInt(3, note.getEndPage());
            stmt.setInt(4, note.getBookIndex());
            stmt.setInt(5, note.getNoteNo());
            
            result = stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeResources(conn, stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }      
        return result;
    }
    
    /**
     * 노트 테이블에서 book_index를 이용해서 찾음
     * @param keyword
     * @param bookIndex
     * @return
     */
    public List<NoteTable> selectNoteKeyword(String keyword , int bookIndex){
        List<NoteTable> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("select * from note_table  where book_index =? and lower(note) like lower(?) ");
            stmt.setInt(1, bookIndex);
            stmt.setString(2, "%"+keyword+"%");
            rs= stmt.executeQuery();
            
            while(rs.next()) {
                Integer noteNo = rs.getInt(COL_NOTE_NO);
                String note = rs.getString(COL_NOTE);
                Integer stPage = rs.getInt(COL_NOTE_ST_PAGE);
                Integer endPage = rs.getInt(COL_NOTE_ENDPAGE);
                LocalDateTime date = rs.getTimestamp(COL_NOTE_DATE).toLocalDateTime();
                Integer bookno = rs.getInt(COL_NOTE_BOOK_INDEX);
                
                NoteTable noteT = new NoteTable(noteNo, note, stPage, endPage, date, bookno);
                
                list.add(noteT);
            } 
            
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        
        
        return list;
    }
    
    
    public int noteEndPage(BookTable book) {
        int endPage = 0;
        int bookIndex = book.getBookIndex();
        String a= null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        //select max(end_page) from note_table where book_index = 28;

            
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("select max(end_page) from note_table where book_index = ?");
            stmt.setInt(1, bookIndex);
            
            rs = stmt.executeQuery();
            if(rs.next()) {
                endPage = rs.getInt("max(end_page)");
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

        return endPage;
    }
    
    
    
    public String endDate(BookTable book) {
        String endDate = null;
        int bookIndex = book.getBookIndex();
        System.out.println("test");
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        // LocalDateTime date = rs.getTimestamp(COL_NOTE_DATE).toLocalDateTime();
        //select max(end_page) from note_table where book_index = 28;
        
        if(findNoteNo(bookIndex)==1) {
            return endDate;
        }
        try {
            System.out.println("test1");
            conn = getConnection();
            stmt = conn.prepareStatement("select max(note_date) from note_table where book_index = ?");
            stmt.setInt(1, bookIndex);
            
            System.out.println("test2");
            rs = stmt.executeQuery();
            System.out.println("test3");
            
            if(rs.next()) {
                System.out.println("test4");
                endDate = rs.getTimestamp("max(note_date)").toLocalDateTime().toString();
            } else {
                endDate = "  ";
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

        return endDate;
    }
    
    
    public int bookN(int userNo) {
        int n = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null ;
        System.out.println(n);
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("select count(*) from user_table u , page_table p where u.user_no = p.user_no and u.user_no = ? ");
            stmt.setInt(1, userNo);
            
            rs = stmt.executeQuery();
            if(rs.next()) {
                n= rs.getInt("count(*)");
            }
            System.out.println(n);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return n;
    }
    public int noteN(int userNo) {
        int n = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null ;
        System.out.println(n);
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("select count(*) from  user_table u , page_table p , note_table n "
                    + "where u.user_no = p.user_no and p.book_index = n.book_index  and u.user_no = ?");
            stmt.setInt(1, userNo);
            
            rs = stmt.executeQuery();
            if(rs.next()) {
                n= rs.getInt("count(*)");
            }
            System.out.println(n);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return n;
    }
    public int readN(int userNo) {
        int n = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null ;
        System.out.println(n);
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("select count(*) from user_table u , page_table p "
                    + "where u.user_no = p.user_no and p.status = '읽는 중' and u.user_no = ?");
            stmt.setInt(1, userNo);
            
            rs = stmt.executeQuery();
            if(rs.next()) {
                n= rs.getInt("count(*)");
            }
            System.out.println(n);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return n;
    }
    
    /**
     * 총 회원 수 
     */
    public int userNo() {
         int n = 0;
         Connection conn = null;
         PreparedStatement stmt = null;
         ResultSet rs = null ;
         System.out.println(n);
         try {
             conn = getConnection();
             stmt = conn.prepareStatement("select count(*) from user_table");
             rs = stmt.executeQuery();
             if(rs.next()) {
                 n= rs.getInt("count(*)");
             }
             System.out.println(n);
         } catch (SQLException e) {
             e.printStackTrace();
         }finally {
             try {
                 closeResources(conn, stmt, rs);
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
         System.out.println(n);
         return n;
     }
    
    public int userRank(int userNo) {
        int n = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null ;
        System.out.println(n);
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(SQL_NOTE_RANK);
            stmt.setInt(1, userNo);
            
            rs = stmt.executeQuery();
            if(rs.next()) {
                n= rs.getInt("rank");
            }
            System.out.println(n);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                closeResources(conn, stmt, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println(n);
        return n;
    }
    
    
    
            
//            stmt = conn.prepareStatement("select count(*) from  user_table u , page_table p , note_table n "
//                    + "where u.user_no = p.user_no and p.book_index = n.book_index  and u.user_no = ?");
//            stmt.setInt(1, userNo);
//            if(rs.next()) {
//                n = rs.getInt("count(*)");
//            }
//            System.out.println("??"+n);
//            
//            stmt = conn.prepareStatement("select count(*) from user_table u , page_table p "
//                    + "where u.user_no = p.user_no and p.status = '읽는 중' and u.user_no = ?");
//            System.out.println("???"+n);
//            stmt.setInt(1, userNo);
//            if(rs.next()) {
//                n = rs.getInt("count(*)");
//            }
            
    
    
    
}
