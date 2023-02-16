package edu.java.login.view;

//import java.awt.EventQueue;
//
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//
//public class SearchMain extends JFrame {
//
//    private JPanel contentPane;
//
//    /**
//     * Launch the application.
//     */
//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    SearchMain frame = new SearchMain();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    /**
//     * Create the frame.
//     */
//    public SearchMain() {
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(100, 100, 450, 300);
//        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//
//        setContentPane(contentPane);
//    }
//
//}

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.java.login.controller.LoginDaoImpl;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import javax.swing.SwingConstants;

public class SearchMain extends JFrame {

    private JPanel contentPane;
    private JTextField textEmail;
    private JLabel lbl;
    private JLabel lblWrong;
    private LoginDaoImpl ldao;
    private JButton btnNewButton;
    private JLabel lblNewLabel_1;
    private JTextField textEmail2;
    private JLabel lblNewLabel_2;
    private JTextField textId;
    private JButton btnNewButton_1;
    private JLabel lblpa1;
    private JLabel lblpa1_1;
    int n = 90;
    
    private Component parent;
    /**
     * Launch the application.
     */
    public static void newSearchMain(Component parent) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SearchMain frame = new SearchMain(parent);
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
    public SearchMain(Component parent) {
        this.parent = parent;
        ldao = LoginDaoImpl.getInstance();
        initialize();
        
    }
    
    
    
    public void initialize() {
                
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        setBounds(x,y, 450, 393);
        setTitle("아이디 / 비밀번호 찾기");
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("이메일");
        lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel.setBounds(31, 35, 66, 24);
        contentPane.add(lblNewLabel);
        
        textEmail = new JTextField();
        textEmail.setFont(new Font("굴림", Font.BOLD, 15));
        textEmail.setBounds(109, 26, 262, 36);
        contentPane.add(textEmail);
        textEmail.setColumns(10);
        
        lbl = new JLabel("이메일을 입력하시오");
        lbl.setEnabled(false);
        lbl.setFont(new Font("굴림", Font.ITALIC, 16));
        lbl.setBounds(109, 61, 262, 24);
        contentPane.add(lbl);
        
        lblWrong = new JLabel("등록된 회원이 없습니다. 다시 확인해주세요.");
        lblWrong.setForeground(new Color(255, 0, 0));
        lblWrong.setVisible(false);
        lblWrong.setFont(new Font("굴림", Font.ITALIC, 16));
        lblWrong.setBounds(63, 69, 359, 24);
        contentPane.add(lblWrong);
        
        btnNewButton = new JButton("아이디 찾기");
        btnNewButton.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnId();
            }
        });
        btnNewButton.setBounds(132, 95, 166, 36);
        contentPane.add(btnNewButton);
        
        lblNewLabel_1 = new JLabel("이메일");
        lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1.setBounds(31, 150, 66, 24);
        contentPane.add(lblNewLabel_1);
        
        textEmail2 = new JTextField();
        textEmail2.setFont(new Font("굴림", Font.BOLD, 15));
        textEmail2.setColumns(10);
        textEmail2.setBounds(109, 141, 262, 36);
        contentPane.add(textEmail2);
        
        lblNewLabel_2 = new JLabel("아이디");
        lblNewLabel_2.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_2.setBounds(31, 196, 66, 24);
        contentPane.add(lblNewLabel_2);
        
        textId = new JTextField();
        textId.setFont(new Font("굴림", Font.BOLD, 15));
        textId.setColumns(10);
        textId.setBounds(109, 187, 262, 36);
        contentPane.add(textId);
        
        btnNewButton_1 = new JButton("비밀번호 찾기");
        btnNewButton_1.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPassword ();
            }
        });
        btnNewButton_1.setBounds(132, 297, 166, 34);
        contentPane.add(btnNewButton_1);
        
        lblpa1 = new JLabel("일치하는 회원이 없습니다.");
        lblpa1.setHorizontalAlignment(SwingConstants.CENTER);
        lblpa1.setVisible(false);
        lblpa1.setForeground(new Color(255, 0, 0));
        lblpa1.setFont(new Font("굴림", Font.ITALIC, 16));

        contentPane.add(lblpa1);
        
        lblpa1_1 = new JLabel("아이디와 이메일을 확인해주세요.");
        lblpa1_1.setVisible(false);
        lblpa1_1.setForeground(new Color(255, 0, 0));
        lblpa1_1.setFont(new Font("굴림", Font.ITALIC, 16));
        
        contentPane.add(lblpa1_1);
    }
    
    
    
    protected void btnPassword() {
        // TODO Auto-generated method stub
        String email = textEmail2.getText();
        String id = textId.getText();
        System.out.println(email);
        System.out.println(id);
        String pass = ldao.searchPass(email, id);
        System.out.println(pass);
        if(pass == null) {
            lblpa1.setBounds(n+=1, 233, 249, 26);
            lblpa1_1.setBounds(n+=1, 258, 297, 26);
            lblpa1.setVisible(true);
            lblpa1_1.setVisible(true);
            System.out.println(pass);
        } else {
            lblpa1.setVisible(false);
            lblpa1_1.setVisible(false);
            JOptionPane.showMessageDialog(this, id+" 님의 비밀번호는 \n"+ pass+ " 입니다! ");
            dispose();
        }
        
    }

    public void btnId() {
        String email = textEmail.getText();
        String id = ldao.searchId(email);
        System.out.println(id);
        if(id == null) {
            lbl.setVisible(false);
            lblWrong.setVisible(true);
        }else {
            lbl.setVisible(true);
            lblWrong.setVisible(false);
            
            JOptionPane.showMessageDialog(this, "아이디는 "+ id+" 입니다!");
        }
    }
}



