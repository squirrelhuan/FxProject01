package model.CGQ_log;

import java.io.Serializable;

/**
 * logcate 标签模型
 * @author CGQ
 *
 */
public class LogCate_Tag implements Serializable{

	private int id;
	private String filterName;
	private String tag;
	private String applicationName;
	private int level;
	
	public LogCate_Tag(int id,int level,String filterName, String tag, String applicationName) {
		super();
		this.level = level;
		this.id = id;
		this.filterName = filterName;
		this.tag = tag;
		this.applicationName = applicationName;
	}
	
	public LogCate_Tag(int level,String filterName, String tag, String applicationName) {
		super();
		this.level = level;
		this.filterName = filterName;
		this.tag = tag;
		this.applicationName = applicationName;
	}
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
