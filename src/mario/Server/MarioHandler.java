package mario.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import mario.Frame.ArrayDTO;
import mario.dao.MarioDAO;
import mario.dto.MarioDTO;


public class MarioHandler extends Thread {

	// 필드
	Socket socket;
	List<MarioHandler> list_Handler;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	
	/* 해당 핸들러의 클라이언트 정보 */
	private String nickname;

	static List<MarioDTO> list_PlayerInfo;

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
			System.out.println(" # MarioHandler : oos, ois 생성 완료");
			
			if(list_PlayerInfo == null) {
				list_PlayerInfo = new ArrayList<MarioDTO>();
				System.out.println(" # 첫 접속! list_PlayerInfo 생성완료 ");
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(" # MarioHandler : oos, ois 생성 실패");
		}
		
		
		/* ******************************************************************* */

	} // MarioHandler()

	
	
	
	/********************************************************************************/

	@Override
	public void run() {

		try {

			MarioDTO dto = null;

			while (true) {
				
				dto = (MarioDTO) ois.readObject();
				
				if(dto.getNickname() != null) {
					nickname = dto.getNickname(); 
					this.setName(nickname);
				}
				/* 아래 코드 작성하기 편하게 닉네임 정보를 미리 저장 */

				
				
			/* ******************************************************************* */
				
				// 플레이어 좌표, 모션
				
				if (dto.getProtocol() == Protocols.MOVE) {
					
					/* 모든 클라이언트 좌표수신 후 리스트에 업데이트 */
					/* 클라이언트 캐릭터의 모션정보 broadcast */
					
					
					/* 리스트 중에서 해당 클라이언트의 정보에 좌표와 모션정보 업데이트 */
					for(MarioDTO data : list_PlayerInfo) {
//						System.out.println("MOVE:dto : " + dto.getNickname());
//						System.out.println("MOVE:list_PlayerInfo : " + data.getNickname());
						
						if(dto.getNickname().equals(data.getNickname())) {
							
							data.setPlayerCoordinateX(dto.getPlayerCoordinateX());
							data.setPlayerCoordinateY(dto.getPlayerCoordinateY());
							data.setPlayerMotionNum(dto.getPlayerMotionNum());
							
							break;
						}
					}
					

					int[][] array_Coordinate = new int[list_PlayerInfo.size()][3];
					String[] array_nickname = new String[list_PlayerInfo.size()];
					
					for(int i = 0; i < list_PlayerInfo.size(); i++) {
						
						String nick = list_PlayerInfo.get(i).getNickname();
						int x = list_PlayerInfo.get(i).getPlayerCoordinateX();
						int y = list_PlayerInfo.get(i).getPlayerCoordinateY();
						int n = list_PlayerInfo.get(i).getPlayerMotionNum();
						
						array_nickname[i] = nick;
						array_Coordinate[i][0] = x;
						array_Coordinate[i][1] = y;
						array_Coordinate[i][2] = n;
						
						System.out.println("sendDTO 서버 좌표 : " + nick + ", " + x +  ", " + y + ", " + n);
					}
					
					/* 모든 클라이언트에 보내기  */
					
					ArrayDTO arraydto = new ArrayDTO();
					arraydto.setNickname(array_nickname);
					arraydto.setCoordinate(array_Coordinate);
					
					broadcast(arraydto);
					
					
					
			/* ******************************************************************* */		
					
				// 메세지 전송	

				} else if (dto.getProtocol() == Protocols.SEND) {
					/* 메세지 송수신 */
					
					/* 보낼 객체 생성  */
					MarioDTO sendDTO = new MarioDTO();
					sendDTO.setProtocol(Protocols.SEND);
					sendDTO.setChatMessage("[" + nickname + "] " + dto.getChatMessage());
					
					/* 서버 콘솔에도 메세지 남기기 */
					System.out.println("[" + nickname + "] " + dto.getChatMessage());
					
					/* 모든 클라이언트에게 전송 */
					broadcast(sendDTO);
					
					
					
					
			/* ******************************************************************* */		

				// 입장	
					
				} else if (dto.getProtocol() == Protocols.JOIN) {
					
					/* 클라이언트 입장  */
					/* 닉네임, 좌표정보 수신 */
					/* 입장 메세지 broadcast */
					
					/* 접속한 클라이언트를 리스트에 저장 */
					boolean newPlayer = true;
					
					if(list_PlayerInfo.size() != 0) {
						
						for(int i = 0; i < list_PlayerInfo.size(); i++) {
							
							if(dto.getNickname().equals(list_PlayerInfo.get(i).getNickname())) {
								
									System.out.println("Protocols.JOIN : 플레이어의 데이터가 이미 리스트에 있습니다.");
									System.out.println("이메일 : " + list_PlayerInfo.get(i).getClientAccount());
									System.out.println("닉네임 : " + list_PlayerInfo.get(i).getNickname());
									System.out.println("seq : " + list_PlayerInfo.get(i).getSeq());
									System.out.println();
									
									newPlayer = false;
								break;
							}
						}
					}
					
					if(newPlayer) {
						list_PlayerInfo.add(dto);
						System.out.println("Protocols.JOIN : 새로운 플레이어 리스트에 추가 완료 (" + list_PlayerInfo.size() + ")");
					}
					
					MarioDTO sendDTO = new MarioDTO();
					sendDTO.setProtocol(Protocols.JOIN);
					sendDTO.setChatMessage("[" + nickname + "] 님이 입장했습니다. " );
					System.out.println("[" + nickname + "] 님이 입장했습니다. " );
					
					broadcast(sendDTO);

					
			/* ******************************************************************* */
					
				// 퇴장	
					
				} else if (dto.getProtocol() == Protocols.EXIT) {
					
					/* 클라이언트 퇴장  */
					/* EXIT 프로토콜 보내기 */
					/* 퇴장 메세지 broadcast */
					
					MarioDTO sendDTO = new MarioDTO();
					sendDTO.setProtocol(Protocols.EXIT);
					oos.writeObject(sendDTO);
					oos.flush();
					
					/* oos, ois 종료  */
					oos.close();
					ois.close();
					
					/* 플레이어 정보 리스트에서 해당 리스트 제거 */
					for(int i = 0; i < list_PlayerInfo.size(); i++) {
						if(dto.getNickname().equals(list_PlayerInfo.get(i).getNickname())) {
							
						list_PlayerInfo.remove(i);
						System.out.println("Protocols.EXIT :  list_PlayerInfo 리스트 수 : " + list_PlayerInfo.size());
							
							break;
						}
					}
					
					
					/* 접속자 리스트에서 해당 핸들러 제거  */
					list_Handler.remove(this); 
					
					sendDTO.setProtocol(Protocols.SEND);
					sendDTO.setChatMessage("[" + nickname + "] 님이 퇴장했습니다. " );
					System.out.println("[" + nickname + "] 님이 퇴장했습니다. " );
					broadcast(sendDTO);
					
					/* 소켓 종료  */
					socket.close();
					break;
				
				
				/* ******************************************************************* */		

				// 입장	
					
				} else if (dto.getProtocol() == Protocols.CONNECT) {
					
					/* 클라이언트와 DB 소통  */
					// TODO 임시로 만들어 둔 dao
					
					MarioDAO dao = MarioDAO.getInstance();
					
					System.out.println("handler.CONNECT dao.getMarioList() : " + dao.getMarioList());
					
					MarioDTO sendDTO = new MarioDTO();
					sendDTO.setProtocol(Protocols.CONNECT);
					sendDTO.setList_PlayerInfo(dao.getMarioList());
					
					oos.writeObject(sendDTO);
					oos.flush();
					
				/* ******************************************************************* */		

					
				}else if (dto.getProtocol() == Protocols.SIGNUP) {
					
					/* 받은 DTO를 DB에 입력  */
					// TODO 
					
					MarioDAO dao = MarioDAO.getInstance();
					
					int seq = dao.getSeq(); // DB로부터 시퀀스 번호를 받는다.
					dto.setSeq(seq);
					dao.insertArticle(dto);
					System.out.println("Protocols.SIGNUP : DB로 전송 성공!");
					
					/* 클라이언트에  DB 리스트 다시 보내기 */
					MarioDTO sendDTO = new MarioDTO();
					sendDTO.setProtocol(Protocols.CONNECT);
					sendDTO.setList_PlayerInfo(dao.getMarioList());
					
					oos.writeObject(sendDTO);
					oos.flush();
				}
				
				
				
			/* ******************************************************************* */		
				
				
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
			e.printStackTrace();
		}

	} // run()

	
	
	
	
	/********************************************************************************/
	
	private void broadcast(MarioDTO sendDTO) {

		for (MarioHandler handler : list_Handler) {
			
			try {
				System.out.println(sendDTO.getProtocol() + " | 스레드 이름 : " + handler.getName());
				handler.oos.writeObject(sendDTO);

				handler.oos.flush();

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(sendDTO.getProtocol() + " | 스레드 이름 : " + handler.getName());
			}
		}

	} // broadcast();
	
	private void broadcast(ArrayDTO arraydto) {
		
		for (MarioHandler handler : list_Handler) {
			
			try {
				handler.oos.writeObject(arraydto);
				
				handler.oos.flush();
				
			} catch (IOException e) {
			}
		}
		
	} // broadcast();
	
	public static void main(String[] args) {
		new MarioServer();
	}
}
