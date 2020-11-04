package mario.dao;

import java.awt.Container;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import mario.dto.MarioDTO;

public class MarioDBTLogIn extends JFrame implements ActionListener {
   //1.필드 선언
   private JLabel label_emailAccount, label_pwd, label_pwdCheck, label_securenotice;
   private JTextField tf_emailAccount, tf_pwd;
   private JButton okBtn, cancleBtn;
   private String id;
   private String pw;
   
   
   public MarioDBTLogIn() {
      super("회원확인");
      
      //3.컴포넌트 생성
      label_securenotice = new JLabel("<html><body>  [안내]보안을 위해 다시 한번 <br><br>아이디와 비밀번호를 입력해주세요</body></html>");
      label_securenotice.setFont(new Font("MD개성체", Font.BOLD, 11));
      label_securenotice.setBounds(50, 10, 250, 40);
      label_emailAccount = new JLabel("아이디  ");
      label_emailAccount.setFont(new Font("MD개성체", Font.BOLD, 15));
      label_pwd = new JLabel("비밀번호");
      label_pwd.setFont(new Font("MD개성체", Font.BOLD, 15));
      
      //★★label_pwdCheck부분 수정!!!!
//      label_pwdCheck = new JLabel();
//      label_pwdCheck.setFont(new Font("MD개성체", Font.BOLD, 15));
//      label_pwdCheck.setBounds(30, 130, 200, 300);
//      label_pwdCheck.setVisible(true); //★
      tf_emailAccount = new JTextField(10);
      tf_pwd = new JTextField(10);
      okBtn = new JButton("확인");
      cancleBtn = new JButton("취소");
      
      
      
      
      
      Panel p1 = new Panel();
      p1.add(label_emailAccount);
      p1.add(tf_emailAccount);
      p1.setBounds(30, 70, 200, 30);
      
      Panel p2 = new Panel();
      p2.add(label_pwd);
      p2.add(tf_pwd);
      p2.setBounds(30, 110, 200, 30);
      
      Panel p3 = new Panel();
      p3.add(okBtn);
      p3.add(cancleBtn);
      p3.setBounds(40, 200, 200, 40);
      
      Container container = this.getContentPane();
      container.add(label_securenotice);
      //container.add(label_pwdCheck);
      container.add(p1);
      container.add(p2);
      container.add(p3);
      
      //2.프레임 생성
      setLayout(null);
      setBounds(700,300, 300, 300);
      setVisible(true);
      setResizable(false);
      
      
      }//MarioDBTLogIn()
      

   public void event() {
      okBtn.addActionListener(this);
      cancleBtn.addActionListener(this);
   }
   
      
   public void enterTable(String id, String pw) {
      this.id = id;
      this.pw = pw;
      MarioDTO attempt = new MarioDTO();
      attempt.setClientAccount(id);
      attempt.setPassword(pw);
      new MarioDBTable(attempt);
   }
   
   public static void main(String[] args) {
      new MarioDBTLogIn().event();

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if(e.getSource() == okBtn) { //확인버튼을 눌렀을때 입력한 id/pw와 DB에 들어있는 id/pw가 같으면 enterTable(MarioDBTable)실행
                                  //직접 new MarioDBTable()하지 않은 이유는 객체도 같이 넘겨주기 위해(관리자와 일반사용자 구분용)
    	  
    	  System.out.println("로그인 버튼 눌림");
         MarioDAO dao = MarioDAO.getInstance(); //DAO객체 생성
         List<MarioDTO> dtoList = dao.getMarioList(); 
         
         for(MarioDTO dto : dtoList) {
            if(tf_emailAccount.getText().equals(dto.getClientAccount()) && tf_pwd.getText().equals(dto.getPassword())) {
               
                  enterTable(tf_emailAccount.getText(),tf_pwd.getText());
               
            }else {
               //★★label_pwdCheck부분 수정!!!!
//               label_pwdCheck.setVisible(true);
//               label_pwdCheck.setText("아이디와 비밀번호가 일치하지 않습니다");
               return;
            }
         }
      }else if(e.getSource() == cancleBtn) {
         dispose();
      }
      
   }

}