package event.Task;

public class TaskMain {
	 public static void main(String[] args) {
	        TaskManager manager = new TaskManager();
	        manager.addDoorListener(new TaskListenerImpl());// 给门1增加监听器
	      //  manager.addDoorListener(new DoorListener2());// 给门2增加监听器
	        // 开门
	       // manager.onLogsChanged();
	        System.out.println("我已经进来了");
	        double d = 1;
	        // 关门
	        manager.onProcessChanged(d);
	    }
}
