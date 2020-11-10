package mario.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MarioServer {
	
	private ServerSocket svSocket;
	private List<MarioHandler> list_Handler;
	
	public MarioServer() {
		try {
			
				svSocket = new ServerSocket(9500);
				System.out.println(" # 소켓 서버 준비 완료");
			
				list_Handler = new ArrayList<MarioHandler>();
			
			while(true) {
				
				
				System.out.println(" # 소켓 생성 대기 (" + list_Handler.size() + ")	");
			
				Socket socket = svSocket.accept();
				
				MarioHandler handler = new MarioHandler(socket, list_Handler);
				
				handler.start();
				
				list_Handler.add(handler);
				
			
			}

			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MarioServer();
	}
	
	

}
