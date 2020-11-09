package mario.dto;

public class BalloonDTO {
	
	private int timer;
	private String nickname;
	private String message;
	
	public BalloonDTO(int timer, String nickname, String message) {
		this.timer = timer;
		this.nickname = nickname;
		this.message = message;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}


//// 말풍선
//public void showChatBalloon(String BalloonNick, String textMessage) {
//	
//	 /* 말풍선 스레드 */
//	 new Thread( new Runnable() {
//		
//		@Override
//		public void run() {
//			
//			/* 말풍선 표시 시간 3초 */
//			int chatTimer = 300;
//			
//			/* 표시 시간이 0이 아닐때만 반복 */
//			while(chatTimer != 0) {
//				
//				
//				/* 닉네임을 비교해 좌표를 받기위해 생성 */
//				String[] nickname = marioLogin.arraydto.getNickname();
//				int[][] coordinate = marioLogin.arraydto.getCoordinate();
//				String text = textMessage;
//				
//				for(int i = 0; i < coordinate.length; i++) {
//					
//					if(nickname[i].equals(BalloonNick)) {
//						
////						System.out.println("nickname : " + nickname[i] + ", " + BalloonNick);
////						System.out.println(coordinate[i][0] + ", " + coordinate[i][1]);
//						
//						/* 마리오 좌표를 기준으로 말풍선 좌표 잡기 */
//						int xT = coordinate[i][0] - 40;
//						int yT = coordinate[i][1] - 55;
//	
//						int x2[] = { xT, xT + 90, xT + 90, xT + 70, xT + 60, xT + 55, xT };
//						int y2[] = { yT, yT, yT + 40, yT + 40, yT + 50, yT + 40, yT + 40 };
//	
//						mCanvas.bufferGraphic.setColor(Color.WHITE);
//						mCanvas.bufferGraphic.fillPolygon(x2, y2, 7); // 말풍선 색
//	
//						mCanvas.bufferGraphic.setColor(Color.BLACK);
//						mCanvas.bufferGraphic.drawPolygon(x2, y2, 7); // 말풍선 테두리
//						
//						//TODO 텍스트 표시
//						System.out.println("text : " + text +" 말풍선 표시 남은 시간 ( " + chatTimer + " / 3 )");
//						
//						if (text.length() > 0) {
//	
//							/* 문자열 8자마다 1줄 */
//							int row = text.length() / 8;
//	
//							/* 문자열이 2줄 이상이면 (0,1) */
//							if (row >= 1) {
//								
//								/* 0번 줄부터 마지막줄까지 반복 */
//								for (int index = 0; index < row; index++) {
//									
//									/* 해당 줄의 문자열의 길이가 8자보다 길면  */
//									if (text.length() > 8) {
//										
//										/* 0번 문자부터 7번문자까지 bufferGraphic에 그려내고, 출력된 문자(0 ~ 7)는 문자열에서 제거(subString) */ 
//										mCanvas.bufferGraphic.drawString(text.substring(0, 7), xT + 5, yT + 15 + (index * 12));
//										text = text.substring(8);
//									}
//									
//								} // for;
//								
//								/* 마지막 줄의 길이가 8자가 안되면 여기서 그린다 */
//								mCanvas.bufferGraphic.drawString(text, xT + 5, yT + 15 + (row * 12));
//							} else {
//								
//								/* 출력할 문자가 1줄이면 여기서 그린다 */
//								mCanvas.bufferGraphic.drawString(text, xT + 5, yT + 15);
//							}
//						}
//						
//						break;
//					}
//				}
//				
//				
//				chatTimer--;
//				
////				try {
//////					Thread.sleep(10);
////				} catch (InterruptedException e) {
////					e.printStackTrace();
////				}
//			}
//		}
//	}).start();
//	 
//}
//