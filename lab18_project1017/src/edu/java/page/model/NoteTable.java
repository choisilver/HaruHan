package edu.java.page.model;

import java.time.LocalDateTime;

public class NoteTable {
    public interface Entity {
        String TBL_NOTE = "NOTE_TABLE";
        // 6개 행에 대해서는 다 변수로 가져야지 다른거 할때 얻을 수 있음
        // sql 문장에 넣으려고 만듦...
        String COL_NOTE_NO = "NOTE_NO";
        String COL_NOTE = "NOTE";
        String COL_NOTE_ST_PAGE = "START_PAGE";
        String COL_NOTE_ENDPAGE = "END_PAGE";
        String COL_NOTE_DATE = "NOTE_DATE";
        String COL_NOTE_BOOK_INDEX= "BOOK_INDEX";
        
    }
    
    // 테이블 타입과 맞게 변수 선언
    private Integer noteNo;
    private String note;
    private Integer stPage;
    private Integer endPage;
    private LocalDateTime dateNote;
    private Integer bookIndex ;
    

    public NoteTable() {}


    public NoteTable(Integer noteNo, String note, Integer stPage, Integer endPage, LocalDateTime dateNote,
            Integer bookIndex ) {
        this.noteNo = noteNo;
        this.note = note;
        this.stPage = stPage;
        this.endPage = endPage;
        this.dateNote = dateNote;
        this.bookIndex  = bookIndex ;
    }


    public Integer getNoteNo() {
        return noteNo;
    }


    public String getNote() {
        return note;
    }


    public Integer getStPage() {
        return stPage;
    }


    public Integer getEndPage() {
        return endPage;
    }


    public LocalDateTime getDateNote() {
        return dateNote;
    }


    public Integer getBookIndex() {
        return bookIndex ;
    }
    
    @Override
    public String toString() {
        return String.format("Note(No=%d, note=%s, stpage=%d, endpage=%d, date=%s, bookIndex  =%d",
                noteNo, note, stPage, endPage, dateNote, bookIndex );
    }
    
    
    
    
    
}
