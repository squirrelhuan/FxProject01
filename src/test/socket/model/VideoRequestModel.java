package test.socket.model;

import java.io.Serializable;

/**
 * 视频请求模型
 * @author CGQ
 *
 */
public class VideoRequestModel implements Serializable{

	String from;
	String to;
	String type;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
