package interfaces;

import javafx.scene.Parent;

public abstract class PluginService {

	public Lorder mLorder;
	public PluginService() {
	}
	/**
	 * 开始初始化执行
	 * @param lorder
	 */
	public void onCreate(Lorder lorder){
		mLorder = lorder;

	}

	/**
	 *
	 * @param title
	 * @param contentView
	 * @return
	 */
	public Parent setContentView(String title,Parent contentView){
	  return mLorder.addTabContentView(title,contentView);
	}

	//public abstract void setContentView(Parent contentView);

	/**
	 * 结束执行
	 * @param lorder
	 */
	public void onDestroy(Lorder lorder){

	}

	public static interface PluginType{
		int Service = 0;//单纯的数据计算
		int WithView = 1;//有界面的交互
	}


}