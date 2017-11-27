package event.LogCateEvent;

public class LogCateMain {
	 public static void main(String[] args) {
	        LogCateManager manager = new LogCateManager();
	        manager.addDoorListener(new LogCateListenerImpl());// 给门1增加监听器
	      //  manager.addDoorListener(new DoorListener2());// 给门2增加监听器
	        // 开门
	        manager.onLogsChanged();
	        System.out.println("我已经进来了");
	        // 关门
	        manager.onTagsChanged();
	    }
}
