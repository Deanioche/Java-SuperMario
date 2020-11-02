package mario.Main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import mario.Entity.Block;
import mario.Entity.Mario;

public class MarioCanvas extends Canvas implements KeyListener, Runnable {

	public static MarioCanvas mCanvas;

	Image bufferImage;
	Graphics bufferGraphic;
	
	
	// 캐릭터 동작
	boolean pushing_Left = false;
	boolean pushing_Right = false;
	boolean pushing_Up = false;
	
	boolean direction = false; // false = 왼쪽, right = 오른쪽
	boolean runMotion = false; // false = run1, false = run2
	
	
	/*
	 * # 캐릭터 동작
	 * 
	 */

	// 생성자
	public MarioCanvas() {

		mCanvas = MarioCanvas.this;

		setBackground(new Color(200, 150, 255));
		setVisible(true);
		setBounds(500, 500, 500, 500);

		System.out.println("캔버스 실행");

		addKeyListener(this);

		// 스레드 생성
		Thread canvasThread = new Thread(this);
		canvasThread.start();

		
		
	} // MarioCanvas();

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

		for (int i = 0; i < 20; i++) {
			new Block(0, i * 50 + 200, 800).render(bufferGraphic);
		}

		
		
		/* 캐릭터 */
		if(pushing_Left) {
			
		}else if(pushing_Right) {
			
		}else if(pushing_Up) {
			
		}else {
			new Mario(0, 100, 50).render(bufferGraphic);
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
			System.out.println("pushing_Left : " + pushing_Left);

		}
		/* 우로 이동 */
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			
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

		while (true) {

			try {
				repaint();

				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/*********************************************************************/

}
