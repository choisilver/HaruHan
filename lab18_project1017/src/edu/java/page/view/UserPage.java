package edu.java.page.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.java.login.model.UserTable;
import edu.java.login.view.LoginMain;
import edu.java.login.view.LoginMain2;
import edu.java.page.controller.PageDaoImpl;
import edu.java.page.model.BookTable;
import edu.java.page.view.Mypage.OnMypageListener;
import edu.java.page.view.PageBookInsert.onPageBookInsertListener;
import edu.java.page.view.PageBookNote.onPageNoteBookListener;
import edu.java.page.view.PageBookNote.onPageNoteBookListener;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import static edu.java.page.model.BookTable.Entity.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;


public class UserPage extends JFrame implements onPageBookInsertListener, onPageNoteBookListener, OnMypageListener   {
    
    private static final String[] COLUMN_NAMES = {
            COL_BOOK_NO, COL_BOOK_TITLE, COL_AUTHOR,"상태","☆ 별점 ☆" ,COL_CATEGORY, COL_BOOK_PAGE, "날짜"
    }; // TODO 유저번호는 나중에 지우기
    
    private String[] listOrder ={"최근 등록 ","예전 등록 ", "★★★★★", "★"};
    private String[] listStatus =   {"읽는 중", "다 읽었어요!", "읽고 싶어요", "멈췄어요"};
    
    private final String[] searchModel = {"제목", "작가", "메모", "전체검색"};

    private JPanel contentPane;
    private JTextField textNick;
    
    private UserTable user;
    private Component parent;
    private JTable table;
    private DefaultTableModel model;
    private PageDaoImpl pdao;
    private JComboBox boxSearch;
    private JTextArea textSearch;
    private JComboBox orderBox;
    
    private Integer orderIndex;
    private JComboBox orderStatusBox;
    private JLabel lblIcon;
    
    

    /**
     * 연결되는게 굳이 상속을 받아야 할까? 그냥 정보 주기만 하면,,!
     * Launch the application.
     */
    public static void newUserPage(Component parent, UserTable user) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserPage frame = new UserPage(parent, user);
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
    // 생성자 따로 만들기 
    // 여기도 테이블 만들어야 함. 
    
    public UserPage( Component parent ,UserTable user) {
        this.parent = parent;
        this.user = user;
        initialize();
        pdao = PageDaoImpl.getInstance();
        initializeTable(pdao.select(user,orderIndex));
        initialInfo();
    }
    
    
    
    public void initialInfo() {
        textNick.setText(user.getNick());
    }
    
    
    public void initializeTable(List<BookTable> list) {
        
        
        model = new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(model);
        table.setRowHeight(20);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setMaxWidth(300);
        table.getColumnModel().getColumn(1).setMinWidth(300);
        
        table.getColumnModel().getColumn(4).setMaxWidth(60);
        table.getColumnModel().getColumn(4).setMinWidth(60);
        
        
        System.out.println(list);
        for(BookTable p : list) {
            Object[] row = {
                    p.getBookNo(),
                    p.getTitle(),
                    p.getAuthor(),
                    p.getStatus(),
                    p.getStar(),
                    p.getCategory(),
                    p.getPages(), 
                    p.getDateTest()
                    
            };
            model.addRow(row);
        }

        
        
    }
    
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        setBounds(x,y, 1040, 700);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setTitle(user.getNick()+", 하루 한줄 독서 기록 :) ");
        
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("님의 서재");
        lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel.setBounds(298, 35, 150, 62);
        contentPane.add(lblNewLabel);
        
        textNick = new JTextField();
        textNick.setEditable(false);
        textNick.setHorizontalAlignment(SwingConstants.LEFT);
        textNick.setFont(new Font("굴림", Font.BOLD, 18));

        
        textNick.setBounds(101, 46, 184, 41);
        
        contentPane.add(textNick);
        textNick.setColumns(10);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(52, 212, 931, 325);
        contentPane.add(scrollPane);
//    TODO    
//        DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
//        celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
        table = new JTable();

        scrollPane.setViewportView(table);
        
        JButton btnNewButton = new JButton("책 등록");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnInsertBook();
                
            }
        });
        btnNewButton.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton.setBounds(410, 46, 123, 41);
        contentPane.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("독서 노트");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnBookNote();
                
            }
        });
        btnNewButton_1.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_1.setBounds(624, 46, 123, 41);
        contentPane.add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("휴지통");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDelete();
            }
        });
        btnNewButton_2.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_2.setBounds(823, 91, 123, 41);
        contentPane.add(btnNewButton_2);
        
        JButton btnNewButton_3 = new JButton("마이페이지");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // user랑 연결되는건데.. 딱히 연결할 필요는 없겠지?? 그냥 불러오기만 하면 될듯?
                btnMyPage();
            }
        });
        btnNewButton_3.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_3.setBounds(101, 97, 150, 41);
        contentPane.add(btnNewButton_3);
        
        JButton btnNewButton_4 = new JButton("검색");
        btnNewButton_4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		btnSearch();
        	}
        });
        btnNewButton_4.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_4.setBounds(810, 558, 123, 41);
        contentPane.add(btnNewButton_4);
        
        boxSearch = new JComboBox();
        boxSearch.setModel(new DefaultComboBoxModel(searchModel));
        boxSearch.setSelectedIndex(0);
        boxSearch.setToolTipText("제목, 작가, 카테고리");
        boxSearch.setFont(new Font("굴림", Font.BOLD, 18));
        boxSearch.setBounds(54, 558, 109, 41);
        contentPane.add(boxSearch);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(187, 558, 611, 41);
        contentPane.add(scrollPane_1);
        
        textSearch = new JTextArea();
        textSearch.setFont(new Font("Monospaced", Font.BOLD, 18));
        scrollPane_1.setViewportView(textSearch);
        
        JButton btnNewButton_1_1 = new JButton("전체 보기");
        btnNewButton_1_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		initializeTable(pdao.select(user, orderIndex));
        	}
        });
        btnNewButton_1_1.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_1_1.setBounds(823, 46, 123, 41);
        contentPane.add(btnNewButton_1_1);
        
        JButton btnNewButton_2_1 = new JButton("로그아웃");
        btnNewButton_2_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLogout();

                
                
            }
        });
        btnNewButton_2_1.setFont(new Font("굴림", Font.BOLD, 15));
        btnNewButton_2_1.setBounds(823, 142, 123, 41);
        contentPane.add(btnNewButton_2_1);
        
        orderBox = new JComboBox();
        orderBox.setModel(new DefaultComboBoxModel(listOrder));
        orderBox.setSelectedIndex(0);
        orderBox.setFont(new Font("D2Coding ligature", Font.BOLD, 18));
        orderBox.setBounds(101, 172, 184, 30);
        contentPane.add(orderBox);
        
        JButton btnNewButton_5 = new JButton("검색");
        btnNewButton_5.setFont(new Font("D2Coding", Font.BOLD, 15));
        btnNewButton_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnOrder();
            }
        });
        btnNewButton_5.setBounds(302, 172, 97, 30);
        contentPane.add(btnNewButton_5);
        
        orderStatusBox = new JComboBox();
        orderStatusBox.setModel(new DefaultComboBoxModel(listStatus));
        orderStatusBox.setSelectedIndex(0);
        orderStatusBox.setFont(new Font("D2Coding ligature", Font.BOLD, 18));
        orderStatusBox.setBounds(432, 172, 184, 30);
        contentPane.add(orderStatusBox);
        
        JButton btnNewButton_5_1 = new JButton("검색");
        btnNewButton_5_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnOrderStatus();
            }
        });
        btnNewButton_5_1.setFont(new Font("D2Coding", Font.BOLD, 15));
        btnNewButton_5_1.setBounds(650, 172, 97, 30);
        contentPane.add(btnNewButton_5_1);
        
        lblIcon = new JLabel(changeImageSizw("image/books.jpg"));
        lblIcon.setBounds(12, 10, 66, 41);
        contentPane.add(lblIcon);
        
        orderIndex = orderBox.getSelectedIndex();
        System.out.println(orderIndex);
    }

    protected void btnOrderStatus() {
        // TODO Auto-generated method stub
        int index = orderStatusBox.getSelectedIndex();
        System.out.println("stati = "+index);
        String status = listStatus[index];
        System.out.println("sta"+status);
        initializeTable(pdao.selectStatus(user, status));
        
    }

    protected void btnOrder() {
        System.out.println(orderIndex);
        orderIndex = orderBox.getSelectedIndex();

            System.out.println("선택 = "+orderIndex);
            initializeTable(pdao.select(user, orderIndex));
            
        
    }

    protected void btnMyPage() {
        Mypage.newMypage(this, user, this);
    }

    protected void btnLogout() {
        int a= JOptionPane.showConfirmDialog(this , user.getNick()+"님 \n 정말 로그아웃 하시겠습니까? ", " ㅠ_ㅠ", JOptionPane.YES_NO_OPTION);
        if(a == JOptionPane.YES_OPTION) {
            LoginMain2.logout();
            dispose();
        }
    }

    protected void btnSearch() {
    	String keyword = textSearch.getText();
    	int index = boxSearch.getSelectedIndex(); // 제목, 작가, 메모, 전체 검색 switch
    	List<BookTable> list = pdao.selectByComboBox(user, keyword,index);

    	initializeTable(list);
    	

    	
	}

	protected void btnBookNote() {
        int row = table.getSelectedRow();
        int result = 0 ;
        if(row == -1) {
            JOptionPane.showMessageDialog(this, "독서 노트를 작성하실 책을 선택해 주세요 ");
        }else {
            int bookNo = (Integer)model.getValueAt(row, 0);
            // 책 번호로 PageTable을 읽고, 그걸 입력값으로 주고, 다른 dao 문장을 이용해야 함! 
            // 출력은 pageTable, 입력은 bookno
            BookTable book = pdao.selectBook(bookNo, user.getUserNo());
            System.out.println(book);
            
            PageBookNote.newBookNote(this, book, UserPage.this);
            
            
        }
        
    }

    protected void btnDelete() {
        int row = table.getSelectedRow();
        int result = 0 ;
        
        if(row == -1) {
            JOptionPane.showMessageDialog(this, "삭제하실 행을 선택해 주세요 ");
        }else {
            int bookNo = (Integer)model.getValueAt(row, 0);
            
            int con = JOptionPane.showConfirmDialog(this, bookNo+"번째 책을 삭제 하시겠습니까? ","책 리스트 삭제" ,JOptionPane.YES_NO_OPTION );
            
            if(con==JOptionPane.YES_OPTION) {
                result =  pdao.deleteBook(bookNo, user.getUserNo());
                if(result ==1) {
                    JOptionPane.showMessageDialog(this, bookNo+"번째 책이 삭제 되었습니다...");
                    
                }
                
                initializeTable(pdao.select(user,orderIndex));
                
            }
        }
        
    }

    protected void btnInsertBook() {
        // 책 테이블에 등록하기 책 제목, 작가, 카테고리, 페이지 수 
        // 유저 넘버는 어떻게 받지??
        PageBookInsert.newBookInsert(UserPage.this, UserPage.this, user);
  
    }

    @Override
    public void onBookInsertNotify() {
        initializeTable(pdao.select(user, orderIndex));
        
    }

    @Override
    public void onBookPageNotify() {
        initializeTable(pdao.select(user,orderIndex));
        
        
    }

    @Override
    public void onMypageNotify(UserTable userU) {
        // TODO mypage에 있는  this.user를 userU로 바꿈
        this.user = userU;
        textNick.setText(userU.getNick());
        setTitle(user.getNick()+", 하루 한줄 독서 기록 :) ");
    }
    
    public ImageIcon changeImageSizw(String imgName) {
        ImageIcon icon = new ImageIcon(imgName);
        Image img = icon.getImage();
        Image changeImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon changeIcon = new ImageIcon(changeImg);
        return changeIcon;
        
        
    }
}
