package cn.itcast.chatroom.domain;

import com.google.gson.annotations.Expose;

//登录临时用户类
public class User {
	@Expose
	private String id; //sessionId
	@Expose
	private String nickname; //这次用户登录用的昵称
	@Expose
	private String userId; //用户id


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + nickname  + "]";
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
