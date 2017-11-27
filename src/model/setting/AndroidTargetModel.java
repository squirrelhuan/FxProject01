package model.setting;

public class AndroidTargetModel {
	/*id: 3 or "android-23"
    Name: Android 6.0
    Type: Platform
    API level: 23
    Revision: 3
    Skins: HVGA, QVGA, WQVGA400, WQVGA432, WSVGA, WVGA800 (default), WVGA854, WXGA720, WXGA800, WXGA800-7in
Tag/ABIs : no ABIs.*/

private String id;
	private String name;
	private String type;
	private String apilevel;
	private String revision;
	private String skins;
	private String ABIs;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getApilevel() {
		return apilevel;
	}
	public void setApilevel(String apilevel) {
		this.apilevel = apilevel;
	}
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public String getSkins() {
		return skins;
	}
	public void setSkins(String skins) {
		this.skins = skins;
	}
	public String getABIs() {
		return ABIs;
	}
	public void setABIs(String aBIs) {
		ABIs = aBIs;
	}

	
	
}
