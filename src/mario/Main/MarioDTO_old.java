package mario.Main;

import java.awt.Color;
import java.util.List;

import mario.Coordinate;

public class MarioDTO_old {

	// 필드
	// 프로토콜
	private Protocols protocol;

	// 회원가입시 입력 정보
	private int seq;
	private String nickname;
	private String realName;
	private int age;
	private int gender;
	private String clientAccount; // 이메일
	private String password;
	private String passwordCheck;

	/*
	 * 실명, 나이, 계정명, 비밀번호, 비밀번호재확인
	 */

	// 게임 종료 후 받을 정보
	private int score;
	private String goalTime;
	private int playerRank;

	// ServerSocket과 주고받을 정보

	private Color playerColor;
	private Coordinate playerCoordinate;
	private Coordinate blockCoordinate;

	private String chatMessage;

	private List<MarioDTO_old> list_PlayerInfo;

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

	public Coordinate getPlayerCoordinate() {
		return playerCoordinate;
	}

	public void setPlayerCoordinate(Coordinate playerCoordinate) {
		this.playerCoordinate = playerCoordinate;
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

	public List<MarioDTO_old> getList_PlayerInfo() {
		return list_PlayerInfo;
	}

	public void setList_PlayerInfo(List<MarioDTO_old> list_PlayerInfo) {
		this.list_PlayerInfo = list_PlayerInfo;
	}

	public Protocols getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocols protocol) {
		this.protocol = protocol;
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
	public Coordinate getBlockCoordinate() {
		return blockCoordinate;
	}

	public void setBlockCoordinate(Coordinate blockCoordinate) {
		this.blockCoordinate = blockCoordinate;
	}
}
