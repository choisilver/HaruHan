package edu.java.page.view;

import java.awt.Component;
import static edu.java.page.model.NoteTable.Entity.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import edu.java.page.controller.PageDaoImpl;
import edu.java.page.model.BookTable;
import edu.java.page.model.NoteTable;
import edu.java.page.model.BookTable;
import edu.java.page.view.NoteInsert.onNoteInsertListener;
import edu.java.page.view.NoteUpdate.onNoteUpdateListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.Font;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.net.http.WebSocket.Listener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;

public class PageBookNote extends JFrame  implements onNoteUpdateListener , onNoteInsertListener{
    // 수정을 하거나 업데이트를 하면  새로운 값이 나왔으면 좋겠어
    public interface onPageNoteBookListener{
        void onBookPageNotify();
    }
    
    
    
    private String[] categories = {"소설", "시/에세이", "인문", "취미", "경제/경영", "자기계발", "역사", "과학"};
    private String[] statuses =  {"읽는 중", "다 읽었어요!", "읽고 싶어요", "멈췄어요"};
    private String[] star = {"♡", "♥", "♥♥", "♥♥♥", "♥♥♥♥", "♥♥♥♥♥"};
    

    private JPanel contentPane;
    private JTable table;
    private JTextField textDate;
    private JTextField textEndDate;
    private JTextField textPage;
    private JTextField textAuthor;
    private JTextField textTitle;
    
    
    private BookTable book;
    private Component parent;
    private DefaultTableModel model;
    private PageDaoImpl pdao;
    private onPageNoteBookListener listener;
    
    private static final String[] COLUMN_NAMES = {
            COL_NOTE_NO, COL_NOTE, COL_NOTE_ST_PAGE,COL_NOTE_ENDPAGE,
            COL_NOTE_DATE
    }; // 책 번호는 지울 예정
    private JTextField textBookPage;
    private JTextArea textMemo;
    private JTextField textNote;
    private JComboBox starBox;
    private JComboBox cateBox;
    private JComboBox statusBox;
    private JTextField textPersent;
    

    /**
     * Launch the application.
     */
    public static void newBookNote( Component parent,BookTable book, onPageNoteBookListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PageBookNote frame = new PageBookNote( parent, book,listener);
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
    public PageBookNote(Component parent, BookTable book,onPageNoteBookListener listener) {
        this.listener = listener;
        this.parent = parent;
        this.book = book;
        pdao = PageDaoImpl.getInstance();
        initialize();
        initalizetable();
//        noteUpdateData();
        bookDataInfo();
        textPage.setText(pdao.noteEndPage(book)+ "");
        
        JButton btnNewButton_2 = new JButton("전체보기");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initalizetable();
            }
        });
        btnNewButton_2.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_2.setBounds(622, 566, 108, 36);
        contentPane.add(btnNewButton_2);
        

    }
    


    public void initalizetable() {
        model = new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(model);
        List<NoteTable> list = pdao.noteSelect(book.getBookIndex());
        for(NoteTable n : list) {
            Object[] row  = {
                n.getNoteNo(), 
                n.getNote(), 
                n.getStPage(), 
                n.getEndPage(), 
                n.getDateNote(),
                n.getBookIndex()
            };
            model.addRow(row);
        }
        table.setRowHeight(20);
        table.getColumnModel().getColumn(0).setMaxWidth(100);
        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(2).setMinWidth(50);
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMinWidth(50);
        table.getColumnModel().getColumn(4).setMaxWidth(200);
        table.getColumnModel().getColumn(4).setMinWidth(200);
        
    }
    
    
    
    public void bookDataInfo() {
        textTitle.setText(book.getTitle());
        textAuthor.setText(book.getAuthor());
        textMemo.setText(book.getMemo());
        textBookPage.setText(book.getPages().toString());
        starBox.setSelectedIndex(book.getStar());
        
        cateBox.setSelectedItem(book.getCategory());
        statusBox.setSelectedItem(book.getStatus());
        
        textEndDate.setText("");
        textPage.setText("");
        textPersent.setText("");
        
        textDate.setText(book.getStartdate().toString());
        
        noteUpdateData();
        
    }

    
    
    
    
    // 날짜 시간 예쁘게 출력
    private String[] dateTimeLocal(String localtime) {
        String d;
        String t;
        
        String array[] = localtime.split("T");
        String arrayD[] = array[0].split("-");
        String date = arrayD[0]+"년"+arrayD[1]+"월"+arrayD[2]+"일";
        String arrayT[]  = array[1].split(":");
        String time = arrayT[0]+"시"+arrayT[1]+"분";
        
        String[] dateAndTime = {date,time};

        return dateAndTime;
    }
    
    
    
    private void noteUpdateData() { // note에 있는 날짜, 페이지 데이터 가져옴

        String[] date = dateTimeLocal(book.getStartdate().toString());
        textDate.setText(date[0]+"  "+ date[1]);
        
        if(book.getPages()==0) {
            textPage.setText(pdao.noteEndPage(book)+ "");
            double endpage = (double)pdao.noteEndPage(book)/1 * 10000 ;
            textPersent.setText( Math.round(endpage)/100.0+ " %");
            return;
        } else {
            textPage.setText(pdao.noteEndPage(book)+ "");
            double endpage = (double)pdao.noteEndPage(book)/book.getPages() * 10000 ;
            textPersent.setText( Math.round(endpage)/100.0+ " %");
        }
        
        String b = pdao.endDate(book);
        if(b==null) {
            textEndDate.setText("");
        } else {
            String [] a = dateTimeLocal(b);
            textEndDate.setText(a[0]+" "+a[1]);
        }
          
    }
    
    
    
    
    
    
    public void initialize() {
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        int w = parent.getWidth();
        int h = parent.getHeight();
        
        setBounds(x+(w-920)/2,y+(h-663), 920, 663);
        contentPane = new JPanel();
        contentPane.setRequestFocusEnabled(false);
        contentPane.setFocusable(false);
        contentPane.setVerifyInputWhenFocusTarget(false);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setTitle(book.getTitle()+" , 하루 한줄 ");
        
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("제목");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel.setBounds(12, 23, 93, 23);
        contentPane.add(lblNewLabel);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 266, 870, 290);
        contentPane.add(scrollPane);
        
        table = new JTable();
        table.setFont(new Font("굴림", Font.BOLD, 15));
        scrollPane.setViewportView(table);
        
        JButton btnNewButton = new JButton("독서노트 기록");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNoteInsert();
            }
        });
        btnNewButton.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton.setBounds(719, 62, 163, 32);
        contentPane.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("한, 줄 수정");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNoteUpdate();
            }
        });
        btnNewButton_1.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_1.setBounds(719, 104, 163, 27);
        contentPane.add(btnNewButton_1);
        
        JButton btnNewButton_1_1 = new JButton("한줄 지우기");
        btnNewButton_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNoteDelete();
            }
        });
        btnNewButton_1_1.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_1_1.setBounds(719, 141, 163, 28);
        contentPane.add(btnNewButton_1_1);
        
        textDate = new JTextField();
        textDate.setRequestFocusEnabled(false);
        textDate.setFont(new Font("굴림", Font.BOLD, 15));
        textDate.setBounds(136, 103, 295, 32);
        contentPane.add(textDate);
        textDate.setColumns(10);
        
        
        textAuthor = new JTextField();
        textAuthor.setFont(new Font("굴림", Font.BOLD, 15));
        textAuthor.setColumns(10);
        textAuthor.setBounds(136, 60, 295, 33);
        contentPane.add(textAuthor);
        
        textTitle = new JTextField();
        textTitle.setDragEnabled(true);
        textTitle.setFont(new Font("굴림", Font.BOLD, 15));
        textTitle.setColumns(10);
        textTitle.setBounds(136, 19, 295, 31);
        contentPane.add(textTitle);
        
        // 제목, 작가, 등록 날짜, 페이지 수 

        
        JLabel lblNewLabel_1 = new JLabel("작가");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1.setBounds(12, 65, 93, 23);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_1_1 = new JLabel("시작한 날짜");
        lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_1_1.setBounds(12, 108, 112, 23);
        contentPane.add(lblNewLabel_1_1);
        
        JLabel lblNewLabel_1_1_1 = new JLabel("최근 읽은 날짜");
        lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1_1_1.setFont(new Font("굴림", Font.BOLD, 15));
        lblNewLabel_1_1_1.setBounds(12, 173, 144, 23);
        contentPane.add(lblNewLabel_1_1_1);
        
        JLabel lblNewLabel_1_1_2 = new JLabel("어디까지 읽었나~");
        lblNewLabel_1_1_2.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1_1_2.setFont(new Font("굴림", Font.BOLD, 15));
        lblNewLabel_1_1_2.setBounds(12, 206, 144, 23);
        contentPane.add(lblNewLabel_1_1_2);
        
        JButton btnNewButton_2 = new JButton("검색");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnNoteSearch();
            }
        });
        btnNewButton_2.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_2.setBounds(527, 566, 83, 36);
        contentPane.add(btnNewButton_2);
        
        JLabel lblNewLabel_2 = new JLabel("카테고리");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_2.setBounds(436, 23, 93, 23);
        contentPane.add(lblNewLabel_2);
        
        JLabel lblNewLabel_2_1 = new JLabel("별점 ");
        lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_2_1.setBounds(436, 100, 93, 23);
        contentPane.add(lblNewLabel_2_1);
        
        JButton btnNewButton_1_1_1 = new JButton("☆별점 등록☆ ");
        
        btnNewButton_1_1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnUpdateBook();
                JOptionPane.showMessageDialog(PageBookNote.this, textTitle.getText()+" 책에 별점을 \n 등록 하셨습니다~!");
                
            }
        });
        btnNewButton_1_1_1.setFont(new Font("굴림", Font.BOLD, 18));
        btnNewButton_1_1_1.setBounds(719, 23, 163, 29);
        contentPane.add(btnNewButton_1_1_1);
        
        JLabel lblNewLabel_2_1_1 = new JLabel("페이지");
        lblNewLabel_2_1_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2_1_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_2_1_1.setBounds(436, 142, 93, 23);
        contentPane.add(lblNewLabel_2_1_1);
        
        textBookPage = new JTextField();
        textBookPage.setFont(new Font("굴림", Font.BOLD, 15));
        textBookPage.setColumns(10);
        textBookPage.setBounds(527, 138, 163, 32);
        contentPane.add(textBookPage);
        
        JLabel lblNewLabel_2_1_1_1 = new JLabel("메모");
        lblNewLabel_2_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2_1_1_1.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_2_1_1_1.setBounds(436, 172, 93, 23);
        contentPane.add(lblNewLabel_2_1_1_1);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(510, 174, 372, 58);
        contentPane.add(scrollPane_1);
        
        textMemo = new JTextArea();
        textMemo.setFont(new Font("굴림", Font.BOLD, 15));
        textMemo.setLineWrap(true);
        scrollPane_1.setViewportView(textMemo);
        
        starBox = new JComboBox();
        starBox.setFont(new Font("굴림", Font.BOLD, 18));
        starBox.setModel(new DefaultComboBoxModel(star));
        starBox.setBounds(527, 99, 163, 33);
        contentPane.add(starBox);
        
        cateBox = new JComboBox();
        cateBox.setFont(new Font("굴림", Font.BOLD, 15));
        cateBox.setModel(new DefaultComboBoxModel(categories));
        cateBox.setBounds(527, 19, 163, 33);
        contentPane.add(cateBox);
        
        JLabel lblNewLabel_2_1_2 = new JLabel("독서 상태");
        lblNewLabel_2_1_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2_1_2.setFont(new Font("굴림", Font.BOLD, 18));
        lblNewLabel_2_1_2.setBounds(436, 60, 93, 23);
        contentPane.add(lblNewLabel_2_1_2);
        
        statusBox = new JComboBox();
        
        
        statusBox.setModel(new DefaultComboBoxModel(statuses));
        statusBox.setFont(new Font("굴림", Font.BOLD, 15));
        statusBox.setBounds(527, 60, 163, 33);
        contentPane.add(statusBox);
        
        textEndDate = new JTextField();
        textEndDate.setFont(new Font("굴림", Font.BOLD, 15));
        textEndDate.setColumns(10);
        textEndDate.setBounds(152, 170, 228, 28);
        contentPane.add(textEndDate);

        textPersent = new JTextField();
        textPersent.setText("0");
        textPersent.setFont(new Font("굴림", Font.BOLD, 15));
        textPersent.setColumns(10);
        textPersent.setBounds(241, 204, 77, 28);
        contentPane.add(textPersent);
        
        
        textPage = new JTextField();
        textPage.setFont(new Font("굴림", Font.BOLD, 15));
        textPage.setColumns(10);
        textPage.setBounds(152, 204, 77, 28);
        contentPane.add(textPage);
        
        
        

        
        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(12, 566, 479, 36);
        contentPane.add(scrollPane_2);
        
        textNote = new JTextField();
        textNote.setFont(new Font("굴림", Font.PLAIN, 15));
        scrollPane_2.setViewportView(textNote);
        textNote.setColumns(10);
        
        JButton btnF = new JButton("완료");
        btnF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnUpdateBook();
                JOptionPane.showMessageDialog(PageBookNote.this, textTitle.getText()+" 책의\n '하루 한줄'을 완료 하셨습니다~!");
                dispose();
            }
        });
        btnF.setFont(new Font("굴림", Font.BOLD, 18));
        btnF.setBounds(789, 568, 93, 33);
        contentPane.add(btnF);
        
    }
    

    

    protected void btnNoteSearch() {
        // TODO Auto-generated method stub
        List<NoteTable> list = new ArrayList<>();
        String keyword = textNote.getText();
        
         list = pdao.selectNoteKeyword(keyword, book.getBookIndex());
        
         model = new DefaultTableModel(null, COLUMN_NAMES);
         table.setModel(model);
         table.setRowHeight(20);
         table.getColumnModel().getColumn(0).setMaxWidth(100);
         table.getColumnModel().getColumn(0).setMinWidth(50);
         table.getColumnModel().getColumn(2).setMaxWidth(100);
         table.getColumnModel().getColumn(2).setMinWidth(50);
         table.getColumnModel().getColumn(3).setMaxWidth(100);
         table.getColumnModel().getColumn(3).setMinWidth(50);
         table.getColumnModel().getColumn(4).setMaxWidth(200);
         table.getColumnModel().getColumn(4).setMinWidth(200);
         
         for(NoteTable n : list) {
             Object[] row  = {
                 n.getNoteNo(), n.getNote(), n.getStPage(), n.getEndPage(), n.getDateNote()
                 , n.getBookIndex()
             };
             model.addRow(row);
         }
    }

    protected void btnUpdateBook() {
//        PageBookUpdate
        String title = textTitle.getText();
        String author = textAuthor.getText();
        System.out.println(title +",  "+ author);
        String memo = textMemo.getText();
        Integer page = Integer.parseInt(textBookPage.getText());
        
        int cateIndex =  cateBox.getSelectedIndex();
        int statusIndex = statusBox.getSelectedIndex();
        int starIndex = starBox.getSelectedIndex();
        
        String category = categories[cateIndex];
        String status = statuses[statusIndex];
        
        
        BookTable bookUpdate = new BookTable(book.getBookIndex(), null, title, author,
                category, page, null, null, memo, status, starIndex);
        System.out.println(bookUpdate);

        System.out.println(book);
        
        int result = pdao.bookUpdate(bookUpdate);
        
        listener.onBookPageNotify();
               
               
    }

    protected void btnNoteInsert() {
        // TODO Auto-generated method stub
        NoteInsert.newNoteInsert(this, PageBookNote.this, book);
    }

    protected void btnNoteDelete() {
        // note 선택부터
        int row = table.getSelectedRow();
        if(row==-1) {
            JOptionPane.showMessageDialog(this, "노트를 선택해 주세요~!");
            return;
        }
        int noteNo = (Integer)model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "독서 노트를 삭제하시겠습니까?",
                "삭제 확인",
                JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION) {
            pdao.noteDelete(noteNo);
            JOptionPane.showMessageDialog(this, "삭제 되었습니다.");
            initalizetable();
            noteUpdateData();
        }
        
    }

    protected void btnNoteUpdate() {
        // 글 선택하고 테이블 읽어야함
        int row = table.getSelectedRow();
        if(row==-1) {
            JOptionPane.showMessageDialog(this, "노트를 선택해 주세요~!");
            return;
        }
        int noteNo = (Integer)model.getValueAt(row, 0);

        NoteTable note = pdao.selectNote(noteNo, book.getBookIndex());
        NoteUpdate.newNoteUpdate(this, note,book,PageBookNote.this);
        
    }
    
    
    
    @Override
    public void onNoteUpdateNotify(NoteTable note) {
        noteUpdateData();
        initalizetable();
    }

    @Override
    public void onNoteInsertNotify(NoteTable note) {
        noteUpdateData();
        initalizetable();
    }
}
