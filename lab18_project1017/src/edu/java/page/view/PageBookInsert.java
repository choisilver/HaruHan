package edu.java.page.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.java.login.model.UserTable;
import edu.java.page.controller.PageDaoImpl;
import edu.java.page.model.BookTable;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class PageBookInsert extends JFrame {
    
    public interface onPageBookInsertListener{
        void onBookInsertNotify();
    }
    
    private String[] categories = {"소설", "시/에세이", "인문", "취미", "경제/경영", "자기계발", "역사", "과학"};
    private String[] statuses =  {"읽는 중", "다 읽었어요!", "읽고 싶어요", "멈췄어요"};
    private String[] star = {"♡","♥", "♥♥", "♥♥♥", "♥♥♥♥", "♥♥♥♥♥"};
    
    private JPanel contentPane;
    private JTextField textAuthor;
    private JTextField textTitle;
    private JTextField textPage;
    
    private JTextArea textMemo;
    
    private PageDaoImpl pdao;
    private Component parent;
    private UserTable user;
    private onPageBookInsertListener listener;
    private JComboBox cateBox;
    private JComboBox starBox;
    private JComboBox statusBox;
    
    /**
     * Launch the application.
     */
    public static void newBookInsert(Component parent,onPageBookInsertListener listener ,UserTable user) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PageBookInsert frame = new PageBookInsert(parent, listener, user);
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
    public PageBookInsert(Component parent, onPageBookInsertListener listener ,UserTable user) {
        this.listener = listener;
        this.parent = parent;
        this.user = user;
        pdao = PageDaoImpl.getInstance();
        initialize();
    }
    
    
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        int w = parent.getWidth();
        int h = parent.getHeight();
        
        setBounds(x+(w-554)/2,y+(h-487)/2, 554, 487);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setTitle("서재에 책 등록하기 ^_^");

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("제목");
        lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel.setBounds(12, 10, 51, 31);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("작가");
        lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1.setBounds(12, 51, 51, 31);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_1_1 = new JLabel("카테고리");
        lblNewLabel_1_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1_1.setBounds(12, 92, 87, 31);
        contentPane.add(lblNewLabel_1_1);
        
        JLabel lblNewLabel_1_1_1 = new JLabel("페이지");
        lblNewLabel_1_1_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1_1_1.setBounds(12, 209, 73, 31);
        contentPane.add(lblNewLabel_1_1_1);
        
        JLabel lblNewLabel_1_1_1_1 = new JLabel("메모");
        lblNewLabel_1_1_1_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1_1_1_1.setBounds(12, 320, 73, 31);
        contentPane.add(lblNewLabel_1_1_1_1);
        
        textAuthor = new JTextField();
        textAuthor.setFont(new Font("굴림", Font.BOLD, 18));
        textAuthor.setColumns(10);
        textAuthor.setBounds(126, 51, 380, 31);
        contentPane.add(textAuthor);
        
        textTitle = new JTextField();
        textTitle.setFont(new Font("굴림", Font.BOLD, 18));
        textTitle.setColumns(10);
        textTitle.setBounds(126, 10, 380, 31);
        contentPane.add(textTitle);
        
        textPage = new JTextField();
        textPage.setFont(new Font("굴림", Font.BOLD, 18));
        textPage.setColumns(10);
        textPage.setBounds(126, 209, 380, 31);
        contentPane.add(textPage);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(126, 250, 380, 107);
        contentPane.add(scrollPane);
        
        textMemo = new JTextArea();
        textMemo.setLineWrap(true);
        textMemo.setFont(new Font("Monospaced", Font.BOLD, 18));
        scrollPane.setViewportView(textMemo);
        
        JButton btnNewButton = new JButton("책 등록");
        btnNewButton.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBookInsert();
            }
        });
        btnNewButton.setBounds(180, 385, 173, 40);
        contentPane.add(btnNewButton);
        
        cateBox = new JComboBox();
        cateBox.setFont(new Font("굴림", Font.BOLD, 18));
        cateBox.setModel(new DefaultComboBoxModel(categories));
        cateBox.setBounds(126, 92, 184, 31);
        contentPane.add(cateBox);
        
        JLabel lblNewLabel_1_1_2 = new JLabel("상태");
        lblNewLabel_1_1_2.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1_1_2.setBounds(12, 133, 87, 31);
        contentPane.add(lblNewLabel_1_1_2);
        
        statusBox = new JComboBox();
        statusBox.setFont(new Font("굴림", Font.BOLD, 18));
        statusBox.setModel(new DefaultComboBoxModel(statuses));
        statusBox.setBounds(126, 133, 184, 31);
        contentPane.add(statusBox);
        
        JLabel lblNewLabel_1_1_2_1 = new JLabel("별점");
        lblNewLabel_1_1_2_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1_1_2_1.setBounds(12, 168, 87, 31);
        contentPane.add(lblNewLabel_1_1_2_1);
        
        starBox = new JComboBox();
        starBox.setModel(new DefaultComboBoxModel(new String[] {"♡","♥", "♥♥", "♥♥♥", "♥♥♥♥", "♥♥♥♥♥"}));
        starBox.setFont(new Font("굴림", Font.BOLD, 18));
        starBox.setBounds(126, 168, 184, 31);
        contentPane.add(starBox);
    }

    private void btnBookInsert() {
        // pages는 숫자값 인티저만 받아야 함. userno는 안받아도 되겠지? 둘이 연결할 interface 만들면?? 
        // user객체를 받았음! user.getNo 해서 번호 받음됨
    	int cateIndex =  cateBox.getSelectedIndex();
    	int statusIndex = statusBox.getSelectedIndex();
    	int starIndex = starBox.getSelectedIndex();
    	
    	int bookno = pdao.findBookNo(user.getUserNo());//
    	System.out.println(bookno+"= insert");
    	bookno+=1;
    	System.out.println(bookno+"= insert+1");
    	
    	
        String title = textTitle.getText();
        String author = textAuthor.getText();
        String memo = textMemo.getText();
        String category = categories[cateIndex];
        String status = statuses[statusIndex];
        
        
        
        Integer pages = 0;
        if(title.equals("")||author.equals("")) {
            JOptionPane.showMessageDialog(this, "책 제목과 작가는 반드시 입력하세요!");
            return;
        }
        
        
        try {
            pages = Integer.parseInt( textPage.getText());
        } catch (NumberFormatException e) { 
            JOptionPane.showMessageDialog(this, "페이지 칸엔 정수만 입력 하시오.");
            //빈칸으로 둘땐? 아예 0으로 두면 안되나?
            //TODO
            return;
            
        }
        
        BookTable book = new BookTable(null,bookno , title, author, category, pages, null, user.getUserNo(), memo
        		, status, starIndex);
        
        System.out.println(book);
        int result = pdao.bookInsert(book);
        
        if(result == 1) {
            JOptionPane.showMessageDialog(this, "책을 등록 하셨습니다! \n 즐거운 독서 시간 되세요~!");
            dispose();
            listener.onBookInsertNotify();
            
        }
        
        
        
    }
}
