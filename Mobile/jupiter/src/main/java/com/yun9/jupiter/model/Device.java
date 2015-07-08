package com.yun9.jupiter.model;

import java.util.Map;

public class Device implements java.io.Serializable {
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	private String id;
	private String deviceid;
	private String serial;
	private String model;
	private String board;
	private String brand;
	private String fingerprint;
	private String pushRegid;
	private Map<String, String> others;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public Map<String, String> getOthers() {
		return others;
	}

	public void setOthers(Map<String, String> others) {
		this.others = others;
	}

	public String getPushRegid() {
		return pushRegid;
	}

	public void setPushRegid(String pushRegid) {
		this.pushRegid = pushRegid;
	}
}
