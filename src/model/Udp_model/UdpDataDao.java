package model.Udp_model;

import java.io.Serializable;

public class UdpDataDao implements Serializable{

	private int id;
	private enums requestType;
	private UdpMessage uudpMessageDao;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public enums getRequestType() {
		return requestType;
	}
	public void setRequestType(enums requestType) {
		this.requestType = requestType;
	}
	public UdpMessage getUudpMessageDao() {
		return uudpMessageDao;
	}
	public void setUudpMessageDao(UdpMessage uudpMessageDao) {
		this.uudpMessageDao = uudpMessageDao;
	}
	
	
}
