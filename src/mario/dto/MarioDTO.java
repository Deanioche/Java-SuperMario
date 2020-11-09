package mario.dto;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;

import mario.Server.Protocols;

public class MarioDTO implements Serializable {

	// 필드
	// 프로토콜
	private Protocols protocol;

	// 회원가입시 입력 정보
	private int seq;
	private String clientAccount; // 이메일

	private String password;
	private String passwordCheck;

	private String nickname;
	private String realName;
	private int age;
	private int gender;
	private int infoAgree;
	
	
	/*
	 * 실명, 나이, 계정명, 비밀번호, 비밀번호재확인
	 */
	
	private String[] array_nickname;
	private int[][] array_coordinate;
	

	public String[] getArray_nickname() {
		return array_nickname;
	}

	public void setArray_nickname(String[] array_nickname) {
		this.array_nickname = array_nickname;
	}

	public int[][] getArray_coordinate() {
		return array_coordinate;
	}

	public void setArray_coordinate(int[][] array_coordinate) {
		this.array_coordinate = array_coordinate;
	}

	// 게임 종료 후 받을 정보
	private int score;
	private String goalTime;
	private int playerRank;

	// ServerSocket과 주고받을 정보

	private Color playerColor;
	private int playerCoordinateX;
	private int playerCoordinateY;
	private int playerMotionNum;

	private String chatMessage;
	
	private List<MarioDTO> list_PlayerInfo;

	/*
	 * 플레이어 번호, 점수, 플레이어색상, x좌표, y좌표, 채팅 메세지, 도착시간, DTO를 담는 모든 플레이어 정보리스트
	 */

	// Setter & Getter

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getClientAccount() {
		return clientAccount;
	}

	public void setClientAccount(String clientAccount) {
		this.clientAccount = clientAccount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordCheck() {
		return passwordCheck;
	}

	public void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Color getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(Color playerColor) {
		this.playerColor = playerColor;
	}

	public int getPlayerCoordinateX() {
		return playerCoordinateX;
	}

	public void setPlayerCoordinateX(int playerCoordinateX) {
		this.playerCoordinateX = playerCoordinateX;
	}

	public int getPlayerCoordinateY() {
		return playerCoordinateY;
	}

	public void setPlayerCoordinateY(int playerCoordinateY) {
		this.playerCoordinateY = playerCoordinateY;
	}

	public String getChatMessage() {
		return chatMessage;
	}

	public void setChatMessage(String chatMessage) {
		this.chatMessage = chatMessage;
	}

	public String getGoalTime() {
		return goalTime;
	}

	public void setGoalTime(String goalTime) {
		this.goalTime = goalTime;
	}

	public List<MarioDTO> getList_PlayerInfo() {
		return list_PlayerInfo;
	}

	public void setList_PlayerInfo(List<MarioDTO> list_PlayerInfo) {
		this.list_PlayerInfo = list_PlayerInfo;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getPlayerRank() {
		return playerRank;
	}

	public void setPlayerRank(int playerRank) {
		this.playerRank = playerRank;
	}
	public Protocols getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocols protocol) {
		this.protocol = protocol;
	}
	public int getInfoAgree() {
		return infoAgree;
	}

	public void setInfoAgree(int infoAgree) {
		this.infoAgree = infoAgree;
	}

	public int getPlayerMotionNum() {
		return playerMotionNum;
	}

	public void setPlayerMotionNum(int playerMotionNum) {
		this.playerMotionNum = playerMotionNum;
	}
	
}
