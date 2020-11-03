package mario.Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import mario.Protocols;
import mario.Frame.MarioCanvas;
import mario.dao.MarioDAO;
import mario.dto.MarioDTO;

@SuppressWarnings("serial")
public class MarioClient_old extends JFrame implements Runnable, ActionListener {

	// 필드
	private MarioCanvas marioCanvas;

	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	private String nickname;
	private int score;
	
	//JFrame 상에 출력될 항목들
	
		private JTextArea textArea;
		private JTextField textField;
		private JButton btn_send;
		
		private JTable scoreTable;
		public DefaultTableModel model;
		public JButton btn_Start;
	
	
	// 모든 클라이언트 리스트
	private List<MarioDTO> list_PlayerInfo;
	
	/*	 # 필드
	 * 
	 * 		다른 클래스 주소로 필드값 설정
	 * 		서버 연결용 객체들
	 * 		닉네임, 점수
	 * 		//JFrame 상 출력될 항목들
	 * 		서버로부터 받는 플레이어 리스트
	 */

	// 생성자
	public MarioClient_old() {
		super("Mario");
		
		/* ******************************************************************* */
		
		// 윈도우 창 설정 
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		  System.out.println("해상도 : " + res.width + " x " + res.height);
		  
		setSize(res.width,res.height);										//프레임의 크기
//		setSize(1900,1000);										//프레임의 크기
		setResizable(false);									//창의 크기를 변경하지 못하게
		setLocationRelativeTo(null);							//창이 가운데 나오게
		setLayout(null);										//레이아웃을 내맘대로 설정가능하게 해줌.
		setVisible(true);										//창이 보이게	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			//windowClosing 이벤트로 종료되도록 

		
		/* ******************************************************************* */
		
		//JFrame 상에 출력될 항목들
				
		JPanel panel_Score = new JPanel(new BorderLayout());
		panel_Score.setBackground(Color.DARK_GRAY);
		
		// 채팅창
		JPanel textPanel = new JPanel(new BorderLayout());
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		JScrollPane jscChat = new JScrollPane(textArea);
		jscChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textPanel.add("Center", jscChat);
		
		// 채팅창 하단 패널
		JPanel southTextPanel = new JPanel(new BorderLayout());
		textField = new JTextField();
		btn_send = new JButton("Send");
		southTextPanel.add("Center", textField);
		southTextPanel.add("East", btn_send);
		textPanel.add("South", southTextPanel);
		panel_Score.add("Center", textPanel);		
		
		// 버튼 생성 (우측 패널 하단)
		btn_Start = new JButton("게임 시작");
		panel_Score.add("South", btn_Start);
		
		// 점수표 생성
		Vector<String> vector = new Vector<String>();
		vector.add("닉네임");
		vector.add("점수");
		vector.add("몸길이");
		model = new DefaultTableModel(vector, 0);
		scoreTable = new JTable(model);
		scoreTable.setEnabled(false);
		JScrollPane jsc = new JScrollPane(scoreTable);

		panel_Score.add("North", jsc);
		jsc.setPreferredSize(new Dimension(200, 200));
		panel_Score.setPreferredSize(new Dimension(200, 200));
		
		
		
		/* ******************************************************************* */
		
		
		//캔버스
		marioCanvas = new MarioCanvas(MarioClient_old.this);
		this.add("East", panel_Score);
		add("Center", marioCanvas);

		
		System.out.println("패널 생성 완료");
		
		/* ******************************************************************* */
		
		//창 종료 이벤트
		
//		addWindowListener(new WindowAdapter() {
//			
//			@Override
//			public void windowClosing(WindowEvent e) {
//
//				try {
//					// EXIT 프로토콜을 서버로 보낸다.
//					MarioDTO dto = new MarioDTO();
//					dto.setCommand(Notice.EXIT);
//					oos.writeObject(dto);
//					oos.flush();
//
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//				
//			}
//			
//		});
		
		
		/* ******************************************************************* */
		
		// 스레드 생성 & 시작
		
//		Thread clientThread = new Thread(this);
//		clientThread.start();
		
		
		
		/* ******************************************************************* */
		
		// 버튼 이벤트
		
		
		
		
		
		/* ******************************************************************* */

	} // MarioClient()

	@Override
	public void paint(Graphics g) {


		g.drawString("왜 안돼", 1800, 100);
	}
	
	/************************************************************************************/
	
	// 서버 접속
//	public void init() {
//		
//		try {
//			
//			/* ******************************************************************* */
//			
//			// 서버와 연결
//			
//				socket = new Socket("아이피", 9500);
//				oos = new ObjectOutputStream(socket.getOutputStream());
//				ois = new ObjectInputStream(socket.getInputStream());
//				System.out.println("MarioClient().init() : socket, oos, ois : 생성 완료");
//			
//			
//			/* ******************************************************************* */
//			
//			// 서버로 JOIN 보내기
//			
//				MarioDTO dto = new MarioDTO();
//				dto.setCommand(Notice.JOIN);
//				dto.setNickname(nickname);
//				dto.setScore(0);
//				oos.writeObject(dto);
//				oos.flush();
//			
//			
//			
//			/* ******************************************************************* */
//			
//			// 스레드 생성 & 시작
//			
//			Thread clientThread = new Thread(this);
//			clientThread.start();
//			
//			/* ******************************************************************* */
//			
//		} catch (IOException e) 
//			e.printStackTrace();
//		}
//		
//		
//	} // init()
	
	/************************************************************************************/
	
	//버튼 이벤트
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//버튼 설정

		
		
	}
	
	

	/************************************************************************************/
	
	// 서버로부터 정보 수신용 스레드
	
	@Override
	public void run() {
		
		MarioDTO dto = null;
		
		 while(true) {
			 
			 try {
				// 0.02초마다 내 캐릭터의 좌표와 모션을 서버로 보내기
				 	
				 MarioDTO sendDTO = new MarioDTO();
				 
//				 sendDTO.setCommand(Notice.MOVE);
//				 sendDTO.setMotion(Motion.~~~); 
//				 sendDTO.setPlayerCoordinateX(playerCoordinateX);
//				 sendDTO.setPlayerCoordinateY(playerCoordinateY);
				 
//				 oos.writeObject(sendDTO);
//				 oos.flush();
				 
				 /* ******************************************************************* */
				 
				// 0.02초마다 서버로부터 dto객체 데이터 수신
				 
				 dto = (MarioDTO) ois.readObject(); 
				 System.out.println("getDTO = (MarioDTO) ois.readObject() : 성공 ");
				 
				 if(dto.getProtocol() == Protocols.SEND) {			// 메세지 받기
					 
				 }else if(dto.getProtocol() == Protocols.JOIN) {	// 입장
					 
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
				 
				 /* ******************************************************************* */
				 
				 
				Thread.sleep(17);	 
				/* 1초에 60회 수신  */
				
			} catch (InterruptedException | ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			 
		 } // while(true)
		 
	}//run()
	
	
	/************************************************************************************/


}
