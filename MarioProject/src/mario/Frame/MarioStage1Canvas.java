package mario.Frame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class MarioStage1Canvas extends Canvas {
	private MarioClient marioClient;
	private Image bufferImage;
	private Graphics bufferGraphic;
	private Image img;

	public MarioStage1Canvas(MarioClient marioClient) {
		this.marioClient = marioClient;
		
		img = Toolkit.getDefaultToolkit().getImage("Image/background/MarioBackground2.png");
		
		
		
		//setBackground(new Color(250, 200, 200));

	}

	@Override
	public void update(Graphics g) {
		// bufferImage = createImage(this.getWidth(), this.getHeight());
	//	 bufferGraphic = bufferImage.getGraphics();
		 g.drawImage(img, 0, 0, getWidth(), getHeight(), this);   //이크기만큼 그린다이미지를 
		// 게임 필드 줄긋기
		// marioStage1.drawRect(50, 50, 900, 750); // 게임 필드 범위 x 850 y 700
		// marioStage1.drawRect(45, 45, 910, 760);
		paint(g);
	}

	@Override
	public void paint(Graphics g) {
//	g.drawImage(bufferImage, 0, 0, this);
	g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
//		g.drawString("213213213", 50, 50);

	}
}
