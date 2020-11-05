package mario.dao;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import mario.dto.MarioDTO;

public class MarioDBTable extends JFrame implements ActionListener { 
 
   //★★★★★중요!!! sql 자료 입력후 반드시 commit이 필요 - 2020/11/05 commit하지 않고도 실행됨(나중에 테스트해서 정말 괜찮으면 본 주석 지울예정)
   //역할 : DB에 저장된 객체별 정보를 JTable에 표기
   //     MarioDBTLogin에서 입력된 id/pw에 따라 표기되는 테이블 내용이 달라짐
   //     계정삭제(탈퇴), 회원정보 수정이 가능
    
   //1.필드선언
   private JButton updateBtn, deleteBtn, exitBtn;
   private JLabel titleL;
   private JLabel noticeL;
   private Vector<String> vector; //테이블 필드용 및 내용 생성용
   private static DefaultTableModel model; //테이블 생성용
   private JTable table;
   public final static int SUBJECT_COUNT = 11; //테이블에 저장된 항목 수
   public String editorID;
   public String editorPW;
   
   
   public MarioDBTable(MarioDTO attempt) {
      
      
      this.editorID = attempt.getClientAccount();
      this.editorPW = attempt.getPassword();
      
      //4.테이블 필드명 생성
      vector = new Vector<String>();
      vector.add("일련번호");
      vector.add("아이디");
      vector.add("비밀번호"); //비밀번호 보호 필요??
      vector.add("이름");
      vector.add("나이");
      vector.add("닉네임");
      vector.add("성별");
      vector.add("개인정보보호");
      vector.add("점수");
      vector.add("소요시간");
      vector.add("순위");
      
      

      //5.테이블 생성
      model = new DefaultTableModel(vector,0) {
         public boolean isCellEditable(int row, int column) {
            return (column != 0 && column != 1) ? true : false;
         }   
      };
      table = new JTable(model);
      JScrollPane scroll = new JScrollPane(table);
      scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      
      MarioDAO dao = MarioDAO.getInstance(); //DAO객체 생성
      List<MarioDTO> dtoList = dao.getMarioList(); 
      
     
      //6.DB정보를 List에 담기(테이블에 표기)
       //일반 사용자 정보 표시(본인 것만 표시)
      //DBTLogin에서 입력받은 id/pw(객체 attempt)와 DB에 저장된 id/pw를 조회하여 일치하는 객체를 표시
      for(MarioDTO dto : dtoList) {
         String[] checkID = dto.getClientAccount().split("@");
         if(editorID.equals(checkID[0]) && editorPW.equals(dto.getPassword())) {
            Vector<String> v = new Vector<String>();
            v.add(dto.getSeq()+"");
            v.add(dto.getClientAccount());
            v.add(dto.getPassword());
            v.add(dto.getRealName());
            v.add(dto.getAge() + "");
            v.add(dto.getNickname());
            if(dto.getGender() == 0) {
               v.add("남성");
               
            }else if(dto.getGender() == 1) {
               v.add("여성");
            }
            if(dto.getInfoAgree() == 0) {
               v.add("비동의");
            }else if(dto.getInfoAgree() == 1) {
               v.add("동의");
            }
            v.add(dto.getScore() + "");
            v.add(dto.getGoalTime());
            v.add(dto.getPlayerRank() + "");
               
               model.addRow(v);   
            }
   
         } 
      
      
      //3.컴포넌트 생성
      updateBtn = new JButton("정보수정");
      deleteBtn = new JButton("계정삭제");
      exitBtn = new JButton("종료");
      titleL = new JLabel("회원정보 관리");
      titleL.setFont(new Font("MD개성체", Font.BOLD, 20));
      noticeL = new JLabel("※회원정보 수정 후, 반드시 Enter를 눌러주세요");
      noticeL.setFont(new Font("MD개성체", Font.PLAIN, 10));
      noticeL.setForeground(new Color(0,0,255));
      
      JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p1.add(noticeL);
      p1.add(updateBtn);
      p1.add(deleteBtn);
      p1.add(exitBtn);
      
      JPanel p2 = new JPanel();
      p2.add(titleL);
      
      
      Container container = this.getContentPane();
      container.add("South",p1);
      container.add("North",p2);
      container.add("Center",scroll);
      
      
      //테이블 내용 가운데 정렬 및 컬럼 크기 조절
      DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
      dtcr.setHorizontalAlignment(SwingConstants.CENTER);
      TableColumnModel tcm = table.getColumnModel(); //테이블에서 컬럼가져오기
      table.getColumnModel().getColumn(1).setPreferredWidth(150); //clientAccount만 가로조정
      
      for(int i = 0; i < tcm.getColumnCount(); i++) { //컬럼 수만큼 가운데 정렬
         tcm.getColumn(i).setCellRenderer(dtcr);
      }
      
      
      //2.프레임 생성
      setBounds(700, 300, 1000, 300);
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   }
   
 

   //7.이벤트메소드
   public void event() {
      updateBtn.addActionListener(this);
      deleteBtn.addActionListener(this);
      exitBtn.addActionListener(this);
     
   }
   
   //8.버튼 작동
   @Override
   public void actionPerformed(ActionEvent e) {
      if(e.getSource() == updateBtn) {
         updateArticle();
      }else if(e.getSource() == deleteBtn) {
         deleteArticle();
      }else if(e.getSource() == exitBtn) {
       dispose(); //해당 창만 종료
      }
   }
   
   
   //9.정보수정버튼 클릭시 호출되는 메소드
   private void updateArticle() {
      
      //회원정보관리 리스트에서 선택한 목록의 일련번호(시퀀스)값을 가져오기 - MarioDAO테이블의 동일명메소드로 객체를 넘겨줌
      //프레임 테이블에 있는 값을 지역변수(DTO작성용)에 저장
      String seq = (String)table.getValueAt(table.getSelectedRow(), 0); //int 형변환 필요
      String clientAccount = (String)table.getValueAt(table.getSelectedRow(), 1);
      String password = (String)table.getValueAt(table.getSelectedRow(), 2);
      String realName = (String)table.getValueAt(table.getSelectedRow(), 3);
      String age = (String)table.getValueAt(table.getSelectedRow(), 4); //int 형변환 필요
      String nickName = (String)table.getValueAt(table.getSelectedRow(), 5);
      String gender = null;
      if(((String)table.getValueAt(table.getSelectedRow(), 6)).equals("남성")) {
        gender = "0";
      }else if(((String)table.getValueAt(table.getSelectedRow(), 6)).equals("여성")) {
        gender = "1";
      }
      String infoAgree = null;
      if(((String)table.getValueAt(table.getSelectedRow(), 7)).equals("비동의")){
         infoAgree = "0";
      }else if(((String)table.getValueAt(table.getSelectedRow(), 7)).equals("동의")) {
         infoAgree = "1";
      }
      String score = (String)table.getValueAt(table.getSelectedRow(), 8); //int 형변환 필요
      String goalTime = (String)table.getValueAt(table.getSelectedRow(), 9);
      String playerRank = (String)table.getValueAt(table.getSelectedRow(), 10); //int 형변환 필요

      
      //DTO작성
      MarioDTO dto = new MarioDTO();
      dto.setSeq(Integer.parseInt(seq));
      dto.setClientAccount(clientAccount);
      dto.setPassword(password);
      dto.setRealName(realName);
      dto.setAge(Integer.parseInt(age));
      dto.setNickname(nickName);
      dto.setGender(Integer.parseInt(gender));
      dto.setInfoAgree(Integer.parseInt(infoAgree));
      dto.setScore(Integer.parseInt(score));
      dto.setGoalTime(goalTime);
      dto.setPlayerRank(Integer.parseInt(playerRank));
      
      //DB
      MarioDAO dao = MarioDAO.getInstance();
      dao.updateArticle(dto);
      
      //수정완료 메시지 출력
      JOptionPane.showMessageDialog(null, "회원 정보가 수정되었습니다", "회원정보 수정", JOptionPane.INFORMATION_MESSAGE);

   }//updateArticle()
   
   
   //10.계정삭제 버튼 클릭시 호출되는 메소드 - MarioDAO테이블의 동일명메소드로 시퀀스값을 넘겨줌
   private void deleteArticle() {
      
      //회원정보관리 리스트에서 선택한 목록의 일련번호(시퀀스)값을 가져오기
      String seq = (String) table.getValueAt(table.getSelectedRow(), 0);
      String realName = (String) table.getValueAt(table.getSelectedRow(), 3);
      
      
      int result = JOptionPane.showConfirmDialog(null, realName + "님의 정보를 정말 삭제하시겠습니까?", "삭제여부 확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      if(result == JOptionPane.YES_OPTION) {
         //DB
         MarioDAO dao = MarioDAO.getInstance();
         dao.deleteArticle(Integer.parseInt(seq));
         
         //테이블에서 삭제
         model.removeRow(table.getSelectedRow());
         JOptionPane.showMessageDialog(null, "삭제되었습니다");
      }else if(result == JOptionPane.NO_OPTION) {
         return; 
      }
      
      
   }//deleteArticle()



   public static void selectArticle() {
      
      MarioDAO dao = MarioDAO.getInstance();
      List<MarioDTO> dtoList = dao.getMarioList();
      
      for(MarioDTO dto : dtoList) {
            String[] checkID = dto.getClientAccount().split("@");
            
               Vector<String> v = new Vector<String>();
               v.add(dto.getSeq()+"");
               v.add(dto.getClientAccount());
               v.add(dto.getPassword());
               v.add(dto.getRealName());
               v.add(dto.getAge() + "");
               v.add(dto.getNickname());
               if(dto.getGender() == 0) {
                  v.add("남성");
                  
               }else if(dto.getGender() == 1) {
                  v.add("여성");
               }
               if(dto.getInfoAgree() == 0) {
                  v.add("비동의");
               }else if(dto.getInfoAgree() == 1) {
                  v.add("동의");
               }
               v.add(dto.getScore() + "");
               v.add(dto.getGoalTime());
               v.add(dto.getPlayerRank() + "");
               
               model.addRow(v);   
            
 
      }
   }


  
   
   
}