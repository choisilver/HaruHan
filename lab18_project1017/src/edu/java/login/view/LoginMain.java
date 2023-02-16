package edu.java.login.view;

//import static edu.java.login.model.UserTable.Entity.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;

import edu.java.login.controller.LoginDaoImpl;
import edu.java.login.model.UserTable;
import edu.java.page.view.UserPage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;

public class LoginMain {
    int n = 80;

    private JFrame frame;
    private JTextField textId;
    private JButton btnLogin;
    private JButton btnNewButton;
    private JButton btnNewButton_1;
    
    private LoginDaoImpl lDao;
    private JPasswordField textPw;
    private JLabel lblLoginId;
    private JLabel lblLoginPw;
    private JLabel lblWrong;
    private JLabel lblIcon;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginMain window = new LoginMain();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public LoginMain() {
    	lDao = LoginDaoImpl.getInstance();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 646, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("하루에 한줄씩,  책을 읽고 기록 하기 : )");
        
        JLabel lblNewLabel = new JLabel("아이디");
        lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel.setBounds(121, 31, 102, 41);
        frame.getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("비밀번호");
        lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1.setBounds(121, 110, 102, 41);
        frame.getContentPane().add(lblNewLabel_1);
        
        textId = new JTextField();
        textId.setFont(new Font("굴림", Font.BOLD, 18));
        textId.setBounds(235, 31, 228, 41);
        frame.getContentPane().add(textId);
        textId.setColumns(10);
        
        btnLogin = new JButton("로그인");
        btnLogin.setFont(new Font("굴림", Font.BOLD, 18));
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmLogin();
                
            }
        });
        btnLogin.setBounds(476, 57, 122, 55);
        frame.getContentPane().add(btnLogin);
        
        btnNewButton = new JButton("회원가입");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertUser();
            }
        });
        btnNewButton.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton.setBounds(138, 202, 132, 35);
        frame.getContentPane().add(btnNewButton);
        
        btnNewButton_1 = new JButton("아이디/비밀번호 찾기");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSearchIdPass();
            }
        });
        btnNewButton_1.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_1.setBounds(292, 202, 256, 35);
        frame.getContentPane().add(btnNewButton_1);
        
        textPw = new JPasswordField();
        textPw.setFont(new Font("굴림", Font.BOLD, 18));
        textPw.setBounds(235, 110, 228, 41);
        frame.getContentPane().add(textPw);
        
        lblLoginId = new JLabel("아이디를 입력하시오.");
        lblLoginId.setVisible(false);
        lblLoginId.setEnabled(false);
        lblLoginId.setFont(new Font("굴림", Font.ITALIC, 15));
        lblLoginId.setBounds(235, 73, 195, 24);
        frame.getContentPane().add(lblLoginId);
        
        lblLoginPw = new JLabel("비밀번호를 입력하시오.");
        lblLoginPw.setEnabled(false);
        lblLoginPw.setVisible(false);
        lblLoginPw.setFont(new Font("굴림", Font.ITALIC, 15));
        lblLoginPw.setBounds(235, 149, 195, 24);
        frame.getContentPane().add(lblLoginPw);
        
        lblWrong = new JLabel( "아이디와 비밀번호가 틀렸습니다. 다시 입력하세요.");
        lblWrong.setVisible(false);
        lblWrong.setForeground(new Color(255, 0, 0));
        lblWrong.setFont(new Font("굴림", Font.ITALIC, 18));
        lblWrong.setBounds(109, 161, 447, 31);

        frame.getContentPane().add(lblWrong);
        
        lblIcon = new JLabel(changeImageSizw("image/books.jpg") );
        lblIcon.setBounds(12, 41, 97, 110);
        frame.getContentPane().add(lblIcon);
    }

    protected void btnSearchIdPass() {
        SearchMain.newSearchMain(frame);
    }

    protected void insertUser() {
        InsertUserMain.newInsertUser(frame);
    }

    protected void confirmLogin() {
        String id = textId.getText();
        String pw = textPw.getText();
        
        if(id.equals("") ) {
            lblLoginId.setVisible(true);
            return;
        }
        if(pw.equals("")) {
            lblLoginPw.setVisible(true);
        	return;
        }
        
        UserTable user = lDao.confirm(id, pw);
        lblLoginId.setVisible(false);
        lblLoginPw.setVisible(false);
        lblWrong.setVisible(false);
        if(user == null) {
        	lblWrong.setVisible(true);
            lblWrong.setBounds(n+=1, 164, 447, 30);
            System.out.println(n);
        } else {
            System.out.println(user);
            UserPage.newUserPage(this.frame, user);
            frame.dispose();
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
