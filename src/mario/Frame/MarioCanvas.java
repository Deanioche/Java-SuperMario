package mario.Frame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mario.Entity.Block;
import mario.Entity.CreateStage;
import mario.Entity.Mario;
import mario.Server.Protocols;
import mario.dto.BalloonDTO;
import mario.dto.MarioDTO;

public class MarioCanvas extends Canvas implements KeyListener, Runnable {

	public static MarioCanvas mCanvas;

	Image img = Toolkit.getDefaultToolkit().getImage("Image/background/MarioBackground2.png");
	Image bufferImage;
	Graphics bufferGraphic;

	MarioClient marioClient;
	MarioLogin marioLogin;

	// 캐릭터 동작
	boolean pushing_Left = false;
	boolean pushing_Right = false;
	boolean pushing_Up = false;

	public int movePower = 0; // -30 ~ 30 음수 : 좌, 양수 : 우측
	public int jumpPower = 0; // 35;

	boolean direction = true; // false = 왼쪽, true = 오른쪽
	boolean runMotion = false; // true = run1, false = run2
	boolean isJumping = false; // 상승중
	boolean isInAir = false; // 공중
	boolean isFalling = false; // 낙하중 : marioY += gravity
	boolean chatOpen = false;

	public int marioX = 100, marioY = 4800, motionNum = 0;
	public int gravity = 0;

	// 개체 영역
	Rectangle marioR;
	Rectangle blockR;
	private boolean touchLeft = false;
	private boolean touchRight = false;

	List<Block> list_Block;
	public MarioDTO clientData;
	ArrayList<BalloonDTO> list_Balloon;

	/************************************************************************************************/

	// getter
//	
//	public int getMarioX() {
//		
//		return marioX;
//	}
//	public int getMarioY() {
//		
//		return marioY;
//	}
//	public int getMarioMotion() {
//		
//		return motionNum;
//	}

	/************************************************************************************************/

	// 생성자
	public MarioCanvas(MarioClient marioClient, MarioLogin marioLogin) {

		this.marioLogin = marioLogin;
		this.marioClient = marioClient;
		clientData = marioClient.clientData;
		marioLogin.loginSuccess = true;

		mCanvas = MarioCanvas.this;
		list_Block = new ArrayList<Block>();
		list_Balloon = new ArrayList<BalloonDTO>();

		setBackground(new Color(250, 200, 200));
		setVisible(true);
//		setBounds(0, 0, 10000, marioClient.getHeight());

		// 전체 맵 생성
		new CreateStage(bufferGraphic, list_Block);

		System.out.println("캔버스 실행");

		addKeyListener(this);

		// 스레드 생성
		Thread canvasThread = new Thread(this);
		canvasThread.start();


	} // MarioCanvas();

	/*********************************************************************/

	// 블록 생성하는 메소드

	/* 첫번째 블록의 x좌표, y좌표, 블록이 뻗어나갈 방향(동1,서2,남3,북4), 생성할 블록 갯수, 블록 종류(0 ~ 4) */
//	private void createBlocks(int x, int y, String toWhere, int length, int type) {
//		
//		for(int i = x; i < length * 50; i += 50) { 
//				
//			/* # toWhere 블록 생성 방향 */
//			
//			/* 1: 동  */
//			if(toWhere.equals("East")) { list_Block.add(new Block(type, i, y)); }
//			/* 2: 서  */
//			if(toWhere.equals("West")) { list_Block.add(new Block(type, x -i, y)); }
//			/* 3: 남  */
//			if(toWhere.equals("South")) { list_Block.add(new Block(type, x, i)); }
//			/* 4: 북  */
//			if(toWhere.equals("North")) { list_Block.add(new Block(type, x, -i)); }
//	
//		}
//	}

	/*********************************************************************/

	private void characterControl() {

		/* 좌측 버튼 눌림 */
		if (pushing_Left) {

			if (direction) {
				direction = false;
//				movePower = 0;	
			}

			if (movePower > -20 && !isInAir && !touchLeft) {
				movePower--;
			} else if (movePower > -5 && isInAir && !touchLeft) {
				movePower--;
			}
			/*
			 * 좌측 화살표 버튼이 눌리면, 1. 캐릭터가 우측을 보고 있을 경우, 움직이던 속도를 0으로 만들고 반대 방향을 바라본다. 2. 이동속도가
			 * -50(좌측방향)이 될때까지 -1씩 천천히 줄인다.
			 */

			/* 우측 버튼 눌림 */
		} else if (pushing_Right) {

			if (!direction) {
				direction = true;
//				movePower = 0;	
			}

			if (movePower < 20 && !isInAir && !touchRight) {
				movePower++;
			} else if (movePower < 5 && isInAir && !touchRight) {
				movePower++;
			}
			/*
			 * 우측 화살표 버튼이 눌리면, 1. 캐릭터가 좌측을 보고 있을 경우, 움직이던 속도를 0으로 만들고 반대 방향을 바라본다. 2. 이동속도가
			 * 50(우측방향)이 될때까지 1씩 천천히 늘린다.
			 */

			/* 좌우 버튼 떼짐 */
		} else if (!pushing_Left && !pushing_Right) {

			if (movePower < 0) {
				movePower++;
			}

			if (movePower > 0) {
				movePower--;
			}
		}

		marioX += movePower;

		/*
		 * 좌우 화살표 버튼이 모두 떼지면, 캐릭터가 움직이던 방향으로 천천히 감속한다.
		 */

		/* 위 버튼 눌림 */
		if (pushing_Up) {

			if (jumpPower == 0 && !isInAir) {
				jumpPower = 35; // 점프파워가 있다는건 점프중이라는 뜻
			}
			/*
			 * 위 버튼을 눌렀을때, 점프파워가 0이고 현재 공중이 아닐 경우 점프파워 = 20
			 */

			/* 위 버튼 떼짐 - 동작 없음 */
		} else if (!pushing_Up) {

		}

		if (jumpPower > 0) {

			jumpPower--;
			isJumping = true;
		}
		/*
		 * 점프파워가 0보다 크면 점프파워를 1씩 감소시키고 점프중 = true
		 */

		else if (jumpPower <= 0 && isInAir) {

			isJumping = false;
			isFalling = true;
		}
		/*
		 * 점프파워가 0이면 점프중 = false 낙하중 = true
		 */

		if (isFalling && isInAir) {

			marioY += gravity;
			if (gravity < 19) {
				gravity++;
			}
		}
		/*
		 * 점프중, 낙하중 = true 이면 마리오 Y좌표에 중력만큼 감소시킨다
		 */

		marioY -= jumpPower;

		if (marioX < -25) {
			marioX = 5000;
		} else if (marioX > 5025) {
			marioX = 0;
		}

		if (marioY > 5500) {
			marioY = -50;
			gravity = 0;
		}

	} // characterControl();

	/*********************************************************************/

	// 지상, 공중 판별

	private void checkGround() {

		Rectangle marioRBottom = new Rectangle(marioX + 20, marioY + 46, 10, 5);
		Rectangle marioRTop = new Rectangle(marioX + 20, marioY, 10, 5);
		Rectangle marioRLeft = new Rectangle(marioX, marioY + 30, 5, 10);
		Rectangle marioRRight = new Rectangle(marioX + 45, marioY + 30, 5, 10);

		isFalling = true;
		isInAir = true;
		touchLeft = false;
		touchRight = false;

		for (int i = 0; i < list_Block.size(); i++) {

			int blockX = list_Block.get(i).getX();
			int blockY = list_Block.get(i).getY();

			blockR = new Rectangle(blockX, blockY, 50, 50);

			/* 블록 위에 서있기 */
			if (marioRBottom.intersects(blockR)) {

				if (new Rectangle(marioX + 10, marioY + 46 + gravity, 30, 5).intersects(blockR)) {
				}

				marioY = blockY - 50;
				gravity = 0;
				isFalling = false;
				isInAir = false;

			}
			/* 블록에 머리가 닿으면 */
			else if (marioRTop.intersects(blockR)) {

				if (new Rectangle(marioX + 10, marioY + 4 + jumpPower, 30, 5).intersects(blockR)) {
				}

				marioY = blockY + 51;
				jumpPower = 0;
				isFalling = true;
				isInAir = true;
				pushing_Up = false;

			}

			/* 왼쪽 충돌 */
			if (marioRLeft.intersects(blockR)) {

				if (new Rectangle(marioX + movePower, marioY + 20, 5, 10).intersects(blockR)) {
					marioX = blockX + 51;
				}

//					movePower = 0;
				marioX = blockX + 51;
				touchLeft = true;
			}

			/* 우측 충돌 */
			else if (marioRRight.intersects(blockR)) {

				if (new Rectangle(marioX + 50 + movePower, marioY + 20, 5, 10).intersects(blockR)) {
					marioX = blockX - 51;
				}

				marioX = blockX - 51;
//					movePower = 0;
				touchRight = true;
			}

		}

	}

	/*********************************************************************/

	@Override
	public void update(Graphics g) {

		// 컨트롤

		checkGround();
		characterControl();

		bufferImage = createImage(this.getWidth(), this.getHeight());
		bufferGraphic = bufferImage.getGraphics();

		bufferGraphic.drawImage(img, 0, 0, getWidth(), getHeight(), this);

		bufferGraphic.translate(-marioX + (MarioClient.WIDTH / 2) - 100, -marioY + (MarioClient.HEIGHT / 2));

		// 패널
		int i = marioY - 400;
		mCanvas.bufferGraphic.drawString("Coordinate : " + marioX + ", " + marioY, marioX + 500, i);
		mCanvas.bufferGraphic.drawString("pushing_Left : " + pushing_Left, marioX + 500, i + 20);
		mCanvas.bufferGraphic.drawString("pushing_Right : " + pushing_Right, marioX + 500, i + 40);
		mCanvas.bufferGraphic.drawString("pushing_Up : " + pushing_Up, marioX + 500, i + 60);
		mCanvas.bufferGraphic.drawString("MoverPower : " + movePower, marioX + 500, i + 80);
		mCanvas.bufferGraphic.drawString("jumpPower : " + jumpPower, marioX + 500, i + 100);
		mCanvas.bufferGraphic.drawString("isJumping : " + isJumping, marioX + 500, i + 120);
		mCanvas.bufferGraphic.drawString("isInAir : " + isInAir, marioX + 500, i + 140);
		mCanvas.bufferGraphic.drawString("isFalling : " + isFalling, marioX + 500, i + 160);
		mCanvas.bufferGraphic.drawString("gravity : " + gravity, marioX + 500, i + 180);
		mCanvas.bufferGraphic.drawString("touchLeft : " + touchLeft, marioX + 500, i + 200);
		mCanvas.bufferGraphic.drawString("touchRight : " + touchRight, marioX + 500, i + 220);

		bufferGraphic.drawRect(marioX + 15, marioY + 46, 20, 5);
		bufferGraphic.drawRect(marioX + 15, marioY, 20, 5);
		bufferGraphic.drawRect(marioX, marioY + 30, 5, 10);
		bufferGraphic.drawRect(marioX + 45, marioY + 30, 5, 10);

		/* ******************************************************************* */

		// 배경화면 그리기

		/*
		 * 모니터 화면 크기에 맞추기 Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		 */

		/* ******************************************************************* */

		// 맵 요소 그리기

		/*
		 * 1. 고정 블록 2. 움직이는 블록, 디딤돌
		 */

		/* 바닥 */

		/* ******************************************************************* */

		// 캐릭터 그리기 모든 캐릭터 좌표 받아서 for문안에 그리기

		/*
		 * 1. 이펙트 - 아이템 획득 효과 2. - 목적지점 도착 효과 3. 캐릭터 - 멈춤, 이동, 점프 4. 캐릭터이름표 5. 말풍선
		 */

		/* 블록 */

		if (list_Block != null) {
			for (int index = 0; index < list_Block.size(); index++) {
				list_Block.get(index).render(bufferGraphic);
			}
		}

		/* 내 캐릭터 */
		if (pushing_Left || pushing_Right) {
			if (direction) { // 오른쪽
				if (runMotion) {
					new Mario(3, marioX, marioY).render(bufferGraphic);
					motionNum = 3;
				} else {
					new Mario(5, marioX, marioY).render(bufferGraphic);
					motionNum = 5;
				}
			} else { // 왼쪽
				if (runMotion) {
					new Mario(2, marioX, marioY).render(bufferGraphic);
					motionNum = 2;
				} else {
					new Mario(4, marioX, marioY).render(bufferGraphic);
					motionNum = 4;
				}
			}

		} else if (pushing_Up) {
			if (direction) { // 오른쪽
				new Mario(7, marioX, marioY).render(bufferGraphic);
				motionNum = 7;
			} else { // 왼쪽
				new Mario(6, marioX, marioY).render(bufferGraphic);
				motionNum = 6;
			}

		} else {
			if (direction) { // 오른쪽
				new Mario(1, marioX, marioY).render(bufferGraphic);
				motionNum = 1;
			} else { // 왼쪽
				new Mario(0, marioX, marioY).render(bufferGraphic);
				motionNum = 0;
			}
		}

		/* 모든 플레이어 캐릭터 그리기 */
//		MarioLogin.list_PlayerInfo;

//		if(list_PlayerInfo.size() != 0) {
//			System.out.println("list_PlayerInfo.size() : " + list_PlayerInfo.size());
//			
//			for(MarioDTO data : list_PlayerInfo) {
//			
//			System.out.println(data.getNickname() + " : "+data.getPlayerCoordinateX() +  ", " + data.getPlayerCoordinateY());
//			}
//		}

		drawAllCharacters(bufferGraphic);

		// 캐릭터가 공중이면 중력의 힘을 받는다.
		// 캐릭터다 공중이다?

		bufferGraphic.setColor(new Color(255, 255, 255));
		bufferGraphic.fillRoundRect(marioX - 5, marioY + 55, 60, 16, 10, 10);
		bufferGraphic.setColor(new Color(0, 0, 0));
		bufferGraphic.drawRoundRect(marioX - 5, marioY + 55, 60, 16, 10, 10);
		bufferGraphic.setColor(new Color(0, 0, 0));
		bufferGraphic.drawString(clientData.getNickname(), marioX + 7, marioY + 68);
		
		/* ******************************************************************* */
		
		

		/* ******************************************************************* */
		paint(g);

	} // update();

	/*********************************************************************/

	@Override
	public void paint(Graphics g) {

//		g2d.translate(cam.getX(), cam.getY());

//		System.out.println((-marioX + 900)+", "+(-marioY + 500));

		g.drawImage(bufferImage, 0, 0, this); // 더블버퍼링 구현

//		g2d.translate(-cam.getX(), -cam.getY());

//		repaint();

	} // paint();

	/*********************************************************************/

	// 키보드 이벤트

	@Override
	public void keyPressed(KeyEvent e) {

		/* 좌로 이동 */
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {

			pushing_Left = true;
//			System.out.println("pushing_Left : " + pushing_Left);

		}
		/* 우로 이동 */
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

			pushing_Right = true;
//			System.out.println("pushing_Right : " + pushing_Right);

		}
		/* 점프 */
		if (e.getKeyCode() == KeyEvent.VK_UP) {

			pushing_Up = true;
//			System.out.println("pushing_Up : " + pushing_Up);

		}
		/* 채팅창 */
		else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			
			if(!chatOpen) {
			MarioClient.textField_Chat.requestFocus();
			chatOpen = true;
			}

		}
	}

	/*********************************************************************/

	// 키보드 떼기 이벤트
	@Override
	public void keyReleased(KeyEvent e) {

		/* 좌로 이동 */
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {

			pushing_Left = false;
//			System.out.println("pushing_Left : " + pushing_Left);

		}
		/* 우로 이동 */
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

			pushing_Right = false;
//			System.out.println("pushing_Right : " + pushing_Right);

		}
		/* 점프 */
		else if (e.getKeyCode() == KeyEvent.VK_UP) {

			pushing_Up = false;
//			System.out.println("pushing_Up : " + pushing_Up);

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	/*********************************************************************/

	@Override
	public void run() {

		int runTimer = 2;

		while (true) {

			try {

				/* ******************************************************************* */
				// 좌표 전송

				if (MarioLogin.serverConnected) {

					MarioDTO sendDTO = new MarioDTO();

					sendDTO.setProtocol(Protocols.MOVE);
					sendDTO.setNickname(clientData.getNickname());
					sendDTO.setPlayerCoordinateX(marioX);
					sendDTO.setPlayerCoordinateY(marioY);
					sendDTO.setPlayerMotionNum(motionNum);

					try {
						marioLogin.oos.writeObject(sendDTO);
						marioLogin.oos.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}

	

				/* ******************************************************************* */
				// 달리기 모션

				runTimer--;
				if (runTimer <= 0 && runMotion) {

					runMotion = false;
					runTimer = 3;

				} else if (runTimer <= 0 && !runMotion) {

					runMotion = true;
					runTimer = 3;

				}

				/* ******************************************************************* */

				repaint();
				Thread.sleep(33);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/*********************************************************************/

	private void drawAllCharacters(Graphics g) {

		if (marioLogin.arraydto != null && marioLogin.arraydto.getCoordinate().length != 0
				&& MarioLogin.serverConnected) {

			String[] nickname = marioLogin.arraydto.getNickname();
			int[][] coordinate = marioLogin.arraydto.getCoordinate();

			for (int i = 0; i < coordinate.length; i++) {

				if (!(nickname[i].equals(clientData.getNickname()))) {
					int marioX = coordinate[i][0];
					int marioY = coordinate[i][1];
					int motion = coordinate[i][2];

					new Mario(motion, marioX, marioY).render(g);

					bufferGraphic.setColor(new Color(255, 255, 255));
					bufferGraphic.fillRoundRect(marioX - 5, marioY + 55, 60, 16, 10, 10);
					bufferGraphic.setColor(new Color(0, 0, 0));
					bufferGraphic.drawRoundRect(marioX - 5, marioY + 55, 60, 16, 10, 10);
					bufferGraphic.setColor(new Color(0, 0, 0));
					bufferGraphic.drawString(nickname[i], marioX + 7, marioY + 68);
						
					
				}
			}
			showChatBalloon(g, nickname, coordinate);
		}
	}

	/*********************************************************************/
	
	
	
	// 말풍선
	public void showChatBalloon(Graphics bufferGraphic, String[] textNick, int[][] coordinate) {
		/* ******************************************************************* */
		// 말풍선 그리기 TODO

		if (list_Balloon.size() != 0) {

//			String[] textNick = marioLogin.arraydto.getNickname();
//			int[][] coordinate = marioLogin.arraydto.getCoordinate();

			/* 모든 말풍선리스트 */
			for (BalloonDTO data : list_Balloon) {

//				System.out.println("nickname.length : " + textNick.length);
				for (int i = 0; i < textNick.length; i++) {

					/* 좌표값을 가져오는 배열에서 일치하는 닉네임 찾기 */
					if (data.getNickname().equals(textNick[i])) {

						if (data.getTimer() > 0) {

							/* 타이머 -1 */
							data.setTimer((data.getTimer() - 1));
//							System.out.println("data.getTimer() : " + data.getTimer() + " / 91");
							int xT = coordinate[i][0] - 40;
							int yT = coordinate[i][1] - 55;
							
//							System.out.println("mario : " + coordinate[i][0] + ", "+ coordinate[i][1]);

							int x2[] = { xT, xT + 90, xT + 90, xT + 70, xT + 60, xT + 55, xT };
							int y2[] = { yT, yT, yT + 40, yT + 40, yT + 50, yT + 40, yT + 40 };

							bufferGraphic.setColor(Color.WHITE);
							bufferGraphic.fillPolygon(x2, y2, 7); // 말풍선 색

							bufferGraphic.setColor(Color.BLACK);
							bufferGraphic.drawPolygon(x2, y2, 7); // 말풍선 테두리

							String text = data.getMessage();

							/* 텍스트 말풍선 위에 그리기 */
							if (text.length() > 0) {

								/* 문자열 8자마다 1줄 */
								int row = text.length() / 8;

								/* 문자열이 2줄 이상이면 (0,1) */
								if (row >= 1) {

									/* 0번 줄부터 마지막줄까지 반복 */
									for (int index = 0; index < row; index++) {

										/* 해당 줄의 문자열의 길이가 8자보다 길면 */
										if (text.length() > 8) {

											/*
											 * 0번 문자부터 7번문자까지 bufferGraphic에 그려내고, 출력된 문자(0 ~ 7)는 문자열에서
											 * 제거(subString)
											 */
											bufferGraphic.drawString(text.substring(0, 7), xT + 5,
													yT + 15 + (index * 12));
											text = text.substring(8);
										}

									} // for;

									/* 마지막 줄의 길이가 8자가 안되면 여기서 그린다 */
									bufferGraphic.drawString(text, xT + 5, yT + 15 + (row * 12));
								} else {

									/* 출력할 문자가 1줄이면 여기서 그린다 */
									bufferGraphic.drawString(text, xT + 5, yT + 15);
								}
							}

							break;
						}
					}
				}
				
				if(data.getTimer() <= 0) {
					list_Balloon.remove(data);
					break;
				}
			}

		}
		
		
	}	

	/*********************************************************************/

	public static void main(String[] args) {
		new MarioLogin();
	}

}