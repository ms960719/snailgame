package Model;

public class Member {
	private String nickname;
	private String password;
	private int score;
	
	public Member(String nickname, String password, int score) {
		super();
		this.nickname = nickname;
		this.password = password;
		this.score = score;
	}
	
	public Member(int score) {
		super();
		this.score = score;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
	
	

}
