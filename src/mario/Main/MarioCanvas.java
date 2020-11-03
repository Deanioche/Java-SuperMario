package mario.Main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import mario.Entity.Block;
import mario.Entity.Mario;

public class MarioCanvas extends Canvas implements KeyListener, Runnable {

	public static MarioCanvas mCanvas;

	Image bufferImage; 
	Graphics bufferGraphic;
	
	MarioClient marioClient;
	
	
	// 캐릭터 동작
	boolean pushing_Left = false;
	boolean pushing_Right = false;
	boolean pushing_Up = false;
	
	public int movePower = 0;
	public int jumpPower = 0;
	
	boolean direction = true; // false = 왼쪽, true = 오른쪽
	boolean runMotion = false; // true = run1, false = run2
	boolean isJumping = false;
	boolean isInAir = false;
	boolean isFalling = false;
	
	public int marioX = 400, marioY = 900;
	public int gravity = 0;
	
	
	List<Block> list_Block;
	
	
	/*
	 * # 캐릭터 동작
	 * 
	 */

	// 생성자
	public MarioCanvas(MarioClient marioClient) {

		this.marioClient = marioClient;
		mCanvas = MarioCanvas.this;
		list_Block = new ArrayList<Block>();

		setBackground(new Color(250, 200, 200));
		setVisible(true);
		setBounds(0, 0, marioClient.getWidth(), marioClient.getHeight());
		
		//블록 생성
		createBlock();

		System.out.println("캔버스 실행");

		addKeyListener(this);

		// 스레드 생성
		Thread canvasThread = new Thread(this);
		canvasThread.start();
		
	} // MarioCanvas();
	
	
	/*********************************************************************/
	
	
	
	
	/*********************************************************************/
	
	//상하좌우 키 버튼 boolean(pushing_Left 등) true가 되있는 동안 이동 게이지가 천천히 최대값 까지 상승 double 0.1 씩?
	// 상승한 값만큼 좌표 이동하다가 최대값 되면 등속으로
	// 키에서 손을 떼면 (pushing_Left가 false가 되면) 이동 게이지가 줄면서 감속하다가 0이되면 멈춤

	//점프
	// up키를 누르면 점프력만큼 게이지가 full인 상태로 생성되고 
	// 점점 게이지가 감소하며 게이지만큼 y에서 빼는 값이 줄어든다 (상승속도가 줄어듬) 
	// 점프력 게이지가 0이 되면 중력 게이지가 올라간다?
	
	//대각선 이동 구현 
	// 좌우측으로 점프
	
	
	private void characterControl() {
		
		/* 좌측 버튼 눌림 */
		if(pushing_Left) {
			
			if(direction) {
				direction = false;
				movePower = 0;	
			}
			
			if(movePower > -50 && !isInAir) {
				movePower --;
			}
			/*
			 * 좌측 화살표 버튼이 눌리면,
			 * 1. 캐릭터가 우측을 보고 있을 경우, 움직이던 속도를 0으로 만들고 반대 방향을 바라본다.
			 * 2. 이동속도가 -50(좌측방향)이 될때까지 -1씩 천천히 줄인다.
			 */
			
			
		/* 우측 버튼 눌림 */
		}else if(pushing_Right) {
			
			if(!direction) {
				direction = true;
				movePower = 0;	
			}
			
			if(movePower < 50 && !isInAir) {
				movePower ++;
			}
			/*
			 * 우측 화살표 버튼이 눌리면,
			 * 1. 캐릭터가 좌측을 보고 있을 경우, 움직이던 속도를 0으로 만들고 반대 방향을 바라본다.
			 * 2. 이동속도가 50(우측방향)이 될때까지 1씩 천천히 늘린다.
			 */
			
			
		/* 좌우 버튼 떼짐 */
		}else if(!pushing_Left && !pushing_Right) {
			
			if(movePower < 0 ) {
				movePower ++;
			}
			
			if(movePower > 0 ) {
				movePower --;
			}
		}
		
		marioX += movePower;
		
		/*
		 * 좌우 화살표 버튼이 모두 떼지면,
		 * 캐릭터가 움직이던 방향으로 천천히 감속한다.
		 */
		
		
		/* 위 버튼 눌림 */
		if(pushing_Up) {		
			
			if(jumpPower == 0 && !isInAir) {
				jumpPower = 35;	// 점프파워가 있다는건 점프중이라는 뜻
			}
			/*
			 * 위 버튼을 눌렀을때,
			 * 점프파워가 0이고 현재 공중이 아닐 경우
			 * 점프파워 = 20
			 */
		
		/* 위 버튼 떼짐 */
		}else if (!pushing_Up) {
			
		}
		
		if(jumpPower > 0) {
			
			jumpPower--;
			isJumping = true;
		}
		/*
		 * 점프파워가 0보다 크면
		 * 점프파워를 1씩 감소시키고
		 * 점프중 = true
		 */
		
		else if(jumpPower <= 0 && isInAir) {
			
			isJumping = false;
			isFalling = true;
		}
		/*
		 * 점프파워가 0이면
		 * 점프중 = false
		 * 낙하중 = true
		 */
		
		if (isFalling && isInAir) {
			
			marioY += gravity;
			if(gravity < 50) {
			gravity++;
			}
		}
		/*
		 * 점프중, 낙하중 = true 이면
		 * 마리오 Y좌표에 중력만큼 감소시킨다
		 */
		
		marioY -= jumpPower;
		
	} // characterControl();
	
	
	
	/*********************************************************************/
	
	// 지상, 공중 판별
	
	private void checkGround() {
		
		
		for(int i = 0; i < list_Block.size(); i++) {
			
			int blockX = list_Block.get(i).getX();
			int blockY = list_Block.get(i).getY();
			
			Rectangle marioR = new Rectangle(marioX -1, marioY -1, 52, 52);
			Rectangle blockR = new Rectangle(blockX, blockY, 50 ,50);
			
			if(marioR.intersects(blockR)) {
				
				if(isFalling && marioY + gravity < blockY) {
					marioY = blockY - 50;
					}
				
				jumpPower = 0;
				gravity = 0;
				isFalling = false;
				isInAir = false;
				return;
				
			}else if(Math.sqrt(
					Math.pow((marioX - blockX), 2) + Math.pow((marioY - blockY), 2)) > 50 &&
					Math.abs(marioX - blockX) > 25){
				System.out.println("낙하");
				
				isFalling = true;
				isInAir = true;
			}
		}
	}
			
			
			
			
			
//			if (Math.sqrt(
//					Math.pow((marioX - blockX), 2) + Math.pow((marioY - blockY), 2)) <= 50 &&
//					Math.abs(marioX - blockX) <= 25) {
//				
//				if(isFalling && marioY + gravity < blockY) {
//				marioY = blockY - 50;
//				}
//				
//				gravity = 0;
//				isFalling = false;
//				isInAir = false;
//			}
//		}
			
			
			
//			if((marioY == 900 && marioX >= 0 && marioX <= 1550)) {
//				
//				System.out.println("지상");
//				isFalling = false;
//				isInAir = false;
//				gravity = 0;
//				
//				/*
//				 * 블럭 좌표와 마리오
//				 */
//				
//			}else if(marioY == 550 && marioX >= 500 && marioX <= 1100){
//			
//				System.out.println("지상2");
//				isFalling = false;
//				isInAir = false;
//				gravity = 0;
//				
//				/*
//				 * 블럭 좌표와 마리오
//				 */
//				
//			}
//			
//	}
	
	
	
	
	/*********************************************************************/
	
	//블록 생성]
	
	private void createBlock() {
		
		for(int i = 0; i <= 1500; i += 50) { 
			
			list_Block.add(new Block(0, i, 950));
		}
		
		for(int i = 500; i <= 1050; i += 50) { 
			
			list_Block.add(new Block(1, i, 700));
		}
		
		for(int i = 1000; i <= 1500; i += 50) { 
			
			list_Block.add(new Block(2, i, 550));
		}
		
		for(int i = 400; i <= 800; i += 50) {
			
			list_Block.add(new Block(2, i, 250));
		}
		
		
	}

	
	
	/*********************************************************************/

	@Override
	public void update(Graphics g) {


		bufferImage = createImage(this.getWidth(), this.getHeight());
		bufferGraphic = bufferImage.getGraphics();
		
		// 패널
		
		bufferGraphic.drawString("Coordinate : " + marioX + ", " + marioY, 1300, 100);
		bufferGraphic.drawString("pushing_Left : " + pushing_Left, 1300, 140);
		bufferGraphic.drawString("pushing_Right : " + pushing_Right, 1300, 160);
		bufferGraphic.drawString("pushing_Up : " + pushing_Up, 1300, 180);
		bufferGraphic.drawString("MoverPower : " + movePower, 1300, 200);
		bufferGraphic.drawString("jumpPower : " + jumpPower, 1300, 220);
		bufferGraphic.drawString("isJumping : " + isJumping, 1300, 240);
		bufferGraphic.drawString("isInAir : " + isInAir, 1300, 260);
		bufferGraphic.drawString("isFalling : " + isFalling, 1300, 280);
		bufferGraphic.drawString("gravity : " + gravity, 1300, 300);

		/* ******************************************************************* */

		// 배경화면 그리기

		/*
		 * 모니터 화면 크기에 맞추기 
		 * Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		 */

		
		
		
		
		
		
		/* ******************************************************************* */

		// 맵 요소 그리기

		/*
		 * 1. 고정 블록
		 * 2. 움직이는 블록, 디딤돌
		 */

		/* 바닥 */

//			bufferGraphic.drawImage(block, 0, 0, null);

		bufferGraphic.drawString("이건 되는데 왜 이미지는 안불러와져", 500, 550);

		
		
		
		
		/* ******************************************************************* */

		// 캐릭터 그리기 모든 캐릭터 좌표 받아서 for문안에 그리기

		/*
		 * 1. 이펙트 - 아이템 획득 효과 
		 * 2. - 목적지점 도착 효과 
		 * 3. 캐릭터 - 멈춤, 이동, 점프 
		 * 4. 캐릭터이름표 
		 * 5. 말풍선
		 */

		/* 블록 */

		if(list_Block != null) {
			for (int i = 0; i < list_Block.size(); i++) {
				list_Block.get(i).render(bufferGraphic);
			}
		}
		
		
		
		/* 캐릭터 */
		if(pushing_Left || pushing_Right) {
			if(direction) { // 오른쪽
				if(runMotion) {
					new Mario(3, marioX, marioY).render(bufferGraphic);
				}else {
					new Mario(5, marioX, marioY).render(bufferGraphic);
				}
			}else {			// 왼쪽
				if(runMotion) {
					new Mario(2, marioX, marioY).render(bufferGraphic);
				}else {
					new Mario(4, marioX, marioY).render(bufferGraphic);
				}
			}
			
		}else if(pushing_Up) {
			if(direction) { // 오른쪽
				new Mario(7, marioX, marioY).render(bufferGraphic);
			}else {			// 왼쪽
				new Mario(6, marioX, marioY).render(bufferGraphic);
			}
			
		}else {
			if(direction) { // 오른쪽
				new Mario(1, marioX, marioY).render(bufferGraphic);
			}else {			// 왼쪽
				new Mario(0, marioX, marioY).render(bufferGraphic);
			}
		}

		
		// 캐릭터가 공중이면 중력의 힘을 받는다.
		// 캐릭터다 공중이다?
	
		checkGround();
		characterControl();
		
		// 너무많이 떨어지면 복귀
		if(marioY > 1500) {
			marioX = 200;
			marioY = 900;
			gravity = 0;
		}
		
		/* ******************************************************************* */
		paint(g);

	} // update();
	
	
	
	
	
	

	/*********************************************************************/

	@Override
	public void paint(Graphics g) {

		g.drawImage(bufferImage, 0, 0, this); // 더블버퍼링 구현

		g.drawString("왜 안되지", 500, 500);

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
	}
	
	/*********************************************************************/

	// 키보드 떼기 이벤트
	@Override
	public void keyReleased(KeyEvent e) {
		
		/* 좌로 이동 */
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			
			pushing_Left = false;
			System.out.println("pushing_Left : " + pushing_Left);
			
			

		}
		/* 우로 이동 */
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			
			pushing_Right = false;
			System.out.println("pushing_Right : " + pushing_Right);
			
			

		}
		/* 점프 */
		else if (e.getKeyCode() == KeyEvent.VK_UP) {
			
			pushing_Up = false;
			System.out.println("pushing_Up : " + pushing_Up);

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
				// 중력
			

				
				/* ******************************************************************* */
				
				//달리기 모션
				runTimer--;
				if(runTimer <= 0 && runMotion) {
					runMotion = false;
					runTimer = 3;
				}else if(runTimer <= 0 && !runMotion){
					runMotion = true;
					runTimer = 3;
				}
				
				
				/* ******************************************************************* */
				
				repaint();
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/*********************************************************************/

}
