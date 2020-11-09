package mario.dto;

import java.io.Serializable;
import java.util.ArrayList;

import mario.Server.Protocols;

public class ArrayDTO implements Serializable{
	
	
	private String[] nickname;
	private int[][] coordinate;
	
	public String[] getNickname() {
		return nickname;
	}
	public void setNickname(String[] nickname) {
		this.nickname = nickname;
	}
	public int[][] getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(int[][] coordinate) {
		this.coordinate = coordinate;
	}

}
