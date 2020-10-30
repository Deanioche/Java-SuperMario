package Mario;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class MarioJComponent extends JComponent{
	
	Image bufferImage;
	Graphics bufferg;

	public MarioJComponent() {
		
		setSize(300,300);
		setVisible(true);

	}

	@Override
	public void paint(Graphics g) {
		
		int x2[] = { 50,150,150,100,85,70,50 };
		int y2[] = { 50,50,100,100,130,100,100 };
		g.drawPolygon( x2, y2, 7 );     //  별그리기
		
	}

}
