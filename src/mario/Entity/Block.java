package mario.Entity;

import java.awt.Graphics;

import mario.ImageBox;
import mario.Main.MarioCanvas;

public class Block {

	public int x, y; // 좌표
	public int width = 50, height = 50;
	public int picNum = 0;
	
	
	public Block(int picNum, int x, int y) {
		this.picNum = picNum * 50;
		this.x = x;
		this.y = y;
	}

	public Block(int picNum, int x, int y, int width, int height) {
		this.picNum = picNum * 50;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void render(Graphics g) {

		g.drawImage(ImageBox.block[0], x, y, width, height, MarioCanvas.mCanvas);

	}

}
