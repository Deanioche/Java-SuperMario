package mario.Main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;


public class MarioHandler extends Thread {

	// 필드
	Socket socket;
	List<MarioHandler> list_Handler;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private String nickname;

	private static List<MarioDTO> list_PlayerInfo;

	/*
	 *	  # 필드 
	 *		  서버클래스로부터 받은 소켓, 
	 *		  핸들러 리스트 
	 *		 dto객체정보 송수신 클래스 
	 *		  각각 핸들러에 닉네임 정보 저장하기
	 *		 static 전체 플레이어 정보 리스트
	 */

	public MarioHandler(Socket socket, List<MarioHandler> list_Handler) {

		/* ******************************************************************* */

		// 필드에 값 입력
		this.list_Handler = list_Handler;
		this.socket = socket;

		/* ******************************************************************* */

		// 클라이언트로부터 정보 송수신
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("MarioHandler : oos, ois 생성 완료");

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("MarioHandler : oos, ois 생성 실패");
		}

		/* ******************************************************************* */

	} // MarioHandler()

	/********************************************************************************/

	@Override
	public void run() {

		try {

			MarioDTO dto = null;

			/* ******************************************************************* */

			while (true) {
				dto = (MarioDTO) ois.readObject();

				nickname = dto.getNickname();
				/* 아래 코드 작성하기 편하게 닉네임 정보를 미리 저장 */

				if (dto.getProtocol() == Protocols.MOVE) {
					/* 모든 클라이언트 좌표수신 후 저장 */
					/* 클라이언트 캐릭터의 모션정보 broadcast */

				} else if (dto.getProtocol() == Protocols.SEND) {
					/* 메세지 송수신 */

				} else if (dto.getProtocol() == Protocols.JOIN) {
					
					
					
					/* 클라이언트 입장  */
					/* 닉네임, 좌표정보 수신 */
					/* 입장 메세지 broadcast */

				} else if (dto.getProtocol() == Protocols.EXIT) {
					
					
					
					/* 클라이언트 퇴장  */
					/* EXIT 프로토콜 보내기 */
					/* 퇴장 메세지 broadcast */

				}
				
				/*
				 * 	# 사용 빈도수가 높은 순서대로
				 *  	MOVE > SEND > JOIN > EXIT
				 *  
				 *  # 프로토콜  
				 *  	MOVE : 클라이언트 좌표 송수신
				 *  	SEND : 메세지 송수신
				 *  	JOIN : 입장
				 *  	EXIT : 퇴장
				 *  
				 */
				

			} // while (true)

			/* ******************************************************************* */

		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // run()

	/********************************************************************************/
	
	private void broadcast(MarioDTO sendDTO) {

		for (MarioHandler handler : list_Handler) {
			System.out.println("broadcast 동작 : " + sendDTO.getCommand());

			try {
				handler.oos.writeObject(sendDTO);
				handler.oos.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	
}
