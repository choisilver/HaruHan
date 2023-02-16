package edu.java.login.view;

//import static edu.java.login.model.UserTable.Entity.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import edu.java.login.controller.LoginDaoImpl;
import edu.java.login.model.UserTable;
import edu.java.page.view.UserPage;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;

public class LoginMain2 {
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

    /**
     * Launch the application.
     */
    public static void logout() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginMain2 window = new LoginMain2();
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
    public LoginMain2() {
    	lDao = LoginDaoImpl.getInstance();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(600, 100, 600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setTitle("하루에 한줄씩,  책을 읽고 기록 하기 : )");
        
        JLabel lblNewLabel = new JLabel("아이디");
        lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel.setBounds(93, 34, 102, 41);
        frame.getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("비밀번호");
        lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1.setBounds(93, 113, 102, 41);
        frame.getContentPane().add(lblNewLabel_1);
        
        textId = new JTextField();
        textId.setFont(new Font("굴림", Font.BOLD, 18));
        textId.setBounds(207, 34, 228, 41);
        frame.getContentPane().add(textId);
        textId.setColumns(10);
        
        btnLogin = new JButton("로그인");
        btnLogin.setFont(new Font("굴림", Font.BOLD, 18));
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 로그인을 해서 아이디 비번이 맞으면 개인 창이 떠야 해 
                // confirm 메서드를 통해서 확인하고 그 user 객체
                confirmLogin();
                
            }
        });
        btnLogin.setBounds(448, 60, 122, 55);
        frame.getContentPane().add(btnLogin);
        
        btnNewButton = new JButton("회원가입");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertUser();
            }
        });
        btnNewButton.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton.setBounds(110, 205, 132, 35);
        frame.getContentPane().add(btnNewButton);
        
        btnNewButton_1 = new JButton("아이디/비밀번호 찾기");
        btnNewButton_1.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_1.setBounds(264, 205, 256, 35);
        frame.getContentPane().add(btnNewButton_1);
        
        textPw = new JPasswordField();
        textPw.setFont(new Font("굴림", Font.BOLD, 18));
        textPw.setBounds(207, 113, 228, 41);
        frame.getContentPane().add(textPw);
        
        lblLoginId = new JLabel("아이디를 입력하시오.");
        lblLoginId.setVisible(false);
        lblLoginId.setEnabled(false);
        lblLoginId.setFont(new Font("굴림", Font.ITALIC, 15));
        lblLoginId.setBounds(207, 76, 195, 24);
        frame.getContentPane().add(lblLoginId);
        
        lblLoginPw = new JLabel("비밀번호를 입력하시오.");
        lblLoginPw.setEnabled(false);
        lblLoginPw.setVisible(false);
        lblLoginPw.setFont(new Font("굴림", Font.ITALIC, 15));
        lblLoginPw.setBounds(207, 152, 195, 24);
        frame.getContentPane().add(lblLoginPw);
        
        lblWrong = new JLabel( "아이디와 비밀번호가 틀렸습니다. 다시 입력하세요.");
        lblWrong.setVisible(false);
        lblWrong.setForeground(new Color(255, 0, 0));
        lblWrong.setFont(new Font("굴림", Font.ITALIC, 18));
        lblWrong.setBounds(81, 164, 447, 31);

        frame.getContentPane().add(lblWrong);
    }

    protected void insertUser() {
        // 창 만들어서 회원 가입 하자..ㅎ
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
        System.out.println(user);
//        lblLoginId.setVisible(false);
//        lblLoginPw.setVisible(false);
//        lblWrong.setVisible(false);
        
        if(user == null) {
        	lblWrong.setVisible(true);
            lblWrong.setBounds(80, 164, 447, 30);
//            JOptionPane.showMessageDialog(frame, "아이디와 비밀번호가 틀렸습니다. \n 다시 확인 하세요.");
        } else {
            System.out.println(user);
            UserPage.newUserPage(this.frame, user);
            frame.dispose();
        }
        
    }
}
