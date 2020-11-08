package mario.Frame;

public class BalloonDTO {
	
	private int timer;
	private String nickname;
	private String message;
	
	public BalloonDTO(int timer, String nickname, String message) {
		this.timer = timer;
		this.nickname = nickname;
		this.message = message;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
