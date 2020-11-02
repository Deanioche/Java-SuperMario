package mario.Entity;

import java.awt.Graphics;

import mario.ImageBox;
import mario.Main.MarioCanvas;

public class Mario {

	public int x, y; // 좌표
	public int width = 50, height = 50;
	public int picNum = 0;
	/*
	 * # picNum
	 * 0 = 벽돌
	 * 1 = 깨짐
	 * 2 = 피라미드
	 * 3 = 철판
	 * 4 = 물음표
	 */

	public Mario(int picNum, int x, int y) {
		this.picNum = picNum;
		this.x = x;
		this.y = y;
	}
	
	public Mario(int picNum, int x, int y, int width, int height) {
		this.picNum = picNum;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/*
	 * 0 =	왼서
	 * 1 =	왼달1
	 * 2 =	왼달2
	 * 3 =	왼점
	 * 4 =	왼밀
	 * 5 =	오서
	 * 6 =	오달1
	 * 7 =	오달2
	 * 8 =	오점
	 * 9 =	오밀
	 */

	public void render(Graphics g) {
		
		if(picNum < 5) {
		g.drawImage(ImageBox.mario_red[picNum][0], x, y, width, height, MarioCanvas.mCanvas);
		}else if (picNum >= 5){
			g.drawImage(ImageBox.mario_red[picNum - 5][1], x, y, width, height, MarioCanvas.mCanvas);
		}

	}

}
