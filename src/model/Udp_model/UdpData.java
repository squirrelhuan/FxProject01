package model.Udp_model;

import java.io.Serializable;

public class UdpData implements Serializable{

	private int id;
	private enums.requestType requestType;
	private UdpMessage udpMessage;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UdpMessage getUdpMessage() {
		return udpMessage;
	}
	public void setUdpMessage(UdpMessage udpMessage) {
		this.udpMessage = udpMessage;
	}
	public enums.requestType getRequestType() {
		return requestType;
	}
	public void setRequestType(enums.requestType requestType) {
		this.requestType = requestType;
	}
	
	
}
