package Mario;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;

public class MarioJComponent extends JComponent{
	
	Image bufferImage, chara;
	Graphics bufferg;

	public MarioJComponent() {
		
		setSize(300,300);
		setVisible(true);

	}

	@Override
	public void paint(Graphics g) {
		
		chara = Toolkit.getDefaultToolkit().getImage("Image/run(blue).png");
		
		g.drawImage(chara, 100, 100, this);
		
		// 캐릭터 위치
		int locX = 100, locY = 100;
		
		int x2[] = { locX 	  , locX + 80 , locX + 80, locX + 70, locX + 60, locX + 55 , locX};
		int y2[] = { locY - 70, locY - 70 , locY - 20, locY - 20, locY - 10, locY - 20, locY - 20 };
		g.drawPolygon( x2, y2, 7 );     //  별그리기
		
	}

}
