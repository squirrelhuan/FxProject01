package event.PushFileEvent;

public class PushFileMain {
	 public static void main(String[] args) {
	        PushFileManager manager = new PushFileManager();
	        manager.addDoorListener(new PushFileListenerImpl());// 给门1增加监听器
	      //  manager.addDoorListener(new DoorListener2());// 给门2增加监听器
	        // 开门
	        manager.onLogsChanged();
	        System.out.println("我已经进来了");
	        double d = 1;
	        // 关门
	        manager.onProcessChanged(d);
	    }
}
