package edu.java.page.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.java.page.controller.PageDaoImpl;
import edu.java.page.model.NoteTable;
import edu.java.page.model.BookTable;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;

public class NoteUpdate extends JFrame {
    public interface onNoteUpdateListener{
        void onNoteUpdateNotify(NoteTable note);
    }

    private JPanel contentPane;
    
    private BookTable book;
 
    private PageDaoImpl pdao;
    private onNoteUpdateListener listener;
    private Component parent;
    private NoteTable note;
    private JTextField textStPage;
    private JTextField textEndPage;
    private JTextField textNoteDate;
    private JTextArea textNote;
    private JLabel lblNewLabel_2;
    /**
     * Launch the application.
     */
    public static void newNoteUpdate(Component parent, NoteTable note, BookTable book, onNoteUpdateListener listener ){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NoteUpdate frame = new NoteUpdate(parent, note,book, listener);
                    frame.setVisible(true);
                    System.out.println(note);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public NoteUpdate(Component parent, NoteTable note, BookTable book, onNoteUpdateListener listener) {
        this.parent = parent;
        this.note = note;
        this.book = book;
        this.listener =listener;
        
        pdao = PageDaoImpl.getInstance();
        initialize();
        initialInfo();
        
    }
    public void initialInfo() {
        textNote.setText(note.getNote());
        textStPage.setText(note.getStPage().toString());
        textEndPage.setText(note.getEndPage().toString());
        textNoteDate.setText(note.getDateNote().toString());
        
    }
    
    
    
    
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        int w = parent.getWidth();
        int h = parent.getHeight();
        
        
        setBounds(x+(w-600)/2, y+(h-500)/2, 600, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setTitle(book.getTitle()+ ", 하루에 한줄 기록 ");

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 174, 560, 277);
        contentPane.add(scrollPane);
        
        textNote = new JTextArea();
        textNote.setLineWrap(true);
        textNote.setFont(new Font("D2Coding", Font.BOLD, 18));
        scrollPane.setViewportView(textNote);
        
        JLabel lblNewLabel = new JLabel("여기서 부터");
        lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel.setBounds(31, 23, 123, 31);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("여기까지");
        lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1.setBounds(31, 52, 123, 31);
        contentPane.add(lblNewLabel_1);
        
        textStPage = new JTextField();
        textStPage.setFont(new Font("굴림", Font.BOLD, 18));
        textStPage.setBounds(166, 23, 130, 31);
        contentPane.add(textStPage);
        textStPage.setColumns(10);
        
        textEndPage = new JTextField();

        textEndPage.setFont(new Font("굴림", Font.BOLD, 18));
        textEndPage.setColumns(10);
        textEndPage.setBounds(166, 52, 130, 31);
        contentPane.add(textEndPage);
        
        JButton btnNewButton = new JButton("노트 수정하기");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNoteUpdate();
            }
        });
        btnNewButton.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton.setBounds(389, 23, 183, 47);
        contentPane.add(btnNewButton);
        
        JLabel lblNewLabel_1_1 = new JLabel("등록한 날짜 ");
        lblNewLabel_1_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1_1.setBounds(31, 112, 123, 31);
        contentPane.add(lblNewLabel_1_1);
        
        textNoteDate = new JTextField();
        textNoteDate.setRequestFocusEnabled(false);
        textNoteDate.setFont(new Font("굴림", Font.BOLD, 18));
        textNoteDate.setColumns(10);
        textNoteDate.setBounds(166, 112, 211, 31);
        contentPane.add(textNoteDate);
        
        lblNewLabel_2 = new JLabel("읽었슴당 ~!");
        lblNewLabel_2.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_2.setBounds(31, 81, 123, 31);
        contentPane.add(lblNewLabel_2);
        

        
    }
    
    private void btnNoteUpdate() {
        // 줄 선택이 먼저.. 
        // 적혀 있는 값을 읽고, pdoa.updateNote(note)
        System.out.println(book.getBookNo());
        System.out.println(book.getBookIndex());
        System.out.println(book.getUserNo());
        System.out.println(note);
    
        
        String noteText = textNote.getText();
        Integer stPage = Integer.parseInt(textStPage.getText());
        Integer endPage = Integer.parseInt(textEndPage.getText());
        NoteTable noteUP = new NoteTable(note.getNoteNo(), noteText, stPage, endPage, null, note.getBookIndex());
        
        pdao.noteUpdate(noteUP, note.getNoteNo(), note.getBookIndex());
        System.out.println(noteUP);
        dispose();
        listener.onNoteUpdateNotify(noteUP);
        
        
    }


}
