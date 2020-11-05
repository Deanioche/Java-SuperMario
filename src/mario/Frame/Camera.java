package mario.Frame;




public class Camera {
	public int x,y;
	
	public Camera(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(int x, int y) {
		
		this.x = -x + 900;
		this.y = -y + 500;
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