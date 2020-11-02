package mario.Main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
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
	
	boolean direction = true; // false = 왼쪽, true = 오른쪽
	boolean runMotion = false; // true = run1, false = run2
	boolean isJumping = false;
	boolean isInAir = false;
	
	public int marioX = 400, marioY = 950;
	
	public int gravity = 10;
	public int jumpPower = 0;
	
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

		setBackground(new Color(200, 150, 255));
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
	
	private void getGravity() {
		
		if(isInAir && jumpPower <= 0) {
				
			marioY += gravity;
			isJumping = false;
				
		}else if (jumpPower > 0) {
			
			marioY -= jumpPower;
			jumpPower--;
			
		}
		
	}
	
	
	
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
		
		/* 이동 */
		if(pushing_Left) {
			
			marioX -= 15;
			
		}else if(pushing_Right) {
			
			marioX += 15;
			
		}else if(pushing_Up) {		
			
			if(!isInAir) {
				jumpPower = 5;
				isJumping = true;
			}
			
		}
		
	}
	
	
	/*********************************************************************/
	
	// 지상, 공중 판별
	
	private void checkGround() {
		
		for(int i = 0; i < list_Block.size(); i++) {
			
			int blockY = list_Block.get(i).getY();
			
			if (Math.abs(blockY - marioY) <= 50) {
				
				System.out.println("지상");
				isInAir = false;
				
			}
			
		}
	}
	
	
	
	
	/*********************************************************************/
	
	//블록 생성
	
	private void createBlock() {
		
		for(int i = 0; i <= 1500; i += 50) { // 높이 950 넓이 1450까지 블록 깔기
			list_Block.add(new Block(0, i, 950));
		}
	}

	
	
	/*********************************************************************/

	@Override
	public void update(Graphics g) {


		bufferImage = createImage(this.getWidth(), this.getHeight());
		bufferGraphic = bufferImage.getGraphics();

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
	
		characterControl();
		checkGround();
		getGravity();
		
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
			
			direction = false;
			pushing_Left = true;
			System.out.println("pushing_Left : " + pushing_Left);

			

		}
		/* 우로 이동 */
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			
			direction = true;
			pushing_Right = true;
			System.out.println("pushing_Right : " + pushing_Right);
			
		}
		/* 점프 */
		else if (e.getKeyCode() == KeyEvent.VK_UP) {
			
			pushing_Up = true;
			System.out.println("pushing_Up : " + pushing_Up);

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
					runTimer = 2;
				}else if(runTimer <= 0 && !runMotion){
					runMotion = true;
					runTimer = 2;
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
