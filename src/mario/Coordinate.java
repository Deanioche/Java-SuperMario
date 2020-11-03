package mario;

public class Coordinate {
	
	int x, y;
	
	/*
	 * 개체의 좌표값을 저장하는 클래스
	 * 모든 이미지 개체의 크기는 50 x 50이므로
	 * 그 중심값은 (x + 25, y + 25)이다.
	 */

	public Coordinate(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

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
	
	

}
