package mario.Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import mario.dao.MarioDAO;
import mario.dto.MarioDTO;

public class MarioSignup extends JFrame implements ActionListener, TextListener {

	// 라벨
	private JLabel label_signup_info, label_emailAccount, label_pwd, label_cf_pwd, label_nick_name, label_name, label_age,
			label_gender, label_atMark; // 경고메세지 창말고 라벨로 대체 true false 로 아직구상중

	private JTextField tf_emailAccount, tf_nickname, tf_name, tf_age;
	private JPasswordField tf_pwd, tf_pwd_check; // 비밀번호 암호화

	private JCheckBox cb_person_info, checkBox_man, checkBox_woman; // 개인정보 취급방침에 대해 동의합니다
	private JButton btn_EmailAuth, btn_nickname_check, btn_signup, btn_cancel;

	private JComboBox<String> comboBox_email; // 네이버 이메일선택
	private ImageIcon ic_bg_signup = new ImageIcon("image/background/signupBackground.png"); // 배경아이콘
	private Image im_bg_signup = ic_bg_signup.getImage();

	private DefaultTableModel model;
	private Vector<String> vector;

	private List<MarioDTO> dtoList = new ArrayList<MarioDTO>();

	public MarioSignup() {

		super("Sign up");
		
		setBounds(0, 0, 500, 600);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		/* ******************************************************************* */

		// 항목 생성
		
			// 라벨
			label_signup_info = new JLabel("마리오 회원가입");
			label_emailAccount = new JLabel("Email 계정");
			label_pwd = new JLabel("비밀 번호");
			label_cf_pwd = new JLabel("비밀번호확인");
			label_nick_name = new JLabel("닉 네 임");
			label_name = new JLabel("이    름 ");
			label_age = new JLabel("나    이");
			label_gender = new JLabel("성    별");
			label_atMark = new JLabel("@");

			// 텍스트필드
			tf_emailAccount = new JTextField(20);
			comboBox_email = new JComboBox<String>(new String[]{"naver.com", "gmail.com"});
			tf_pwd = new JPasswordField(20);
			tf_pwd_check = new JPasswordField(20);
			tf_nickname = new JTextField(20);
			tf_name = new JTextField(20);
			tf_age = new JTextField(20);

			// 버튼
			btn_EmailAuth = new JButton("인증받기");
			btn_signup = new JButton("회원가입");
			btn_cancel = new JButton("취소");
			btn_nickname_check = new JButton("닉네임중복확인");
		
			// 성별 체크박스 & 버튼 그룹
			ButtonGroup group_gender = new ButtonGroup();
			checkBox_man = new JCheckBox("남성", true);
			checkBox_woman = new JCheckBox("여성");
			group_gender.add(checkBox_man);
			group_gender.add(checkBox_woman);

			// 개인정보 수락 체크박스
			cb_person_info = new JCheckBox("개인정보 취급방침에 대해 동의합니다");

			// 테두리속없애기
			cb_person_info.setBorder(BorderFactory.createEtchedBorder());
			checkBox_man.setBorder(BorderFactory.createEtchedBorder());
			checkBox_woman.setBorder(BorderFactory.createEtchedBorder());

		// 둘 같이 해야없어짐
//		cb_person_info.setOpaque(false);
//		checkBox_woman.setOpaque(false);
//		checkBox_man.setOpaque(false);

			// 버튼이 선택(focus)되었을때 생기는 테두리 사용안함
			cb_person_info.setFocusable(false);
			btn_cancel.setFocusable(false);
			btn_signup.setFocusable(false);
			btn_nickname_check.setFocusable(false);
			btn_EmailAuth.setFocusable(false);
			checkBox_woman.setFocusable(false);
			checkBox_man.setFocusable(false);

			// 폰트
			label_signup_info.setFont(new Font("MD개성체", Font.BOLD, 20));
			label_emailAccount.setFont(new Font("MD개성체", Font.BOLD, 15));
			label_pwd.setFont(new Font("MD개성체", Font.BOLD, 15));
			label_cf_pwd.setFont(new Font("MD개성체", Font.BOLD, 15));
			label_nick_name.setFont(new Font("MD개성체", Font.BOLD, 15));
			label_name.setFont(new Font("MD개성체", Font.BOLD, 15));
			label_age.setFont(new Font("MD개성체", Font.BOLD, 15));
			label_gender.setFont(new Font("MD개성체", Font.BOLD, 15));
			label_atMark.setFont(new Font("MD개성체", Font.BOLD, 15));

			// 글자 색
			tf_pwd.setForeground(Color.BLACK);
			tf_emailAccount.setForeground(Color.BLACK);
			label_atMark.setForeground(Color.BLACK);
			btn_signup.setBackground(Color.getHSBColor(150, 200, 240));
			btn_cancel.setBackground(Color.getHSBColor(150, 200, 240));

			// 라벨좌표
			int x = 40, y = 0; //기준 좌표
			
			/* 마리오 회원가입 배너 */
			label_signup_info.setBounds	(x + 280, 	y, 		 200, 20);
			
			/* 이메일 */
			label_emailAccount.setBounds			(x,		 	y + 100, 	120, 20);
			tf_emailAccount.setBounds				(x + 100,	y + 95, 	110, 28);
			label_atMark.setBounds					(x + 216,	y + 100,    30,  20);
			comboBox_email.setBounds				(x + 240, 	y + 95, 	100, 28);
			btn_EmailAuth.setBounds					(x + 345,	y + 95,		90, 28);
			
			
			label_pwd.setBounds			(x, 		y + 140, 120, 20);
			label_cf_pwd.setBounds		(x,			y + 180, 120, 20);
			label_nick_name.setBounds	(x, 		y + 220, 120, 20);
			label_name.setBounds		(x, 		y + 260, 120, 60);
			label_age.setBounds			(x, 		y + 300, 120, 60);
			label_gender.setBounds		(x + 240, 	y + 285, 120, 60);

			// 텍스트필드좌표
			tf_pwd.setBounds			(x + 100, 		205, 	 110, 28);
			tf_pwd_check.setBounds		(x + 100, 		245, 	 110, 28);
			tf_nickname.setBounds		(x + 100, 		285, 	 110, 28);
			tf_name.setBounds			(x + 100, 		325, 	 110, 28);
			tf_age.setBounds			(x + 100, 		365, 	 50,  28);

			// 이메일콤보박스

		// 성별체크박스 좌표
		checkBox_man.setBounds(320, 380, 50, 20);
		checkBox_woman.setBounds(270, 380, 50, 20);
		// 개인정보수신동의
		cb_person_info.setBounds(150, 430, 250, 50);

		// btn_id_cf.setBounds(250, 165, 100, 32);
		
		btn_signup.setBounds(140, 480, 100, 40);
		btn_cancel.setBounds(240, 480, 100, 40);
		btn_nickname_check.setBounds(250, 285, 125, 32);
		
		// 배경화면
		JPanel background = new JPanel() {
//			public void paintComponent(Graphics g) {
//				g.drawImage(im_bg_signup, 0, 0, null);
//				setOpaque(true);
//			}
		};
		

		// 패널 레이아웃패널 null값주기
		background.setLayout(null);

		// 라벨 패널추가
		background.add(label_signup_info);
		background.add(label_emailAccount);
		background.add(label_pwd);
		background.add(label_cf_pwd);
		background.add(label_nick_name);
		background.add(label_name);
		background.add(label_age);
		background.add(label_gender);

		// 부가라벨
		background.add(label_atMark);
		background.add(label_emailAccount);
		// 텍스트필드 패널추가
		background.add(tf_emailAccount);
		background.add(comboBox_email);
		background.add(tf_pwd);
		background.add(tf_pwd_check);
		background.add(tf_nickname);
		background.add(tf_name);
		background.add(tf_age);

		// 콤보박스 패널추가

		background.add(cb_person_info);

		// 버튼 패널추가
		background.add(btn_cancel);
		background.add(btn_signup);

		background.add(btn_EmailAuth);
		background.add(btn_nickname_check);
		// 성별체크박스
		background.add(checkBox_man);
		background.add(checkBox_woman);

		// 컨테이너
		add(background);

		// 마우스좌표확인 지울거임

		// 암호화값 string에 담기
//      pwd = "";
//      // tf_pw 필드에서 패스워드를 얻어옴,
//      char[] secret_pw = tf_pwd.getPassword(); // secret_pw 배열에 저장된 암호의 자릿수 만큼 for문 돌리면서 cha 에 한 글자씩 저장
//      for (char cha : secret_pw) {
//         Character.toString(cha); // cha 에 저장된 값 string으로 변환
//         // pw 에 저장하기, pw 에 값이 비어있으면 저장, 값이 있으면 이어서 저장하는 삼항연산자
//         pwd += (pwd.equals("")) ? "" + cha + "" : "" + cha + "";
//      }
//      pwd_cf = "";
//      char[] secret_pw2 = tf_pwd.getPassword();
//      for (char cha2 : secret_pw2) {
//         Character.toString(cha2);
//         pwd_cf += (pwd_cf.equals("")) ? "" + cha2 + "" : "" + cha2 + "";

		// }

		setVisible(true);
		
		// 이벤트
		btn_signup.addActionListener(this);
		btn_cancel.addActionListener(this);
		btn_EmailAuth.addActionListener(this);
		btn_nickname_check.addActionListener(this);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(MarioSignup.this, "기입한 내용이모두 사라집니다 그래도나가시겠습니까?", "종료창",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					dispose();

				} else if (result == JOptionPane.NO_OPTION) {

				}
			}
		});

	}// MarioSignup ()

	@Override
	public void actionPerformed(ActionEvent e) {

		// 회원가입 이벤트
		if (e.getSource() == btn_signup) {
			// 회원가입버튼을 누르면 비밀번호가 일치하는지 , 중복확인과 인증번호 받았는지
			// 메소드만들기 위의 조건이맞을떄 dto의 값을 보내고 회원가입
			if (tf_age == null || tf_emailAccount == null || tf_name == null || tf_pwd == null || tf_pwd_check == null) {

				JOptionPane.showMessageDialog(this, "입력란을 확인해주세요");

			} else {

				CheckArticle();
				insertArticle();
				dispose();
			}

			// 취소버튼 이벤트
		} else if (e.getSource() == btn_cancel) {
			// 취소버튼누르면 x창과동일하게 경고메세지후나가기
			int result = JOptionPane.showConfirmDialog(MarioSignup.this, "기입한 내용이모두 사라집니다 그래도나가시겠습니까?", "종료창",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				dispose();
			}

			// 이메일 인증번호랑 중복화인이벤트
		} else if (e.getSource() == btn_EmailAuth) {
			// 창이뜨고 인증번호 입력과 확인버튼 아닐시 경고메세지뜨고 받을수있게
			// 이메일인증번호보내기
			// 이메일 인증이 완료되면 버튼이 비활성화된다 .(비활성화로 if문걸수있는지
			String pwd = "";
			// tf_pw 필드에서 패스워드를 얻어옴,
			char[] secret_pw = tf_pwd.getPassword(); // secret_pw 배열에 저장된 암호의 자릿수 만큼 for문 돌리면서 cha 에 한 글자씩 저장
			for (char cha : secret_pw) {
				Character.toString(cha); // cha 에 저장된 값 string으로 변환
				// pw 에 저장하기, pw 에 값이 비어있으면 저장, 값이 있으면 이어서 저장하는 삼항연산자
				pwd += (pwd.equals("")) ? "" + cha + "" : "" + cha + "";

			}

			// 아이디 중복확인이벤트
			String id = tf_emailAccount.getText();
			for (MarioDTO dto : dtoList) { // 전체db에서 비교 전체클라이언트에서비교를할것이기떄문에
				if (id.equals(dto.getClientAccount()) || pwd.equals(dto.getPassword())) {

				}

			}

			JOptionPane.showMessageDialog(MarioSignup.this, "없는아이디나 비밀번호와 아이디가 맞지않습니다 ", "경고",
					JOptionPane.ERROR_MESSAGE);
			tf_emailAccount.setText("");

			// 인증번호값 가져오기
			String email = JOptionPane.showInputDialog(this, "이메일인증번호를 입력해주세요", "이메일 인증번호",
					JOptionPane.INFORMATION_MESSAGE);
			if (email == null || email.length() == 0) {
				JOptionPane.showMessageDialog(MarioSignup.this, "인증번호를 입력해주세요", "경고", JOptionPane.ERROR_MESSAGE);
				// 같지않을떄 if 문

				// 잘성사되면버튼닫기 이메일인증번호완료후 버튼닫기
				btn_EmailAuth.setEnabled(false);
			}

			/// 닉네임중복 버튼
		} else if (e.getSource() == btn_nickname_check) {

			// dto 에서 닉네임정보가져오기
			String nickName_cf = tf_nickname.getText();
			for (MarioDTO dto : dtoList) { // 전체db에서 비교 전체클라이언트에서비교를할것이기떄문에
				if (nickName_cf.equals(dto.getNickname())) {
					JOptionPane.showMessageDialog(MarioSignup.this, "중복된닉네임입니다 다시입력해주세요.", "경고",
							JOptionPane.ERROR_MESSAGE);

				} else {
					// 잘 성사되면 버튼 닫기
					btn_nickname_check.setEnabled(false);
					return;
				}

			}

		}
	}

	private void insertArticle() { // 값보내기 dto 에

		// 데이터 받아오기
		// int seq = 0; 출력할떄만사용하기에 필요없다 .

		// 비밀번호받아오기
		String pwd = "";
		// tf_pw 필드에서 패스워드를 얻어옴,
		char[] secret_pw = tf_pwd.getPassword(); // secret_pw 배열에 저장된 암호의 자릿수 만큼 for문 돌리면서 cha 에 한 글자씩 저장
		for (char cha : secret_pw) {
			Character.toString(cha); // cha 에 저장된 값 string으로 변환
			// pw 에 저장하기, pw 에 값이 비어있으면 저장, 값이 있으면 이어서 저장하는 삼항연산자
			pwd += (pwd.equals("")) ? "" + cha + "" : "" + cha + "";

		}

		String id = tf_emailAccount.getText();
		// 패스워드는 이미 전환해서 String에 담겨있음
		String nickName = tf_nickname.getText().trim();
		String name = tf_name.getText().trim();

		int age = 0;

		if (tf_age != null) {

			age = Integer.parseInt((tf_age.getText() + ""));

			return;
		} else {
			age = 0;
		}

		int gender = 0;
		int person_info = 0;
		if (cb_person_info.isSelected())
			person_info = 1;
		if (checkBox_man.isSelected())
			gender = 0;
		else if (checkBox_woman.isSelected())
			gender = 1;
		int score = 0;
		String goalTime = null;
		int playerRank = 0;
		dtoList = new ArrayList<MarioDTO>();

		System.out.println("아이디" + id);
		System.out.println("비밀번호" + pwd);

		// MarioDTO
		MarioDTO signupdto = new MarioDTO();
		signupdto.setClientAccount(id);
		signupdto.setPassword(pwd);
		signupdto.setNickname(nickName);
		signupdto.setRealName(name);
		signupdto.setAge(age);
		signupdto.setGender(gender);
		signupdto.setInfoAgree(person_info);
		signupdto.setScore(score);
		signupdto.setGoalTime(goalTime);
		signupdto.setPlayerRank(playerRank);
		System.out.println("dto에 보내기");

		MarioDAO dao = MarioDAO.getInstance();
		int seq = dao.getSeq();// 시퀀스번호를 그냥받았는데 지금은 다시 DB에서 객체로 넣어주었다.
		signupdto.setSeq(seq);
		dao.insertArticle(signupdto);
		System.out.println("dao성공");

	}

	// model.set(listT.getSelectedIndex(), dto);
	// updateArticle()
	public void CheckArticle() {

		String pwd = "";
		// tf_pw 필드에서 패스워드를 얻어옴,
		char[] secret_pw = tf_pwd.getPassword(); // secret_pw 배열에 저장된 암호의 자릿수 만큼 for문 돌리면서 cha 에 한 글자씩 저장
		for (char cha : secret_pw) {
			Character.toString(cha); // cha 에 저장된 값 string으로 변환
			// pw 에 저장하기, pw 에 값이 비어있으면 저장, 값이 있으면 이어서 저장하는 삼항연산자
			pwd += (pwd.equals("")) ? "" + cha + "" : "" + cha + "";
		}
		String pwd_cf = "";
		char[] secret_pw2 = tf_pwd_check.getPassword();
		for (char cha2 : secret_pw2) {
			Character.toString(cha2);
			pwd_cf += (pwd_cf.equals("")) ? "" + cha2 + "" : "" + cha2 + "";

		}

		// 비밀번호 중복일떄 경고문
		if (!(pwd.equals(pwd_cf))) {
			JOptionPane.showMessageDialog(MarioSignup.this, "비밀번호가맞지않습니다", "경고", JOptionPane.ERROR_MESSAGE);
			tf_pwd.setText("");
			tf_pwd_check.setText("");

			System.out.println("비밀번호 일치기능 완료 " + pwd + "-----" + pwd_cf);
		}

		String id = tf_emailAccount.getText();
		// 패스워드는 이미 전화해서 String에 담겨있음
		String nickName = tf_nickname.getText();
		String name = tf_name.getText();
		String age = tf_age.getText();

		int gender = 0;
		int person_info = 0;
		if (cb_person_info.isSelected())
			person_info = 1;
		else
			person_info = -1;
		if (checkBox_man.isSelected())
			gender = 0;
		else if (checkBox_woman.isSelected())
			gender = 1;
		else
			gender = -1;

		// 항목에대한 구체화
		if (id.equals("")) {
			JOptionPane.showMessageDialog(MarioSignup.this, "아이디를 입력해주세요", "경고", JOptionPane.ERROR_MESSAGE);
		} else if (nickName.equals("")) {
			JOptionPane.showMessageDialog(MarioSignup.this, "비밀번호을입력해주세요", "경고", JOptionPane.ERROR_MESSAGE);

		} else if (pwd_cf.equals("")) {
			JOptionPane.showMessageDialog(MarioSignup.this, "비밀번호을입력해주세요", "경고", JOptionPane.ERROR_MESSAGE);

		} else if (name.equals("")) {
			JOptionPane.showMessageDialog(MarioSignup.this, "이름을입력해주세요", "경고", JOptionPane.ERROR_MESSAGE);
		} else if (age.equals("")) {
			JOptionPane.showMessageDialog(MarioSignup.this, "나이을입력해주세요", "경고", JOptionPane.ERROR_MESSAGE);

		} else if (gender == -1) {
			JOptionPane.showMessageDialog(MarioSignup.this, "성별을입력해주세요", "경고", JOptionPane.ERROR_MESSAGE);
		} else if (person_info == -1) {
			JOptionPane.showMessageDialog(MarioSignup.this, "수락을눌러주세요", "경고", JOptionPane.ERROR_MESSAGE);

		}

	}

	private void ArrayList() {
		// dtoList = new ArrayList<MarioDTO> ();

		String pwd = "";
		// tf_pw 필드에서 패스워드를 얻어옴,
		char[] secret_pw = tf_pwd.getPassword(); // secret_pw 배열에 저장된 암호의 자릿수 만큼 for문 돌리면서 cha 에 한 글자씩 저장
		for (char cha : secret_pw) {
			Character.toString(cha); // cha 에 저장된 값 string으로 변환
			// pw 에 저장하기, pw 에 값이 비어있으면 저장, 값이 있으면 이어서 저장하는 삼항연산자
			pwd += (pwd.equals("")) ? "" + cha + "" : "" + cha + "";
		}
		String pwd_cf = "";
		char[] secret_pw2 = tf_pwd_check.getPassword();
		for (char cha2 : secret_pw2) {
			Character.toString(cha2);
			pwd_cf += (pwd_cf.equals("")) ? "" + cha2 + "" : "" + cha2 + "";

		}

		// 패스워드는 이미 전화해서 String에 담겨있음
		String nickName = tf_nickname.getText();
		String name = tf_name.getText();
		String age2 = tf_age.getText();

	}

	public static void main(String[] args) {
		new MarioSignup();
	}

	@Override
	public void textValueChanged(TextEvent e) {
		// TODO Auto-generated method stub

	}

}