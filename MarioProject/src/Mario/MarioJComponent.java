package Mario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MarioJComponent extends JPanel implements KeyListener {

	Image bufferImage, chara;
	Graphics bufferGraphic;

	
	//생성자
	public MarioJComponent() {

		setBackground(new Color(200, 250, 250));
		setVisible(true);
		setBounds(500,500,500,500);
		
		System.out.println("JComponent 실행");
		
		addKeyListener(this);
		

	} // MarioJComponent();

	
	/*********************************************************************/
	
	
	@Override
	public void paintComponent(Graphics g) {
		
		System.out.println("update 작동");

		bufferImage = createImage(this.getWidth(), this.getHeight());
		bufferGraphic = bufferImage.getGraphics();

		/* ******************************************************************* */
		
		// 이미지 불러오기
		
			Image block = Toolkit.getDefaultToolkit().getImage("/Image/blocks.png");
		
		
		
		
		
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
		
		g.drawImage(block, 0, 0, 50, 50, 100, 100, 150, 150, this);

		g.drawString("sdsad", 500, 500);
		
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
		
		
		
		

		/* ******************************************************************* */

	} // update();
	
	
	
	
	/*********************************************************************/

//	@Override
//	public void paint(Graphics g) {
//
//		g.drawImage(bufferImage, 0, 0, this); // 더블버퍼링 구현
//		
//		g.drawString("sdsad", 500, 500);
//
//	} // paint();

	
	
	/*********************************************************************/
	
	// 키보드 이벤트
	
	@Override
	public void keyPressed(KeyEvent e) {

		/* 좌로 이동 */
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {

		} 
		/* 우로 이동 */
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

		}
		/* 점프 */
		else if (e.getKeyCode() == KeyEvent.VK_SPACE) {

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	/*********************************************************************/
	
}