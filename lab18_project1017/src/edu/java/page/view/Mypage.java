package edu.java.page.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.java.login.controller.LoginDaoImpl;
import edu.java.login.model.UserTable;
import edu.java.page.controller.PageDaoImpl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Color;

public class Mypage extends JFrame {    
    private static LoginDaoImpl instance = null;
    

    
    
    
    public interface OnMypageListener {
        void onMypageNotify(UserTable userU);
    }
    
    private OnMypageListener listener;
    private PageDaoImpl pdao;
    
    private Component parent;
    private UserTable user;
    private LoginDaoImpl ldao;
    private static String[] AGEMODEL =  {"비공개", "10", "20", "30", "40", "50", "60"};
    private static String[] GENDERMODEL = {"비공개","여성", "남성"};
    

    private JPanel contentPane;
    private JTextField textEmail;
    private JTextField textID;
    private JTextField textNick;
    private JComboBox genderBox;
    private JComboBox ageBox;
    private JPasswordField textPw;
    private JLabel lblNbook;
    private JLabel lblNnote;
    private JLabel lblNread;
    private JLabel lblUserNo;
    private JLabel lblRank;
    private JLabel lblA2;
    private JLabel lblA1;
    private JLabel lblM2;
    private JLabel lblM1;

    /**
     * Launch the application.
     */
    public static void newMypage(Component parent , UserTable user, OnMypageListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Mypage frame = new Mypage(parent, user, listener);
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
    public Mypage(Component parent , UserTable user,OnMypageListener listener) {
        this.listener = listener;
        this.parent = parent;
        this.user = user;
        ldao = LoginDaoImpl.getInstance();
        pdao = PageDaoImpl.getInstance();
        initialize();
        userDataInfo();
    }
    
    public void userDataInfo() {
        textEmail.setText(user.getEmail());
        textID.setText(user.getId());
        textNick.setText(user.getNick());

        genderBox.setSelectedItem(user.getGender());
        ageBox.setSelectedItem(user.getAge());
        
        booksNumber();
    }
    
    public void booksNumber() {
        int bookN  = pdao.bookN(user.getUserNo());
        int noteN  = pdao.noteN(user.getUserNo());
        int readN  = pdao.readN(user.getUserNo());
        int userNO = pdao.userNo();
        int userRank = pdao.userRank(user.getUserNo());

        lblNbook.setText(bookN+"");
        lblNnote.setText(noteN+"");
        lblNread.setText(readN+"");
        lblUserNo.setText(userNO+"");
        if(userRank==0) {
            lblRank.setText("꼴");
            lblM1.setVisible(true);
            lblM2.setVisible(true);
            
        }else if(userRank <=3) {
            lblRank.setText(userRank+"");
            lblA1.setVisible(true);
            lblA2.setVisible(true);
        }
        else {
            lblRank.setText(userRank+"");
            lblM1.setVisible(true);
            lblM2.setVisible(true);
        }
        
        
    }
    
    
    
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        int w = parent.getWidth();
        int h = parent.getHeight();
                
        setTitle(user.getId()+" 님의 마이페이지");
        
        setBounds(x+(w-450)/2, y+(h-700)/2, 450, 700);
        contentPane = new JPanel();
        contentPane.setFont(new Font("굴림", Font.BOLD, 18));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel label = new JLabel("이메일");
        label.setFont(new Font("굴림", Font.BOLD, 18));
        label.setBounds(50, 10, 66, 34);
        contentPane.add(label);
        
        JLabel laID = new JLabel("아이디");
        laID.setFont(new Font("굴림", Font.BOLD, 18));
        laID.setBounds(50, 54, 66, 34);
        contentPane.add(laID);
        
        JLabel lblNick = new JLabel("닉네임");
        lblNick.setFont(new Font("굴림", Font.BOLD, 18));
        lblNick.setBounds(50, 184, 66, 34);
        contentPane.add(lblNick);
        
        JLabel lblGender = new JLabel("성별");
        lblGender.setFont(new Font("굴림", Font.BOLD, 18));
        lblGender.setBounds(50, 226, 66, 34);
        contentPane.add(lblGender);
        
        JLabel lblAge = new JLabel("나이");
        lblAge.setFont(new Font("굴림", Font.BOLD, 18));
        lblAge.setBounds(50, 271, 66, 34);
        contentPane.add(lblAge);
        
        JLabel lblBook = new JLabel("지금까지");
        lblBook.setFont(new Font("굴림", Font.BOLD, 18));
        lblBook.setBounds(46, 369, 105, 34);
        contentPane.add(lblBook);
        
        JButton btnNewButton = new JButton("회원정보 수정");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnMypageUpdate();
            }
        });
        btnNewButton.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton.setBounds(121, 315, 196, 34);
        contentPane.add(btnNewButton);
        
        JLabel lblR1 = new JLabel("읽고 계신 책은 ");
        lblR1.setFont(new Font("굴림", Font.BOLD, 18));
        lblR1.setBounds(12, 403, 137, 34);
        contentPane.add(lblR1);
        
        JLabel lblR2 = new JLabel("권 이고, ");
        lblR2.setFont(new Font("굴림", Font.BOLD, 18));
        lblR2.setBounds(180, 403, 137, 34);
        contentPane.add(lblR2);
        
        JLabel lblNote2 = new JLabel("개의 노트를 적으셨습니다!");
        lblNote2.setFont(new Font("굴림", Font.BOLD, 18));
        lblNote2.setBounds(96, 432, 242, 34);
        contentPane.add(lblNote2);
        
        JLabel lbl1 = new JLabel("현재 노트기록은 ");
        lbl1.setFont(new Font("굴림", Font.BOLD, 18));
        lbl1.setBounds(60, 486, 211, 34);
        contentPane.add(lbl1);
        
        JLabel lbl2 = new JLabel("명 중");
        lbl2.setFont(new Font("굴림", Font.BOLD, 18));
        lbl2.setBounds(154, 518, 48, 34);
        contentPane.add(lbl2);
        
        JLabel lbl3 = new JLabel("등 입니다!");
        lbl3.setFont(new Font("굴림", Font.BOLD, 18));
        lbl3.setBounds(244, 518, 154, 34);
        contentPane.add(lbl3);
        
        JLabel lblBook2 = new JLabel("권의 책을 등록하셨습니다!");
        lblBook2.setFont(new Font("굴림", Font.BOLD, 18));
        lblBook2.setBounds(163, 369, 259, 34);
        contentPane.add(lblBook2);
        
        JButton btnNewButton_1 = new JButton("비밀번호 변경");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 왜 여기서 this를 하면 안될까?
                String pw = textPw.getText();
                if(pw.equals(user.getPw())) {
                    btnPassword();
                } 
                else {
                    JOptionPane.showMessageDialog(Mypage.this, "현재 비밀번호가 틀렸습니다.  \n  다시 입력하세요!");
                }
                
            }
        });
        btnNewButton_1.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_1.setBounds(121, 140, 196, 34);
        contentPane.add(btnNewButton_1);
        
        textEmail = new JTextField();
        textEmail.setFont(new Font("굴림", Font.BOLD, 14));
        textEmail.setRequestFocusEnabled(false);
        textEmail.setBounds(142, 10, 219, 29);
        contentPane.add(textEmail);
        textEmail.setColumns(10);
        
        textID = new JTextField();
        textID.setFont(new Font("굴림", Font.BOLD, 14));
        textID.setRequestFocusEnabled(false);
        textID.setColumns(10);
        textID.setBounds(142, 55, 219, 29);
        contentPane.add(textID);
        
        textNick = new JTextField();
        textNick.setFont(new Font("굴림", Font.BOLD, 14));
        textNick.setColumns(10);
        textNick.setBounds(138, 188, 223, 29);
        contentPane.add(textNick);
        
        genderBox = new JComboBox();
        genderBox.setModel(new DefaultComboBoxModel(GENDERMODEL));
        genderBox.setFont(new Font("굴림", Font.BOLD, 15));
        genderBox.setBounds(172, 229, 125, 30);
        contentPane.add(genderBox);
        
        ageBox = new JComboBox();
        ageBox.setFont(new Font("굴림", Font.BOLD, 15));
        ageBox.setModel(new DefaultComboBoxModel(AGEMODEL));
        ageBox.setBounds(172, 274, 125, 30);
        contentPane.add(ageBox);
        
        JLabel lblPw = new JLabel("비밀번호");
        lblPw.setFont(new Font("굴림", Font.BOLD, 18));
        lblPw.setBounds(38, 90, 96, 34);
        contentPane.add(lblPw);
        
        textPw = new JPasswordField();
        textPw.setFont(new Font("굴림", Font.BOLD, 22));
        textPw.setBounds(142, 94, 219, 23);
        contentPane.add(textPw);
        
        lblNbook = new JLabel();
        lblNbook.setForeground(new Color(0, 0, 128));
        lblNbook.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNbook.setFont(new Font("굴림", Font.BOLD, 18));
        lblNbook.setBounds(121, 369, 41, 34);
        contentPane.add(lblNbook);
        
        lblNnote = new JLabel();
        lblNnote.setForeground(new Color(0, 0, 128));
        lblNnote.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNnote.setFont(new Font("굴림", Font.BOLD, 18));
        lblNnote.setBounds(26, 432, 66, 34);
        contentPane.add(lblNnote);
        
        lblNread = new JLabel();
        lblNread.setForeground(new Color(0, 0, 128));
        lblNread.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNread.setFont(new Font("굴림", Font.BOLD, 18));
        lblNread.setBounds(124, 403, 54, 34);
        contentPane.add(lblNread);
        
        lblUserNo = new JLabel("");
        lblUserNo.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUserNo.setFont(new Font("굴림", Font.BOLD, 18));
        lblUserNo.setBounds(95, 520, 54, 26);
        contentPane.add(lblUserNo);
        
        lblRank = new JLabel("0");
        lblRank.setForeground(new Color(220, 20, 60));
        lblRank.setHorizontalAlignment(SwingConstants.RIGHT);
        lblRank.setFont(new Font("굴림", Font.BOLD, 18));
        lblRank.setBounds(188, 522, 54, 26);
        contentPane.add(lblRank);
        
        JLabel lblNewLabel = new JLabel("전체 회원 ");
        lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel.setBounds(12, 520, 105, 30);
        contentPane.add(lblNewLabel);
        
        lblM1 = new JLabel("더 많은 독서와 하루 한줄, 노트를 통해");
        lblM1.setVisible(false);
        lblM1.setFont(new Font("굴림", Font.BOLD, 18));
        lblM1.setBounds(12, 573, 349, 25);
        contentPane.add(lblM1);
        
        lblM2 = new JLabel("상위권에 들어보세요~!");
        lblM2.setVisible(false);
        lblM2.setFont(new Font("굴림", Font.BOLD, 18));
        lblM2.setBounds(211, 608, 240, 25);
        contentPane.add(lblM2);
        
        lblA1 = new JLabel("상위권에 드신걸 축하합니다~!");
        lblA1.setVisible(false);
        lblA1.setFont(new Font("굴림", Font.BOLD, 18));
        lblA1.setBounds(60, 585, 268, 25);
        contentPane.add(lblA1);
        
        lblA2 = new JLabel("즐거운 독서 시간 되시기 바랍니다 :)");
        lblA2.setVisible(false);
        lblA2.setFont(new Font("굴림", Font.BOLD, 18));
        lblA2.setBounds(38, 626, 323, 25);
        contentPane.add(lblA2);
    }

    protected void btnPassword() {
        PasswordUpdate.newPasswordUpdate(this, user);

    }

    protected void btnMypageUpdate() {
        
        // TODO Auto-generated method stub
        String nick = textNick.getText();
        String gender = GENDERMODEL[genderBox.getSelectedIndex()];
        String age = AGEMODEL[ageBox.getSelectedIndex()];
        
        UserTable userU = new UserTable(user.getUserNo(), user.getId(),user.getPw(),user.getEmail(), nick, user.getCreateddate(), age, gender);
                
        
        int c = JOptionPane.showConfirmDialog(this, user.getId()+"님의 \n 회원정보를 수정하시겠습니까?", 
                "회원정보 수정",JOptionPane.YES_NO_OPTION);
        if(c==JOptionPane.YES_OPTION) {
            int result =ldao.mypageUpdate(userU);
            if(result==1) {
                dispose();
                listener.onMypageNotify(userU);
                JOptionPane.showMessageDialog(parent, user.getId()+"님의 \n 정보가 수정되었습니다. ");
            }
        }
        
        
        
    }
}
