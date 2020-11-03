package mario.Entity;

import java.awt.Color;
import java.awt.Graphics;

import mario.ImageBox;
import mario.Protocols;
import mario.Frame.MarioCanvas;

public abstract class Entity {
		
	public int picNum;					// 이미지 파일속에서 어떤 이미지조각을 꺼낼지? 0~ 
	public int x, y;					 //	좌표	
	public int width = 50, height = 50; // 기본 크기는 50x50
	
	public boolean isCharacter;		 // 캐릭터인가? false이면  블럭이라는 뜻
	
	public Color color;
	
	public boolean canMove;			 // 움직일수 있나?
	public boolean isMoving;			 // 이동중?
	public boolean isJumping;		     // 점프중?
	
	public Protocols protocol;
	
	public Entity(int picNum, int x, int y, int width, int height, boolean isCharacter,
			int imageNum, Protocols protocol) {
		
		this.picNum = picNum;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isCharacter = isCharacter;
		this.protocol = protocol;
	}		
	
	public abstract void render(Graphics g);
	
		/* 	
		  	g.drawImage(ImageBox.block, x, y, width, height
				, picNum, picNum, picNum + 50, 50, MarioCanvas.mCanvas);
		*/
	
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

	public boolean isCharacter() {
		return isCharacter;
	}

	public void setCharacter(boolean isCharacter) {
		this.isCharacter = isCharacter;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public Protocols getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocols protocol) {
		this.protocol = protocol;
	}
	
	

}
