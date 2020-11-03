package mario.Frame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;



public class MarioSignupPaint extends JPanel{ 
	private ImageIcon ic_bg_MarioImage = new ImageIcon("image/MARIO.jpg");  //배경아이콘
	private Image im_mario_bg = ic_bg_MarioImage.getImage();                         //이미지에넣기
	


	public  MarioSignupPaint() {
		
		
	
	}
	@Override
	public void paint(Graphics g) {
		
		g.drawImage(im_mario_bg , 0, 0, 1000,1000,this); // 좌표 0번분터시작해야함

		// g.drawImage(im_gom_ic,200,990, 990,800 //화면위치  좌표위치 이상함 도와주셈제발 ㅠ
		// ,50,50,0,0,this);


	}
	
}