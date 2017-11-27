package model.CGQ_log;

import java.io.Serializable;

public class FileModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1012538266117166766L;
	private String path;
	private String type;
	private int download_type = 1;//0：http,1:udp,2：tcp
	private float size;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getDownload_type() {
		return download_type;
	}
	public void setDownload_type(int download_type) {
		this.download_type = download_type;
	}
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	
}
