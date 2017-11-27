package model.CGQ_log;

import java.io.Serializable;



/**
 * 数据传输模型
 * 
 * @author Administrator
 *
 */
public class DataModel implements Serializable {

	private int type;

	private Object Data;

	public DataModel(int type, Object Data) {
		this.type = type;
		this.Data = Data;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Object getData() {
		return Data;
	}

	public void setData(Object data) {
		Data = data;
	}

}
