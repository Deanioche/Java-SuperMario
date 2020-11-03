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

	/*
	 * # picNum
	 * 0 = 벽돌
	 * 1 = 깨짐
	 * 2 = 피라미드
	 * 3 = 철판
	 * 4 = 물음표
	 */
	
	public void render(Graphics g) {

		g.drawImage(ImageBox.block[0], x, y, width, height, MarioCanvas.mCanvas);

	}
	
	/**************************************************************************************/
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}


}