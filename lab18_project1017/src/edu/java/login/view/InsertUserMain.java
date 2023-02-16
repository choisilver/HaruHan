package edu.java.login.view;

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
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JEditorPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class InsertUserMain extends JFrame {
    private JTextField textEmail;
    private JTextField textId;
    private JTextField textNick;
    
    private Component parent;
    private LoginDaoImpl ldao;
    private JPasswordField textPw1;
    private JPasswordField textPw;
    private JComboBox boxGender;
    private static String[] AGEMODEL =  {"비공개", "10", "20", "30", "40", "50", "60"};
    private static String[] GENDERMODEL = {"비공개","여성", "남성"};
    private JComboBox boxAge;
    private int emailInt = 0 ;
    private int pwInt = 0;
    private int idInt = 0;
    private JLabel lblID;
    private JLabel lblEMAIL;
    private JPanel contentPane;
    private JLabel lblPW;
    private String[] nicknameRandom = { "잔망루피","보노보노", "새콤달콤"
            , "허쉬초콜렛", "선의의 옹호자","열정적인 중재자", "재기발랄한 활동가"};
    
    /**
     * Launch the application.
     */
    public static void newInsertUser(Component parent ) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InsertUserMain frame = new InsertUserMain(parent);
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
    public InsertUserMain(Component parent) {
        this.parent = parent;
        
        ldao = LoginDaoImpl.getInstance();
        
        initialize();
        
    }
    
    
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        int x = parent.getX();
        int y = parent.getY();
        int w =parent.getWidth();
        int h = parent.getHeight();
        setBounds(x+Math.abs((w-600)/2), y-Math.abs((h-600)/2), 600, 600);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        setTitle("하루 한줄, 회원가입 :)");
        JLabel lblNewLabel = new JLabel("이메일 ");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel.setBounds(28, 22, 105, 34);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("아이디");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1.setBounds(28, 89, 105, 41);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("비밀번호");
        lblNewLabel_2.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_2.setBounds(28, 174, 105, 34);
        contentPane.add(lblNewLabel_2);
        
        JLabel lblNewLabel_2_1 = new JLabel("비밀번호 확인 ");
        lblNewLabel_2_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_2_1.setBounds(28, 218, 130, 34);
        contentPane.add(lblNewLabel_2_1);
        
        JLabel lblNewLabel_2_1_1 = new JLabel("별명");
        lblNewLabel_2_1_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_2_1_1.setBounds(28, 284, 130, 34);
        contentPane.add(lblNewLabel_2_1_1);
        
        JButton btnNewButton = new JButton("회원 가입");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnisertuser();
            }
        });
        btnNewButton.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton.setBounds(137, 489, 332, 62);
        contentPane.add(btnNewButton);
        
        textEmail = new JTextField();
        textEmail.setFont(new Font("굴림", Font.BOLD, 16));
        textEmail.setBounds(154, 22, 218, 35);
        contentPane.add(textEmail);
        textEmail.setColumns(10);
        
        textId = new JTextField();
        textId.setFont(new Font("굴림", Font.BOLD, 16));
        textId.setColumns(10);
        textId.setBounds(154, 92, 218, 35);
        contentPane.add(textId);
        
        textNick = new JTextField();
        textNick.setFont(new Font("굴림", Font.BOLD, 16));
        textNick.setColumns(10);
        textNick.setBounds(154, 284, 218, 35);
        contentPane.add(textNick);
        
        JButton btnNewButton_1 = new JButton("중복 확인 ");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmEM();
            }
        });
        btnNewButton_1.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_1.setBounds(394, 24, 180, 30);
        contentPane.add(btnNewButton_1);
        
        JButton btnNewButton_1_1 = new JButton("중복 확인 ");
        btnNewButton_1_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		btnIdConfirm();
        	}
        });
        btnNewButton_1_1.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_1_1.setBounds(394, 94, 180, 30);
        contentPane.add(btnNewButton_1_1);
        
        JButton btnNewButton_1_1_1 = new JButton("비밀번호 확인 ");
        btnNewButton_1_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                confirmPW();
            }
        });
        btnNewButton_1_1_1.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_1_1_1.setBounds(394, 174, 180, 34);
        contentPane.add(btnNewButton_1_1_1);
        
        textPw1 = new JPasswordField();
        textPw1.setFont(new Font("굴림", Font.BOLD, 18));
        textPw1.setBounds(154, 219, 218, 35);
        contentPane.add(textPw1);
        
        textPw = new JPasswordField();
        textPw.setFont(new Font("굴림", Font.BOLD, 18));
        textPw.setBounds(154, 175, 218, 35);
        contentPane.add(textPw);
        

        
        JLabel lblNewLabel_3 = new JLabel("성별");
        lblNewLabel_3.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_3.setBounds(28, 382, 130, 34);
        contentPane.add(lblNewLabel_3);
        
        JLabel lblNewLabel_3_1 = new JLabel("나이");
        lblNewLabel_3_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_3_1.setBounds(28, 426, 130, 34);
        contentPane.add(lblNewLabel_3_1);
        
        boxGender = new JComboBox();
        boxGender.setFont(new Font("굴림", Font.BOLD, 18));
        boxGender.setModel(new DefaultComboBoxModel(GENDERMODEL));
        boxGender.setSelectedIndex(0);
        boxGender.setBounds(170, 382, 202, 34);
        contentPane.add(boxGender);
        
        boxAge = new JComboBox();
        boxAge.setFont(new Font("굴림", Font.BOLD, 18));
        boxAge.setModel(new DefaultComboBoxModel(AGEMODEL));
        boxAge.setSelectedIndex(0);
        boxAge.setBounds(170, 426, 202, 34);
        contentPane.add(boxAge);

        lblID = new JLabel("아이디가 확인되었습니다.");
        lblID.setFont(new Font("굴림", Font.ITALIC, 15));
        lblID.setBounds(154, 125, 218, 28);
        contentPane.add(lblID);
        lblID.setVisible(false);
        lblID.setEnabled(false);
        
        lblEMAIL = new JLabel("이메일이 확인되었습니다.");
        lblEMAIL.setEnabled(false);
        lblEMAIL.setVisible(false);
        lblEMAIL.setFont(new Font("굴림", Font.ITALIC, 15));
        lblEMAIL.setBounds(154, 55, 218, 28);
        contentPane.add(lblEMAIL);
        
        lblPW = new JLabel("비밀번호가 확인 되었습니다.");
        lblPW.setVisible(false);
        lblPW.setEnabled(false);
        lblPW.setFont(new Font("굴림", Font.ITALIC, 15));
        lblPW.setBounds(154, 250, 218, 28);
        contentPane.add(lblPW);
        
        JLabel lblNewLabel_4 = new JLabel("닉네임을 입력하지 않는다면");
        lblNewLabel_4.setEnabled(false);
        lblNewLabel_4.setFont(new Font("굴림", Font.ITALIC, 15));
        lblNewLabel_4.setBounds(154, 328, 224, 15);
        contentPane.add(lblNewLabel_4);
        
        JLabel lblNewLabel_4_1 = new JLabel("랜덤으로 설정 됩니다.");
        lblNewLabel_4_1.setFont(new Font("굴림", Font.ITALIC, 15));
        lblNewLabel_4_1.setEnabled(false);
        lblNewLabel_4_1.setBounds(154, 345, 224, 15);
        contentPane.add(lblNewLabel_4_1);
        

    }

    protected void btnIdConfirm() {
		String id = textId.getText();
		
        if(id.equals("")) {
        	JOptionPane.showMessageDialog(this, "아이디를 입력 해주세요.", "WARNING", JOptionPane.WARNING_MESSAGE);
        	return;
        }
		boolean idCon = ldao.confirmId(id);
        if(idCon) { //true
            JOptionPane.showMessageDialog(this, "이미 가입된 아이디 입니다. \n 다시 확인해주세요.");
            
        } else {
            JOptionPane.showMessageDialog(this, "아이디가 확인 되었습니다.");

            lblID.setVisible(true);
            lblID.setEnabled(false);

        }
        
        
        if(!idCon) {
        	
        	idInt = 1;
        }
		
	}

	private void confirmEM() {
        // 이건테이블에서 읽어야 되잖아..? 
        // 아이디도,,
        String email = textEmail.getText();
        if(email.equals("")) {
        	JOptionPane.showMessageDialog(this, "이메일을 입력 해주세요.", "WARNING!!!!!", JOptionPane.WARNING_MESSAGE);
        	return;
        }
        
        
        boolean emailCon = ldao.confirmEmail(email);
        
        if(emailCon) { //true
        	JOptionPane.showMessageDialog(this, "이미 가입 된 이메일입니다. \n 이메일을 다시 확인해주세요");
            
        } else {
            JOptionPane.showMessageDialog(this, "이메일이 확인 되었습니다.");
            lblEMAIL.setVisible(true);
        }
        
        if(!emailCon) {
        	// true  가입 하면 안돼 
        	emailInt = 1;
        }
        
        

    }
    
    private void confirmPW() {
        
    	if( textPw.getText().equals("") || textPw1.getText().equals("") ) {
    		JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요!", "WARNING", JOptionPane.WARNING_MESSAGE);
    		return;
    	}
    	
        boolean pwCon = !textPw.getText().equals(textPw1.getText());
        
        if(pwCon) {
    		JOptionPane.showMessageDialog(this, "비밀번호가 다릅니다.  확인해주세요!", "WARNING", JOptionPane.WARNING_MESSAGE);
    		return;
        }
        JOptionPane.showMessageDialog(this, "비밀번호가 확인 되었습니다.");
        lblPW.setVisible(true);
        
        if(!pwCon) {
        	// 가입하면 안됨 
        	pwInt = 1;
        }
        

    }

    protected void btnisertuser() {
        Random random = new Random();
        int a = random.nextInt(nicknameRandom.length);
        
        
        int ageint = boxAge.getSelectedIndex();
        int genderint = boxGender.getSelectedIndex();
        
        String email = textEmail.getText();
        String id = textId.getText();
        String pw = textPw.getText();
        String nick = textNick.getText();
        if(nick.equals("")) {
            nick = nicknameRandom[a];
        }
        System.out.println("별명 =" + nick);
        String age = AGEMODEL[ageint];
        String gender= GENDERMODEL[genderint];
        
        
        if(email.equals("")||id.equals("")|| pw.equals("") ) {
            JOptionPane.showMessageDialog(this,"이메일, 아이디, 비밀번호는 반드시 입력하세요.", "오류",JOptionPane.ERROR_MESSAGE);

            
            return;
        }
        
        // 진짜 모르겟어 TODO
        
        if( emailInt==1 && pwInt ==1  && idInt==1) {
        	
            UserTable user = new UserTable(null, id, pw, email, nick, null, age,gender);
            System.out.println(user);
            
            int result = ldao.insertUser(user);
            
            if(result == 1) {
                JOptionPane.showMessageDialog(this, "하루 한줄, 독서 노트 \n 회원 가입하신 것을 축하드립니다. \n 반갑습니다!");
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "중복 확인 버튼를 눌러주세요!", "WARNING", JOptionPane.WARNING_MESSAGE);
            return;
        }
        

    }
}
