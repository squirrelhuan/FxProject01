package model.setting;

import java.util.ArrayList;
import java.util.List;

public class SettingMenu {

	private String id;
	private String name;//对外显示名称
	private String title;//内容提示
	private String key;//真实的key
	private String value;//真实的Value
	private int type;//视图类型例如 0简单视图 1复杂视图
	private boolean isDirectory;
	private List<SettingMenu> items;
	private SettingMenu parent;
	private boolean isEnd;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isDirectory() {
		isDirectory = (items==null ?false:true);
		return isDirectory;
	}
	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	public List<SettingMenu> getItems() {
		return items;
	}
	public void setItems(List<SettingMenu> items) {
		this.items = items;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public SettingMenu getParent() {
		return parent;
	}
	public void setParent(SettingMenu parent) {
		this.parent = parent;
	}
	public boolean isEnd() {
		return isEnd;
	}
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	public void addMenus(SettingMenu menu) {
		if(items==null){
			items = new ArrayList<SettingMenu>();
		}
		menu.setParent(this);
		items.add(menu);
	}
	
	
}
