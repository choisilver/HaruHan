package edu.java.page.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.java.login.controller.LoginDaoImpl;
import edu.java.login.model.UserTable;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PasswordUpdate extends JFrame {
private Component parent;
private UserTable user;
private LoginDaoImpl ldao;
    
    
    
    private JPanel contentPane;
    private JTextField textPw;
    private JTextField textPw1;

    /**
     * Launch the application.
     */
    public static void newPasswordUpdate(Component parent, UserTable user ) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PasswordUpdate frame = new PasswordUpdate(parent,user);
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
    public PasswordUpdate( Component parent,UserTable user) {
        this.parent = parent;
        this.user= user;
        ldao = LoginDaoImpl.getInstance();
        initialize();
    }
    
    
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x =parent.getX();
        int y = parent.getY();
        setBounds(x,y+200, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        setTitle(user.getId()+" 님의 비밀번호 변경");

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel_1 = new JLabel("새로운 비밀번호 ");
        lblNewLabel_1.setFont(new Font("D2Coding", Font.BOLD, 18));
        lblNewLabel_1.setBounds(23, 53, 163, 26);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("비밀번호  확인");
        lblNewLabel_2.setFont(new Font("D2Coding", Font.BOLD, 18));
        lblNewLabel_2.setBounds(23, 114, 141, 26);
        contentPane.add(lblNewLabel_2);
        //TODO 창 뜨는 위ㅊ랑 틀렸을때 메세지
        
        JButton btnNewButton = new JButton("비밀번호 변경 ");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                btnPasswordUpdate();
            }
        });
        btnNewButton.setFont(new Font("D2Coding", Font.BOLD, 18));
        btnNewButton.setBounds(144, 189, 163, 35);
        contentPane.add(btnNewButton);
        
        textPw = new JTextField();
        textPw.setFont(new Font("D2Coding", Font.BOLD, 18));
        textPw.setBounds(188, 55, 163, 26);
        contentPane.add(textPw);
        textPw.setColumns(10);
        
        textPw1 = new JTextField();
        textPw1.setFont(new Font("D2Coding", Font.BOLD, 18));
        textPw1.setColumns(10);
        textPw1.setBounds(188, 116, 163, 26);
        contentPane.add(textPw1);
    }
    
    protected void btnPasswordUpdate() {
        String pw = textPw.getText();
        String pw1 = textPw1.getText();
        if(pw.equals(pw1)) {
            UserTable userUP  = new UserTable(user.getUserNo() , pw1, pw1, pw1, pw1, null, pw, pw1);
            int result = ldao.mypagePasswordUpdate(userUP);
            if(result == 1) {
                JOptionPane.showMessageDialog(parent, user.getId()+" 님의 비밀번호가 변경 되었습니다~!");
                dispose();
            }else {
                JOptionPane.showMessageDialog(parent, user.getId()+" 님 \n 변경이 실패 했습니다.");
                
            }
        }else {
            JOptionPane.showMessageDialog(parent,"비밀번호가 다릅니다. /n 다시 확인해주세요!");
            
        }
            
    }
}
