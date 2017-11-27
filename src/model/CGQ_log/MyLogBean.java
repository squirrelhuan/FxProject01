package model.CGQ_log;

import java.io.Serializable;

public class MyLogBean implements Serializable{

	private int level;
	private String tag;
	private String content;
	private String application;

	public MyLogBean() {
	}

	public MyLogBean(String tag, String content) {
		this.tag = tag;
		this.content = content;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

}
