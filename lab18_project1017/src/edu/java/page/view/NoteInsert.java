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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;




public class NoteInsert extends JFrame {
    public interface onNoteInsertListener{
            void onNoteInsertNotify(NoteTable note );
    }

    private JPanel contentPane;
    
    
    private PageDaoImpl pdao;
    private BookTable book;
    private Component parent;
    private onNoteInsertListener listener;
    private JTextField textStPage;
    private JButton btnNewButton;
    private JTextField textEndPage;
    private JTextArea textNote;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;
    private JTextField textDate;
    private JLabel lblNewLabel_3;
    private JLabel lblNewLabel;
    private JLabel lblIcon;
    /**
     * Launch the application.
     */
    public static void newNoteInsert(Component parent ,onNoteInsertListener listener ,BookTable book) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NoteInsert frame = new NoteInsert(parent, listener, book);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public NoteInsert(Component parent, onNoteInsertListener listener, BookTable book) {
        this.parent = parent;
        this.listener = listener;
        this.book= book;
        pdao= PageDaoImpl.getInstance();
        initialize();
        
    }
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        int w = parent.getWidth();
        int h = parent.getHeight();
        
        setBounds(x+(w-494)/2 ,y+(h-500)/2, 494, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setTitle(book.getTitle()+", 하루 한줄 독서 노트 기록 :) ");

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(32, 189, 409, 235);
        contentPane.add(scrollPane);
        
        textNote = new JTextArea();
        textNote.setFont(new Font("굴림", Font.BOLD, 15));
        textNote.setLineWrap(true);
        scrollPane.setViewportView(textNote);
        
        textStPage = new JTextField();
        textStPage.setBounds(32, 70, 70, 27);
        contentPane.add(textStPage);
        textStPage.setColumns(10);
        
        btnNewButton = new JButton("노트 저장 ");
        btnNewButton.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNoteInsert();
                //TODO
            }
        });
        btnNewButton.setBounds(292, 113, 149, 44);
        contentPane.add(btnNewButton);
        
        textEndPage = new JTextField();
        textEndPage.setColumns(10);
        textEndPage.setBounds(119, 110, 70, 27);
        contentPane.add(textEndPage);
        
        lblNewLabel_1 = new JLabel("부터");
        lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1.setBounds(109, 70, 59, 30);
        contentPane.add(lblNewLabel_1);
        
        lblNewLabel_2 = new JLabel("까지");
        lblNewLabel_2.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_2.setBounds(201, 107, 70, 30);
        contentPane.add(lblNewLabel_2);
        
        textDate = new JTextField();
        textDate.setFont(new Font("굴림", Font.BOLD, 15));
        textDate.setColumns(10);
        textDate.setBounds(78, 20, 108, 27);
        contentPane.add(textDate);
        
        lblNewLabel_3 = new JLabel("오늘");
        lblNewLabel_3.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_3.setBounds(32, 10, 49, 44);
        contentPane.add(lblNewLabel_3);
        textDate.setText(LocalDate.now().toString());
        
        lblNewLabel = new JLabel("읽었습니다~!");
        lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel.setBounds(32, 150, 174, 29);
        contentPane.add(lblNewLabel);
        
        lblIcon = new JLabel(changeImageSizw("image/books.jpg"));
        lblIcon.setBounds(325, 20, 116, 71);
        contentPane.add(lblIcon);
    }

    protected void btnNoteInsert() {
        // 페이지 값은 굳이 안적어도 되는뎅,, TODO
        String notein = textNote.getText();
        Integer stPage = null;
        Integer endPage = null;
        
        if(textStPage.getText().equals("")|| textEndPage.getText().equals("") || notein.equals("")) {
            JOptionPane.showMessageDialog(this, "시작과 끝 페이지, 노트 내용을 적어 주세요.");
        }
        else {
            try {
                stPage = Integer.parseInt( textStPage.getText());
                endPage = Integer.parseInt( textEndPage.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "시작과 끝 페이지엔 정수만 입력해주세요.");
                
            }
        
        // note no구해야함
        int noteNo = pdao.findNoteNo(book.getBookIndex());
        
        NoteTable note = new NoteTable(noteNo, notein, stPage, endPage, null, book.getBookIndex());
        
        pdao.noteInsert(note);
        JOptionPane.showMessageDialog(this, "한 줄 노트가 작성 되었습니다.");
        dispose();
        // note를 모아보기 해도 좋을 것 같은데 그러려면 폰트나,, 엔터키나,, 밑으로만 스크롤이 되게 해야 할듯
        listener.onNoteInsertNotify(note);
        
        }
    }

    
    public ImageIcon changeImageSizw(String imgName) {
        ImageIcon icon = new ImageIcon(imgName);
        Image img = icon.getImage();
        Image changeImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon changeIcon = new ImageIcon(changeImg);
        return changeIcon;
        
    }
    
    
    
}
