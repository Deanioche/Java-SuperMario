package Mario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;

public class MarioJComponent extends JComponent {

	Image bufferImage, chara;
	Graphics bufferGraphic;

	public MarioJComponent() {

		setVisible(true);

	}
	
	@Override
	public void update(Graphics g) {
		
		bufferImage = createImage(this.getWidth(), this.getHeight());
		bufferGraphic = bufferImage.getGraphics();
		
		
		/* ******************************************************************* */
		
		// 배경화면 그리기
		
		
		
		/* ******************************************************************* */
		
		// 맵 요소 그리기
		
			/* 
			 * 1. 고정 블록
			 * 2. 움직이는 블록, 디딤돌
			 */
		
		
		
		
		/* ******************************************************************* */
		
		// 캐릭터 그리기 모든 캐릭터 좌표 받아서 for문안에 그리기
			
		/*
		 * 1. 이펙트 - 아이템 획득 효과
		 * 2. 		- 목적지점 도착 효과
		 * 3. 캐릭터	- 멈춤, 이동, 점프
		 * 4. 캐릭터이름표
		 * 5. 말풍선
		 */
		
		
		
		/* ******************************************************************* */

		paint(g); // 페인트 메소드 호출
		
	}

	@Override
	public void paint(Graphics g) {
		
		g.drawImage(bufferImage, 0, 0, this); // 더블버퍼링 구현

	} // paint();

}
