package com.yun9.jupiter.model;

import java.util.List;

public class AuthInfo {
	private User userinfo;
	private Inst instinfo;
	private List<Inst> allinsts;

	public User getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(User userinfo) {
		this.userinfo = userinfo;
	}

	public Inst getInstinfo() {
		return instinfo;
	}

	public void setInstinfo(Inst instinfo) {
		this.instinfo = instinfo;
	}

	public List<Inst> getAllinsts() {
		return allinsts;
	}

	public void setAllinsts(List<Inst> allinsts) {
		this.allinsts = allinsts;
	}

}
