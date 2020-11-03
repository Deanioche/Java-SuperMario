package mario.Frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mario.dao.MarioDAO;
import mario.dto.MarioDTO;

import javax.swing.JPasswordField;

public class MarioLogin extends JFrame implements ActionListener {
	private JLabel label_id, label_pwd;
	private JTextField tf_id;
	private JPasswordField tf_pwd;
	private JButton btn_login, btn_sign_up;
	private MarioDAO dao;

	// private Dimension Dimension_windowSize =
	// Toolkit.getDefaultToolkit().getScreenSize(); 로그인창이라 전체화면할필요없을거같아서 1000으로맞춤 근데
	// 전체화면 좌표 이용할떄 1000단위로안끊어지면불편해서 참고바람

	private ImageIcon ic_bg_login = new ImageIcon("Image/background/loginBackground.jpg"); // 배경아이콘
	private Image im_bg_login = ic_bg_login.getImage(); // 이미지에넣기

	private Font f1, f2, f3, f4;
	private List<MarioDTO> dtoList = new ArrayList<MarioDTO>();

	public MarioLogin() {

		super("Login");
		// 생성

		// 페널로 우선순위를 잡는다 .
		f1 = new Font("Hobo BT", Font.BOLD, 20);
		f2 = new Font("Hobo BT", Font.BOLD, 15);
		f3 = new Font("Forte", Font.BOLD, 10);
		f4 = new Font("Forte", Font.BOLD, 12);

		// 라벨생성
		label_id = new JLabel("ID"); // 이메일주소입력하라고 표시남기기
		label_pwd = new JLabel("Password");

		// 텍스트필드생성
		tf_id = new JTextField(25);
		tf_pwd = new JPasswordField(25);
		btn_login = new JButton("LOGIN");
		btn_sign_up = new JButton("Sign up");
		// 페인트패널생성
		JPanel background = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(im_bg_login, 0, 0, null);
				setOpaque(false);
			}
		};
		// 패널 레이아웃패널 null값주기
		background.setLayout(null);

		// 좌표
		label_id.setBounds(50, 820, 200, 200);
		label_pwd.setBounds(320, 820, 200, 200);

		tf_pwd.setBounds(430, 900, 140, 40);
		tf_id.setBounds(100, 900, 140, 40);

		btn_login.setBounds(750, 900, 100, 40);
		btn_sign_up.setBounds(850, 900, 120, 40);

		// 폰트
		label_id.setFont(f1);
		label_pwd.setFont(f1);
		tf_id.setFont(f2);
		btn_login.setFont(f3);
		btn_sign_up.setFont(f4);

		tf_pwd.setForeground(Color.white);
		tf_id.setForeground(Color.white);
		btn_login.setBackground(Color.getHSBColor(150, 100, 240));
		btn_sign_up.setBackground(Color.getHSBColor(150, 100, 240));

		// 테두리속없애기
		tf_pwd.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		tf_id.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		tf_id.setOpaque(false);
		tf_pwd.setOpaque(false);

		// 패널잡기
		background.add(label_id);
		background.add(label_pwd);

		background.add(tf_id);
		background.add(tf_pwd);

		background.add(btn_login);
		background.add(btn_sign_up);

		// 컨테이너
		Container c = this.getContentPane();
		c.add(background);

		// 모든 레코드를 꺼내서 JList에 뿌리기

		setBounds(0, 0, 1000, 1000);
		// 화면가운데위치
		setLocationRelativeTo(null);
		setVisible(true);
		// 창고정
		setResizable(false);
		// 화면 닫지말기
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		// 이벤트
		btn_login.addActionListener(this);
		btn_sign_up.addActionListener(this);

		// 마우스좌표확인 지울거임
//      addMouseListener(new MouseAdapter() {
//         @Override
//         public void mousePressed(MouseEvent e) {
//            System.out.println(e);
//
//         }
//
//      });

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(MarioLogin.this, "종료하시겠습니까?", "종료창",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {

					System.exit(0);
				} else if (result == JOptionPane.NO_OPTION) {
					return;
				}
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btn_login) {
			
			dao = MarioDAO.getInstance();
			
			String id = tf_id.getText();
			// 암호화값 string에 담기
			String pwd = "";
			// tf_pw 필드에서 패스워드를 얻어옴,
			char[] secret_pw = tf_pwd.getPassword(); // secret_pw 배열에 저장된 암호의 자릿수 만큼 for문 돌리면서 cha 에 한 글자씩 저장
			for (char cha : secret_pw) {
				Character.toString(cha); // cha 에 저장된 값 string으로 변환
				// pw 에 저장하기, pw 에 값이 비어있으면 저장, 값이 있으면 이어서 저장하는 삼항연산자
				pwd += (pwd.equals("")) ? "" + cha + "" : "" + cha + "";
			}

			
			if ((id.equals("") || (pwd.equals(""))))
				JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력해주십시요", "경고", JOptionPane.WARNING_MESSAGE);
			tf_pwd.setText("");
			tf_id.setText("");

			// 아이디 중복확인이벤트 중복아이디가있을떄 경고하기

			for (MarioDTO dto : dao.getMarioList()) { // 전체db에서 비교 전체클라이언트에서비교를할것이기떄문에
				
				System.out.println("dto.getClientAccount() : " + dto.getClientAccount());
				System.out.println("dto.getPassword() : " + dto.getPassword());
				
				if (id.equals(dto.getClientAccount()) && pwd.equals(dto.getPassword())) {
					new MarioStage1();
					dispose();
					System.out.println("break!");
					break;
					
				}else {
					JOptionPane.showMessageDialog(MarioLogin.this, "없는아이디나 비밀번호와 아이디가 맞지않습니다 ", "경고",
							JOptionPane.ERROR_MESSAGE);
				}
					
			}
			
			
			tf_id.setText("");

			insertArticle();

		} else if (e.getSource() == btn_sign_up) {
			new MarioSignup();
		}

	}

	private void insertArticle() {
		String id = tf_id.getText();
		// 암호화값 string에 담기
		String pwd = "";
		// tf_pw 필드에서 패스워드를 얻어옴,
		char[] secret_pw = tf_pwd.getPassword(); // secret_pw 배열에 저장된 암호의 자릿수 만큼 for문 돌리면서 cha 에 한 글자씩 저장
		for (char cha : secret_pw) {
			Character.toString(cha); // cha 에 저장된 값 string으로 변환
			// pw 에 저장하기, pw 에 값이 비어있으면 저장, 값이 있으면 이어서 저장하는 삼항연산자
			pwd += (pwd.equals("")) ? "" + cha + "" : "" + cha + "";
		}
		System.out.println(pwd);
		// 데이터받기

		// 데이터넣기
		MarioDTO logindto = new MarioDTO();
		logindto.setClientAccount(id);
		logindto.setPassword(pwd);

		// 중복된아이디입니다 다이얼로그뜨게하기

	}

	public static void main(String[] args) {
		new MarioLogin();
	}

}