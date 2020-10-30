package Mario;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

public class MarioJComponent extends JFrame {
	
	Image bufferImage;
	Graphics bufferg;

	public MarioJComponent() {
		
		setBounds(300, 500, 500, 500);
		setVisible(true);

	}

	@Override
	public void paint(Graphics g) {
		
		int x2[] = { 50,150,150,110,210,310,270,360,245,210 };
		int y2[] = { 50,150,160,225,340,270,340,225,160,160,60 };
		g.drawPolygon( x2, y2, 10 );     //  별그리기
		
	}
	

	public static void main(String[] args) {
		new MarioJComponent();

	}

}
