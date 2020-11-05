package mario.Frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import mario.dao.MarioDAO;
import mario.dao.MarioDBTLogIn;
import mario.dto.MarioDTO;

public class MarioLogin extends JFrame implements ActionListener {
	private JLabel label_id, label_pwd;
	private JTextField tf_emailAccount;
	private JPasswordField tf_pwd;
	private JButton btn_login, btn_sign_up, btn_dataManage;
	private MarioDAO dao;
	private JComboBox<String> comboBox_Email;

	private boolean loginSuccess = false;

	private ImageIcon ic_bg_login = new ImageIcon("Image/background/loginBackground.jpg"); // 배경아이콘
	private Image im_bg_login = ic_bg_login.getImage(); // 이미지에넣기

	private List<MarioDTO> dtoList = new ArrayList<MarioDTO>();
	
	
	
	/*************************************************************************************************************/

	// 생성자
	
	public MarioLogin() {

		super("Login");
		
		
		/* ******************************************************************* */
		
		

		// 라벨생성
		label_id = new JLabel("ID"); // 이메일주소입력하라고 표시남기기
		label_pwd = new JLabel("Password");

		// 텍스트필드생성
		tf_emailAccount = new JTextField(25);
		tf_pwd = new JPasswordField(25);
		btn_login = new JButton("LOGIN");
		btn_sign_up = new JButton("Sign up");
		btn_dataManage = new JButton("회원 데이터 관리");
		
		// 콤보박스 생성
		comboBox_Email = new JComboBox<String>( new String[] {" @ naver.com", " @ gmail.com"} );

		// 페인트패널생성
		JPanel background = new JPanel(null) {
			public void paintComponent(Graphics g) {
				g.drawImage(im_bg_login, 0, 0, null);
				setOpaque(false);
			}
		};
		
		
		
		/* ******************************************************************* */
		
		// 항목 좌표 및 꾸미기

		
		// 좌표
		label_id.setBounds(50, 820, 200, 200);
		label_pwd.setBounds(430, 820, 200, 200);

		tf_pwd.setBounds(550, 900, 140, 40);
		tf_emailAccount.setBounds(100, 900, 140, 40);
		comboBox_Email.setBounds(250, 900, 160, 40);

		btn_login.setBounds(750, 890, 100, 40);
		btn_sign_up.setBounds(850, 890, 120, 40);
		btn_dataManage.setBounds(750, 930, 220, 30);

		// 폰트
		label_id.setFont(new Font("Hobo BT", Font.BOLD, 20));
		label_pwd.setFont(new Font("Hobo BT", Font.BOLD, 20));
		tf_emailAccount.setFont(new Font("Hobo BT", Font.BOLD, 20));
		btn_login.setFont(new Font("Hobo BT", Font.BOLD, 15));
		btn_sign_up.setFont(new Font("Hobo BT", Font.BOLD, 15));
		comboBox_Email.setFont(new Font("Hobo BT", Font.BOLD, 17));
		btn_dataManage.setFont(new Font("고딕체", Font.BOLD, 13));

		tf_pwd.setForeground(new Color(250, 100, 100));
		tf_emailAccount.setForeground(new Color(250, 100, 100));
		btn_login.setBackground(Color.getHSBColor(150, 100, 240));
		btn_sign_up.setBackground(Color.getHSBColor(150, 100, 240));
		btn_dataManage.setBackground(Color.getHSBColor(150, 100, 240));
		
		btn_dataManage.setFocusable(false);
		btn_login.setFocusable(false);
		btn_sign_up.setFocusable(false);

		// 테두리속없애기
		tf_pwd.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		tf_emailAccount.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		tf_emailAccount.setOpaque(true);
		tf_pwd.setOpaque(true);
		
		
		
		/* ******************************************************************* */

		// 패널잡기
		background.add(label_id);
		background.add(label_pwd);

		background.add(tf_emailAccount);
		background.add(tf_pwd);
		background.add(comboBox_Email);

		background.add(btn_login);
		background.add(btn_sign_up);
		background.add(btn_dataManage);


		setBounds(0, 0, 1000, 1000);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		
		
		/* ******************************************************************* */
		
		// 이벤트
		
		btn_login.addActionListener(this);
		btn_sign_up.addActionListener(this);
		btn_dataManage.addActionListener(this);
		tf_pwd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				System.out.println("로그인 눌림");
				btn_login.doClick();
				}
				
			}
		});

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
	
	
	/*************************************************************************************************************/
	

	@Override
	public void actionPerformed(ActionEvent e) {

		// 로그인 버튼 이벤트
		
		if (e.getSource() == btn_login) {
			
			/* 빈칸이 없을때 작동 */
			if(tf_emailAccount.getText().length() != 0 && new String(tf_pwd.getPassword()).length() != 0) {
				
				/* 어드민 계정 */
				if (tf_emailAccount.getText().equals("admin") && new String(tf_pwd.getPassword()).equals("1234")) {
					dispose();
					new MarioClient();
					System.out.println("로그인 성공!");
					loginSuccess = true;
					return;
				}
				
				/* dao 불러와서 비교 */
				dao = MarioDAO.getInstance();

				for (MarioDTO dto : dao.getMarioList()) { 

					String[] checkId = dto.getClientAccount().split("@"); 
					if ((tf_emailAccount.getText().equals(checkId[0]) 
							&& new String(tf_pwd.getPassword()).equals(dto.getPassword()))) {
						new MarioClient();
						dispose();
						System.out.println("로그인 성공!");
						loginSuccess = true;
						break;

					}

				}
				
				/* 빈칸 로그인  */
			}else {
				
				JOptionPane.showMessageDialog(this, "입력란을 확인해주세요.");
			}
			/* 로그인  실패  */
			if (!loginSuccess) {
				JOptionPane.showMessageDialog(this, "이메일 계정 또는 비밀번호를 확인해주세요");
			}

			tf_emailAccount.setText("");

			MarioDTO logindto = new MarioDTO();
			logindto.setClientAccount(tf_emailAccount.getText());
			logindto.setPassword(new String(tf_pwd.getPassword()));
			
			
			
			/* ******************************************************************* */
			
			/* 회원가입  */
		} else if (e.getSource() == btn_sign_up) {
			new MarioSignup();
			
			/* 데이터 관리창  */
		}else if (e.getSource() == btn_dataManage) {
	        new MarioDBTLogIn().event();
		}

	}
	

	
	/*************************************************************************************************************/

	public static void main(String[] args) {
		new MarioLogin();
	}

}