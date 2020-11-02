package mario.Main;

import java.awt.Dimension;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MarioClient extends JFrame implements Runnable, ActionListener {

	// 필드
	private MarioCanvas marioCanvas;

	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	private String nickname;
	private int score;
	
//	private JButton btn_~~~;
//	private JLabel label_~~~;
//	private JTextField tf_~~~;

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
	public MarioClient() {
		super("Marioㅐㅐ");
		
		/* ******************************************************************* */
		
		// 윈도우 창 설정 
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		  System.out.println("해상도 : " + res.width + " x " + res.height);
		  
		setSize(res.width,res.height);										//프레임의 크기
		setResizable(false);									//창의 크기를 변경하지 못하게
		setLocationRelativeTo(null);							//창이 가운데 나오게
		setLayout(null);										//레이아웃을 내맘대로 설정가능하게 해줌.
		setVisible(true);										//창이 보이게	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			//windowClosing 이벤트로 종료되도록 

		
		/* ******************************************************************* */
		
		//JFrame 설정
		
		
		
		
		/* ******************************************************************* */
		
		//JComponent 생성 및 화면에 출력

		marioCanvas = new MarioCanvas(MarioClient.this);
		add("Center", marioCanvas);

		
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
