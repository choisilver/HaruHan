package edu.java.login.view;

import static edu.java.page.model.BookTable.Entity.*;

import static edu.java.page.model.NoteTable.Entity.*;
import static edu.java.login.model.UserTable.Entity.*;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Manager extends JFrame {

    private JPanel contentPane;
    private JTable table;

    private DefaultTableModel model;
    
    
    
    private static final String[] COLUMN_NAMES = {
            COL_USER_NO, COL_ID, COL_PW, COL_EMAIL, COL_NICK, COL_DATE, COL_AGE, COL_GENDER, 
            COL_BOOK_TITLE, COL_AUTHOR,COL_CATEGORY, COL_BOOK_STATUS, COL_BOOK_STAR, COL_NOTE
    };
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Manager frame = new Manager();
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
    public Manager() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 900, 663);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(43, 204, 793, 338);
        contentPane.add(scrollPane);
        
        model = new DefaultTableModel(null, COLUMN_NAMES);
        table = new JTable();
        table.setModel(model);
        // 테이블 초기화
        
        scrollPane.setViewportView(table);
    }
    
    
}
