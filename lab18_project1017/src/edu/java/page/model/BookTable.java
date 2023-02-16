package edu.java.page.model;

import java.time.LocalDateTime;

public class BookTable {
    // 책제목, 작가, 
    public interface Entity {
        String TBL_PAGE = "PAGE_TABLE";
        
        String COL_BOOK_INDEX = "BOOK_INDEX";
        String COL_BOOK_TITLE = "BOOK_TITLE";
        String COL_AUTHOR = "BOOK_AUTHOR";
        String COL_CATEGORY = "CATEGORY";
        String COL_BOOK_PAGE = "BOOK_PAGE";
        String COL_USER_BOOK_NO = "USER_NO";
        String COL_START_DATE = "STARTDATE";
        String COL_BOOK_MEMO = "MEMO";
        String COL_BOOK_NO = "BOOK_NO";
        String COL_BOOK_STATUS = "STATUS";
        String COL_BOOK_STAR = "GRADE";
                
    }
    
    private Integer bookIndex;
    private Integer bookNo;
    private String title;
    private String author;
    private String category;
    private Integer pages;
    private LocalDateTime startdate;
    private Integer userNo;
    private String memo;
    private String status;
    private Integer star;
    private String dateTest;
    
    
    public BookTable() {}
    
    public BookTable(Integer bookIndex, Integer bookNo, String title,
    		String author, String category, Integer pages,
            LocalDateTime startdate, Integer userNo, String memo
            ,String status, Integer star) {
    	this.bookIndex = bookIndex;
        this.bookNo = bookNo;
        this.title = title;
        this.author = author;
        this.category = category;
        this.pages = pages;
        this.startdate = startdate;
        this.userNo = userNo;
        this.memo = memo;
        this.status = status;
        this.star = star;

    }
    
    
    
    public String getStatus() {
    	return status;
    }
    public Integer getStar() {
    	return star;
    }
    public Integer getBookIndex() {
    	return bookIndex;
    }
    public Integer getBookNo() {
        return bookNo;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public Integer getPages() {
        return pages;
    }

    public LocalDateTime getStartdate() {
        return startdate;
    }

    public Integer getUserNo() {
        return userNo;
    }
    
    public String getMemo() {
        return memo;
    }
    
    public String getDateTest() {
        return dateTest;
    }

    public void setDateTest(String dateTest) {
        this.dateTest = dateTest;
    }

    @Override
    public String toString() {
        return String.format("bookTable(Index=%d, No=%d, title=%s, author=%s,"
                + " category=%s, pages=%d, date=%s, userNo=%d, memo=%s"
                + "status = %s, star = %d) ",
                bookIndex,bookNo,title, author,category,pages,startdate, userNo, memo, status, star);
    }
    

}
