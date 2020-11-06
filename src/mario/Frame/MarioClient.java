package mario.Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import mario.ImageBox;
import mario.Server.Protocols;
import mario.dto.MarioDTO;

public class MarioClient extends JFrame implements ActionListener, Runnable {

	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;
	
	
	/* 서버 객체  */
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	/* 서버로부터 받아올 객체  */
	private List<MarioDTO> list_PlayerInfo;
	
	
	/* 필드 객체  */
	private MarioCanvas marioCanvas;
	private String nickname = "guest";
	private JTextArea textArea_Chat;
	private JTextField textField_Chat;
	private JLabel label_Timer_Minute, label_Timer_Second, label_Timer_MiliSec, label_Elapsed_Time;

	private JTable scoreTable;
	public DefaultTableModel model;
	public JButton btn_send, btn_start;
	private Thread clientThread;
	
	/* 타이머  */
	Thread timerThread;
	public boolean timerStart = false;
	private int timer_Minute = 0;
	private int timer_Second = 0;
	private int timer_MiliSec = 0;
	

	/**********************************************************************************************/

	
	// 생성자
	
	public MarioClient() {
		super("Mario");

		/* 다른 클래스 생성 */
		new ImageBox();
		marioCanvas = new MarioCanvas(MarioClient.this);

		/* ******************************************************************* */

		// 표시될 객체 생성

		

		/* 라벨 */
		label_Timer_Second = new JLabel("00 : ");
		label_Timer_Minute = new JLabel("00 : ");
		label_Elapsed_Time = new JLabel("");
		label_Timer_MiliSec = new JLabel("00ms");

		/* 채팅창 텍스트필드 */
		textField_Chat = new JTextField("");
		textArea_Chat = new JTextArea();
		textArea_Chat.setEditable(false);
		
		/* 채팅창 스크롤 */
		JScrollPane scroll_chat = new JScrollPane(textArea_Chat);

		/* 버튼 */
		btn_start = new JButton("게임생성");
		btn_send = new JButton("보내기");
		btn_start = new JButton("시작");

		/* JTable 랭킹창 */
		Vector<String> vector = new Vector<String>();
		vector.add("Rank");
		vector.add("Name");
		vector.add("Complete");
		vector.add("Flag");
		/* 랭킹, 닉네임, 진행도, 깃발 획득횟수 */

		model = new DefaultTableModel(vector, 0);
		scoreTable = new JTable(model);
		scoreTable.getColumn("Rank").setPreferredWidth(55);
		scoreTable.getColumn("Flag").setPreferredWidth(35);
		scoreTable.setEnabled(false);
		JScrollPane jsc = new JScrollPane(scoreTable);

		/* ******************************************************************* */

		// 객체 설정

		label_Timer_Minute.setFont(new Font("MD개성체", Font.BOLD, 20));
		label_Timer_Second.setFont(new Font("MD개성체", Font.BOLD, 20));
		label_Elapsed_Time.setFont(new Font("MD개성체", Font.BOLD, 20));
		label_Timer_MiliSec.setFont(new Font("MD개성체", Font.BOLD, 20));

		/* 랭킹창 사이즈 */
		jsc.setPreferredSize(new Dimension(250, 200));

		scroll_chat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		/* 채팅창하단 패널 */
		JPanel p_s_text = new JPanel(new BorderLayout());
		p_s_text.add("Center", textField_Chat);
		p_s_text.add("East", btn_send);
		p_s_text.setBounds(200, 400, 400, 300);

		/* 채팅창 패널 */
		JPanel p_text = new JPanel(new BorderLayout());
		p_text.add("South", p_s_text);
		p_text.add("Center", scroll_chat);

		/* 점수 패널 */
		JPanel p_score = new JPanel(new BorderLayout());
		p_score.add("Center", p_text);
		p_score.add("South", btn_start);
		p_score.add("North", jsc); // 스크롤넣음

		/* 상단 패널 */
		JPanel northPanel = new JPanel(new BorderLayout());

		/* 타이머 패널 */
		JPanel panel_Timer = new JPanel(new FlowLayout());
		panel_Timer.add(label_Elapsed_Time);
		panel_Timer.add(label_Timer_Minute);
		panel_Timer.add(label_Timer_Second);
		panel_Timer.add(label_Timer_MiliSec);
		
		panel_Timer.setBackground(Color.ORANGE);
		panel_Timer.setPreferredSize(new Dimension(250, 30));

		northPanel.add("East", panel_Timer);

		add("East", p_score);
		add("Center", marioCanvas);
		add("North", northPanel);

		/* 윈도우 창 설정 */
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLayout(null);

		/* ******************************************************************* */

		// 이벤트

		btn_start.addActionListener(this);
		btn_send.addActionListener(this);

		/* 윈도우 닫기 이벤트 */
		textField_Chat.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {

				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					
					btn_send.doClick();
					
				}
				
			}
		});
		
		
		/* 윈도우 닫기 이벤트 */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				int result = JOptionPane.showConfirmDialog(MarioClient.this, "게임을 종료하시겠습니까?", "종료창",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (result == JOptionPane.YES_OPTION) {

					dispose();

				} else if (result == JOptionPane.NO_OPTION) {

				}
			}
		});
		
		
		
		/* 타이머 스레드 */
		timerThread = new Thread(this) {
			@Override
			public void run() {
				
				while(true) {
					
					/* ******************************************************************* */
					
					runTimer();
					
					/* ******************************************************************* */
						
					try {
						Thread.sleep(33);
					} catch (InterruptedException e) {
			
						e.printStackTrace();
					}
				}
				
			}
		};

	} // MarioClient();
	
	
	
	/********************************************************************************************/
	
	
	// 서버 접속 메소드
	
	public void connectServer() {
		
		try {
		
		/* ******************************************************************* */
		
		// 서버와 연결
		
			socket = new Socket("192.168.0.28", 9500);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			System.out.println("MarioClient().init() : socket, oos, ois : 생성 완료");
		
		
		/* ******************************************************************* */
		
		// 서버로 JOIN 보내기
		
			MarioDTO dto = new MarioDTO();
			
			dto.setProtocol(Protocols.JOIN);
//			dto.setNickname(MarioSignup.signupdto.getNickname());
			dto.setNickname(nickname);
			
			oos.writeObject(dto);
			oos.flush();
			
		
		
		/* ******************************************************************* */
		
		// 스레드 생성 & 시작
		
		Thread clientThread = new Thread(this);
		clientThread.start();
		
		/* ******************************************************************* */
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	
		
	}

	
	
	
	
	/********************************************************************************************/

	
	
	// 버튼 이벤트

	@Override
	public void actionPerformed(ActionEvent e) {

		
		
		/* ******************************************************************* */
		
		// 시작 버튼
		
		if (e.getSource() == btn_start) {

			if(!timerThread.isAlive()) {
			timerThread.start();
			timerStart = true;
			}
			
		}// if;
		
		
		/* ******************************************************************* */
		
		// 채팅 보내기
		
		else if( e.getSource() == btn_send) {
			
			/* 입력값이 없으면 보내지 않는다. */
			if(textField_Chat.getText().length() == 0) {
				
				return;
				
			}else {
			
			MarioDTO dto = new MarioDTO();
			dto.setNickname(nickname);
			dto.setProtocol(Protocols.SEND);
			dto.setChatMessage(textField_Chat.getText());
			textField_Chat.setText("");
			
				try {
					oos.writeObject(dto);
					oos.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
			/* 채팅 전송 후, 캔버스가 포커스를 되찾는다. */
			marioCanvas.requestFocus();
			
		} // else if;

		/* ******************************************************************* */

	}
	
	
	
	

	/********************************************************************************************/

	
	// 스레드 동작

	@Override
	public void run() {
		
		MarioDTO dto = null;
		
		
		while(true) {
		
			/* ******************************************************************* */
			
			dataFromServer(dto);
			
			
			/* ******************************************************************* */
				
			try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
	
				e.printStackTrace();
			}
		}

		/* ******************************************************************* */

	} // run();

	
	
	
	/********************************************************************************************/
	
	// 타이머

	
	private void runTimer() {
		
		if(timerStart) {
		timer_MiliSec += 33; // 0.033초
		}
		
		if(timer_MiliSec >= 1000) {
			timer_MiliSec -= 1000;
			timer_Second++;
		}
		
		if(timer_Second >= 60) {
			timer_Second -= 60;
			timer_Minute++;
		}
		
		label_Timer_MiliSec.setText(new DecimalFormat("00").format(timer_MiliSec / 10) + "ms");
		label_Timer_Second.setText(new DecimalFormat("00").format(timer_Second) + " : ");
		label_Timer_Minute.setText(new DecimalFormat("00").format(timer_Minute) + " : ");
		
	}
	
	/********************************************************************************************/
	
	// 서버로부터 데이터 수신
	
	private void dataFromServer(MarioDTO dto) {
		
		try {
		
			// 항시 송신용?
//		MarioDTO sendDTO = new MarioDTO();
		 
//		 sendDTO.setCommand(Notice.MOVE);
//		 sendDTO.setMotion(Motion.~~~); 
//		 sendDTO.setPlayerCoordinateX(playerCoordinateX);
//		 sendDTO.setPlayerCoordinateY(playerCoordinateY);
		 
//		 oos.writeObject(sendDTO);
//		 oos.flush();
		 
		 /* ******************************************************************* */
			
			
			dto = (MarioDTO) ois.readObject();
			
			 System.out.println("getDTO = (MarioDTO) ois.readObject() : 성공 ");
			 
			 if(dto.getProtocol() == Protocols.SEND) {			// 메세지 받기
				 System.out.println("dto.getChatMessage() : " + dto.getChatMessage());
				 textArea_Chat.append(dto.getChatMessage() + "\n");
				 textArea_Chat.setCaretPosition(textArea_Chat.getText().length());
				 
			 }else if(dto.getProtocol() == Protocols.JOIN) {	// 입장
				 
				 textArea_Chat.append(dto.getChatMessage() + "\n");
				 textArea_Chat.setCaretPosition(textArea_Chat.getText().length());
				 
			 }else if(dto.getProtocol() == Protocols.EXIT) {	// 퇴장
				 
			 }
			 
			 
			/*
			 * 	# 사용 빈도수가 높은 순서대로
			 *  	SEND > JOIN > EXIT
			 *  
			 *  # 프로토콜  
			 *  	SEND : 메세지 송수신
			 *  	JOIN : 입장
			 *  	EXIT : 퇴장
			 *  
			 */
		
		 
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} 
		
	} // dataFromServer();
	
	
	
	/********************************************************************************************/
	
	public static void main(String[] args) {
		new MarioClient().connectServer();
	}

}
