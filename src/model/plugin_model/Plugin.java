package model.plugin_model;

public class Plugin {
	private String name;
	
	private String jar;
	
	private String className;
	
	public Plugin(String name, String jar, String className) {
		super();
		this.name = name;
		this.jar = jar;
		this.className = className;
	}
	public Plugin() {
		super();
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJar() {
		return jar;
	}

	public void setJar(String jar) {
		this.jar = jar;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	// setter、getter省略…
}