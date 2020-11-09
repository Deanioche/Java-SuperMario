package mario.Entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import mario.ImageBox;
import mario.Frame.MarioCanvas;

public class Mario {

	public int x, y; // 좌표
	public int width = 50, height = 50;
	public int picNum = 0;


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
	 * # picNum
	 * [0] = 왼서
	 * [1] = 오서
	 * [2] = 왼달
	 * [3] = 오달
	 * [4] = 왼달2
	 * [5] = 오달2
	 * [6] = 왼점
     * [7] = 오점
	 * [8] = 왼밀
     * [9] = 오밀   
	 */ 

	public void render_Red(Graphics g) {
		
		g.drawImage(ImageBox.mario_red[picNum], x, y, width, height, MarioCanvas.mCanvas);

	}
	public void render_Blue(Graphics g) {
		
		g.drawImage(ImageBox.mario_blue[picNum], x, y, width, height, MarioCanvas.mCanvas);

	}
	public void render_Green(Graphics g) {
		
		g.drawImage(ImageBox.mario_green[picNum], x, y, width, height, MarioCanvas.mCanvas);
		
	}
	public void render_Purple(Graphics g) {
		
		g.drawImage(ImageBox.mario_purple[picNum], x, y, width, height, MarioCanvas.mCanvas);
		
	}
	public void render_Yellow(Graphics g) {
		
		g.drawImage(ImageBox.mario_yellow[picNum], x, y, width, height, MarioCanvas.mCanvas);
		
	}
	

	public Rectangle getbounds() {
		
		return new Rectangle(x, y, width, height);
		
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
