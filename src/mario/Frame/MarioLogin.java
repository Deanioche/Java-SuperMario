package mario.Frame;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
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

import mario.ImageBox;
import mario.Server.Protocols;
import mario.dao.MarioDAO;
import mario.dao.MarioDBTLogIn;
import mario.dto.ArrayDTO;
import mario.dto.BalloonDTO;
import mario.dto.MarioDTO;

class MyPanel extends JPanel {

	Image image;

	MyPanel() {
		image = Toolkit.getDefaultToolkit().createImage("Image/backG.gif");
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, this);
		}
	}
}

public class MarioLogin extends JFrame implements ActionListener {

	/* 프레임상에 출력될 개체들 */
	private JLabel label_id, label_pwd, label_ip, label_port;
	private JTextField tf_emailAccount, tf_port;
	private JPasswordField tf_pwd;
	private JButton btn_login, btn_sign_up, btn_dataStorage, btn_ServerSetting, btn_save_ServerSetting;
	private MarioDAO dao;
	private JComboBox<String> comboBox_Email, comboBox_IP_Address;

	/* 서버 객체 */
	private Socket socket;
	public ObjectOutputStream oos;
	public ObjectInputStream ois;

	public static boolean serverConnected = false;
	public static MarioLogin mLogin;

	/* 서버세팅, 로그인이 완료되었는지 확인 */
	boolean connectSuccess = false;
	boolean loginSuccess = false;
	private boolean ServerSet = false;

	/* 배경 */
	private ImageIcon ic_bg_login = new ImageIcon("Image/backG.gif"); // 배경아이콘
	private Image im_bg_login = ic_bg_login.getImage(); // 이미지에넣기

	/* 다른 클래스로부터 받는 객체 저장 */
	public static List<MarioDTO> dtoList = new ArrayList<MarioDTO>();
	public static List<MarioDTO> list_PlayerInfo = new ArrayList<MarioDTO>();
	public MarioDTO clientData;

	/* 서버세팅으로 입력받는 ip와 포트 */
	private String ipAddress;
	private int port = 0;

	ArrayDTO arraydto;

	/*************************************************************************************************************/

	// getter

	/* 회원가입이 완료될시 로그인계정과 콤보박스가 자동 세팅되고 비밀번호로 커서가 들어감. */
	public void fill_login_emailAccount(String email, int comboIndex) {

		tf_emailAccount.setText(email);
		tf_pwd.requestFocus();
		comboBox_Email.setSelectedIndex(comboIndex);
	}

	/*************************************************************************************************************/

	// 생성자

	public MarioLogin() {

		super("Login");

		this.mLogin = MarioLogin.this;
		arraydto = new ArrayDTO();
		/* ******************************************************************* */

		// 라벨생성
		label_id = new JLabel("ID"); // 이메일주소입력하라고 표시남기기
		label_pwd = new JLabel("Password");
		label_ip = new JLabel("IP Address");
		label_port = new JLabel("Port");

		// 텍스트필드생성
		tf_emailAccount = new JTextField(25);
		tf_port = new JTextField("9500", 25);
		tf_pwd = new JPasswordField(25);
		btn_login = new JButton("LOGIN");
		btn_sign_up = new JButton("Sign up");
		btn_dataStorage = new JButton("Data Storage");
		btn_ServerSetting = new JButton("Server Setting");
		btn_save_ServerSetting = new JButton("Save / Connect");

		// 콤보박스 생성
		comboBox_Email = new JComboBox<String>(new String[] { " @ naver.com", " @ gmail.com" });
		comboBox_IP_Address = new JComboBox<String>(
				new String[] { "192.168.0.28", "192.168.1.3", "192.168.0.98", "직접 입력 후 반드시 엔터" });
		comboBox_IP_Address.setEditable(true);
		comboBox_IP_Address.setSelectedIndex(0);

		// 페인트패널생성
//		MyPanel background = new MyPanel(null); // TODO
		
		JPanel background = new JPanel(null) {
			
			Image image = Toolkit.getDefaultToolkit().createImage("Image/backG.gif");
			
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (image != null) {
					g.drawImage(ImageBox.background, 0, 0, this);
					g.drawImage(image, 0, 0, 500, 400, this);
				}
			}
		};
		
		background.setBackground(new Color(0,0,0));

		/* ******************************************************************* */

		// 항목 좌표 및 꾸미기 (위부터 아래로)

		/* 데이터 관리 버튼 */
		btn_dataStorage.setBounds(530, 15, 120, 25);

		/* 서버 접속 세팅 버튼 */
		btn_ServerSetting.setBounds(530, 45, 120, 25);
		label_ip.setBounds(500, 60, 200, 25);
		comboBox_IP_Address.setBounds(500, 90, 160, 25);
		label_port.setBounds(500, 123, 200, 25);
		tf_port.setBounds(500, 150, 160, 25);
		btn_save_ServerSetting.setBounds(535, 180, 125, 25);

		/* 로그인, 회원가입 */
		label_id.setBounds(500, 300, 200, 25);
		tf_emailAccount.setBounds(500, 330, 160, 25);
		comboBox_Email.setBounds(500, 360, 160, 30);
		label_pwd.setBounds(500, 400, 200, 25);
		tf_pwd.setBounds(500, 430, 160, 25);

		btn_login.setBounds(500, 460, 160, 30);
		btn_sign_up.setBounds(500, 495, 160, 30);

		/* ******************************************************************* */

		// 폰트
		label_id.setFont(new Font("Hobo BT", Font.BOLD, 20));
		label_pwd.setFont(new Font("Hobo BT", Font.BOLD, 20));
		label_ip.setFont(new Font("Hobo BT", Font.BOLD, 20));
		label_port.setFont(new Font("Hobo BT", Font.BOLD, 20));
		tf_emailAccount.setFont(new Font("Hobo BT", Font.BOLD, 20));
		tf_port.setFont(new Font("Hobo BT", Font.BOLD, 12));
		btn_login.setFont(new Font("Hobo BT", Font.BOLD, 20));
		btn_sign_up.setFont(new Font("Hobo BT", Font.BOLD, 20));
		comboBox_Email.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		btn_dataStorage.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		comboBox_IP_Address.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btn_ServerSetting.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btn_save_ServerSetting.setFont(new Font("맑은 고딕", Font.BOLD, 12));

		tf_pwd.setForeground(new Color(250, 100, 100));
		tf_emailAccount.setForeground(new Color(250, 100, 100));
		btn_login.setBackground(Color.getHSBColor(150, 100, 240));
		btn_sign_up.setBackground(Color.getHSBColor(150, 100, 240));
		btn_dataStorage.setBackground(Color.getHSBColor(150, 100, 240));
		btn_ServerSetting.setBackground(Color.getHSBColor(150, 100, 240));
		btn_save_ServerSetting.setBackground(Color.getHSBColor(150, 100, 240));
		
		label_id.setForeground(new Color(240, 240, 240));
		label_pwd.setForeground(new Color(240, 240, 240));
		label_ip.setForeground(new Color(240, 240, 240));
		label_port.setForeground(new Color(240, 240, 240));

		btn_dataStorage.setFocusable(false);
		btn_login.setFocusable(false);
		btn_sign_up.setFocusable(false);
		btn_ServerSetting.setFocusable(false);
		btn_save_ServerSetting.setFocusable(false);

		// 테두리속없애기
		tf_pwd.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		tf_emailAccount.setBorder(javax.swing.BorderFactory.createEtchedBorder());

		tf_emailAccount.setOpaque(true);
		tf_pwd.setOpaque(true);

		/* ******************************************************************* */

		// ServerSetting 버튼 누르기 전에는 안보임

		comboBox_IP_Address.setVisible(false);
		tf_port.setVisible(false);
		label_port.setVisible(false);
		label_ip.setVisible(false);
		btn_save_ServerSetting.setVisible(false);

		/* ******************************************************************* */

		// 생성된 개체들 프레임에 추가

		background.add(btn_dataStorage);

		background.add(btn_ServerSetting);
		background.add(label_ip);
		background.add(comboBox_IP_Address);
		background.add(label_port);
		background.add(tf_port);
		background.add(btn_save_ServerSetting);

		background.add(label_id);
		background.add(tf_emailAccount);
		background.add(comboBox_Email);
		background.add(label_pwd);
		background.add(tf_pwd);

		background.add(btn_login);
		background.add(btn_sign_up);

		add(background);

		/* ******************************************************************* */

		// 윈도우 창 설정

		setBounds(0, 0, 680, 580);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		/* ******************************************************************* */

		// 이벤트

		btn_login.addActionListener(this);
		btn_sign_up.addActionListener(this);
		btn_dataStorage.addActionListener(this);
		btn_ServerSetting.addActionListener(this);
		btn_save_ServerSetting.addActionListener(this);

		tf_pwd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("로그인 눌림");
					btn_login.doClick();
				}

			}
		});

		tf_port.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("서버 세팅 Save 눌림");
					btn_save_ServerSetting.doClick();
				}

			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(MarioLogin.this, "종료하시겠습니까?", "종료창",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION && !serverConnected && !loginSuccess) {

					System.exit(0);

				} else if (result == JOptionPane.YES_OPTION && serverConnected) {

					MarioDTO sendDTO = new MarioDTO();
					sendDTO.setProtocol(Protocols.EXIT);
					loginSuccess = false;

					try {
						oos.writeObject(sendDTO);
						oos.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				} else if (result == JOptionPane.NO_OPTION) {
					return;
				}
			}
		});

	}

	/*************************************************************************************************************/

	@Override
	public void actionPerformed(ActionEvent e) {

		/* ******************************************************************* */
		// 로그인 버튼 이벤트

		if (e.getSource() == btn_login) {

			/* 빈칸이 없으면 진행 */
			if (tf_emailAccount.getText().length() != 0 && new String(tf_pwd.getPassword()).length() != 0) {

				/* 접속중이 아니면 세팅창 오픈 */
				if (!serverConnected) {

					btn_ServerSetting.doClick();
//					btn_save_ServerSetting.doClick();
					JOptionPane.showMessageDialog(this, "서버 세팅을 확인 후, Connect 버튼을 눌러주세요");
					return;
				}

				/*
				 * 로그인 비번이 빈칸이 아니고 서버 세팅이 완료되면 서버에 접속한다.
				 */

				/* 서버에서 받은 dtoList로 비교 */

				for (MarioDTO dto : dtoList) {

					String[] checkId = dto.getClientAccount().split("@");
					if ((tf_emailAccount.getText().equals(checkId[0])
							&& new String(tf_pwd.getPassword()).equals(dto.getPassword()))) {

						/* 로그인한 클라이언트 정보 저장 */
						clientData = dto;

						new MarioClient(MarioLogin.this);
						setVisible(false);
//						connectSuccess = true;
						System.out.println("로그인 성공!");
						break;

					}

				}

				/* 빈칸 로그인 */
			} else {

				JOptionPane.showMessageDialog(this, "입력란을 확인해주세요.");
			}

			/* 로그인 실패 */
			if (new String(tf_pwd.getPassword()).length() != 0) {
//				JOptionPane.showMessageDialog(this, "이메일 계정 또는 비밀번호를 확인해주세요");
			}

			tf_pwd.setText("");

			/* ******************************************************************* */

			/* 회원가입 */
		} else if (e.getSource() == btn_sign_up) {

			/* 서버에 접속 */
			if (!serverConnected) {
				btn_ServerSetting.doClick();
				JOptionPane.showMessageDialog(this, "서버 세팅을 확인 후, Connect 버튼을 눌러주세요");
				return;
			}

			// 회원가입
			new MarioSignup(MarioLogin.this);

			/* ******************************************************************* */

			/* 데이터 관리창 */
		} else if (e.getSource() == btn_dataStorage) {

			/* 서버에 접속 */
			if (!serverConnected) {
				btn_ServerSetting.doClick();
				JOptionPane.showMessageDialog(this, "서버 세팅을 확인 후, Connect 버튼을 눌러주세요");
				return;
			}

			// 데이터관리 로그인
			new MarioDBTLogIn(MarioLogin.this).event();

			/* ******************************************************************* */

			/* 서버 접속 관리창 */
		} else if (e.getSource() == btn_ServerSetting) {

			/*
			 * 서버세팅할 항목들이 표시되고 btn_ServerSetting 버튼은 사라짐 btn_save_ServerSetting 버튼이 보인다.
			 */

			comboBox_IP_Address.setVisible(true);
			tf_port.setVisible(true);
			label_port.setVisible(true);
			label_ip.setVisible(true);

			btn_ServerSetting.setVisible(false);
			btn_save_ServerSetting.setVisible(true);

			/* ******************************************************************* */

			/* 서버 설정 저장 */
		} else if (e.getSource() == btn_save_ServerSetting) {

			/*
			 * 입력한 항목들이 저장된다. 서버세팅할 항목들이 사라지고 btn_save_ServerSetting 버튼도 사라지고
			 * btn_ServerSetting 버튼이 다시 생긴다.
			 */

			/* ComboBox에서 선택된 문자열은 "."을 포함하고 "."을 제외한 모든 문자는 숫자여야한다. */
			if (((String) comboBox_IP_Address.getSelectedItem()).contains(".")
					&& ((String) comboBox_IP_Address.getSelectedItem()).replace(".", "").matches("\\d*")) {

				ipAddress = (String) comboBox_IP_Address.getSelectedItem();
			} else {
				JOptionPane.showMessageDialog(this, "ip 주소를 확인해주세요");
				return;
			}

			/* 포트는 빈칸이 아니고 모두 숫자여야한다. */
			if (tf_port.getText().length() != 0 && tf_port.getText().matches("\\d*")) {
				port = Integer.parseInt(tf_port.getText());
			} else {
				JOptionPane.showMessageDialog(this, "Port를 확인해주세요");
				return;
			}

			System.out.println("btn_save_ServerSetting : ipAddress : " + ipAddress + " / port : " + port);

			comboBox_IP_Address.setVisible(false);
			tf_port.setVisible(false);
			label_port.setVisible(false);
			label_ip.setVisible(false);
			btn_save_ServerSetting.setVisible(false);
			btn_ServerSetting.setVisible(true);

			ServerSet = true;

			connectServer();

		}

	}

	/********************************************************************************************/

	// 서버 접속 메소드

	public void connectServer() {

		try {

			/* ******************************************************************* */

			// 서버와 연결

			/* 서버 접속 timeout : 1초 */
			socket = new Socket();
			socket.connect(new InetSocketAddress(ipAddress, port), 1000);

			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			System.out.println("MarioClient().init() : socket, oos, ois : 생성 완료");

			serverConnected = true;

			/* 버튼 색,텍스트 변경 */
			btn_save_ServerSetting.setText("Save / Connect");
			btn_ServerSetting.setBackground(new Color(100, 200, 250));
			btn_ServerSetting.setText("서버 접속 완료!");
			btn_save_ServerSetting.setBackground(Color.getHSBColor(150, 100, 240));
			btn_save_ServerSetting.setText("Save / Connect");

			/* ******************************************************************* */

			// 서버로 CONNECT 보내기

			MarioDTO dto = new MarioDTO();
			dto.setProtocol(Protocols.CONNECT);

			oos.writeObject(dto);
			oos.flush();

			System.out.println(" # 서버로 CONNECT 전송 완료");

			/* ******************************************************************* */

			// 스레드 생성 & 시작

			Thread loginThread = new Thread(new Runnable() {

				@Override
				public synchronized void run() {

					MarioDTO objectDTO = null;
//				ListDTO listDTO = null; // TODO

					while (serverConnected) {
						dataFromServer(objectDTO);
					}
				}

			});

			loginThread.start();
			System.out.println(" # 데이터 수신 스레드 시작");

			/* ******************************************************************* */

		} catch (IOException e) {
//		e.printStackTrace();
			btn_save_ServerSetting.setBackground(new Color(250, 150, 50));
//		btn_save_ServerSetting.setText("Connect");
			System.out.println("서버 접속 실패!");
			btn_ServerSetting.doClick();
			serverConnected = false;

		}

	} // connectServer();

	/*************************************************************************************************************/

	// 서버로부터 데이터 수신

	private void dataFromServer(MarioDTO dto) {

		try {

			/* ******************************************************************* */
			dto = (MarioDTO) ois.readObject();

//				 System.out.println("dataFromServer : dto = (MarioDTO) ois.readObject() : 성공 ");

			/* ******************************************************************* */
			// 좌표 수신
			if (dto.getProtocol() == Protocols.MOVE && loginSuccess) {

				arraydto.setNickname(dto.getArray_nickname());
				arraydto.setCoordinate(dto.getArray_coordinate());

//						if(arraydto.getCoordinate().length != 0) {
//							for(int i = 0; i < arraydto.getCoordinate().length; i++) {
//								 
//								String nick = arraydto.getNickname()[i];
//								 int x = arraydto.getCoordinate()[i][0];
//								 int y = arraydto.getCoordinate()[i][1];
//								 int n = arraydto.getCoordinate()[i][2];
//								 
//							System.out.println("array x, y, n : " + nick + ":" + x + " , " + y + ", " + n);
//						}
//					}// for;

			} // ArrayDTO

			/* ******************************************************************* */

//				else if(objectDTO instanceof MarioDTO){
//					
//				// 메세지 받기
//				MarioDTO dto = (MarioDTO)objectDTO;

			else if (dto.getProtocol() == Protocols.SEND) {

				if (dto.getChatMessage() != null) {
					System.out.println("dto.getChatMessage() : " + dto.getChatMessage());
					MarioClient.textArea_Chat.append(dto.getChatMessage() + "\n");
					MarioClient.textArea_Chat.setCaretPosition(MarioClient.textArea_Chat.getText().length());

				}

			} // SEND

			/* ******************************************************************* */
			// 입장

			else if (dto.getProtocol() == Protocols.JOIN && loginSuccess) {

				MarioClient.textArea_Chat.append(dto.getChatMessage() + "\n");
				MarioClient.textArea_Chat.setCaretPosition(MarioClient.textArea_Chat.getText().length());

			} // JOIN

			/* ******************************************************************* */
			// 퇴장

			else if (dto.getProtocol() == Protocols.EXIT && loginSuccess) {

				serverConnected = false;
				ois.close();
				oos.close();
				socket.close();
				
				System.exit(0);

			} // EXIT

			/* ******************************************************************* */
			// 접속 - DB 리스트 수신

			else if (dto.getProtocol() == Protocols.CONNECT) {

				dtoList = dto.getList_PlayerInfo();

				if (dtoList != null) {
					for (MarioDTO data : dtoList) {
						System.out.println("2 data : " + data);
						System.out.println("3 data.getNickname() : " + data.getNickname());
					}
				} else {
					System.out.println("1 dtoList : " + dtoList);
				}

				// TODO DB 연결 및 수신 추가

			} // CONNECT

			/*
			 * # 사용 빈도수가 높은 순서대로 MOVE > SEND > JOIN > EXIT > CONNECT
			 * 
			 * # 프로토콜 MOVE : 좌표 수신 SEND : 메세지 수신 JOIN : 입장 EXIT : 퇴장 CONNECT : 접속
			 */

			// else if(objectDTO instanceof MarioDTO);

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	} // dataFromServer();

	/*************************************************************************************************************/
	public static void main(String[] args) {
		new MarioLogin();
	}

}